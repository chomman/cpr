<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>


<div class="left-panel">
<span class="head"><spring:message code="menu.nav" /></span>

	<!-- SUB NAV -->
	<ul class="nav">
		<c:forEach items="${model.submenu}" var="item">
			<li><a title="${item.title}" <c:if test="${model.webpage.id == item.id}" >class="curr"</c:if> href="<c:url value="${item.code}" />">${item.name}</a></li>
		</c:forEach>
	</ul>
</div>