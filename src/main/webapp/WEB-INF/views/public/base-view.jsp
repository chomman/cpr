<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>
			<a:localizedValue object="${model.webpage}" fieldName="title" />
		</title>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span><a:localizedValue object="${model.webpage}" fieldName="name" /></span>
	</div> 

		<div id="main-content">
			<article>
			<a:localizedValue object="${model.webpage}" fieldName="topText" />
			<a:localizedValue object="${model.webpage}" fieldName="bottomText" />
			</article>
		</div>
	</body>
</html>


