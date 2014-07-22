<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!-- NAVIGATION  -->
<c:if test="${model.log.editable or isQuasarAdmin and model.log.status != 'APPROVED'}">
<div class="qs-log-nav">
	<span>
		<strong><spring:message code="options" />:</strong>
	</span>
		<c:if test="${model.log.editable}">
			<a class="qs-btn qs-new" href="?iid=0">
				<spring:message code="auditLog.item.create" /> <strong>+</strong>
			</a>
			<c:if test="${not model.showForm and not empty model.log.items}">
			<a href="#change-status" class="qs-btn qs-change-status-btn qs-submit-for-approval"  data-cls="qs-submit-for-approval" 
				data-status="PENDING" data-note="<spring:message code="log.penging.note" />">
				<spring:message code="log.penging" /> &raquo;
			</a>
			</c:if>
		</c:if>
		<c:if test="${not model.log.editable and isQuasarAdmin}">
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
<c:if test="${not empty model.log.items}">
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