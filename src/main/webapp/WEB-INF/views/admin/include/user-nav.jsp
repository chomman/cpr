<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_SUPERADMIN')">	
	<c:set var="isSuperAdmin" value="true"/>
</sec:authorize>
<strong class="nav-head"><spring:message code="menu.nav" /></strong>
<ul>
<c:if test="${isSuperAdmin}">
	<li>
		<a 
		<c:if test="${model.tab == 1}">class="active"</c:if>
		 href="<c:url value="/admin/users" />" >
			<spring:message code="user.view" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 2}">class="active"</c:if>
		href="<c:url value="/admin/user/add" />" >
			<spring:message code="user.add" />
		</a>
	</li>
</c:if>
	<li>
		<a 
		<c:if test="${model.tab == 3}">class="active"</c:if>
		href="<c:url value="/admin/user/profile" />" >
			<spring:message code="user.profile.view" />
		</a>
	</li>
<c:if test="${isSuperAdmin}">
	<li>
		<a 
		<c:if test="${model.tab == 4}">class="active"</c:if>
		href="<c:url value="/admin/user/roles" />" >
			<spring:message code="user.role.view" />
		</a>
	</li>	
	<li>
		<a 
		<c:if test="${model.tab == 5}">class="active"</c:if>
		href="<c:url value="/admin/user/logs" />" >
			<spring:message code="user.log.view" />
		</a>
	</li>
</c:if>
</ul>