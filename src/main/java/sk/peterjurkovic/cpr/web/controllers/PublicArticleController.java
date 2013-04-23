package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.Article;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.ArticleService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;



@Controller
public class PublicArticleController {
	
	public static final String ARTICLE_URL = "/aktuality";
	
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private ArticleService articleService;
	
	/**
	 * Zobrazi vereju sekciu s obsahem aktualit
	 * 
	 * @param modelmap
	 * @param request
	 * @return
	 * @throws PageNotFoundEception
	 */
	@RequestMapping(ARTICLE_URL)
	public String showPublicArticles(ModelMap modelmap, HttpServletRequest request) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode(ARTICLE_URL);
		
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<Article> articles = articleService.getArticlePageForPublic(currentPage);
		model.put("paginationLinks", paginationLinks);
		model.put("articles", articles);
		model.put("webpage", webpage);
		model.put("articleUrl", ARTICLE_URL);
		model.put("tab", webpage.getId());
		modelmap.put("model", model);
		return "/public/articles";
	}
	
	/**
	 * Zobrazi detail aktuality
	 * 
	 * @param String kod aktuality
	 * @param Modelmap Model
	 * @return String view
	 * @throws PageNotFoundEception
	 */
	@RequestMapping(ARTICLE_URL + "/{articleCode}")
	public String showArticleDetail(@PathVariable String articleCode, ModelMap modelmap) throws PageNotFoundEception{
		
		Webpage webpage = webpageService.getWebpageByCode(ARTICLE_URL);
		Article article = articleService.getArticleByCode(articleCode);
		if(webpage == null || article == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", webpage.getId());
		model.put("article", article);
		model.put("articleUrl", ARTICLE_URL);
		model.put("webpage", webpage);
		modelmap.put("model", model);
		return "/public/article-detail";
	}
	
	
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params, int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/aktuality");
		paginger.setPageSize(Constants.PUBLIC_PAGINATION_PAGE_SIZE);
		paginger.setCurrentPage(currentPage);
		Long count = articleService.getCountOfArticlesForPublic();
		paginger.setRowCount( count.intValue() );
		return paginger.getPageLinks(); 
	}
	
}
