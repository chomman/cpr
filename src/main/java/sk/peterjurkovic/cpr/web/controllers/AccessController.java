package sk.peterjurkovic.cpr.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;


@Controller
public class AccessController extends SupportAdminController {
	 
	 @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	 public String getAdminLoginPage(HttpServletRequest request,   ModelMap model) {	  
	  
		 if(StringUtils.isNotBlank(request.getParameter(Constants.FAILURE_LOGIN_PARAM_KEY))){
			 model.put(Constants.FAILURE_LOGIN_PARAM_KEY, "1");
		 }
	  
	  final User user = UserUtils.getLoggedUser();
	  if(user != null && user.isAdministrator()){
		  return "redirect:" + Constants.SUCCESS_ROLE_ADMIN_URL;
	  }
	  return Constants.ADMIN_ENTRY_POIN_REDIRECT_URL;
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	  public String getDeniedPage() {
	  return "/blank/access-denied";
	 }
	 
	 
	 @RequestMapping(value = "/online", method = RequestMethod.GET)
	  public String getCsnOnline(ModelMap map, @RequestParam(required = false) String auth) throws ClientProtocolException, IOException {		 	
		 String url = "sgpstandard.cz/editor/files/plasty/web.htm";
		 if(StringUtils.isNotBlank(auth)){
			 CloseableHttpClient httpclient = HttpClientBuilder.create().build();		 
			 HttpHost targetHost = new HttpHost("212.111.0.32", 80, "http");

			 CredentialsProvider credsProvider = new BasicCredentialsProvider();
			 credsProvider.setCredentials(
			         new AuthScope(targetHost.getHostName(), targetHost.getPort()),
			         new UsernamePasswordCredentials("itc", "Int112"));
			
			 // Add AuthCache to the execution context
			 HttpClientContext context = HttpClientContext.create();
			 context.setCredentialsProvider(credsProvider);	
			 HttpGet httpget = new HttpGet("http://www.sgpstandard.cz/editor/files/plasty/web.htm");
			 CloseableHttpResponse response = httpclient.execute(   targetHost, httpget, context);
		     try {
		         HttpEntity entity = response.getEntity();

		     } finally {
		         response.close();
		     }
			
		}
		 map.put("url", url);
		 return "/public/online";
	 }
}
