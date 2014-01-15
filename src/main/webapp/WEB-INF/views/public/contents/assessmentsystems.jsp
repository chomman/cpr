<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>


<c:if test="${not empty model.webpage.topText}">
	<article class="full-width">
		${model.webpage.topText}
	</article>
</c:if>

<div class="item-list">
	<ul class="br">
		<c:forEach items="${model.assessmentSystems}" var="item">
			<li><a title="${item.name}"  href="<c:url value="/cpr/as/${item.id}" />">${item.name}</a></li>
		</c:forEach>
	</ul>
</div>

<c:if test="${not empty model.webpage.bottomText}">
	<article>
		${model.webpage.bottomText}
	</article>
</c:if>