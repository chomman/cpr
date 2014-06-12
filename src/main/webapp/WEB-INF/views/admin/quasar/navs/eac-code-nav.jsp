<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<ul class="sub-nav">
	<li>
		<a:adminurl href="/quasar/manage/eac-codes">
			<spring:message code="eacCode.show" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/manage/eac-code/0">
			<spring:message code="eacCode.add" />
		</a:adminurl>
	</li>
</ul>