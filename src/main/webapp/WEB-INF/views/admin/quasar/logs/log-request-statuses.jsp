<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:if test="${not empty successCreate}">
	<p class="msg ok"><spring:message code="success.create" /></p>
</c:if>

<c:if test="${not empty successDelete}">
	<p class="msg ok"><spring:message code="success.delete" /></p>
</c:if>

<c:if test="${not empty companyFound}">
	<p class="msg alert">
		<spring:message code="log.alert.companyFound" arguments="${companyFound}"  argumentSeparator=";"/>
	</p>
</c:if>