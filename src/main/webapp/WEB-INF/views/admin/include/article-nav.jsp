<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<strong class="nav-head"><spring:message code="menu.nav" /></strong>
<ul>
	<li>
		<a 
		<c:if test="${model.tab == 1}">class="active"</c:if>
		 href="<c:url value="/admin/articles" />" >
			<spring:message code="menu.article.show" />
		</a>
	</li>
	<li>
		<a 
		<c:if test="${model.tab == 2}">class="active"</c:if>
		href="<c:url value="/admin/article/add" />" >
			<spring:message code="menu.article.add" />
		</a>
	</li>
</ul>