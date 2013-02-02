<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="settings.countries.edit" /></title>
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
			 <a href="<c:url value="/admin/settings/countries" />"><spring:message code="settings.countries" /></a> &raquo;
			 <span><spring:message code="settings.countries.edit" /></span>
		</div>
		<h1><spring:message code="settings.countries.edit" /></h1>

		<div id="content">
			
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
			
			
			<c:if test="${empty notFoundError}">
					
					
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/settings/countries"  />"><spring:message code="settings.countries.view" /></a></li>
				<li><a class="active" href="<c:url value="/admin/settings/countries/edit/0"  />"><spring:message code="settings.countries.add" /></a></li>
			</ul>
				
					<c:if test="${not empty country.createdBy}">
						<table class="info">
							<tr>
								<td class="key"><spring:message code="meta.created" /></td>
								<td class="val">${country.createdBy.firstName} ${country.createdBy.lastName}</td>
								<td class="val"><joda:format value="${country.created}" pattern="${dateTimeFormat}"/></td>
							</tr>
							<c:if test="${not empty country.changedBy}">
							<tr>
								<td class="key"><spring:message code="meta.edited" /></td>
								<td class="val">${country.changedBy.firstName} ${country.changedBy.lastName}</td>
								<td class="val"><joda:format value="${country.changed}" pattern="${dateTimeFormat}"/></td>
							</tr>
							</c:if>
						</table>
					</c:if>
					<c:url value="/admin/settings/countries/edit/${countryId}" var="formUrl"/>
					
					<form:form commandName="country" method="post" action="${formUrl}"  >
						
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
						
						<p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="form.name" /> <spring:message code="settings.country.name" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input  htmlEscape="true" path="countryName" maxlength="255" cssClass="mw500" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<spring:message code="form.code" /> <spring:message code="form.code" />
                        	</label>
                            <span class="field">
                            	<form:input  htmlEscape="true" path="code" maxlength="255" />
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