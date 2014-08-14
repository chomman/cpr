<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>QUASAR &amp; nlfnorm.cz login page</title>
	<meta name="description"  content="Přihlasovací stránka do administrace informačního systému CPR" />
	<meta name="robots" content="noindex, nofollow"/>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/admin-login.css" />" />
</head>
<body>
	      <form action="<c:url value="/j_spring_security_check" />" method="post">
              <h1><span>NLF</span> admin</h1>
              <strong>QUASAR &amp; nlfnorm.cz administration login page</strong>
              
              <div class="error <c:if test='${!command.hasErrors && empty loginError}'>hidden</c:if>">
		         <c:if test="${loginError == 1}">
		          Incorrect e-mail address or password.
		         </c:if>
		         <form:errors path="command.*"/>
		       </div>  
		       
              <table class="form-firma">
                <tr class="even">
                  <td class="label"><label for="j_username">Login:</label></td>
                  <td class="filed">
                  	<input 
                  	placeholder="Write you e-mail..." 
                  	type="text" class="title span-6" name="j_username" value="<c:if test="${not empty username}">${username}</c:if>"/></td>
                </tr>
                <tr class="even">
                  <td class="label"><label for="j_password">Password:</label></td> 
                  <td class="filed">
                  	<input placeholder="Write you password..." type="password" class="title span-6" name="j_password" value=""/>
                  </td>
                </tr>
              </table>
              <span class="remeber">
              	<input class="checkbox" type="checkbox" name="_spring_security_remember_me">
              	<label>Keep me logged in</label>
              	<a  class="fp" target="_blank" href="<c:url value="/informacni-portal/zapomenute-heslo" />">Forgot your password?</a> 
              </span>
      		  <input type="submit" value="Log In" class="submit"/>
      </form>
	
</body>
</html>
