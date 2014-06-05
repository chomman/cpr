<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<strong class="nav-head"><spring:message code="menu.nav" /></strong>
<ul>
	<li>
		<a 
		<c:if test="${model.tab == 1}">class="active"</c:if>
		 href="<c:url value="/admin/portal/orders" />" >
			<spring:message code="admin.portal.orders" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 2}">class="active"</c:if>
		href="<c:url value="/admin/portal/users" />" >
			<spring:message code="admin.portal.users" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 3}">class="active"</c:if>
		href="<c:url value="/admin/portal/products" />" >
			<spring:message code="admin.portal.services" />
		</a>
	</li>
	
	<li>
		<a 
		<c:if test="${model.tab == 4}">class="active"</c:if>
		href="<c:url value="/admin/portal/settings" />" >
			<spring:message code="menu.settings" />
		</a>
	</li>
	
	<li>
		<a 
		<c:if test="${model.tab == 5}">class="active"</c:if>
		href="<c:url value="/admin/portal/email-templates" />" >
			<spring:message code="admin.emailTemplate" />
		</a>
	</li>
	
	<li>
		<a 
		<c:if test="${model.tab == 6}">class="active"</c:if>
		href="<c:url value="/admin/portal/statistics" />" >
			Statistiky
		</a>
	</li>
	
</ul>