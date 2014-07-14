<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ taglib prefix="quasar"  uri="http://nlfnorm.cz/quasar"%>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="auditors" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
	<script src="<c:url value="/resources/admin/js/scripts.quasar.js" />"></script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
				 <span><spring:message code="auditors" /></span>
			</div>
			<h1><spring:message code="auditors" /></h1>
	
			<div id="content">
								
				<jsp:include page="navs/auditor-code-nav.jsp" />
				
				
				<form class="filter user" method="get">
				<div>
					<span class="long filter-label"><spring:message code="form.orderby" />:</span>
					<select name="orderBy" class="chosenMini">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
						</c:forEach>
					</select>
					<span class="filter-label"><spring:message code="auditor.reassessmentDate.short" /> from:</span>
					<input type="text" class="date"  name="dateFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span class="filter-label">to:</span>
					<input type="text" class="date" name="dateTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
					
				</div>
				<div>
					<span class="long filter-label"><spring:message code="user.activated" />:</span>
					<select name="enabled" class="enabled chosenMini">
							<option value=""  <c:if test="${empty model.params.enabled}" >selected="selected"</c:if> ><spring:message code="notmatter" /></option>
							<option value="1" <c:if test="${model.params.enabled}" >selected="selected"</c:if> ><spring:message code="yes"/></option>
							<option value="0" <c:if test="${not empty model.params.enabled and not model.params.enabled}" >selected="selected"</c:if> ><spring:message code="no"/></option>
					</select>
					<span class="filter-label"><spring:message code="auditor.inTraining" />:</span>
					<select name="inTraining" class="enabled chosenMini">
							<option value=""  <c:if test="${empty model.params.inTraining}" >selected="selected"</c:if> ><spring:message code="notmatter" /></option>
							<option value="1" <c:if test="${model.params.inTraining}" >selected="selected"</c:if> ><spring:message code="yes"/></option>
							<option value="0" <c:if test="${not empty model.params.inTraining and not model.params.inTraining}" >selected="selected"</c:if> ><spring:message code="no"/></option>
					</select>
					<span class="filter-label"><spring:message code="auditor.head.formalRequirements" />:</span>
					<select name="formalLegal" class="enabled chosenMini">
							<option value=""  <c:if test="${empty model.params.formalLegal}" >selected="selected"</c:if> ><spring:message code="notmatter" /></option>
							<option value="1" <c:if test="${model.params.formalLegal}" >selected="selected"</c:if> ><spring:message code="complaint"/></option>
							<option value="0" <c:if test="${not empty model.params.formalLegal and not model.params.formalLegal}" >selected="selected"</c:if> ><spring:message code="nonComplaint"/></option>
					</select>
				</div>
				<div>
					<span class="long filter-label"><spring:message code="form.name" />:</span>
					<input type="text" class="query" name="query"   value="${model.params.query}" />
					<span class="filter-label"><spring:message code="auditor.partner" />:</span>
					<select name="partner" class="chosenSmall">
						<option value=""><spring:message code="notmatter" /></option>
						<c:forEach items="${model.partners}" var="i">
							<option value="${i.id}" ${model.params.partner eq i.id ? 'selected="selected"' : ''}>${i.name}</option>
						</c:forEach>
					</select>
					<input type="submit" value="Filter" class="btn" />
				</div>
			</form>		
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
				</c:if>
				
							
				<c:if test="${not empty model.auditors}">
					
					<!-- STRANKOVANIE -->
					<c:if test="${not empty model.paginationLinks}" >
						<div class="pagination">
						<c:forEach items="${model.paginationLinks}" var="i">
							<c:if test="${not empty i.url}">
								<a title="Stánka č. ${i.anchor}"  class="tt"  href="<c:url value="${i.url}"  />">${i.anchor}</a>
							</c:if>
							<c:if test="${empty i.url}">
								<span>${i.anchor}</span>
							</c:if>
						</c:forEach>
						</div>
					</c:if>
															
					<table class="data">
						<thead>
							<tr>
								<th><spring:message code="auditor.itcId" /></th>
								<th><spring:message code="auditor.name" /></th>
								<th><spring:message code="auditor.reassessmentDate.short" /></th>
								<th><spring:message code="auditor.head.formalRequirements" /></th>
								<th>Changed</th>
								<th>Activated</th>
								<th>&nbsp;</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.auditors}" var="i">
								<tr class="${i.enabled ? '' : 'is-disabled'}"> 
									<td class="w50 code c">${i.itcId}</td>	 
									<td>
									<quasar:auditor auditor="${i}" />
									</td>
									<td class="last-edit">
										<c:if test="${not empty i.reassessmentDate}">
											<joda:format value="${i.reassessmentDate}" pattern="dd.MM.yyyy"/>
										</c:if>
										<c:if test="${empty i.reassessmentDate}">
											-
										</c:if>
									</td>
									<td class="w50 c ${i.areFormalAndLegalReqiurementValid ? 'scope-yes' : 'scope-no'}">
										<c:if test="${i.areFormalAndLegalReqiurementValid}">
											<spring:message code="complaint" />
										</c:if>
										<c:if test="${not i.areFormalAndLegalReqiurementValid}">
											<spring:message code="nonComplaint" />
										</c:if>
									</td>
									<td class="last-edit">
										<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
									</td>
									<td class="w100">
										<c:if test="${i.enabled}">
											<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
												Yes
											</span>
										</c:if>
										<c:if test="${not i.enabled}">
											<span class="published no tt" title="<spring:message code="published.no.title" />" >
												No
											</span>
										</c:if>
									</td>		
									<td class="edit">
										<a:adminurl href="/quasar/manage/auditor/${i.id}">
											<spring:message code="quasar.edit" />
										</a:adminurl>
									</td>
								</tr>
							
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				
				<c:if test="${empty model.auditors}">
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