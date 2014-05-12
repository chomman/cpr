<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<c:if test="${not empty webpageModel.portalOrders}">
	<table class="data">
		<thead>
			<tr>
				<tH><spring:message code="admin.portal.order.no" /></th>
				<th><spring:message code="admin.portal.order.created" /></th>
				<th><spring:message code="admin.portal.order.stav" /></th>
				<th><spring:message code="form.view" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${webpageModel.portalOrders}" var="i" varStatus="s" >
			 	<tr class="status-${i.orderStatus.id } ${s.index % 2 == 0 ? 'even' : 'odd'}">
			 		<td class="oid c">
				 			<strong>${i.id}</strong>
			 		</td>
			 		<td class="last-edit">
			 			<joda:format value="${i.created}" pattern="dd.MM.yyyy / HH:mm"/>
			 		</td>
			 	
			 		<td class="w100 status c">
			 			<spring:message code="${i.orderStatus.code}" />
			 		</td>
			 		<td class="edit">
			 			<a href="<a:url href="/${webpageModel.profileUrl}/order/${i.id}" linkOnly="true"  />">
			 				<spring:message code="form.view" />
			 			</a>
			 		</td>
			 	</tr>
			 </c:forEach>
		</tbody>
	</table>
</c:if>

<c:if test="${empty webpageModel.portalOrders}">
	<p class="status alert">
		<spring:message code="alert.empty" />
	</p>
</c:if>