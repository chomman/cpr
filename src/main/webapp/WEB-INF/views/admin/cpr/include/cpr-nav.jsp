<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<strong class="nav-head"><spring:message code="menu.nav" /></strong>
<ul>
	<li>
		<a 
		<c:if test="${model.tab == 1}">class="active"</c:if>
		 href="<c:url value="/admin/cpr/standards" />" >
			<spring:message code="menu.cpr.ehn" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 9}">class="active"</c:if>
		 href="<c:url value="/admin/cpr/standard-csn" />" >
			<spring:message code="menu.cpr.csn" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 11}">class="active"</c:if>
		href="<c:url value="/admin/cpr/notifiedbodies" />" >
			<spring:message code="menu.cpr.aono" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 13}">class="active"</c:if>
		href="<c:url value="/admin/cpr/standard-categories" />" >
			<spring:message code="standardCategories" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 12}">class="active"</c:if>
		href="<c:url value="/admin/cpr/regulations" />" >
			<spring:message code="regulations" />
		</a>
	</li>
	<li class="nav-sep">
		<strong>CPR</strong>
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
		<c:if test="${model.tab == 8}">class="active"</c:if>
		 href="<c:url value="/admin/cpr/commission-decisions" />" >
			<spring:message code="menu.cpr.commissiondecision" />
		</a>
	</li>
	<li>
		<a
		<c:if test="${model.tab == 10}">class="active"</c:if>
		 href="<c:url value="/admin/cpr/reports" />" >
			<spring:message code="menu.cpr.report" />
		</a>
	</li>
</ul>