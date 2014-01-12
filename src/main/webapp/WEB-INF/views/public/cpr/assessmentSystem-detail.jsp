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
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<a title="${model.parentWebpage.title}" href="<c:url value="${model.parentWebpage.code}" />">${model.parentWebpage.name}</a> &raquo;
			<a title="${model.webpage.title}" href="<c:url value="${model.webpage.code}" />">${model.webpage.name}</a> &raquo;
			<span>${model.assessmentSystem.name}</span>
	</div> 

		<div id="main-content">
			 
			 <%--  <jsp:include page="../include/left-panel.jsp" /> --%>
			 
			 <div class="right-panel">
		 		<article>
		 			<h1>${model.assessmentSystem.name}</h1>
		 			${model.assessmentSystem.description}
		 		</article>
			 </div>
			 <div class="clear"></div>
			
		</div>
	</body>
</html>