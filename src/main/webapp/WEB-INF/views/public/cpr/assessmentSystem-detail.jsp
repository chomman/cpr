<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.assessmentSystem.name}</title>
		<meta name="description" content="${model.webpage.description}" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
		<a:url href="/"><spring:message code="homepage" /></a:url> &raquo; 
		<a:url title="${model.parentWebpage.title}" href="${model.parentWebpage.code}">${model.parentWebpage.name}</a:url> &raquo;
		<a:url title="${model.webpage.title}" href="${model.webpage.code}">${model.webpage.name}</a:url> &raquo;
		<span>${model.assessmentSystem.name}</span>
	</div> 

		<div id="main-content">
	 		<article class="full-width">
	 			<h1>${model.assessmentSystem.name}</h1>
	 			${model.assessmentSystem.description}
	 		</article>
		</div>
	</body>
</html>