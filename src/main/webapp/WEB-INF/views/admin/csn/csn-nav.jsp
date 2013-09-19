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
		<c:if test="${model.tab == 3}">class="active"</c:if>
		href="<c:url value="/admin/csn/categories" />" >
			<spring:message code="csn.category" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 4}">class="active"</c:if>
		href="<c:url value="/admin/csn/terminology/import" />" >
			<spring:message code="csn.terminology.import" />
		</a>
	</li>
	
	<li>
		<a 
		<c:if test="${model.tab == 5}">class="active"</c:if>
		href="<c:url value="/admin/csn/terminology/log" />" >
			<spring:message code="csn.terminology.log" />
		</a>
	</li>
</ul>