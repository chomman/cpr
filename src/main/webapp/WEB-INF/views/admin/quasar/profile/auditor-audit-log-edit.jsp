<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="auditLog.edit" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.tagit.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/tagit.ui-zendesk.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" /> 
	<script src="<c:url value="/resources/admin/js/tag-it.min.js" />"></script>
	<script src="<c:url value="/resources/admin/quasar/js/scripts.quasar.auditLog.js" />"></script>
</head>
<body>
<div id="wrapper">
	<div id="breadcrumb">
		 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
		 <a:adminurl href="/quasar/audit-logs"><spring:message code="auditLogs" /></a:adminurl>  &raquo;
		 <span><spring:message code="auditLog.edit" /></span>
	</div>
	<h1>
		<spring:message code="auditLog.edit" />: 
		<strong><joda:format value="${model.auditLog.created}" pattern="dd.MM.yyyy"/></strong>
	</h1>

	<div id="content"> 
									
		<c:if test="${not empty successCreate}">
			<p class="msg ok"><spring:message code="success.create" /></p>
		</c:if>
		
		<c:if test="${not empty successDelete}">
			<p class="msg ok"><spring:message code="success.delete" /></p>
		</c:if>
		
		<div class="qs-bx-wrapp qs-log-items">
			<p class="form-head"><spring:message code="auditLog.auditLog.items" /></p>
			<c:if test="${empty model.auditLog.items}">
				<p class="msg alert"><spring:message code="log.empty" /></p>
			</c:if>
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
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${model.auditLog.items}" var="i" varStatus="s">
						<tr>
							<td class="c">${s.index + 1}</td>
							<td class="c qsd"><joda:format value="${i.auditDate}" pattern="dd.MM.yyyy" /></td>
							<td>${i.company.name}</td>
							<td>${i.certifiedProduct}</td>
							<td class="c">
								<c:forEach items="${i.eacCodes}" var="j">
									<span class="qsc">${j.code}</span>
								</c:forEach>
							</td>
							<td class="c">
								<c:forEach items="${i.nandoCodes}" var="j">
									<span class="qsc">${j.code}</span>
								</c:forEach>
							</td>
							<td class="c qs-duration">${i.durationInDays}</td>
							<td>${i.certificationBody.name}</td>
							<td class="c">${i.orderNo}</td>
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
						</tr>
					</c:forEach>
				</tbody>
			</table>
			 <div class="qs-totals">
			 	<spring:message code="auditLog.auditDays" />: <strong>${model.auditLog.sumOfAuditDays }</strong>
			 	<spring:message code="auditLog.audits" />: <strong>${model.auditLog.countOfAudits }</strong>
			 </div>
		</div>
		
		
		
		<form:form commandName="command" cssClass="auditLog">
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
			<div class="input-wrapp smaller  pj-type">
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
			<p class="button-box">
			<input type="submit" class="button" value="<spring:message code="form.save" />" />
			</p>    
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>	    
		</form:form>
		
	</div>	
</div>
<div id="minDate" class="hidden"><c:if test="${not empty model.dateThreshold}"><joda:format value="${model.dateThreshold}" pattern="dd.MM.yyyy"/></c:if></div>
<div id="loader" class="loader"></div>

</body>
</html>