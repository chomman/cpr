<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
		<meta name="description" content="${model.webpage.description}" />
		<script src="<c:url value="/resources/public/js/tag.autocomplete.js" />"></script>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span>${model.webpage.name}</span>
	</div> 

		<div id="main-content">
			<article>
				${model.webpage.topText}
			</article>
			<c:if test="${model.webpage.webpageContent.id == 8}">
				
				<c:url value="/dop/form" var="formUrl"/>	
				<form:form commandName="declarationOfPerformace" method="post" action="${formUrl}" >
					
					
				</form:form>
			
			
			</c:if>
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>