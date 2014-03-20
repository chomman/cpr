<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><decorator:title/></title>
		<meta charset="utf-8" />
		<link rel="stylesheet" href="<c:url value="/resources/admin/css/flick/jquery-ui-1.9.2.custom.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/public/css/screen.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/public/css/common.css" />" />
		<c:if test="${isPreview}">
		<link rel="stylesheet" href="<c:url value="/resources/public/css/preview.css" />" />
		</c:if>
		<!--[if lt IE 9]>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]--> 
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
		<script src="<c:url value="/resources/public/js/common.js" />"></script>
		<script src="<c:url value="/resources/public/js/jquery.lang.switcher.js" />"></script>
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
						<span class="is"><a:localizedValue object="${commonPublic.settings}" fieldName="systemName" /></span>
						<span class="is-name"><a:localizedValue object="${commonPublic.settings}" fieldName="headerTitle" /></span>
						<span class="itc-name"><a:localizedValue object="${commonPublic.settings}" fieldName="ownerName" /></span>
					</strong>
						<div id="lswitcher-wrapp">
							<div id="lswitcher">
								<form action="#">
									<select id="polyglot-language-options">
										<option <c:if test="${commonPublic.locale == 'cs'}">selected="selected"</c:if> id="lcs" value="cs"><spring:message code="cs" /></option>
										<option <c:if test="${commonPublic.locale == 'en'}">selected="selected"</c:if> id="len" value="en"><spring:message code="en" /></option>
									</select>
								</form>
							</div>
						</div>
			</header>


			<!-- NAVIGATION -->
			<nav>
				<ul class="page-width">
					<c:forEach items="${commonPublic.mainMenu}" var="webpage">
						<li>
							<a title="<a:localizedValue object="${webpage}" fieldName="title" />" <c:if test="${model.tab == webpage.id or webpage.id == model.webpage.id}" >class="curr"</c:if> 
							href="<a:url href="${webpage.code}" linkOnly="true" />">
							<a:localizedValue object="${webpage}" fieldName="name" />
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
				<a href="http://www.itczlin.cz/cz/" title="<a:localizedValue object="${commonPublic.settings}" fieldName="ownerName" />" class="itc-logo"></a>
				<p class="itc-name">
					<a:localizedValue object="${commonPublic.settings}" fieldName="systemName" /><br />
					<a href="http://www.itczlin.cz/cz/" >
						<a:localizedValue object="${commonPublic.settings}" fieldName="ownerName" />
					</a>
				</p>
				<a class="admin" href="<c:url value="/admin/login" />" title="Přihlášení do administrace systému" ><spring:message code="admin" /></a>
			</div>
		</footer>
		 <div id="base" class="hidden"><c:url value="/" /></div>
		 <div id="locale" class="hidden">${commonPublic.locale}</div>
	</body>
</html>