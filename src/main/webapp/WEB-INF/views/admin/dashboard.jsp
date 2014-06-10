<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title><spring:message code="menu.dashboard" /></title>
</head>
<body>
	
	<h1><spring:message code="dashboard.h1"/></h1>
	
	<div id="dashboard">
		
		<div class="hbox">
			<h2><spring:message code="menu.dashboard" /></h2>
		</div>
		<ul id="speed-nav">
			<c:set var="portalAdmin" value="${common.user.portalAdmin}" />
			<c:if test="${portalAdmin}">
			<li>
				<a href="<c:url value="/admin/cpr" />" class="ico-cpr tt" title="<spring:message code="dashboard.cpr.title"/>">
					<spring:message code="dashboard.cpr"/>
				</a>
			</li>
			<li>
				<a href="<c:url value="/admin/csn" />" class="ico-csn tt" title="<spring:message code="dashboard.csn.title"/>">
					<spring:message code="dashboard.csn"/>
				</a>
			</li>
			<li>
				<a href="<c:url value="/admin/webpages" />" class="ico-web tt" title="<spring:message code="menu.webpages"/>" >
					<spring:message code="menu.webpages"/>
				</a>
			</li>
			<li>
				<a href="<c:url value="/admin/portal/orders" />" class="ico-portal tt" title="<spring:message code="dashboard.portal.title"/>">
					<spring:message code="menu.portal"/>
				</a>
			</li>
			</c:if>
			<c:if test="${common.user.superAdministrator}">
			<li>
				<a href="<c:url value="/admin/users" />" class="ico-user tt" title="<spring:message code="menu.users"/>" >
					<spring:message code="dashboard.users"/>
				</a>
			</li>
			<li>
				<a href="<c:url value="/admin/settings/basic" />" class="ico-sett tt" title="<spring:message code="dashboard.settings.title"/>" >
					<spring:message code="dashboard.settings"/>
				</a>
			</li>
			</c:if>
			<li>
				<a href="<c:url value="/admin/help" />" class="ico-info tt" title="<spring:message code="dashboard.help.title"/>">
					<spring:message code="dashboard.help"/>
				</a>
			</li>
		</ul>

		<div class="hbox">
			<h2><spring:message code="homepage.newest.standards" /></h2>
		</div>

		<div class="dashboard-standards">
	 			<c:if test="${not empty model.standards}">
		 			<c:forEach items="${model.standards}" var="standard">
						<div class="norm">
			 				<span class="edit"><joda:format value="${standard.changed}" pattern="${common.dateTimeFormat}"/></span>
			 				<c:if test="${portalAdmin}">
			 					<a href="<c:url value="/admin/cpr/standard/edit/${standard.id}" />" class="blue-color link">${standard.standardId}</a>
			 				</c:if>
			 				<c:if test="${not portalAdmin}">
			 					<a style="display:inline-block;width:200px;" class="blue-color link">${standard.standardId}</a>
			 				</c:if>
			 				<span class="sname">${standard.czechName}</span>
		 				</div>	
		 			</c:forEach>
	 			</c:if>
	  	</div>
		<p class="version"><strong><spring:message code="version" />:</strong> ${model.version} </p>	
		<div class="clear"></div>
	</div>
	
</body>
</html>