<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><decorator:title/> - ADMIN</title>
	<meta charset="utf-8" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/admin-main.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/flick/jquery-ui-1.9.2.custom.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/chosen.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.fancybox.css" />" />
	<link rel="shortcut icon" href="<c:url value="/resources/admin/img/favico.png" />">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
	<!-- <script src="<c:url value="/resources/admin/js/jquery-1.8.3.min.js" />"></script> -->
	<!-- <script src="<c:url value="/resources/admin/js/jquery-ui-1.9.2.custom.min.js" />"></script> -->
	<decorator:head/>
	<script src="<c:url value="/resources/admin/js/jquery.quicksearch.js" />"></script>
	<script src="<c:url value="/resources/admin/js/chosen.jquery.min.js" />"></script>
	<script src="<c:url value="/resources/public/js/common.js" />"></script>
	<script	src="<c:url value="/resources/admin/js/jquery.fancybox.pack.js" />"></script>
	<script src="<c:url value="/resources/admin/js/scripts.js" />"></script>
	
</head>
<body>
	<!-- HEADER  -->
	<header>
		<a href="<c:url value="/admin/" />" id="logo">CPR <span>ADMIN</span></a>
		
		<span id="logged-user" class="radius">${common.time}  <a href="<c:url value="/admin/user/profile" />">${common.user.firstName} ${common.user.lastName}</a></span>
		<a id="logout" href="<c:url value="/j_logout" />" class="tt" title="Odhlásit se"></a>
	</header>
	
	<!-- MAIN NAV  -->
	<nav>
		<ul>
			<li><a href="<c:url value="/admin/" />" class="home"><spring:message code="menu.dashboard"/></a></li>
			<li><a href="<c:url value="/admin/cpr" />" class="doc"><spring:message code="menu.cpr"/></a></li>
			<li><a href="<c:url value="/admin/articles" />" class="cal"><spring:message code="menu.news"/></a></li>
			<li><a href="<c:url value="/admin/csn" />" class="csn"><spring:message code="menu.csn"/></a></li>
			<li><a href="<c:url value="/admin/webpages" />" class="web"><spring:message code="menu.webpages"/></a></li>
			<li><a href="<c:url value="/admin/users" />" class="user"><spring:message code="menu.users"/></a></li>
			<li><a href="<c:url value="/admin/settings/basic" />" class="sett"><spring:message code="menu.settings"/></a></li>
		</ul>
	</nav>
	
	
 <decorator:body/>
 <div id="status"></div>
 <div id="base" class="hidden"><c:url value="/" /></div>
</body>
</html>