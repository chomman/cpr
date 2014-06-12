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
</ul>