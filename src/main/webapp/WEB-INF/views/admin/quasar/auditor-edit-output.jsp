<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<h3 class="qs-function"><strong><spring:message code="summary" /></strong></h3>

<table class="qs-scope">
	<tr>
		<td><spring:message code="auditor.name" />: </td>
		<td><strong>${model.auditor.nameWithDegree}</strong></td>
	</tr>
	<tr>
		<td><spring:message code="auditor.itcId" />: </td>
		<td><strong>${model.auditor.itcId}</strong></td>
	</tr>
	<tr>
		<td><spring:message code="auditor.reassessmentDate.short" />: </td>
		<td><strong>
			<c:if test="${not empty model.auditor.reassessmentDate}">
				<joda:format value="${model.auditor.reassessmentDate}" pattern="dd.MM.yyyy"/>
			</c:if>
			<c:if test="${empty model.auditor.reassessmentDate}">
				-
			</c:if>
		</strong></td>
	</tr>
</table>


<div class="qs-scope">
	<h2><spring:message code="qsAuditor.scope" /></h2>
	<textarea class="mw500 mh100">${model.eFunctions.qsAuditor.grantedCodes}</textarea>
</div>

<div class="qs-scope">
	<h2><spring:message code="productAssessorA.scope" /></h2>
	<textarea class="mw500 mh100">${model.eFunctions.productAssessorA.grantedCodes}</textarea>
</div>

<div class="qs-scope">
	<h2><spring:message code="productAssessorR.scope" /></h2>
	<textarea class="mw500 mh100">${model.eFunctions.productAssessorR.grantedCodes}</textarea>
</div>

<div class="qs-scope">
	<h2><spring:message code="productSpecialist.scope" /></h2>
	<textarea class="mw500 mh100">${model.eFunctions.productSpecialist.grantedCodes}</textarea>
</div>