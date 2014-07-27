<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ taglib prefix="quasar"  uri="http://nlfnorm.cz/quasar"%>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="trainingLogs" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
	<script src="<c:url value="/resources/admin/js/scripts.quasar.js" />"></script>
</head>
<body>
	<div id="wrapper">
		<c:if test="${model.isQuasarAdmin}">
		<div id="left">
			<jsp:include page="../../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
		</c:if>
			<div id="breadcrumb">
				<c:if test="${model.isQuasarAdmin}">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				</c:if>
				 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
				 <span><spring:message code="trainingLogs" /></span>
			</div>
			<h1><spring:message code="trainingLogs" /></h1>
	
			<div id="content">
				
													
				<ul class="sub-nav auditor-nav">
					<li>
						<a:adminurl href="/quasar/training-logs">
							<spring:message code="quasar.show" />
						</a:adminurl>
					</li>
					<li>
						<a:adminurl href="/quasar/training-log/0">
							<spring:message code="quasar.add" />
						</a:adminurl>
					</li>
				</ul>
								
				<jsp:include page="log-filter.jsp" />
								
				<c:if test="${not empty model.logs}">
				<jsp:include page="log-pagination.jsp" />
														
				<table class="data">
					<thead>
						<tr>
							<th><spring:message code="logStatus" /></th>
							<th><spring:message code="trainingLog" /> date</th>
							<th><spring:message code="auditors" /></th>
							<th><spring:message code="trainingLog.totals" /></th>
							<th>Changed</th>
							<th>&nbsp;</th> 
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.logs}" var="i">
							<tr class="qs-log-status-${i.status.id}">  
								<td class="w100 qs-status">
									<span class="qs-log-status"><spring:message code="${i.status.code}" /></span>
								</td>	
								<td>
									<a:adminurl href="/quasar/training-log/${i.id}">
											<spring:message code="trainingLog" /> - 
											<strong><joda:format value="${i.created}" pattern="dd.MM.yyyy"/></strong>
									</a:adminurl>
									<c:if test="${i.revision > 1}">
										(<spring:message code="auditLog.auditLog.revision" /> ${i.revision})
									</c:if>
									<c:if test="${i.status.locked}">
										<strong>&nbsp; (<spring:message code="locked" />)</strong>
									</c:if>
									<span class="created-by">Created by: <strong>${i.createdBy.name}</strong></span>
								</td>
								<td>
									${i.formatedWorkers}
								</td>
								<td class="w100 c">
									<strong>${i.totalHours}</strong>
									 <spring:message code="hours" />
								</td>
								<td class="last-edit">
									<joda:format value="${i.changed}" pattern="dd.MM.yyyy HH:mm"/> / ${i.changedBy.name} 
								</td>
								<td class="edit">
									<c:if test="${not i.status.locked}">
										<a:adminurl href="/quasar/training-log/${i.id}">
											<spring:message code="quasar.edit" />
										</a:adminurl>
									</c:if>
									<c:if test="${i.status.locked}">
										<a:adminurl href="/quasar/training-log/${i.id}">
											<spring:message code="form.view" />
										</a:adminurl>
									</c:if>
								</td>
							</tr>
						
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${empty model.logs}">
				<p class="msg alert">
					<spring:message code="alert.empty" />
				</p>
			</c:if>
			
			</div>	
	<c:if test="${model.isQuasarAdmin}">
		</div>
		<div class="clear"></div>	
	</c:if>
	</div>
</body>
</html>