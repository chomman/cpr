<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="settings.basic" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/settings-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/settings" />"><spring:message code="settings" /></a> &raquo;
			 <span><spring:message code="settings.basic" /></span>
		</div>
		<h1><spring:message code="settings.basic" /></h1>


		<div id="content">
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
				
					<c:if test="${not empty basicSettings.changedBy}">
						<table class="info">
							<tr>
								<td class="key"><spring:message code="meta.edited" /></td>
								<td class="val">${basicSettings.changedBy.firstName} ${basicSettings.changedBy.lastName}</td>
								<td class="val"><joda:format value="${basicSettings.changed}" pattern="${common.dateTimeFormat}"/></td>
							</tr>
						</table>
					</c:if>
					<c:url value="/admin/settings/basic" var="formUrl"/>
					
					<form:form commandName="basicSettings" method="post" action="${formUrl}"  cssClass="valid" >
						
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
						
						<p>
                        	<label class="tt" title="<spring:message code="settings.basic.systemname.info" />">
                        		<strong><em class="red">*</em>
                        			<spring:message code="settings.basic.systemname" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input  htmlEscape="true" path="systemName" maxlength="255" cssClass="mw500 required" />
                            </span>
                        </p>
                        
                        <p>
                        	<label class="tt" title="<spring:message code="settings.basic.systemname.info" />">
                        		<strong><em class="red">*</em>
                        			<spring:message code="settings.basic.headertitle" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input  htmlEscape="true" path="headerTitle" maxlength="150" cssClass="mw500 required" />
                            </span>
                        </p>
                        
                        <p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="settings.basic.ownername" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input  htmlEscape="true" path="ownerName" maxlength="255" cssClass="mw500  required" />
                            </span>
                        </p>
                        
                        <p>
                        	<label class="tt" title="<spring:message code="settings.basic.systememail.info" />">
                        		<strong><em class="red">*</em>
                        			<spring:message code="settings.basic.systememail" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input  htmlEscape="true" path="systemEmail" maxlength="255" cssClass="mw500 required email" />
                            </span>
                        </p>
                        
                        <p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="settings.basic.csnonlineurl" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<span class="detailinfo" ><spring:message code="settings.basic.csnonlineurl.info" /></span>
                            	<form:input  htmlEscape="true" path="csnOnlineUrl" maxlength="255" cssClass="mw500 required more7" />
                            </span>
                        </p>
                       
                       <p>
                        	<label>
                        			<spring:message code="settings.basic.analytics" />
                        	</label>
                            <span class="field">
                            	<span class="detailinfo" ><spring:message code="settings.basic.analytics.info" /></span>
                            	<form:textarea path="googleAnalyticsTrackingCode" cssClass="analytics mw500"/>
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