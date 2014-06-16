<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<strong class="nav-head"><spring:message code="menu.nav" /></strong>
<ul>
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
	
	
	
</ul>