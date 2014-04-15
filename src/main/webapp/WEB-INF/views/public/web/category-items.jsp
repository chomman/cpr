<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:forEach items="${webpageModel.webpage.publishedChildrens}" var="i">
	<div class="pj-category-item">
		<c:if test="${webpageModel.webpage.showThumbnail}">
			<div class="pj-foto">
				<c:if test="${not empty i.avatar}">
					<img src="<c:url value="/image/s/100/avatars/${i.avatar}" />" alt="${i.defaultName}" />
				</c:if>
				<c:if test="${ empty i.avatar}">
					<img class="radius" src="<c:url value="/resources/public/img/nothumb.png" />" alt="${i.defaultName}" />
				</c:if>
			</div>
		</c:if>
		<div class="pj-content">
			<h3><webpage:a webpage="${i}" /></h3>
			<p><webpage:filedVal webpage="${i}" fieldName="description" /></p>
		</div>
	</div>
</c:forEach>