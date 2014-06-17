<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="auditor.edit" />: ${model.auditor.name}</title>
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
			  <span>${model.auditor.name}</span>
		</div>
		<h1><spring:message code="auditor.edit" />:&nbsp; <strong>${model.auditor.name}</strong></h1>

		<div id="content">
			
			<jsp:include page="navs/auditor-nav.jsp" />
			<jsp:include page="changed.jsp" />
			
			<form:form  commandName="command" method="post" cssClass="valid" > 
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<p class="form-head"><spring:message code="auditor.head.login" /></p>
				<div class="input-wrapp">
					<label>
						<strong><em class="red">*</em>
						<spring:message code="auditor.email" />:
						</strong>
					</label>
					<div class="field">
						<form:input path="email" maxlength="50" cssClass="w300 required email" />
					</div>
				</div>
				<div class="input-wrapp">
					<label>
						<spring:message code="isActivated" />:
						
					</label>
					<div class="field">
						<form:checkbox path="enabled" />
						<span class="inline"><small>Unchecking this field, you can disable user's access.</small></span> 
					</div>
				</div>
				
				<p class="form-head"><spring:message code="auditor.head.personalInfo" /></p>
				<div class="input-wrapp">
					<label>
						<strong><em class="red">*</em>
                      		<spring:message code="auditor.itcId" />:
                      	</strong>
					</label>
					<div class="field">
						<form:input path="itcId" cssClass="w50 c required numeric" maxlength="5" />
					</div>
				</div>
				<div class="input-wrapp">
					<label>
						<spring:message code="auditor.degrees" />:
					</label>
					<div class="field">
						<form:input path="degrees" cssClass="w100 " maxlength="25" />
					</div>
				</div>
				<div class="input-wrapp">
					<label>
						<strong><em class="red">*</em>
						<spring:message code="auditor.name" />:
						</strong>
					</label>
					<div class="field">
						<form:input path="firstName" cssClass="mw150 required" maxlength="50" />
						<form:input path="lastName" cssClass="mw150 required" maxlength="50" />
					</div>
				</div>
				<div class="input-wrapp">
					<label>
						<spring:message code="auditor.country" />:
					</label>
					<div class="field">
						<form:select path="country" cssClass="chosenSmall">
							<option> --- <spring:message code="auditor.select" /> ---</option>
							<c:forEach items="${model.countries}" var="i">
								<option value="${i.id}" ${i.id eq command.country.id ? 'selected="selected"' : '' }>${i.countryName}</option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="input-wrapp">
					<label>
						<spring:message code="auditor.partner" />:
					</label>
					<div class="field">
						<form:select path="country" cssClass="chosenSmall">
							<option> --- <spring:message code="auditor.select" /> ---</option>
							<c:forEach items="${model.partners}" var="i">
								<option value="${i.id}" ${i.id eq command.partner.id ? 'selected="selected"' : '' }>${i.name}</option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
                     
                      
			</form:form>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>