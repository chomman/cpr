<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>CPR ADMIN</title>
 <link rel="stylesheet" href="<c:url value="/resources/admin/css/admin-main.css" />" />
</head>
<body>
<header>
	<a href="/admin/" title="CPR ADMIN" id="logo">CPR <span>ADMIN</span></a>
	
	<span id="logged-user" class="radius">Peter Jurkovic</span>
	<a id="logout" href="/admin/logOut" title="Odhlásit"></a>
</header>
<nav>
	<ul>
		<li><a href="" class="home">Ovládací panel</a></li>
		<li><a href="" class="doc">Správa CPR</a></li>
		<li><a href="" class="cal">Správa aktualit</a></li>
		<li><a href="" class="user">Správa uživateľov</a></li>
	</ul>
</nav>

admin
</body>
</html>