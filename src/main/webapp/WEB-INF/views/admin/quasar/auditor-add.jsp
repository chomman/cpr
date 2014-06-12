<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="auditor.add" /></title>
		<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
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
			  <a:adminurl href="/quasar/manage/auditors"><spring:message code="auditors" /></a:adminurl>  &raquo;
			  <span><spring:message code="auditor.add" /></span>
		</div>
		<h1><spring:message code="auditor.add" /></h1>

		<div id="content">
			<form:form  commandName="auditorForm" method="post" cssClass="valid" >
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<p class="form-head"><spring:message code="auditor.head.login" /></p>
				<p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="auditor.email" />:
                      		</strong>
                      	</label>
                          <span class="field">
                          	<form:input path="auditor.email" maxlength="50" cssClass="w300 required email" />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="auditor.password" />
                      		</strong>
                      		<small>At least 6 characters and one number</small>
                      	</label>
                          <span class="field">
                          	<form:password path="newPassword" cssClass="w300 required" maxlength="60" />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="auditor.passwordConfirm" />
                      		</strong>
                      	</label>
                          <span class="field">
                          	<form:password path="confirmPassword" cssClass="w300 required" maxlength="60" />
                          </span>
                      </p>
                      
                     <p class="form-head"><spring:message code="auditor.head.personalInfo" /></p>
                      <p>
                      	<label>
                      		<spring:message code="auditor.degrees" />
                      	</label>
                          <span class="field">
                          	<form:input path="auditor.degrees" cssClass="mw150 " maxlength="25" />
                          </span>
                      </p>
                     <p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="auditor.firstName" />
                      		</strong>
                      	</label>
                          <span class="field">
                          	<form:input path="auditor.firstName" cssClass="w300 required" maxlength="50" />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="auditor.lastName" />
                      		</strong>
                      	</label>
                          <span class="field">
                          	<form:input path="auditor.lastName" cssClass="w300 required" maxlength="50" />
                          </span>
                      </p> 
                      <p class="button-box">
                      	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                      </p>
			</form:form>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>