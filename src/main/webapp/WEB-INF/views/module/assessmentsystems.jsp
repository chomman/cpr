<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<div class="item-list">
	<ul class="br">
		<c:forEach items="${model.assessmentSystems}" var="item">
			<li>
				<a:url title="${item.name}" href="/cpr/as/${item.id}">${item.name}</a:url>
			</li>
		</c:forEach>
	</ul>
</div>

