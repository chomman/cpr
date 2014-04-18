<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="error.pageNotFound" /></title>
		<style type="text/css">
			#footer ,header, footer, nav, aside, article, section{display:none !important;}
			body{background:none;}
		</style>
	</head>
<body>
		<div id="error-box">
			<div class="msg alert">
				<h2><spring:message code="error.pageNotFound" /></h2>	
				<p><spring:message code="error.pageNotFound.descr" /></p>
				<a href="<c:url value="/" />"><spring:message code="error.goToMainPage" />  &raquo;</a>
			</div>
		
		</div>
	
</body>
</html>