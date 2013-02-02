<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<strong class="nav-head"><spring:message code="menu.nav" /></strong>
<ul>
	<li>
		<a 
		<c:if test="${model.tab == 1}">class="active"</c:if>
		href="<c:url value="/admin/settings/basic"  />">
			<spring:message code="settings.basic" />
		</a>
	</li>
	
	<li>
		<a 
		<c:if test="${model.tab == 3}">class="active"</c:if>
		href="<c:url value="/admin/settings/countries"  />">
			<spring:message code="settings.countries" />
		</a>
	</li>
</ul>