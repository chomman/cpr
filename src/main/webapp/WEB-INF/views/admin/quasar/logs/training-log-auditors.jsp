<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ taglib prefix="quasar"  uri="http://nlfnorm.cz/quasar"%>
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
</c:if>