<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>ADMIN - <decorator:title/></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/admin-main.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/flick/jquery-ui-1.9.2.custom.css" />" />
	<link rel="shortcut icon" href="<c:url value="/resources/admin/img/favico.png" />">
	<script src="<c:url value="/resources/admin/js/jquery-1.8.3.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/jquery-ui-1.9.2.custom.min.js" />"></script>
	<decorator:head/>
	<script src="<c:url value="/resources/admin/js/jquery.quicksearch.js" />"></script>
	<script src="<c:url value="/resources/admin/js/scripts.js" />"></script>
	
</head>
<body>
	<!-- HEADER  -->
	<header>
		<a href="<c:url value="/admin/" />" id="logo">CPR <span>ADMIN</span></a>
		
		<span id="logged-user" class="radius">${time}  <a href="">${common.user.firstName} ${common.user.lastName}</a></span>
		<a id="logout" href="<c:url value="/j_logout" />" class="tt" title="Odhlásit se"></a>
	</header>
	
	<!-- MAIN NAV  -->
	<nav>
		<ul>
			<li><a href="<c:url value="/admin/" />" class="home"><spring:message code="menu.dashboard"/></a></li>
			<li><a href="<c:url value="/admin/cpr" />" class="doc"><spring:message code="menu.cpr"/></a></li>
			<li><a href="<c:url value="/admin/articles" />" class="cal"><spring:message code="menu.news"/></a></li>
			<li><a href="<c:url value="/admin/users" />" class="user"><spring:message code="menu.users"/></a></li>
		</ul>
	</nav>
	
	
 <decorator:body/>
 <div id="status"></div>
 <div id="base" class="hidden"><c:url value="/" /></div>
</body>
</html>