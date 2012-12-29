<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title> <decorator:title/> - ADMIN</title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/admin-main.css" />" />
	<decorator:head/>
</head>
<body>
	<header>
	<a href="/admin/" title="CPR ADMIN" id="logo">CPR <span>ADMIN</span></a>
	
	<span id="logged-user" class="radius">${time}  <a href="">${common.user.firstName} ${common.user.lastName}</a></span>
	<a id="logout" href="<c:url value="/j_logout" />" title="Odhlásit"></a>
</header>
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