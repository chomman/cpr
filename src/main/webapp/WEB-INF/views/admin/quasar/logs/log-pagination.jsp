<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!-- STRANKOVANIE -->
<c:if test="${not empty model.paginationLinks}" >
	<div class="pagination">
	<c:forEach items="${model.paginationLinks}" var="i">
		<c:if test="${not empty i.url}">
			<a href="<c:url value="${i.url}"  />">${i.anchor}</a>
		</c:if>
		<c:if test="${empty i.url}">
			<span>${i.anchor}</span>
		</c:if>
	</c:forEach>
	</div>
</c:if>