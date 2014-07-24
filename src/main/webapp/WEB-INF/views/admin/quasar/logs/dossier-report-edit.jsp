<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:set var="isQuasarAdmin" value="${common.user.quasarAdmin}" scope="request" />
<!DOCTYPE html>
<html>
<head>
	<title>${model.log.auditor.name}'s <spring:message code="dossierReport" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.tagit.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/tagit.ui-zendesk.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" /> 
	<script src="<c:url value="/resources/admin/js/tag-it.min.js" />"></script>
	<script src="<c:url value="/resources/admin/quasar/js/scripts.quasar.auditLog.js" />"></script>
</head>
<body>
<div id="wrapper" data-type="dossier-report">
		<c:if test="${isQuasarAdmin}">
			<div id="left">
				<jsp:include page="../../include/quasar-nav.jsp" />
			</div>	
			<div id="right">
		</c:if>
	<div id="breadcrumb">
		 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
		 <a:adminurl href="/quasar/dossier-reports"><spring:message code="dossierReports" /></a:adminurl>  &raquo;
		 <span><strong>${model.log.auditor.name}'s</strong> <spring:message code="dossierReport" /></span>
	</div>
	<h1 class="qs-log-status-${model.log.status.id}">
		<strong>${model.log.auditor.name}'s</strong> <spring:message code="dossierReport" />: 
		<strong><joda:format value="${model.log.created}" pattern="dd.MM.yyyy"/></strong> 
		<c:if test="${model.log.revision > 1}">
		(<spring:message code="auditLog.auditLog.revision" /> ${model.log.revision})
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
		
		<c:if test="${not empty companyFound}">
			<p class="msg alert">
				<spring:message code="log.alert.companyFound" arguments="${companyFound}"  argumentSeparator=";"/>
			</p>
		</c:if>
		
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
					<th>
						<spring:message code="dossierReport.item.certificateNo" />
						<spring:message code="auditLog.item.orderNo" />
					</th>
					<th><spring:message code="dossierReport.item.date" /></th>
					<th><spring:message code="dossierReport.item.applicant" /></th>
					<th><spring:message code="auditLog.item.certifiedProduct" /></th>
					<th>Type</th>
					<th><spring:message code="dossierReport.item.category" /></th>
					<th><spring:message code="auditLog.item.nandoCodes" /></th>
					<c:if test="${model.log.editable}">
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					</c:if>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${model.log.items}" var="i" varStatus="s">
						<tr>
							<td class="nos">
								<span class="cert-no">
									${i.cerfication}
								</span>
								<span class="order-no">
									${i.orderNo}
								</span>
							</td>
							<td class="c qsd"><joda:format value="${i.auditDate}" pattern="dd.MM.yyyy" /></td>
							<td>${i.company.name}</td>
							<td>${i.certifiedProduct}</td>
							<td class="c w40 dd-${i.designDossier}">
								<c:if test="${i.designDossier}">
									<span class="tt" title="<spring:message code="designDossier" />">DD</span>
								</c:if>
								<c:if test="${not i.designDossier}">
									<span class="tt" title="<spring:message code="technicalFile" />">TF</span>
								</c:if>
							</td>
							<td class="c w40">${i.category.name}</td>
							<td class="c">
								<c:forEach items="${i.nandoCodes}" var="j">
									<span class="qsc tt" title="${j.specification}">${j.code}</span>
								</c:forEach>
							</td>
							<c:if test="${model.log.editable}">
							<td class="edit">
								<a href="?iid=${i.id}">
									<spring:message code="quasar.edit" />
								</a>
							</td>
							<td class="delete">
								<a:adminurl href="/quasar/dossier-report-item/delete/${i.id}" cssClass="confirm">
									<spring:message code="quasar.delete" />
								</a:adminurl>
							</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="qs-totals">
			 	<spring:message code="technicalFile" />s: <strong>${model.log.coundOfTechnicalFiles}</strong>
			 	<spring:message code="designDossier" />s: <strong>${model.log.coundOfDesignDossiers}</strong>
			</div>
			</c:if>
		</div>
	
		<jsp:include page="log-nav.jsp" />
				
		<!--ADD CHANGE ITEM  -->
		<c:if test="${model.log.editable and model.showForm}">
		<form:form commandName="command" cssClass="dossierReport" htmlEscape="true">
			<p class="form-head"><spring:message code="dossierReport.item" /></p>
			<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="dossierReport.item.date" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="item.auditDate" maxlength="10" cssClass="qs-date required" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="dossierReport.item.certificateNo" />+ Suffix:
					</strong>
				</label>
				<div class="field">
					<form:input path="item.certificationNo" id="certificationNo" maxlength="7" cssClass="qs-cert-no numeric required w100 c" placeholder="Cert. number" />
					<select class="chosenMini required bind-change" name="item.certificationSufix" id="certificationSufix">
						<option value="">Choose</option>
						<c:forEach items="${model.suffixies}" var="i">
							<option value="${i.name}" ${i.name eq command.item.certificationSufix ? 'selected="selected"' : ''}>
								${i.name}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="input-wrapp smaller order-no">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.orderNo" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="item.orderNo" id="orderNo" maxlength="9" cssClass="mw150 numeric required"  />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="dossierReport.item.category" />:
					</strong>
				</label>
				<div class="field">
					<select class="chosenSmall required bind-change" name="item.category" id="category">
						<option value=""><spring:message code="form.select" /></option>
						<c:forEach items="${model.categories}" var="i">
							<option value="${i}" ${i eq command.item.category ? 'selected="selected"' : ''}>
								${i.name}
							</option>
						</c:forEach>
					</select>
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
					<spring:message code="dossierReport.item.applicant" />:
					</strong>
				</label>
				<div class="field">
					<select name="item.company" id="companySelect" data-msg="<spring:message code="form.select" />" data-id="${empty command.item.company ? '0' : command.item.company.id}">
					</select>
					<a class="toggle">Company not found? Create new</a>
				</div>
			</div>
			
			<p class="form-head mini nando-code-wrapp">NANDO Codes</p>
			<div class="input-wrapp smaller pj-type nando-code-wrapp">
				<label>
					<spring:message code="auditLog.item.nandoCodes" />:
					<small>Press ENTER to insert code</small>
				</label>
				<div class="field tags-wrapp">
					<ul id="nandoCodes"><c:forEach items="${command.item.nandoCodes}" var="i"><li>${i.code}</li></c:forEach></ul>
				</div>
			</div>
			<form:hidden path="nandoCodes" id="hNandoCodes"/>
			<form:hidden path="item.id" />
			<form:hidden path="logId" />
			<p class="button-box nando-code-wrapp ">
			<input type="submit" class="button" value="<spring:message code="form.save" />" />
			<a:adminurl href="/quasar/dossier-report/${model.log.id}" cssClass="cancel qs-btn">
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