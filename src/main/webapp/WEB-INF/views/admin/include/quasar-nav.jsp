<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<strong class="nav-head"><spring:message code="menu.nav" /></strong>

<ul>
	<li>
		<a:adminurl href="/quasar/manage/audit-logs" activeTab="${model.tab == 8}">
			<spring:message code="auditLogs" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/dossier-reports" activeTab="${model.tab == 10}">
			<spring:message code="documentationLogs" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/manage/auditors" activeTab="${model.tab == 3}">
			<spring:message code="auditors" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/nando-codes" activeTab="${model.tab == 1}">
			<spring:message code="nandoCodes" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/eac-codes" activeTab="${model.tab == 2}">
			<spring:message code="eacCodes" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/partners" activeTab="${model.tab == 4}">
			<spring:message code="partners" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/manage/educations" activeTab="${model.tab == 5}">
			<spring:message code="fieldOfEducations" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/countries" activeTab="${model.tab == 6}">
			<spring:message code="countries" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/settings" activeTab="${model.tab == 7}">
			<spring:message code="quasarSettings" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/quasar/manage/settings" activeTab="${model.tab == 7}">
			<spring:message code="quasarSettings" />
		</a:adminurl>
	</li>
	
	<li>
		<a:adminurl href="/email-templates?quasar=true" activeTab="${model.tab == 9}">
			<spring:message code="admin.emailTemplate" />
		</a:adminurl>
	</li>	
</ul>