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
			<li>
				<a href="<c:url value="/admin/cpr" />" class="ico-cpr tt" title="<spring:message code="dashboard.cpr.title"/>">
					<spring:message code="dashboard.cpr"/>
				</a>
			</li>
			<li>
				<a href="<c:url value="/admin/articles" />" class="ico-cal tt" title="<spring:message code="dashboard.news.title"/>">
					<spring:message code="dashboard.news"/>
				</a>
			</li>
			<li>
				<a href="<c:url value="/admin/users" />" class="ico-user tt" title="<spring:message code="menu.users"/>" >
					<spring:message code="dashboard.users"/>
				</a>
			</li>
			<li>
				<a href="<c:url value="/admin/settings" />" class="ico-sett tt" title="<spring:message code="dashboard.settings.title"/>" >
					<spring:message code="dashboard.settings"/>
				</a>
			</li>
			<li>
				<a href="<c:url value="/admin/help" />" class="ico-info tt" title="<spring:message code="dashboard.help.title"/>">
					<spring:message code="dashboard.help"/>
				</a>
			</li>
		</ul>

		<div class="hbox">
			<h2><spring:message code="dashboard.stats"/></h2>
		</div>

		<table class="data">
			<tr>
				<td>Počet evidovaných norem: </td>
				<td>125</td>
			</tr>
			
			<tr>
				<td>Počet evidovaných kategórií CPR: </td>
				<td>35</td>
			</tr>
			
			<tr>
				<td>Počet registrovaných uživateľov: </td>
				<td>89</td>
			</tr>
			
		</table>
		<div class="clear"></div>
	</div>
	
</body>
</html>