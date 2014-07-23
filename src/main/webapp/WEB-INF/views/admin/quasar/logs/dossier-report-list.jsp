<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ taglib prefix="quasar"  uri="http://nlfnorm.cz/quasar"%>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="dossierReports" /></title>
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
				 <span><spring:message code="dossierReports" /></span>
			</div>
			<h1><spring:message code="dossierReports" /></h1>
	
			<div id="content">
				
				<c:if test="${common.user.auditor}">									
				<ul class="sub-nav auditor-nav">
					<li>
						<a:adminurl href="/quasar/dossier-reports">
							<spring:message code="quasar.show" />
						</a:adminurl>
					</li>
					<li>
						<a:adminurl href="/quasar/dossier-report/0">
							<spring:message code="quasar.add" />
						</a:adminurl>
					</li>
				</ul>
				</c:if>
				
				<c:if test="${model.isQuasarAdmin}">
					<jsp:include page="create-dossier-form.jsp" />
				</c:if>
				
				<jsp:include page="log-filter.jsp" />
					
				<c:if test="${not empty model.logs}">
				<jsp:include page="log-pagination.jsp" />
														
				<table class="data">
					<thead>
						<tr>
							<th><spring:message code="logStatus" /></th>
							<th><spring:message code="designDossier" /> date</th>
							<c:if test="${model.isQuasarAdmin}">
								<th><spring:message code="auditor.edit" /></th>
							</c:if>
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
									<a:adminurl href="/quasar/dossier-report/${i.id}">
											<spring:message code="documentationLog" /> - 
											<strong><joda:format value="${i.created}" pattern="dd.MM.yyyy"/></strong>
									</a:adminurl>
									<c:if test="${i.revision > 1}">
										(<spring:message code="auditLog.auditLog.revision" /> ${i.revision})
									</c:if>
									<c:if test="${i.status.locked}">
										<strong>&nbsp; (<spring:message code="locked" />)</strong>
									</c:if>
									<c:if test="${i.auditor.id != i.createdBy.id}">
										<span class="created-by">Created by: <strong>${i.createdBy.name}</strong></span>
									</c:if>
								</td>
								<c:if test="${model.isQuasarAdmin}">
									<td><quasar:auditor auditor="${i.auditor}" /></td> 
								</c:if>
								<td class="last-edit">
									<joda:format value="${i.changed}" pattern="dd.MM.yyyy HH:mm"/> / ${i.changedBy.name} 
								</td>
								<td class="edit">
									<c:if test="${not i.status.locked}">
										<a:adminurl href="/quasar/dossier-report/${i.id}">
											<spring:message code="quasar.edit" />
										</a:adminurl>
									</c:if>
									<c:if test="${i.status.locked}">
										<a:adminurl href="/quasar/dossier-report/${i.id}">
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