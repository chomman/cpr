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
		(<spring:message code="trainingLog.revision" /> ${model.log.revision})
		</c:if>
		&nbsp; | &nbsp; Status: <strong class="qs-status qs-log-status">${model.log.status}</strong>
		<c:if test="${model.log.totalHours > 0}">
		&nbsp; | &nbsp;	<spring:message code="trainingLog.totals" />: <strong class="qs-training-log-totals"> ${model.log.totalHours}</strong>
		</c:if>
	</h1>

	<div id="content"> 
		<c:if test="${not empty successCreate}">
			<p class="msg ok"><spring:message code="success.create" /></p>
		</c:if>
		
		<c:if test="${not empty successDelete}">
			<p class="msg ok"><spring:message code="success.delete" /></p>
		</c:if>
			
		<c:if test="${model.isEditable or isQuasarAdmin and model.log.status != 'APPROVED'}">
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
					data-status="REFUSED" data-note="<spring:message code="log.refuse.note" arguments="${model.log.auditor.name}" />">
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
		
		<c:if test="${model.log.editable}">
		<div id="form-wrapp">
		<form:form commandName="command" cssClass="training-log valid transparent" htmlEscape="true" >
			<p class="form-head"><spring:message code="baseInformations" /></p>
			<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="trainingLog.subject" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="subject" maxlength="255" cssClass="mw500 required" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="trainingLog.date" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="date" maxlength="10" cssClass="w100 date required" /> 
				</div>
			</div>
			<p class="form-head"><spring:message code="trainingLog.scope" /></p>
			<div class="qs-fields-wrapp">
				<div class="qs-left-bx">
					<div class="input-wrapp smaller">
						<label> <strong><em class="red">*</em> <spring:message
									code="auditor.iso9001" />: </strong>
						</label>
						<div class="field">
							<form:input path="iso9001" cssClass="w50 c required numeric" maxlength="4" />
							<span class="hour"><spring:message code="hours" /></span>
						</div>
					</div>
					<div class="input-wrapp smaller">
						<label><strong><em class="red">*</em> <spring:message
									code="auditor.iso13485" />: </strong>
						</label>
						<div class="field">
							<form:input path="iso13485" cssClass="w50 c required numeric" maxlength="4" />
							<span class="hour"><spring:message code="hours" /></span>
						</div>
					</div>
					<div class="input-wrapp smaller">
						<label> 
							<strong><em class="red">*</em> <spring:message code="auditor.nb1023Procedures" />: </strong>
						</label>
						<div class="field">
							<form:input path="nb1023Procedures"	cssClass="w50 c required numeric" maxlength="4" />
							<span class="hour"><spring:message code="hours" /></span>
						</div>
					</div>
				</div>
				<div class="qs-right-bx qs-border-right">
					<div class="input-wrapp smaller">
						<label> <strong><em class="red">*</em> <spring:message
									code="auditor.mdd" />: </strong>
						</label>
						<div class="field">
							<form:input path="mdd" cssClass="w50 c required numeric" maxlength="4" />
							<span class="hour"><spring:message code="hours" /></span>
						</div>
					</div>	
					<div class="input-wrapp smaller">
						<label> <strong><em class="red">*</em> <spring:message
									code="auditor.ivd" />: </strong>
						</label>
						<div class="field">
							<form:input path="ivd" cssClass="w50 c required numeric" maxlength="4" />
							<span class="hour"><spring:message code="hours" /></span>
						</div>
					</div>
					<div class="input-wrapp smaller">
						<label> 
							<strong><em class="red">*</em> <spring:message code="auditor.aimd" />: </strong>
						</label>
						<div class="field">
							<form:input path="aimd" cssClass="w50 c required numeric" maxlength="4" />
							<span class="hour"><spring:message code="hours" /></span>
						</div>
					</div>
					
				</div>
				<div class="clear"></div>
				<div class="qs-log-items no-margin">
					<p class="form-head mini"><spring:message code="trainingLog.cst" /></p>
					<c:if test="${not empty model.log.categorySpecificTrainings }">
						<table class="data">
							<thead>
								<tr>
									<th><spring:message code="nandoCode.code" /></th>
									<th><spring:message code="nandoCode.specification" /></th>
									<th><spring:message code="hours" /></th>
									<c:if test="${model.isEditable}">
										<th>&nbsp;</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${model.log.categorySpecificTrainings}" var="i">
								<tr>
								<td class="w100 c">${i.nandoCode.code}</td>
								<td title="${i.nandoCode.specification}"
								>${nlf:crop(i.nandoCode.specification, 100)}</td>
								<td class="w100">
									<strong>${i.hours} <spring:message code="hours" /></strong>
								</td>
								<c:if test="${model.isEditable}">
									<td class="unassign delete">
										<a class="confirmUnassignment" href="?action=${model.codeRemove}&amp;iid=${i.id}">
											<spring:message code="trainingLog.unassign" />
										</a>
									</td>
								</c:if>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<a class="add-cst" href="#">
						<spring:message code="trainingLog.cst.add" /> +
					</a>
				</div>
			</div>
			<form:hidden path="id"/>
			<p class="button-box">
			<input type="submit" class="button" value="<spring:message code="form.save" />" />
			</p>    
		</form:form>	
			<c:if test="${not empty model.unassignedNandoCodes}">
				<div id="add-code-form">
					<form class="inline-form valid">
						<div class="inline-field">
							<select name="iid" class="required">
								<option value="">
									-- <spring:message code="trainingLog.cst.nandoCode" /> --
								</option>
								<c:forEach items="${model.unassignedNandoCodes}" var="i">
									<option value="${i.id}">${i.code} / ${i.specification }</option>
								</c:forEach>
							</select>
						</div>
						<div class="inline-field">
							<span class="label"><spring:message code="hours" />:</span>
							<input type="text" name="hours" class="c w40 numeric required" />
						</div>
						<input type="submit" class="lang mandate-add-btn" value="<spring:message code="add" />" />
						<a href="#" class="cancel smaller qs-btn">
							<spring:message code="cancel" />
						</a>
						<input type="hidden" name="action" value="${model.codeAdd}" />
					</form>
				</div>
			</c:if>
		
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
		</div>
		</c:if>
		
	
		
		<div class="qs-bx-wrapp qs-log-items transparent">
			<p class="form-head"><spring:message code="trainingLog.attendanceList" /></p>
			<c:if test="${not empty model.log.auditors}">
				<table class="data">
					<thead>
						<tr>
							<th><spring:message code="auditor.itcId" /></th>
							<th><spring:message code="auditor.name" /></th>
							<th><spring:message code="auditor.partner" /></th>
							<c:if test="${isQuasarAdmin}">
							<th><spring:message code="auditor.rating" /></th>
							</c:if>
							<c:if test="${model.isEditable and model.isManager}">
								<th>&nbsp;</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.log.auditors}" var="i">
							<tr>  
								<td class="w50 code c">${i.itcId}</td>	 
								<td>
									<c:if test="${isQuasarAdmin}">
										<quasar:auditor auditor="${i}" withDegree="true" />
									</c:if>
									<c:if test="${not isQuasarAdmin}">
										${i.nameWithDegree}
									</c:if>
									<c:if test="${i.id == common.user.id }">
										<strong>(YOU)</strong>
									</c:if>
								</td>
								<td>
									<c:if test="${not empty i.partner}">
										${i.partner.name}
									</c:if>
								</td>
								<c:if test="${isQuasarAdmin}">
									<td class="last-edit">
										<div class="rating" data-rating="${i.rating}"></div>
									</td>
								</c:if>
								<c:if test="${model.isEditable and model.isManager}">
									<td class="unassign delete">
										<a class="confirmUnassignment" href="?action=${model.unassign}&amp;iid=${i.id}">
											<spring:message code="trainingLog.unassign" />
										</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${model.isEditable and model.isManager and not empty model.unassignedAuditors}">
					<form class="inline-form">
						<div class="inline-field">
							<span class="label">
								<spring:message code="trainingLog.assign" />:
							</span>
							<select name="iid" class="chosenSmall">
								<c:forEach items="${model.unassignedAuditors}" var="i">
									<option value="${i.id}">${i.nameWithDegree}</option>
								</c:forEach>
							</select>
						</div>
						<input type="submit" class="lang mandate-add-btn" value="<spring:message code="add" />" />
						<input type="hidden" name="action" value="${model.assign}" />
					</form>
				</c:if>
			</c:if>
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