<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<div class="widget-bx" id="audit-logs" data-url="1">
	<h5>Latest <spring:message code="auditLogs" /></h5>
	<div class="widget-items loading"></div>
	<a title="<spring:message code="new" /> <spring:message code="auditLog.auditLog" />"
	class="add-new tt" href="<c:url value="/admin/quasar/audit-log/0" />">
		<spring:message code="new" /> <strong>+</strong>
	</a>
</div>	

<div class="widget-bx" id="dossier-reports" data-url="2">
	<h5>Latest <spring:message code="dossierReports" /></h5>
	<div class="widget-items loading"></div>
	<a title="<spring:message code="new" /> <spring:message code="dossierReport" />"
		class="add-new tt" href="<c:url value="/admin/quasar/dossier-report/0" />">
		<spring:message code="new" /> <strong>+</strong>
	</a>
</div>

<div class="widget-bx" id="training-logs" data-url="3">
	<h5>Latest <spring:message code="trainingLogs" /></h5>
	<div class="widget-items loading"></div>
		<a title="<spring:message code="new" /> <spring:message code="trainingLog" />"
		class="add-new tt" href="<c:url value="/admin/quasar/training-log/0" />">
		<spring:message code="new" /> <strong>+</strong>
	</a>
</div>	