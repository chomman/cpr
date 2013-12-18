<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
		<meta name="description" content="${model.webpage.description}" />
		<script src="<c:url value="/resources/public/js/ehn.autocomplete.js" />"></script>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<a title="${model.parentWebpage.title}" href="<c:url value="${model.parentWebpage.code}" />">${model.parentWebpage.name}</a> &raquo;
			<span>${model.webpage.name}</span>
	</div> 

		<div id="main-content">
			<c:if test="${model.showStandardGroups}">
				<jsp:include page="../contents/standard-groups.jsp" />
			</c:if>
			<c:if test="${not model.showStandardGroups}">
				<jsp:include page="../contents/standard-list.jsp" />
			</c:if> 
		</div>
	</body>
</html>


