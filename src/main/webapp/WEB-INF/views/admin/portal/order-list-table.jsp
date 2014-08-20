<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<c:if test="${not empty model.portalOrders}">
	<!-- STRANKOVANIE -->
	<c:if test="${not empty model.paginationLinks}" >
		<div class="pagination">
		<c:forEach items="${model.paginationLinks}" var="i">
			<c:if test="${not empty i.url}">
				<a title="Stánka č. ${i.anchor}"  class="tt"  href="<c:url value="${i.url}"  />">${i.anchor}</a>
			</c:if>
			<c:if test="${empty i.url}">
				<span>${i.anchor}</span>
			</c:if>
		</c:forEach>
		</div>
	</c:if>
	
	<table class="data">
		<thead>
			<tr>
				<tH><spring:message code="admin.portal.order.no" /></th>
				<th><spring:message code="admin.portal.order.created" /></th>
				<th><spring:message code="admin.portal.order.stav" /></th>
				<th><spring:message code="admin.portal.order.user" /></th>
				<th><spring:message code="portalOrderSource" /></th>
				<th><spring:message code="form.edit" /></th>
				<th><spring:message code="form.delete" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${model.portalOrders}" var="i">
			 	<tr class="status-${i.orderStatus.id }">
			 		<td class="oid w100 c">
				 		<a:adminurl href="/portal/order/${i.id}">
				 			${i.id}
				 		</a:adminurl>
			 		</td>
			 		<td class="last-edit">
			 			<joda:format value="${i.created}" pattern="dd.MM.yyyy / HH:mm"/>
			 		</td>
			 	
			 		<td class="w100 status c">
			 			<spring:message code="${i.orderStatus.code}" />
			 		</td>
			 		<td>
			 			<c:if test="${not empty i.user}">
			 					<a:adminurl href="/portal/user/${i.user.id}">
				 				<c:out value="${i.firstName} ${i.lastName}" />
				 				</a:adminurl>
			 			</c:if>
			 			<c:if test="${ empty i.user}">
			 				<c:out value="${i.firstName} ${i.lastName}" />
						</c:if>
						
						<c:if test="${not empty i.companyName}">
							&nbsp; (<c:out value="${i.companyName}" />)
						</c:if>
			 		</td>	
			 		<td class="w100 c">
			 			<c:if test="${not empty i.portalOrderSource}">
			 				<spring:message code="${i.portalOrderSource.code}" />
			 			</c:if>
			 		</td>
			 		<td class="edit">
			 			<a:adminurl href="/portal/order/${i.id}">
			 				<spring:message code="form.edit" />
			 			</a:adminurl>
			 		</td>
			 		<td class="delete">
			 			<c:if test="${not i.payed}">
			 			<a:adminurl href="/portal/order/delete/${i.id}" cssClass="confirm">
			 				<spring:message code="form.delete" />
			 			</a:adminurl>
			 			</c:if>
			 			<c:if test="${i.payed}">
			 				-
			 			</c:if>
			 		</td>
			 	</tr>
			 </c:forEach>
		</tbody>
	</table>
</c:if>

<c:if test="${empty model.portalOrders}">
	<p class="msg alert">
		<spring:message code="alert.empty" />
	</p>
</c:if>