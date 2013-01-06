<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<strong class="nav-head"><spring:message code="menu.nav" /></strong>
<ul>
	<li>
		<a 
		<c:if test="${model.tab == 1}">class="active"</c:if>
		 href="<c:url value="/admin/cpr/norm" />" >
			<spring:message code="menu.cpr.norm" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 2}">class="active"</c:if>
		href="<c:url value="/admin/cpr/groups" />" >
			<spring:message code="menu.cpr.groups" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 3}">class="active"</c:if>
		href="<c:url value="/admin/cpr/notifiedbodies" />" >
			<spring:message code="menu.cpr.aono" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 4}">class="active"</c:if>
		href="<c:url value="/admin/cpr/assessmentsystems" />" >
			<spring:message code="menu.cpr.pps" />
		</a>
	</li>
	<li>
		<a
		<c:if test="${model.tab == 5}">class="active"</c:if>
		 href="<c:url value="/admin/cpr/mandates" />" >
			<spring:message code="menu.cpr.mandates" />
		</a>
	</li>
	<li>
		<a
		<c:if test="${model.tab == 6}">class="active"</c:if>
		 href="<c:url value="/admin/cpr/basicrequirements" />" >
			<spring:message code="menu.cpr.requrements" />
		</a>
	</li>
</ul>