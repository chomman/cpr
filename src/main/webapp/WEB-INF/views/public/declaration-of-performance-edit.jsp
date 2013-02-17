<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="dop" /></title>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span><spring:message code="dop" /></span>
	</div> 

		<div id="main-content">
			<h1>Editace <spring:message code="dop" /></h1>
			<c:url value="/dop/edit" var="formUrl"/>	
			<jsp:include page="include/dop-form.jsp" />	
			 
		</div>
	</body>
</html>