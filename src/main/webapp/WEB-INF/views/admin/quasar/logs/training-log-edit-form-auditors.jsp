<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<div style="margin-top:20px;" class="${model.log.auditorListSet ? 'qs-valid' : 'qs-invalid' }">
	<div class="qs-bx-wrapp qs-log-items  bg-white">
		<div class=" transparent">
		<jsp:include page="training-log-auditors.jsp" />
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
		</div>
	</div>
</div>