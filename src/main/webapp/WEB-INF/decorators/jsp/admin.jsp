<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title> <decorator:title/> - ADMIN</title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/admin-main.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/flick/jquery-ui-1.9.2.custom.css" />" />
	<link rel="shortcut icon" href="<c:url value="/resources/admin/img/favico.png" />" type="image/x-ico; charset=binary">
	<script src="<c:url value="/resources/admin/js/jquery-1.8.3.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/jquery-ui-1.9.2.custom.min.js" />"></script>
	<script src="<c:url value="/resources/admin/tiny_mce/tiny_mce.js" />"></script>
	<script src="<c:url value="/resources/admin/js/scripts.js" />"></script>
	<decorator:head/>
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
			<li><a href="" class="home">Ovládací panel</a></li>
			<li><a href="" class="doc">Správa CPR</a></li>
			<li><a href="" class="cal">Správa aktualit</a></li>
			<li><a href="" class="user">Správa uživateľov</a></li>
		</ul>
	</nav>
	
	
 <decorator:body/>
</body>
</html>