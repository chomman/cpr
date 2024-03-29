<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:set var="isLoggedWebmaster" value="false"/>
<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="admin.portal.services" /></title>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <span><spring:message code="admin.portal.services" /></span>
			</div>
			<h1><spring:message code="admin.portal.services" /></h1>
	
			<div id="content">
				
				<script type="text/javascript">  
				$(document).ready(function() { });
				</script>
				
				<jsp:include page="product-nav.jsp" />
				
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
				</c:if>
				
							
				<c:if test="${not empty model.portalProducts}">
										
					<table class="data">
						<thead>
							<tr>
								<tH>Typ</th>
								<tH><spring:message code="admin.service.name" /></th>
								<th><spring:message code="admin.service.price" /></th>
								<th><spring:message code="admin.service.enabled" /></th>
								<th><spring:message code="form.edit" /></th>
								<th><spring:message code="form.delete" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.portalProducts}" var="i">
							 	<tr class="product-${i.portalProductType.id}">
							 		<td class="w100 prod c">
							 			<spring:message code="${i.portalProductType.code}" />
							 		</td>
							 		<td class="pid">
								 		<a:adminurl href="/portal/product/${i.id}">
								 			${i.czechName}
								 		</a:adminurl>
							 		</td>
							 		<td>
							 			<webpage:price price="${i.priceCzk}" /> /
							 			<webpage:price price="${i.priceEur}" isEuro="true" />
							 		</td>			
							 		<td class="w100">
							 			<c:if test="${i.enabled}">
							 				<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
							 					<spring:message code="yes" />
							 				</span>
							 			</c:if>
							 			<c:if test="${not i.enabled}">
							 				<span class="published no tt" title="<spring:message code="published.no.title" />" >
							 					<spring:message code="no" />
							 				</span>
							 			</c:if>
							 		</td>				
							 		<td class="edit">
							 			<a:adminurl href="/portal/product/${i.id}">
							 				<spring:message code="form.edit" />
							 			</a:adminurl>
							 		</td>
							 		<td class="delete">
								 			<a:adminurl href="/portal/product/delete/${i.id}" cssClass="confirm">
								 				<spring:message code="form.delete" />
								 			</a:adminurl>
							 		</td>
							 	</tr>
							 </c:forEach>
						</tbody>
					</table>
				</c:if>
				
				<c:if test="${empty model.portalProducts}">
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