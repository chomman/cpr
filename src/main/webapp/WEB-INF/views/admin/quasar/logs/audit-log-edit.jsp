<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:set  var="isQuasarAdmin" value="${common.user.quasarAdmin}" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<title>${model.log.auditor.name}'s <spring:message code="auditLog.auditLog"/></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.tagit.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/tagit.ui-zendesk.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" /> 
	<script src="<c:url value="/resources/admin/js/tag-it.min.js" />"></script>
	<script src="<c:url value="/resources/admin/quasar/js/audit-log.js" />"></script>
	<link rel="stylesheet" href="<c:url value="/resources/admin/quasar/css/jquery.raty.css" />" />
	<script src="<c:url value="/resources/admin/quasar/js/jquery.raty.js" />"></script>
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
		 <span>${model.log.auditor.name}'s <spring:message code="auditLog.auditLog"/></span>
	</div>
	<h1 class="qs-log-status-${model.log.status.id}">
		${model.log.auditor.name}'s <spring:message code="auditLog.auditLog"/>: 
		<strong><joda:format value="${model.log.created}" pattern="dd.MM.yyyy"/></strong> 
		<c:if test="${model.log.revision > 1}">
		(<spring:message code="auditLog.auditLog.revision" /> ${model.log.revision})
		</c:if>
		| Status: <strong class="qs-status qs-log-status">${model.log.status}</strong>
		<c:if test="${not empty model.log.rating}">
		| <spring:message code="auditor.rating" />:
			 <span class="rating smaller" data-rating="${model.log.rating}"></span>
			 <span class="rating-descr"></span>
		</c:if>
	</h1>

	<div id="content"> 
		
		<jsp:include page="log-request-statuses.jsp" />
		<jsp:include page="log-comments.jsp" />
		
		<div class="qs-bx-wrapp qs-log-items">
			<p class="form-head"><spring:message code="auditLog.auditLog.items" /></p>
			<c:if test="${empty model.log.items}">
				<p class="msg alert"><spring:message code="log.empty" /></p>
			</c:if>
			<c:if test="${not empty model.log.items}">
			<table class="data">
				<thead>
				<tr>
					<th>No.</th>
					<th><spring:message code="auditLog.item.date" /></th>
					<th><spring:message code="auditLog.item.company" /></th>
					<th><spring:message code="auditLog.item.certifiedProduct" /></th>
					<th><spring:message code="auditLog.item.eacCodes" /></th>
					<th><spring:message code="auditLog.item.nandoCodes" /></th>
					<th><spring:message code="auditLog.item.duration" /></th>
					<th><spring:message code="auditLog.item.certificationBody" /></th>
					<th><spring:message code="auditLog.item.orderNo" /></th>
					<c:if test="${model.log.editable}">
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					</c:if>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${model.log.items}" var="i" varStatus="s">
						<tr>
							<td class="c">${s.index + 1}</td>
							<td class="c qsd"><joda:format value="${i.auditDate}" pattern="dd.MM.yyyy" /></td>
							<td>${i.company.name}</td>
							<td>${i.certifiedProduct}</td>
							<td class="c">
								<c:forEach items="${i.eacCodes}" var="j">
									<span class="qsc tt" title="${j.name}">${j.code}</span>
								</c:forEach>
							</td>
							<td class="c">
								<c:forEach items="${i.nandoCodes}" var="j">
									<span class="qsc tt" title="${j.specification}">${j.code}</span>
								</c:forEach>
							</td>
							<td class="c qs-duration">${i.durationInDays}</td>
							<td>${i.certificationBody.name}</td>
							<td class="c">${i.orderNo}</td>
							<c:if test="${model.log.editable}">
							<td class="edit">
								<a href="?iid=${i.id}">
									<spring:message code="quasar.edit" />
								</a>
							</td>
							<td class="delete">
								<a:adminurl href="/quasar/audit-log-item/delete/${i.id}" cssClass="confirm">
									<spring:message code="quasar.delete" />
								</a:adminurl>
							</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="qs-totals">
			 	<spring:message code="auditLog.auditDays" />: <strong>${model.log.sumOfAuditDays }</strong>
			 	<spring:message code="auditLog.audits" />: <strong>${model.log.countOfAudits }</strong>
			</div>
			</c:if>
		</div>
	
		<jsp:include page="log-nav.jsp" />
				
		<c:if test="${not empty model.totals}">
			<div class="qs-log-totals qs-bx-wrapp">
				<p class="form-head">Totals of EAC and NANDO code occurrences</p>
				<c:if test="${not empty model.totals.eacCodes}">
					<div class="qs-left-bx">
					<table class="data">
						<tr>
							<th><spring:message code="eacCode.code" /></th>
							<th>SUM of <spring:message code="auditLogItemType.iso13485" /> + <spring:message code="auditLogItemType.iso9001" /> </th>
						</tr>
						<c:forEach items="${model.totals.eacCodes}" var="entry">
							<tr>
								<td>${entry.key.code}</td>
								<td><strong>${entry.value.total}</strong> = ${entry.value.numberOfIso13485Audits} + ${entry.value.numberOfNbAudits}</td>
							</tr>
						</c:forEach>
					</table>
					</div>
				</c:if>
				<c:if test="${not empty model.totals.nandoCodes}">
					<div class="qs-right-bx">
						<table class="data">
							<tr>
								<th><spring:message code="nandoCode.code" /></th>
								<th>SUM of <spring:message code="auditLogItemType.iso13485" /> + <spring:message code="auditLogItemType.iso9001" /> </th>
							</tr>
							<c:forEach items="${model.totals.nandoCodes}" var="entry">
								<tr>
									<td>${entry.key.code}</td>
									<td><strong>${entry.value.total}</strong> = ${entry.value.numberOfIso13485Audits} + ${entry.value.numberOfNbAudits}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<div class="clear"></div>
			</div>
		</c:if>
		
		
		<!--ADD CHANGE ITEM  -->
		<c:if test="${model.log.editable and model.showForm}">
		<form:form commandName="command" cssClass="auditLog" htmlEscape="true" >
			<p class="form-head"><spring:message code="auditLog.item" /></p>
			<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.date" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="item.auditDate" maxlength="10" cssClass="qs-date required" />
				</div>
			</div>
			
			<!-- COMPANY SELECT -->
			<div class="input-wrapp smaller qs-new-company">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.company" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="companyName" maxlength="50" cssClass="mw500 required" data-url="companies" />
					<a class="toggle ">Choose existing Company</a>
				</div>
			</div>
			<div class="input-wrapp smaller qs-existing-company">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.company.select" />:
					</strong>
				</label>
				<div class="field">
					<select name="item.company" id="companySelect" data-msg="<spring:message code="form.select" />" data-id="${empty command.item.company ? '0' : command.item.company.id}">
					</select>
					<a class="toggle">Company not found? Create new</a>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.certifiedProduct" />:
					</strong>
				</label>
				<div class="field">
					<div id="chars" class="chars"></div>
					<form:textarea cssClass="required mw500 limit"  path="item.certifiedProduct"/>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.duration" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="item.durationInDays" maxlength="3" cssClass="numeric w40 c required" />
				</div>
			</div>
			
			<!-- CLASSIFICATION BODY SELECT -->
			<div class="input-wrapp smaller qs-new-certification-bodies">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.certificationBody" />:
					</strong>
					<small>e.g.: ITC, Loyd, Turkak, ...</small>
				</label>
				<div class="field">
					<form:input path="certificationBodyName" maxlength="60" cssClass="mw150" data-url="certification-bodies" />
					<a class="toggle ">Choose existing Certification Body</a>
				</div>
			</div>
			<div class="input-wrapp smaller qs-existing-certification-bodies">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.certificationBody.select" />:
					</strong>
				</label>
				<div class="field">
					<select class="chosenSmall" name="item.certificationBody" id="certificationBody" data-msg="<spring:message code="form.select" />" 
					data-id="${empty command.item.certificationBody ? '0' : command.item.certificationBody.id}">
					</select>
					<a class="toggle">Certification Body not found? Create new</a>
				</div>
			</div>
			
			
			<div class="input-wrapp smaller order-no">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.orderNo" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="item.orderNo" maxlength="9" cssClass="mw150 numeric"  />
				</div>
			</div>
			
			
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.type" />:
					</strong>
				</label>
				<div class="field">
					<select class="chosenSmall" name="item.type">
						<c:forEach items="${model.auditLogItemTypes}" var="i">
							<option value="${i}" ${i eq command.item.type ? 'selected="selected"' : ''}>
								<spring:message code="${i.code}" />
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<p class="form-head mini">EAC / NANDO Codes</p>
			<div class="input-wrapp smaller  pj-type">
				<label>
					<spring:message code="auditLog.item.eacCodes" />:
					<small>Press ENTER to insert code</small>
				</label>
				<div class="field tags-wrapp">
					<ul id="eacCodes"><c:forEach items="${command.item.eacCodes}" var="i"><li>${i.code}</li></c:forEach></ul>
				</div>
			</div>
			<div class="input-wrapp smaller pj-type">
				<label>
					<spring:message code="auditLog.item.nandoCodes" />:
					<small>Press ENTER to insert code</small>
				</label>
				<div class="field tags-wrapp">
					<ul id="nandoCodes"><c:forEach items="${command.item.nandoCodes}" var="i"><li>${i.code}</li></c:forEach></ul>
				</div>
			</div>
			<form:hidden path="eacCodes" id="hEacCodes"/>
			<form:hidden path="nandoCodes" id="hNandoCodes"/>
			<form:hidden path="item.id" />
			<form:hidden path="logId" />
			<p class="button-box">
			<input type="submit" class="button" value="<spring:message code="form.save" />" />
			<a:adminurl href="/quasar/audit-log/${model.log.id}" cssClass="cancel qs-btn">
				<spring:message code="cancel" />
			</a:adminurl>
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
<div id="minDate" class="hidden"><c:if test="${not empty model.dateThreshold}"><joda:format value="${model.dateThreshold}" pattern="dd.MM.yyyy"/></c:if></div>
<div id="loader" class="loader"></div>

</body>
</html>