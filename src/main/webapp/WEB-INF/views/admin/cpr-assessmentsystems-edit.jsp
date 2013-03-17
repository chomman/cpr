<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="cpr.as.edit" /></title>
		<script src="<c:url value="/resources/admin/tiny_mce/tiny_mce.js" />"></script>
	</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr" />"><spring:message code="menu.cpr" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr/assessmentsystems" />"><spring:message code="menu.cpr.pps" /></a> &raquo;
			 <span><spring:message code="cpr.as.edit" /></span>
		</div>
		<h1><spring:message code="cpr.as.edit" /></h1>

		<div id="content">
			
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
		<c:if test="${empty notFoundError}">
					
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/cpr/assessmentsystems"  />"><spring:message code="cpr.as.view" /></a></li>
				<li><a class="active" href="<c:url value="/admin/cpr/assessmentsystems/edit/0"  />"><spring:message code="cpr.as.add" /></a></li>
			</ul>
				
			<c:if test="${not empty system.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${system.createdBy.firstName} ${system.createdBy.lastName}</td>
						<td class="val"><joda:format value="${system.created}" pattern="${dateTimeFormat}"/></td>
					</tr>
					<c:if test="${not empty system.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${system.changedBy.firstName} ${system.changedBy.lastName}</td>
						<td class="val"><joda:format value="${system.changed}" pattern="${dateTimeFormat}"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			<script type="text/javascript"> 
				$(function() { initWISIWIG("610", "400"); });
			</script>
			<c:url value="/admin/cpr/assessmentsystems/edit/${assessmentSystemId}" var="formUrl"/>
			<form:form commandName="assessmentSystem" method="post" action="${formUrl}"  >
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
					<p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="cpr.as.name" />
                      		</strong>
                      	</label>
                          <span class="field">
                          	<form:input  htmlEscape="true" path="name" maxlength="45" cssClass="mw500" />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<spring:message code="cpr.as.code" />
                      		<small>(Např. 1, 1+, ...)</small>
                      	</label>
                          <span class="field">
                          	<form:input  htmlEscape="true" path="assessmentSystemCode" maxlength="25" cssClass="w100" />
                          </span>
                      </p>
                      <p>
					    <label title="<spring:message code="publish.descr" />" class="tt">
					 		<spring:message code="publish" />
					 	</label>
					     <span class="field">  
					     	<form:checkbox path="enabled" />
					     </span>
					 </p>
					  <p class="form-head">Text, zobrazovaný při generovaní prohlášení<p>
					  <p>
                      	<label>
                      		Text, který se zobrazí v DOP formuláři
                      		<small>Text, který se zobrazí v DOP formuláři, po výběru NO/AO a Systému posuzování.</small>
                      	</label>
                          <span class="field">  
                          	<form:textarea path="declarationOfPerformanceText"  cssClass="variable" />
                          	<span class="variable-info">
                          		V textu je možné použit naledující promjené:<br /><br />
                          		
                          		<strong>${fn:replace(model.varAonoName,'\\','')}</strong> - Výraz bude nahrazen za název vybrané notifikované osoby<br />
                          		<strong>${fn:replace(model.varId,'\\','')}</strong> - Výraz bude nahrazen za identifikátor vybrané notifikované osoby<br />
                          		<strong>${fn:replace(model.varReport,'\\','')}</strong> - Výraz bude nahrazen za identifikátor cerfitikátu/testu<br /><br />
                          		
                          		Příklad:<br />
                          		V případě pokud formulář bude obsahovat následující text:<br />
								<em>Notifikovaná osoba <strong>${fn:replace(model.varAonoName,'\\','')}</strong> s identifikátorem <strong>${fn:replace(model.varId,'\\','')}</strong></em><br />
								V DOP formuláři bude jako NO / AO zvolená ITC Zlín, tak výsledný tvar textu bude:<br />
								<em>Notifikovaná osoba <strong>Institut pro testování a certifikaci</strong> s identifikátorem <strong>AO 224 ITC</strong></em>
                          	</span>
                          </span>
                      </p>
					  <p class="form-head"><spring:message code="cpr.nb.description" /><p>
					  <p>
                      	<label>
                      		<spring:message code="cpr.nb.description" />
                      		<small>Podorbný popis systému.</small>
                      	</label>
                          <span class="field">  
                          	<form:textarea path="description"  cssClass="mceEditor defaultSize" />
                          </span>
                      </p>
					  
                      <form:hidden path="id" />
                      <p class="button-box">
                      	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                      </p>
			</form:form>
	</c:if>
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>