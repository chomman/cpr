<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><decorator:title/></title>
		<meta charset="utf-8" />
		<c:if test="${not empty model.webpage.description}">
		<meta name="description" content="${model.webpage.description}">		
		</c:if>
		<link rel="stylesheet" href="<c:url value="/resources/admin/css/flick/jquery-ui-1.9.2.custom.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/public/css/screen.css" />" />
		<!--[if lt IE 9]>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]--> 
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
		<script src="<c:url value="/resources/public/js/common.js" />"></script>
		<script src="<c:url value="/resources/public/js/scripts.js" />"></script>
		<decorator:head/>
		<c:if test="${not empty commonPublic.settings.googleAnalyticsTrackingCode}">
			<script>
			${commonPublic.settings.googleAnalyticsTrackingCode}
			</script>
		</c:if> 
	</head>
	<body>
		<div id="wrapper">
			
			<!-- HEADER -->
			<div class="border-top"></div>
			<header class="page-width">
					<a:url href="/" id="logo" ></a:url>
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
						<li>
							<a title="${webpage.title}" <c:if test="${model.tab == webpage.id or webpage.id == model.webpage.id}" >class="curr"</c:if> 
							href="<a:url href="${webpage.code}" linkOnly="true" />">${webpage.name}
							</a>
						</li>
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
				<a href="http://www.itczlin.cz/cz/" title="${commonPublic.settings.ownerName}" class="itc-logo"></a>
				<p class="itc-name">
					${commonPublic.settings.systemName}<br />
					<a href="http://www.itczlin.cz/cz/" title="${commonPublic.settings.ownerName}">${commonPublic.settings.ownerName}</a>
				</p>
				<a class="admin" href="<c:url value="/admin/login" />" title="Přihlášení do administrace systému" ><spring:message code="admin" /></a>
			</div>
		</footer>
		 <div id="base" class="hidden"><c:url value="/" /></div>
	</body>
</html>