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
						<jsp:include page="table-rows-dossier-report.jsp" />
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