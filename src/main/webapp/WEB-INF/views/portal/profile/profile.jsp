<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
	<title>${webpageModel.loggedUser.firstName} ${webpageModel.loggedUser.lastName} - <spring:message code="portaluser.profile" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/portal/css/profile.css" />" />
</head>
<body>
	<h3 class="pj-head">
	${webpageModel.loggedUser.firstName} ${webpageModel.loggedUser.lastName} - 
	<spring:message code="portaluser.profile" /></h3>
	
	<div class="profile-nav pj-profile-nav">
		<ul class="pj-aside-nav ">
			<li class="pj-parent ${profileTab == 1 ? 'pj-nav-active' : ''}" >
				<a href="<a:url href="/${webpageModel.profileUrl}" linkOnly="true"  />">
					<spring:message code="portaluser.profile.nav.personalInfo" />
				</a>
			</li>
			<li class="pj-parent ${profileTab == 2 ? 'pj-nav-active' : ''}" > 
				<a href="<a:url href="/${webpageModel.profileUrl}/orders" linkOnly="true"  />">
					<spring:message code="portaluser.profile.nav.orders" />
				</a>
			</li>
			<li class="pj-parent">
				<a href="">
					<spring:message code="portaluser.profile.nav.newOrder" />
				</a>
			</li>
			<li class="pj-parent">
				<a href="">
					<spring:message code="portaluser.profile.nav.changePass" />
				</a>
			</li>
			<li class="pj-parent">
				<a href="<c:url value="/j_logout?${webpageModel.portalParam}=1" />">
					<spring:message code="portaluser.profile.nav.logout" />
				</a>
			</li>
			
		</ul>
	</div>
	<div class="profile-content">
	
		<c:if test="${profileTab == 1}">
			<jsp:include page="profile-edit.jsp" />
		</c:if>
		<c:if test="${profileTab == 2}">
			<jsp:include page="profile-orders.jsp" />
		</c:if>
		<c:if test="${profileTab == 3}">
			<jsp:include page="profile-order-view.jsp" />
		</c:if>
	
		<div id="status"></div>
	</div>
</body>
</html>