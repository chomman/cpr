<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="auditLog.edit" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" /> 
	<script src="<c:url value="/resources/admin/quasar/js/scripts.quasar.auditLog.js" />"></script>

</head>
<body>
<div id="wrapper">
	<div id="breadcrumb">
		 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
		 <a:adminurl href="/quasar/audit-logs"><spring:message code="auditLogs" /></a:adminurl>  &raquo;
		 <span><spring:message code="auditLog.edit" /></span>
	</div>
	<h1>
		<spring:message code="auditLog.edit" />: 
		<strong><joda:format value="${model.auditLog.created}" pattern="dd.MM.yyyy"/></strong>
	</h1>

	<div id="content">
									
		<c:if test="${not empty successCreate}">
			<p class="msg ok"><spring:message code="success.create" /></p>
		</c:if>
		
		<div class="qs-bx-wrapp">
			<p class="form-head"><spring:message code="auditLog.auditLog.items" /></p>
			<c:if test="${empty model.auditLog.items}">
				<p class="msg alert"><spring:message code="log.empty" /></p>
			</c:if>
		</div>
		
		<form:form commandName="command" cssClass="valid">
			<p class="form-head"><spring:message code="auditor.head.login" /></p>
			<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.date" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="item.auditDate" maxlength="10" cssClass="date required" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.company" />:
					</strong>
				</label>
				<div class="field">
					<form:input path="companyName" maxlength="10" cssClass="mw300 required" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>
					<strong><em class="red">*</em>
					<spring:message code="auditLog.item.company" />:
					</strong>
				</label>
				<div class="field">
					<select name="item.company" id="companySelect" data-msg="<spring:message code="form.select" />">
					</select>
				</div>
			</div>
			
			
			
			<form:hidden path="item.id" />
			<p class="button-box">
			<input type="submit" class="button" value="<spring:message code="form.save" />" />
			</p>        
		</form:form>
	
	</div>	
</div>
<div id="loader" class="loader"></div>
</body>
</html>