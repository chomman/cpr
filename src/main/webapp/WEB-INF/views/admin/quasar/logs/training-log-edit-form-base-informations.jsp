<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<div id="form-wrapp" class="${model.log.baseDateAreSet ? 'qs-valid' : 'qs-invalid'}">
<form:form commandName="command" cssClass="training-log valid bg-white" htmlEscape="true" >
	<div class="transparent">
	<p class="form-head"><spring:message code="baseInformations" /></p>
	<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
	<div class="input-wrapp smaller">
		<label>
			<strong><em class="red">*</em>
			<spring:message code="trainingLog.subject" />:
			</strong>
		</label>
		<div class="field">
			<form:input path="subject" maxlength="255" cssClass="mw500 required" />
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label>
			<strong><em class="red">*</em>
			<spring:message code="trainingLog.date" />:
			</strong>
		</label>
		<div class="field">
			<form:input path="date" maxlength="10" cssClass="w100 date required" /> 
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label>
			<spring:message code="trainingLog.date" />:
		</label>
		<div class="field">
			<form:textarea path="description" id="editor" />
		</div>
	</div>
	<p class="form-head"><spring:message code="trainingLog.scope" /></p>
	<div class="qs-fields-wrapp">
		<div class="qs-left-bx">
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.iso9001" />: </strong>
				</label>
				<div class="field">
					<form:input path="iso9001" cssClass="w50 c required numeric" maxlength="4" />
					<span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><strong><em class="red">*</em> <spring:message
							code="auditor.iso13485" />: </strong>
				</label>
				<div class="field">
					<form:input path="iso13485" cssClass="w50 c required numeric" maxlength="4" />
					<span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> 
					<strong><em class="red">*</em> <spring:message code="auditor.nb1023Procedures" />: </strong>
				</label>
				<div class="field">
					<form:input path="nb1023Procedures"	cssClass="w50 c required numeric" maxlength="4" />
					<span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
		</div>
		<div class="qs-right-bx qs-border-right">
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.mdd" />: </strong>
				</label>
				<div class="field">
					<form:input path="mdd" cssClass="w50 c required numeric" maxlength="4" />
					<span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>	
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.ivd" />: </strong>
				</label>
				<div class="field">
					<form:input path="ivd" cssClass="w50 c required numeric" maxlength="4" />
					<span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> 
					<strong><em class="red">*</em> <spring:message code="auditor.aimd" />: </strong>
				</label>
				<div class="field">
					<form:input path="aimd" cssClass="w50 c required numeric" maxlength="4" />
					<span class="hour"><spring:message code="hours" /></span>
				</div>
			</div>
			
		</div>
		<div class="clear"></div>
		<div class="qs-log-items no-margin">
			<jsp:include page="training-log-cst.jsp" />
			<a class="add-cst" href="#">
				<spring:message code="trainingLog.cst.add" /> +
			</a>
		</div>
	</div>
	<form:hidden path="id"/>
	<p class="button-box">
	<input type="submit" class="button" value="<spring:message code="form.save" />" />
	</p>   
	</div>
</form:form>	
	<c:if test="${not empty model.unassignedNandoCodes}">
		<div id="add-code-form">
			<form class="inline-form valid">
				<div class="inline-field">
					<select name="iid" class="required">
						<option value="">
							-- <spring:message code="trainingLog.cst.nandoCode" /> --
						</option>
						<c:forEach items="${model.unassignedNandoCodes}" var="i">
							<option value="${i.id}">${i.code} / ${i.specification }</option>
						</c:forEach>
					</select>
				</div>
				<div class="inline-field">
					<span class="label"><spring:message code="hours" />:</span>
					<input type="text" name="hours" class="c w40 numeric required" />
				</div>
				<input type="submit" class="lang mandate-add-btn" value="<spring:message code="add" />" />
				<a href="#" class="cancel smaller qs-btn">
					<spring:message code="cancel" />
				</a>
				<input type="hidden" name="action" value="${model.codeAdd}" />
			</form>
		</div>
	</c:if>
</div>