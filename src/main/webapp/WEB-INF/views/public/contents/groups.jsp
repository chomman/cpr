<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>


<c:if test="${not empty model.groups}">
				
	<table class="groups">
		<thead>
			<tr>
				<th class="code"><spring:message code="form.code" /> <spring:message code="cpr.groups" /></th>
				<tH><spring:message code="form.name" /> <spring:message code="cpr.groups" /></th>
				<th><spring:message code="commissiondecision" /></th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach items="${model.groups}" var="i">
			 	<tr>
			 		<td>${i.groupCode}</td>
			 		<td  class="l"><a class="tt" title="<spring:message code="groups.detail" />" href="<c:url value="/cpr/skupina/${i.code}" />"> ${i.groupName}</a></td>
			 		<td>
			 		<c:if test="${not empty i.commissionDecisionFileUrl}">
			 			<a class="file pdf" title="${i.urlTitle}" href="${i.commissionDecisionFileUrl}">${i.urlTitle}</a>
			 		</c:if>
			 		</td>
			 		
			 	</tr>
			 </c:forEach>
		</tbody>
	</table>
</c:if>