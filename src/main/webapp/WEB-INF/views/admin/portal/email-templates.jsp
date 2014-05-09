<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="admin.emailTemplate" /></title>
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
				 <span><spring:message code="admin.emailTemplate" /></span>
			</div>
			<h1><spring:message code="admin.emailTemplate" /></h1>
	
			<div id="content">
			
				<sec:authorize access="hasAnyRole('ROLE_WEBMASTER')">
					<ul class="sub-nav">
						<li>
							<a:adminurl href="/portal/email-template/0">
								Přidat novou šablonu
							</a:adminurl>
						</li>
					</ul>
				</sec:authorize>
				
				<c:if test="${not empty model.emailTemplates}">
					<div class="hbox">
						<h2>Štablóny emailů odesílány zákazníkům</h2>
					</div>									
					<table class="data">
						<thead>
							<tr>
								<tH><spring:message code="admin.emailTemplate.subject" /></th>
								<tH><spring:message code="admin.emailTemplate.name" /></th>
								<th><spring:message code="form.lastEdit" /></th>
								<th><spring:message code="form.edit" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.emailTemplates}" var="i">
								<c:if test="${fn:substring(i.code, 0, 15) != 'PORTAL_MEDIATOR'}">
								 	<tr>
								 		<td><strong>${i.subject}</strong></td>
								 		<td>${i.name}</td>
								 		<td class="last-edit">
								 			<c:if test="${empty i.changedBy}">
								 				<joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/>
								 			</c:if>
								 			<c:if test="${not empty i.changedBy}">
								 				<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
								 			</c:if>
								 		</td>
								 		<td class="edit">
								 			<a:adminurl href="/portal/email-template/${i.id}">
								 				<spring:message code="form.edit" />
								 			</a:adminurl>
								 		</td>
								 	</tr>
							 	</c:if>
							 </c:forEach>
						</tbody>
					</table>
					
					<div class="hbox">
						<h2>Štablóny emailů odesílány partnerům</h2>
					</div>									
					<table class="data">
						<thead>
							<tr>
								<tH><spring:message code="admin.emailTemplate.subject" /></th>
								<tH><spring:message code="admin.emailTemplate.name" /></th>
								<th><spring:message code="form.lastEdit" /></th>
								<th><spring:message code="form.edit" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.emailTemplates}" var="i">
								<c:if test="${fn:substring(i.code, 0, 15) == 'PORTAL_MEDIATOR'}">
								 	<tr>
								 		<td><strong>${i.subject}</strong></td>
								 		<td>${i.name}</td>
								 		<td class="last-edit">
								 			<c:if test="${empty i.changedBy}">
								 				<joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/>
								 			</c:if>
								 			<c:if test="${not empty i.changedBy}">
								 				<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
								 			</c:if>
								 		</td>
								 		<td class="edit">
								 			<a:adminurl href="/portal/email-template/${i.id}">
								 				<spring:message code="form.edit" />
								 			</a:adminurl>
								 		</td>
								 	</tr>
							 	</c:if>
							 </c:forEach>
						</tbody>
					</table>
				</c:if>
				
				<c:if test="${empty model.emailTemplates}">
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