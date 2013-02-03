<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><decorator:title/></title>
		<meta charset="utf-8" />
		<link rel="stylesheet" href="<c:url value="/resources/public/css/screen.css" />" />
		<script src="<c:url value="/resources/public/js/jquery-1.9.0.min.js" />"></script>
		<decorator:head/>
	</head>
	<body>
		<div id="wrapper">
			
			<!-- HEADER -->
			<div class="border-top"></div>
			<header class="page-width">
				<a href="" id="logo"></a>
					<strong>
						<span class="is">${commonPublic.settings.systemName}</span>
						<span class="is-name">${commonPublic.settings.headerTitle}</span>
						<span class="itc-name">${commonPublic.settings.ownerName}</span>
					</strong>
			</header>


			<!-- NAVIGATION -->
			<nav>
				<ul class="page-width">
					<c:forEach items="${commonPublic.mainMenu}" var="webpage">
						<li><a href="<c:url value="${webpage.code}" />">${webpage.name}</a></li>
					</c:forEach>
				</ul>
			</nav>
			
			<!-- CONTENT -->
			<div id="content">
				
				<decorator:body />
				
			</div>
			 <div class="push"></div>	
		</div>

		
		<!-- FOOTER -->
		<footer>
			<div id="footer" class="page-width">
				<a href="http://www.itczlin.cz/cz/" title="ITC - Institut pro testování a certifikace" class="itc-logo"></a>
				<p class="itc-name">
					${commonPublic.settings.systemName}<br />
					<a href="http://www.itczlin.cz/cz/" title="ITC - Institut pro testování a certifikace">${commonPublic.settings.ownerName}</a>
				</p>
				<a class="admin" href="<c:url value="/admin/login" />" title="Přihlášení do administrace systému" ><spring:message code="admin" /></a>
			</div>
		</footer>
		<c:if test="${not empty commonPublic.settings.googleAnalyticsTrackingCode}">
			<script>
			${commonPublic.settings.googleAnalyticsTrackingCode}
			</script>
		</c:if> 
	</body>
</html>