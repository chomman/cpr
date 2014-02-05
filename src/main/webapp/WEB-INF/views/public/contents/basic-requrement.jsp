<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<ul class="br">
<c:forEach items="${model.basicRequremets}" var="item">
	<li>
		<a:url title="${item.name}" href="/cpr/br/${item.code}">${item.name}</a:url>
	</li>
</c:forEach>
</ul>