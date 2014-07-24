<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:if test="${not empty model.log.comments}">
	<a class="qs-show-comments">Show users comments <strong>(${model.log.countOfComments})</strong> </a>
	<div class="qs-comments hidden">
		<c:forEach items="${model.log.comments}" var="i">
			<div>
				${i.comment}
				<span class="qs-meta">
					<joda:format value="${i.created}" pattern="dd.MM.yyyy / HH:mm"/>, 
					${i.user.name}
				</span>
			</div>
		</c:forEach>
	</div>
</c:if>