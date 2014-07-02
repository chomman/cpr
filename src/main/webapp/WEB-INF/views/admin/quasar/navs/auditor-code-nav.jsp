<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<ul class="sub-nav">
	
	<li>
		<a:adminurl href="/quasar/manage/auditors">
			<spring:message code="auditor.view" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/auditor/add">
			<spring:message code="auditor.add" />
		</a:adminurl>
	</li>
	
	
	<li>
		<a:adminurl href="/quasar/auditors/2" cssClass="margin-left qsf">
			<spring:message code="auditor.qsAuditor" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/auditors/3" cssClass="qsf">
			<spring:message code="auditor.productAssessorA" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/auditors/4" cssClass="qsf">
			<spring:message code="auditor.productAssessorR" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/auditors/5" cssClass="qsf">
			<spring:message code="auditor.productSpecialist" />
		</a:adminurl>
	</li>
</ul>