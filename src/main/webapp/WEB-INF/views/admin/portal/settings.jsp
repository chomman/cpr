<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_SUPERADMIN')">	
	<c:set var="isSuperAdmin" value="true"/>
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="settings" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="../include/portal-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl> &raquo;
			 <span><spring:message code="settings" /></span>
		</div>
		<h1><spring:message code="settings" /></h1>


		<div id="content">
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
	
			<c:if test="${not empty settings.changedBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${settings.changedBy.firstName} ${settings.changedBy.lastName}</td>
						<td class="val"><joda:format value="${settings.changed}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
				</table>
			</c:if>
					
					
				
				<form:form commandName="settings" method="post" cssClass="valid" >
						
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
					</c:if>
						
					<p class="form-head"><spring:message code="admin.portal.settings.head.fa" /></p>	
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="basicSettings.companyName" />:
							</strong>
						</label>
						<span class="field">
							<form:input path="companyName" maxlength="100" cssClass="mw500 required" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="admin.portal.city" />:
							</strong>
						</label>
						<span class="field">
							<form:input path="city" maxlength="50" cssClass="mw300 required" />
						</span>
					</p>
					 <p>
                       	<label>
                       		<strong><em class="red">*</em>
                       			<spring:message code="admin.portal.street" />:
                       		</strong>
                       	</label>
                        <span class="field">
                           	<form:input path="street" cssClass="mw300" maxlength="80" />
                           	<span> &nbsp; <spring:message code="admin.portal.zip" />:</span>
                           	<form:input path="zip" cssClass="w50" maxlength="6"/>
                         </span>
                     </p>
                      <p>
                       	<label>
                       		<spring:message code="admin.portal.ico" />  
                       	</label>
                           <span class="field">
                           	<form:input path="ico" cssClass="w100" maxlength="8" />
                           	<span><spring:message code="admin.portal.dic" /> </span>
                           	<form:input path="dic" cssClass="w200" maxlength="12" /> 
                           </span>
                       </p>	
                    <p class="form-head"><spring:message code="admin.portal.settings.head.contact" /></p>
                     <p>
                       	<label class="tt" title="Bude se zobrazovat na vygenerovaných dokladech (Zálohová faktura, Příkaz k fakturaci)">
                       		<strong><em class="red">*</em>
                       			<spring:message code="admin.portal.order.email" />:
                       		</strong>
                       	</label>
                        <span class="field">
                           	<form:input path="invoiceEmai" cssClass="mw300 required email" maxlength="35" />
                           	
                         </span>
                     </p>
                     <p>
                       	<label class="tt" title="Bude se zobrazovat na vygenerovaných dokladech (Zálohová faktura, Příkaz k fakturaci)">
                       		<spring:message code="admin.portal.order.phone" />:
                       	</label>
                        <span class="field">
                           	<form:input path="phone" cssClass="mw300" maxlength="20" />
                         </span>
                     </p>
                    
                     <p class="form-head"><spring:message code="admin.portal.settings.head.czBank" /></p>
                      <p>
                       	<label>
                       		<spring:message code="bank.accountNo" />:
                       	</label>
                        <span class="field">
                           	<form:input path="czAccountNumber" cssClass="w200" maxlength="25" />
                           	<span> &nbsp; <spring:message code="bank.swift" />: </span>
                           	<form:input path="czSwift" cssClass="w100" maxlength="8" />
                         </span>
                     </p>
                      <p>
                       	<label>
                       		<spring:message code="bank.iban" />:
                       	</label>
                        <span class="field">
                           	<form:input path="czIban" cssClass="mw500" maxlength="34" />
                         </span>
                     </p>
                    
                     <p class="form-head"><spring:message code="admin.portal.settings.head.euBank" /></p>
                      <p>
                       	<label>
                       		<spring:message code="bank.accountNo" />:
                       	</label>
                        <span class="field">
                           	<form:input path="euAccountNumber" cssClass="w200" maxlength="25" />
                           	<span> &nbsp; <spring:message code="bank.swift" />: </span>
                           	<form:input path="euSwift" cssClass="w100" maxlength="8" />
                         </span>
                     </p>
                      <p>
                       	<label>
                       		<spring:message code="bank.iban" />:
                       	</label>
                        <span class="field">
                           	<form:input path="euIban" cssClass="mw500" maxlength="34" />
                         </span>
                     </p>
                    <p class="form-head"><spring:message code="admin.portal.settings.head.partners" /></p>
                      <p>
                       	<label class="tt" title="Bude se zobrazovat na vygenerovaných dokladech (Zálohová faktura, Příkaz k fakturaci)">
                       		Jméno a příjmení správce: 
                       	</label>
                        <span class="field">
                           	<form:input path="portalAdminName" cssClass="mw300" maxlength="40" />
                         </span>
                     </p>
                      <p>
                       	<label class="tt" title="Budou se zobrazovat na vygenerovaných dokladech">
                       		Kontaktní informace:
                       	</label>
                        <span class="field">
                           	<form:input path="portalAdminContact" cssClass="mw500" maxlength="80" />
                         </span>
                     </p>
                      <p class="form-head"><spring:message code="admin.portal.settings.head.partners" /></p>
                      <p>
                       	<label>
                       		PlasticPortal e-mail:
                       	</label>
                        <span class="field">
                           	<form:input path="plasticPortalEmail" cssClass="mw300" maxlength="40" />
                         </span>
                     </p>
                     
					<p class="button-box">
						<input type="submit" class="button" value="<spring:message code="form.save" />" />
					</p>
				</form:form>
	
	
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>