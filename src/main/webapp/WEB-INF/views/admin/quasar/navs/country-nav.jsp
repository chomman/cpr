<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<ul class="sub-nav">
	<li>
		<a:adminurl href="/quasar/manage/countries">
			<spring:message code="quasar.show" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/manage/country/0">
			<spring:message code="quasar.add" />
		</a:adminurl>
	</li>
</ul>