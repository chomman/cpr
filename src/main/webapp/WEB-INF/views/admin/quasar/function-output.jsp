<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="auditors" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
	<script src="<c:url value="/resources/admin/js/scripts.quasar.js" />"></script>
</head>
<body>
	<div id="wrapper">
		<div class="pj-webpages">
			
			${model.html}
			
		</div>
	</div>
</body>
</html>