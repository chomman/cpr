<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="admin.portal.orders" /></title>
	<script type="text/javascript">  
	$(document).ready(function() {  
		
	});
	</script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <span><spring:message code="admin.portal.orders" /></span>
			</div>
			<h1><spring:message code="admin.portal.orders" /></h1>
	
			<div id="content">
				
				
								
			<form class="filter" method="get">
				<div >
					<span class="filter-label long"><spring:message code="form.orderby" />:</span>
					<select name="orderBy" class="chosenSmall">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
						</c:forEach>
					</select>
					<span class="filter-label"><spring:message code="cpr.standard.added" /></span>
					<select name="orderStatus" class="chosenSmall">
							<option value="">Nezáleží</option>
						<c:forEach items="${model.orderStatuses}" var="i">
							<option value="${i}" <c:if test="${i == model.params.orderStatus}" >selected="selected"</c:if> >
								<spring:message code="${i.code}" />
							</option>
						</c:forEach>
					</select>
										
				</div>
				<div>
					<span class="filter-label long">Datum přijetí:</span>
					<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span class="filter-label">do:</span>
					<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
				</div>
				<div>
					<span class="filter-label long">Název zákazníka:</span>
					<input type="text" class="query " name="query"   value="${model.params.query}" />
					<input type="submit" value="Filtrovat" class="btn filter-btn-standard" />
				</div>
			</form>				
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
				</c:if>
				
							
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
								<th><spring:message code="admin.portal.order.user" /></th>
								<th><spring:message code="admin.portal.order.stav" /></th>
								<th><spring:message code="admin.portal.order.created" /></th>
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
							 		<td>
							 			<c:if test="${not empty i.user}">
							 					<a:adminurl href="/user/edit/${i.user.id}">
								 				${i.firstName} ${i.lastName}
								 				</a:adminurl>
							 			</c:if>
							 			<c:if test="${ empty i.user}">
							 				${i.firstName} ${i.lastName}
										</c:if>
							 		</td>
							 		<td class="w100 status">
							 			<spring:message code="${i.orderStatus.code}" />
							 		</td>
							 		<td class="last-edit">
							 			<joda:format value="${i.created}" pattern="dd.MM.yyyy / HH:mm"/>
							 		</td>
							 		<td class="edit">
							 			<a:adminurl href="/portal/order/${i.id}">
							 				<spring:message code="form.edit" />
							 			</a:adminurl>
							 		</td>
							 		<td class="delete">
							 			<a:adminurl href="/portal/order/delete/${i.id}" cssClass="confirm">
							 				<spring:message code="form.delete" />
							 			</a:adminurl>
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
	
			</div>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>