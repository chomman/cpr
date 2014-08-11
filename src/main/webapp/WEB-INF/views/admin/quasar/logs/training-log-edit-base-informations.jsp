<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<div class="qs-bx-wrapp qs-log-items  bg-white">
<div class=" transparent">
<form:form commandName="command" cssClass="training-log valid bg-white" htmlEscape="true" cssStyle="margin-bottom:20px;">
	<p class="form-head"><spring:message code="baseInformations" /></p>
	<div class="input-wrapp smaller">
		<label>
			<strong><em class="red">*</em>
			<spring:message code="trainingLog.subject" />:
			</strong>
		</label>
		<div class="field">
			${model.log.subject}
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label>
			<spring:message code="trainingLog.date" />:
		</label>
		<div class="field">
			<c:if test="${not empty model.log.date}">
				<joda:format value="${model.log.date}" pattern="dd.MM.yyyy"/>
			</c:if> 
		</div>
	</div>
	<c:if test="${not empty model.log.description}">
		<div class="input-wrapp">
			<label>
				<spring:message code="trainingLog.description" />:
			</label>
			<div class="field">
				<div class="description-wrapp max-height-200">
					${model.log.description}
					<a class="show-more">Show more ...</a>
				</div>
			</div>
		</div>
	</c:if>
	<p class="form-head"><spring:message code="trainingLog.scope" /></p>
	<div class="qs-fields-wrapp">
		<div class="qs-left-bx">
			<div class="input-wrapp smaller">
				<label> 
					<spring:message code="auditor.iso9001" />: 
				</label>
				<div class="field">
				${model.log.iso9001} <span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>
					<spring:message	code="auditor.iso13485" />:
				</label>
				<div class="field">
					${model.log.iso13485} <span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> 
					<spring:message code="auditor.nb1023Procedures" />:
				</label>
				<div class="field">
					${model.log.nb1023Procedures} <span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
		</div>
		<div class="qs-right-bx qs-border-right">
			<div class="input-wrapp smaller">
				<label> 
					<spring:message code="auditor.mdd" />:
				</label>
				<div class="field">
					${model.log.mdd} <span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>	
			<div class="input-wrapp smaller">
				<label>
					<spring:message code="auditor.ivd" />:
				</label>
				<div class="field">
					${model.log.ivd} <span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> 
					<spring:message code="auditor.aimd" />:
				</label>
				<div class="field">
					${model.log.aimd} <span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
			
		</div>
		<div class="clear"></div>
		<div class="qs-log-items no-margin">
			<jsp:include page="training-log-cst.jsp" />
		</div>
	</div>
</form:form>	
</div>
</div>
	
