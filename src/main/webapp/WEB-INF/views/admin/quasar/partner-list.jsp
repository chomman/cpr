<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="partners" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
				 <span><spring:message code="partners" /></span>
			</div>
			<h1><spring:message code="partners" /></h1>
	
			<div id="content">
								
				<jsp:include page="navs/partner-nav.jsp" />
				
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
				</c:if>
				
							
				<c:if test="${not empty model.partners}">
										
					<table class="data">
						<thead>
							<tr>
								<th><spring:message code="partner.name" /></th>
								<th><spring:message code="partner.manager" /></th>
								<th>Changed</th>
								<th>&nbsp;</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.partners}" var="i">
								<tr class="${i.enabled ? '' : 'is-disabled'}"> 
									<td>${i.name}</td>
									<td>
										<c:if test="${empty i.manager}">
											<spring:message code="partner.nonAssigned" />
										</c:if>
										<c:if test="${not empty i.manager}">
											${i.manager.firstName} ${i.manager.lastName}
										</c:if>
									</td>			
									<td class="last-edit">
										<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/> / 
										<span>${i.changedBy.firstName} ${i.changedBy.lastName}</span> 
									</td>
									<td class="edit">
										<a:adminurl href="/quasar/manage/partner/${i.id}">
											<spring:message code="quasar.edit" />
										</a:adminurl>
									</td>
								</tr>
							
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				
				<c:if test="${empty model.partners}">
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