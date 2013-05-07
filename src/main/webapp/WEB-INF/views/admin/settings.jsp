<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="settings" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/settings-nav.jsp" />
		
	</div>	
	<div id="right">
	
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <span><spring:message code="settings" /></span>
		</div>
		
		
		<h1><spring:message code="settings" /></h1>

		<div id="content">
			
			<ul>
				<li>
					<a href="<c:url value="/admin/settings/basic"  />">
						<spring:message code="settings.basic" />
					</a>
				</li>
				<li>
					<a href="<c:url value="/admin/settings/texts"  />">
						<spring:message code="settings.texts" />
					</a>
				</li>
				<li>
					<a href="<c:url value="/admin/settings/countries"  />">
						<spring:message code="settings.countries" />
					</a>
				</li>
				<c:if test="${isLoggedWebmaster}">
				<li>
					<a href="<c:url value="/admin/settings/exceptions"  />">
						<spring:message code="settings.exceptions" />
					</a>
				</li>
				</c:if>
			</ul>

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>