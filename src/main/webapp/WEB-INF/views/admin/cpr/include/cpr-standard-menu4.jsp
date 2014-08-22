<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:if test="${standard.cprCategory}">
<a class="tab tt" title="<spring:message code="cpr.standard.tab.4.title" />" 
	href="<c:url value="/admin/cpr/standard/edit/${standardId}/notifiedbodies" />" >
	<spring:message code="cpr.standard.tab.4" />
</a>
</c:if>