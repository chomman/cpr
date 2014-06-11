<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<strong class="nav-head"><spring:message code="menu.nav" /></strong>
<ul>
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
	
</ul>