<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>


<strong class="nav-head"><spring:message code="menu.nav" /></strong>
<ul>
	<li>
		<a 
		<c:if test="${model.tab == 1}">class="active"</c:if>
		 href="<c:url value="/admin/csn" />" >
			<spring:message code="csn.list" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 2}">class="active"</c:if>
		href="<c:url value="/admin/csn/edit/0" />" >
			<spring:message code="csn.add" />
		</a>
	</li>
</ul>