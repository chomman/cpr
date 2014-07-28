<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="quasar.long" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/quasar/css/jquery.raty.css" />" />
	<script src="<c:url value="/resources/admin/quasar/js/jquery.raty.js" />"></script>
	<script src="<c:url value="/resources/admin/quasar/js/dashboard.js" />"></script>

</head>
<body>
<div id="wrapper">
	<h1><spring:message code="menu.dashboard"/></h1>
	
		<div class="quasar-dashboard"> 
			<jsp:include page="../dashboard-boxsies.jsp" />
		</div>
				
</div>
</body>
</html>