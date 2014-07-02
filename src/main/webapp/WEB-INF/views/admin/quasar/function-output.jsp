<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ taglib prefix="quasar" uri="http://nlfnorm.cz/quasar"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<title><spring:message code="auditors" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/quasar/css/styles.css" />" />
	<link rel="stylesheet" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css" />
	<link rel="stylesheet" href="//cdn.datatables.net/fixedcolumns/3.0.1/css/dataTables.fixedColumns.css" />
		
	<script src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.min.js"></script>
	<script src="//cdn.datatables.net/fixedcolumns/3.0.1/js/dataTables.fixedColumns.min.js"></script>
	<script src="<c:url value="/resources/admin/quasar/js/grid.js" />"></script>

</head>
<body>
	<div class="qs-nav">
			<strong>
				<c:if test="${model.functionType == 2}">
					<spring:message code="auditor.qsAuditor" />
				</c:if>
				<c:if test="${model.functionType == 3}">
					<spring:message code="auditor.productAssessorA" />
				</c:if>
				<c:if test="${model.functionType == 4}">
					<spring:message code="auditor.productAssessorR" />
				</c:if>
				<c:if test="${model.functionType == 5}">
					<spring:message code="auditor.productSpecialist" />
				</c:if>
			</strong>
			<form class="filter user" method="get">
					<span class="filter-label"><spring:message code="form.orderby" />:</span>
					<select name="orderBy" class="enabled chosenMini">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
						</c:forEach>
					</select>
					<span class="filter-label"><spring:message code="auditor.inTraining" />:</span>
					<select name="inTraining" class="enabled chosenMini">
							<option value=""  <c:if test="${empty model.params.inTraining}" >selected="selected"</c:if> ><spring:message code="notmatter" /></option>
							<option value="1" <c:if test="${model.params.inTraining}" >selected="selected"</c:if> ><spring:message code="yes"/></option>
							<option value="0" <c:if test="${not empty model.params.inTraining and not model.params.inTraining}" >selected="selected"</c:if> ><spring:message code="no"/></option>
					</select>
					<span class="filter-label"><spring:message code="auditor.partner" />:</span>
					<select name="partner" class="enabled chosenMini">
						<option value=""><spring:message code="notmatter" /></option>
						<c:forEach items="${model.partners}" var="i">
							<option value="${i.id}" ${model.params.partner eq i.id ? 'selected="selected"' : ''}>${i.name}</option>
						</c:forEach>
					</select>
					<input type="submit" value="Filter" class="btn" />
			</form>	
			<a:adminurl href="/quasar/manage/auditors" cssClass="close-btn">
				<spring:message  code="close"/>
			</a:adminurl>
	</div>
	<div class="container">
		<c:if test="${model.functionType > 1 and model.functionType < 6}">
			<quasar:auditorsGrid list="${model.items}" />
		</c:if>
	</div>
</body>
</html>