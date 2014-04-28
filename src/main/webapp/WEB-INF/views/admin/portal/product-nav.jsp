<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	

<ul class="sub-nav">
	<li>
		<a:adminurl href="/portal/products">
			<spring:message code="admin.service.list" />
		</a:adminurl>
	</li>
	<li>
		<a:adminurl href="/portal/product/0" >
			<spring:message code="admin.service.addNew" />
		</a:adminurl>
	</li>
</ul>