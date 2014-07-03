<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<ul class="sub-nav auditor-nav">
	
	<li>
		<a:adminurl href="/quasar/manage/auditor/${model.auditor.id}" activeTab="${model.subTab == 1}">
			<spring:message code="auditor.personalData" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/auditor/${model.auditor.id}/f/2" activeTab="${model.subTab == 2}">
			<spring:message code="auditor.qsAuditor" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/auditor/${model.auditor.id}/f/3" activeTab="${model.subTab == 3}">
			<spring:message code="auditor.productAssessorA" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/auditor/${model.auditor.id}/f/4" activeTab="${model.subTab == 4}">
			<spring:message code="auditor.productAssessorR" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/auditor/${model.auditor.id}/f/5" activeTab="${model.subTab == 5}">
			<spring:message code="auditor.productSpecialist" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/auditor/${model.auditor.id}/f/6" activeTab="${model.subTab == 6}">
			<spring:message code="summary" />
		</a:adminurl>
	</li>
	
	
</ul>