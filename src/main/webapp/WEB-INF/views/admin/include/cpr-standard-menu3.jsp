<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<a class="tab tt" title="<spring:message code="cpr.standard.tab.3.title" />" 
	href="<c:url value="/admin/cpr/standard/edit/${standardId}/csn" />" >
	<spring:message code="cpr.standard.tab.3" />
</a>