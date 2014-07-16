<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><decorator:title/> - ADMIN</title>
	<meta charset="utf-8" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/admin-main.css" />" />
	<link rel="shortcut icon" href="<c:url value="/resources/admin/img/favico.png" />">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<c:if test="${empty model.disableScripts}">
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/flick/jquery-ui-1.9.2.custom.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/chosen.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.fancybox.css" />" />
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
	<script src="<c:url value="/resources/admin/js/jquery.quicksearch.js" />"></script>
	<script src="<c:url value="/resources/admin/js/chosen.jquery.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/jquery.localize.js" />"></script>
	<script src="<c:url value="/resources/admin/js/${common.locale}.messages.js" />"></script>
	<script src="<c:url value="/resources/public/js/common.js" />"></script>
	<script	src="<c:url value="/resources/admin/js/jquery.fancybox.pack.js" />"></script>
	<script src="<c:url value="/resources/admin/js/scripts.js" />"></script>
	</c:if>
	<decorator:head/>
</head>
<body>
	<!-- HEADER  -->
	<header>
		<c:set value="${common.user.auditor and not common.user.quasarAdmin}" var="auditorView" />
		<c:if test="${auditorView}">
			<a href="<c:url value="/admin/" />" id="logo"> ADMIN <span><spring:message code="quasar.long" /></span></a> 
		</c:if>
		<c:if test="${not auditorView}">
			<a href="<c:url value="/admin/" />" id="logo">NLF <span>ADMIN</span></a>
		</c:if>
		
		
		<span id="logged-user" class="radius">${common.time}  <a href="<c:url value="/admin/user/profile" />">${common.user.firstName} ${common.user.lastName}</a></span>
		<a id="logout" href="<c:url value="/j_logout" />" class="tt" title="Odhlásit se"></a>
	</header>
	
	<!-- MAIN NAV  -->
	<nav>
		<ul>			
			<c:if test="${auditorView}">
				<li><a href="<c:url value="/admin/quasar/dashboard" />" class="home"><spring:message code="menu.dashboard"/></a></li>
				<li><a href="<c:url value="/admin/quasar/profile/1" />" class="user"><spring:message code="auditor.profile"/></a></li>
				<li><a href="<c:url value="/admin/quasar/audit-logs" />" class="doc"><spring:message code="auditLogs"/></a></li>
				<li><a href="<c:url value="/admin/quasar/dossier-reports" />" class="doc"><spring:message code="documentationLogs"/></a></li>
				<li><a href="<c:url value="/admin/quasar/training-logs" />" class="doc"><spring:message code="trainingLogs"/></a></li>
				
			</c:if>
			<c:if test="${not auditorView}">
				<li><a href="<c:url value="/admin/" />" class="home"><spring:message code="menu.dashboard"/></a></li>
				<c:if test="${common.user.portalAdmin}">
					<li><a href="<c:url value="/admin/cpr" />" class="doc"><spring:message code="menu.cpr"/></a></li>
					<li><a href="<c:url value="/admin/csn" />" class="csn"><spring:message code="menu.csn"/></a></li>
					<li><a href="<c:url value="/admin/webpages" />" class="web"><spring:message code="menu.webpages"/></a></li>
					<li><a href="<c:url value="/admin/portal/orders" />" class="portal"><spring:message code="menu.portal"/></a></li>
				</c:if>
				<c:if test="${common.user.quasarAdmin}">
					<li><a href="<c:url value="/admin/quasar/dashboard" />" class="quasar">QUASAR</a></li>
				</c:if>
				<c:if test="${common.user.superAdministrator}">
					<li><a href="<c:url value="/admin/users" />" class="user"><spring:message code="menu.users"/></a></li>
					<li><a href="<c:url value="/admin/settings/basic" />" class="sett"><spring:message code="menu.settings"/></a></li>
				</c:if>
			</c:if>
		</ul>
	</nav>
	
	
 <decorator:body/>
 <div id="status"></div>
 <div id="base" class="hidden"><c:url value="/" /></div>
</body>
</html>