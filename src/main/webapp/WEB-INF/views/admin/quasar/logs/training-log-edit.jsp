<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
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
		 <a:adminurl href="/quasar/audit-logs"><spring:message code="auditLogs" /></a:adminurl>  &raquo;
		 <span>${model.log.auditor.name}'s <spring:message code="trainingLog"/></span>
	</div>
	<h1 class="qs-log-status-${model.log.status.id}">
		${model.log.auditor.name}'s <spring:message code="trainingLog"/>: 
		<strong><joda:format value="${model.log.created}" pattern="dd.MM.yyyy"/></strong> 
		<c:if test="${model.log.revision > 1}">
		(<spring:message code="trainingLog.revision" /> ${model.log.revision})
		</c:if>
		&nbsp; | &nbsp; Status: <strong class="qs-status qs-log-status">${model.log.status}</strong>
	</h1>

	<div id="content"> 
		<c:if test="${not empty successCreate}">
			<p class="msg ok"><spring:message code="success.create" /></p>
		</c:if>
		
		<c:if test="${not empty successDelete}">
			<p class="msg ok"><spring:message code="success.delete" /></p>
		</c:if>
			
		<c:if test="${not empty model.log.comments}">
			<a class="qs-show-comments">Show users comments <strong>(${model.log.countOfComments})</strong> </a>
			<div class="qs-comments hidden">
					<c:forEach items="${model.log.comments}" var="i">
						<div>
							${i.comment}
							<span class="qs-meta">
								<joda:format value="${i.created}" pattern="dd.MM.yyyy / HH:mm"/>, 
								${i.user.name}
							</span>
						</div>
					</c:forEach>
			</div>
		</c:if>
				
		
		<c:if test="${model.log.editable}">
		<form:form commandName="command" cssClass="training-log" >
			<p class="form-head"><spring:message code="baseInformations" /></p>
			<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="trainingLog.subject" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="subject" maxlength="200" cssClass="mw500 required" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="trainingLog.date" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="date" maxlength="10" cssClass="date required" />
				</div>
			</div>
			<p class="form-head"><spring:message code="trainingLog.scope" /></p>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.iso9001" />: </strong>
				</label>
				<div class="field">
					<form:input path="iso9001" cssClass="w50 c required numeric" maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><strong><em class="red">*</em> <spring:message
							code="auditor.iso13485" />: </strong>
				</label>
				<div class="field">
					<form:input path="iso13485" cssClass="w50 c required numeric" maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.ivd" />: </strong>
				</label>
				<div class="field">
					<form:input path="ivd" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.aimd" />: </strong>
				</label>
				<div class="field">
					<form:input path="aimd" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.nb1023Procedures" />: </strong>
				</label>
				<div class="field">
					<form:input path="nb1023Procedures"
						cssClass="w50 c required numeric" maxlength="4" />
				</div>
			</div>
			
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.mdd" />: </strong>
				</label>
				<div class="field">
					<form:input path="mdd" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>	
			
			<form:hidden path="id"/>
			<p class="button-box">
			<input type="submit" class="button" value="<spring:message code="form.save" />" />
			</p>    
		</form:form>
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
		</c:if>
	</div>	
	<c:if test="${isQuasarAdmin}">
			</div>
			<div class="clear"></div>	
	</c:if>
</div>
<div id="loader" class="loader"></div>
</body>
</html>