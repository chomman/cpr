<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<ul class="sub-nav">
	<li>
		<a:adminurl href="/quasar/manage/partners">
			<spring:message code="partner.show" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/manage/partner/0">
			<spring:message code="partner.add" />
		</a:adminurl>
	</li>
</ul>