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
				<a href="<a:url href="${webpageModel.profileUrl}" linkOnly="true"  />">
					<spring:message code="portaluser.profile.nav.personalInfo" />
				</a>
			</li>
			<li class="pj-parent ${profileTab == 4 ? 'pj-nav-active' : ''}" >
				<a href="<a:url href="${webpageModel.profileUrl}/products" linkOnly="true"  />">
					<spring:message code="portaluser.profile.nav.activatedProducts" />
				</a>
			</li>
			<li class="pj-parent ${profileTab == 2 ? 'pj-nav-active' : ''}" > 
				<a href="<a:url href="${webpageModel.profileUrl}/orders" linkOnly="true"  />">
					<spring:message code="portaluser.profile.nav.orders" />
				</a>
			</li>
			<li class="pj-parent ${profileTab == 5 ? 'pj-nav-active' : ''}" >
				<a href="<a:url href="${webpageModel.profileUrl}/new-order" linkOnly="true"  />">
					<spring:message code="portaluser.profile.nav.newOrder" />
				</a>
			</li>
			<li class="pj-parent ${profileTab == 6 ? 'pj-nav-active' : ''}" >
				<a href="<a:url href="${webpageModel.profileUrl}/password" linkOnly="true"  />">
					<spring:message code="portaluser.profile.nav.changePass" />
				</a>
			</li>
			<li class="pj-parent">
				<a href="<c:url value="/j_logout?${webpageModel.portalParam}=1" />">
					<spring:message code="portaluser.profile.nav.logout" />
				</a>
			</li>
			
		</ul>
		
		<c:if test="${not empty webpageModel.loggedUser and not empty webpageModel.loggedUser.registrationValidity}">
			<p class="status info no-ico">
				<spring:message code="portaluser.profile.registrationValidity" />:
				<strong>
					<joda:format value="${webpageModel.loggedUser.registrationValidity}" pattern="dd.MM.yyyy"/>
				</strong>
			</p>
		</c:if>
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
		<c:if test="${profileTab == 4}">
			<jsp:include page="profile-products.jsp" />
		</c:if>
		<c:if test="${profileTab == 5}">
			<jsp:include page="profile-neworder.jsp" />
		</c:if>
		<c:if test="${profileTab == 6}">
			<jsp:include page="profile-password.jsp" />
		</c:if>
	
		<div id="status"></div>
	</div>
</body>
</html>