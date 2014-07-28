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
		<div id="left">
			<jsp:include page="../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <span><spring:message code="quasar.long" /></span>
			</div>
			<h1><spring:message code="quasar.long" /></h1>
			<div id="content">
								
				<div class="widget-bx" id="audit-logs" data-url="1">
					<h5>Latest Audit logs</h5>
					<div class="widget-items loading"></div>
				</div>	
				
				<div class="widget-bx" id="dossier-reports" data-url="2">
					<h5>Latest Dossier reports</h5>
					<div class="widget-items loading"></div>
				</div>
				
				<div class="widget-bx" id="training-logs" data-url="3">
					<h5>Latest Training logs</h5>
					<div class="widget-items loading"></div>
				</div>	
				
	
			</div>	
		</div>
		<div class="clear"></div>	
	</div>
</body>
</html>