<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.assessmentSystem.name}</title>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
		<a:url href="/"><spring:message code="homepage" /></a:url> &raquo; 
		<a:url href="${model.parentWebpage.code}">
				<a:localizedValue object="${model.parentWebpage}" fieldName="name" />
		</a:url> &raquo;
		<a:url href="${model.webpage.code}">
			<a:localizedValue object="${model.webpage}" fieldName="name" />
		</a:url> &raquo;
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