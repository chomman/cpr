<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.notifiedBody.notifiedBodyCode}</title>
		<meta name="description" content="${model.webpage.description}" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo;
			<a title="${model.webpage.name}" href="<c:url value="${model.webpage.code}" />">${model.webpage.name}</a> &raquo; 
			<span>${model.notifiedBody.name}</span>
	</div> 

		<div id="main-content">
			<article>
				<h1>${model.notifiedBody.name}</h1>

			</article>
			
			
			 
		</div>
	</body>
</html>