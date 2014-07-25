<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
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