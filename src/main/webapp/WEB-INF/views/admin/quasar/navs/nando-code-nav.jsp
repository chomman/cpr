<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	

<ul class="sub-nav">
	<li>
		<a:adminurl href="/quasar/manage/nando-codes">
			<spring:message code="nandoCode.show" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/quasar/manage/nando-code/0">
			<spring:message code="nandoCode.add" />
		</a:adminurl>
	</li>
</ul>