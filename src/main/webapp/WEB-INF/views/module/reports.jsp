<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<c:if test="${not empty model.reports}"> 
	<ul class="reports">
		<c:forEach items="${model.reports}" var="i">
			<li>
				<a:url href="/report/${i.id}">
					<joda:format value="${i.dateFrom}" pattern="MMMM, yyyy"/>
				</a:url>
			</li>
		</c:forEach>
	</ul>
</c:if>