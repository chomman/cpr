<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ taglib prefix="quasar"  uri="http://nlfnorm.cz/quasar"%>
<c:set  var="isQuasarAdmin" value="${common.user.quasarAdmin}" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<title>${model.log.createdBy.name}'s <spring:message code="trainingLog"/></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.tagit.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/tagit.ui-zendesk.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" /> 
	<script src="<c:url value="/resources/admin/js/tag-it.min.js" />"></script>
	<script src="<c:url value="/resources/admin/quasar/js/scripts.quasar.auditLog.js" />"></script>
	<c:if test="${isQuasarAdmin}">
	<link rel="stylesheet" href="<c:url value="/resources/admin/quasar/css/jquery.raty.css" />" />
	<script src="<c:url value="/resources/admin/quasar/js/auditor.js" />"></script>
	<script src="<c:url value="/resources/admin/quasar/js/jquery.raty.js" />"></script>
	</c:if>
</head>
<body>
<div id="wrapper"  data-type="audit-log">
	<c:if test="${isQuasarAdmin}">
		<div id="left">
			<jsp:include page="../../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
	</c:if>
	<div id="breadcrumb">
		 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
		 <a:adminurl href="/quasar/training-logs"><spring:message code="trainingLogs" /></a:adminurl>  &raquo;
		 <span>${model.log.createdBy.name}'s <spring:message code="trainingLog"/></span>
	</div>
	<h1 class="qs-log-status-${model.log.status.id}">
		${model.log.createdBy.name}'s <spring:message code="trainingLog"/>: 
		<strong><joda:format value="${model.log.created}" pattern="dd.MM.yyyy"/></strong> 
		<c:if test="${model.log.revision > 1}">
		(<spring:message code="auditLog.auditLog.revision" /> ${model.log.revision})
		</c:if>
		&nbsp; | &nbsp; Status: <strong class="qs-status qs-log-status">${model.log.status}</strong>
		<c:if test="${model.log.totalHours > 0}">
		&nbsp; | &nbsp;	<spring:message code="trainingLog.totals" />: <strong class="qs-training-log-totals"> ${model.log.totalHours}</strong>
		</c:if>
	</h1>

	<div id="content"> 
	
		<jsp:include page="log-request-statuses.jsp" />
			
		<c:if test="${(model.isEditable or isQuasarAdmin) and model.log.valid and model.log.status != 'APPROVED'}"> 
		<div class="qs-log-nav">
			<span>
				<strong><spring:message code="options" />:</strong>
			</span>
				<c:if test="${model.isEditable}">
					<c:if test="${model.log.totalHours > 0}">
					<a href="#change-status" class="qs-btn qs-change-status-btn qs-submit-for-approval"  data-cls="qs-submit-for-approval" 
						data-status="PENDING" data-note="<spring:message code="log.penging.note" />">
						<spring:message code="log.penging" /> &raquo;
					</a>
					</c:if>
				</c:if>
				<c:if test="${not model.isEditable and isQuasarAdmin}">
					<a href="#change-status" class="qs-btn qs-change-status-btn qs-approve" data-cls="qs-approve" 
					data-status="APPROVED" data-note="<spring:message code="log.approve.note" />">
						<spring:message code="log.approve" /> 
					</a>
					<a href="#change-status" class="qs-btn qs-change-status-btn qs-reject" data-cls="qs-reject" 
					data-status="REFUSED" data-note="<spring:message code="log.refuse.note" arguments="${model.log.createdBy.name}" />">
						<spring:message code="log.refuse" /> 
					</a>
				</c:if>
				
		</div>
		</c:if>
		
		<!-- Change status form  -->
		<c:if test="${model.log.totalHours > 0}">
			<c:url var="sUrl" value="/admin/quasar/change-log-status" />
			<form id="change-status" class="qs-change-status hidden" action="${sUrl}" method="post">
				<h4></h4>
				<textarea name="comment" class="mw500" rows="5" cols="5" placeholder="<spring:message code="writeComment" />"></textarea>
				<a class="cancel qs-btn"><spring:message code="cancel" /></a>
				<input type="submit" value="<spring:message code="form.submit" />" class="qs-btn" />
				<input type="hidden" name="status" value="" />
				<input type="hidden" name="action" value="${model.statusType}" /> 
				<input type="hidden" name="logId" value="${model.log.id}" />
			</form>
		</c:if>
		
			
		
	<jsp:include page="log-comments.jsp" />
	
	<c:if test="${model.isEditable}">
		<jsp:include page="training-log-edit-form-base-informations.jsp" />			 
		<jsp:include page="training-log-edit-form-auditors.jsp" />
	</c:if>
	<c:if test="${not model.isEditable}">
		<jsp:include page="training-log-edit-base-informations.jsp" />
		<div class="qs-bx-wrapp qs-log-items  bg-white">
			<div class=" transparent">
				<jsp:include page="training-log-auditors.jsp" />
			</div>
		</div>
	</c:if>
			
	<div id="upload"  class="${model.isEditable ? '' : 'disable' }${model.log.attachmentUploaded ? 'qs-valid' : 'qs-invalid' }">
		<div class="qs-bx-wrapp bg-white">
			<div class="transparent">
			<p class="form-head"><spring:message code="trainingLog.record" /></p>
			<c:url value="/admin/quasar/training-log/${model.log.id}/upload" var="fUrl" />
			<c:if test="${model.log.attachmentUploaded}">
				<span class="label uploaded">
					<spring:message code="trainingLog.attachedFile" />:
				</span>
				
				<a title="<spring:message code="download" />" class="tt attached-file file" href="<c:url value="/f/auth/quasar/logs/${model.log.attachment}" />">
				${model.log.attachment}
				</a>
				
				<c:if test="${model.isEditable}">
					<a href="${fUrl}" class="delete file confirm">
						(Remove and upload another)
					</a>
				</c:if>
			</c:if>
			<c:if test="${not model.log.attachmentUploaded}">
			<form class="inline-form valid" action="${fUrl}" method="POST" enctype="multipart/form-data">
				<div class="inline-field">
					<span class="label">
						<spring:message code="form.file.select" />:
					</span>
				<input type="file" name="fileData" class="required" />
				<c:if test="${uploadError}">
					<span class="msg error">
						<spring:message code="error.upload.failed" />
					</span>
				</c:if>
				</div>
				<input type="submit" class="lang mandate-add-btn" value="<spring:message code="form.file.upload"  />" />
			</form>
			</c:if>
			</div>
		</div>
	</div>
	
			
	</div>	
	<c:if test="${isQuasarAdmin}">
			</div>
			<div class="clear"></div>	
	</c:if>
</div>
<div id="loader" class="loader"></div>
</body>
</html>