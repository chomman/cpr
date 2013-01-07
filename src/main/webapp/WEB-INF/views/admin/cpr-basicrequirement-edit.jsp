<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.br.edit" /></title>
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
			 <a href="<c:url value="/admin/cpr/basicrequirements" />"><spring:message code="menu.cpr.requrements" /></a> &raquo;
			 <span><spring:message code="cpr.br.edit" /></span>
		</div>
		<h1><spring:message code="cpr.as.edit" /></h1>

		<div id="content">
			
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
			
			
		<c:if test="${empty notFoundError}">
					
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/cpr/basicrequirements"  />"><spring:message code="cpr.br.view" /></a></li>
				<li><a class="active" href="<c:url value="/admin/cpr/basicrequirements/edit/0"  />"><spring:message code="cpr.br.add" /></a></li>
			</ul>
				
			<c:if test="${not empty basicRequirement.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${system.createdBy.firstName} ${system.createdBy.lastName}</td>
						<td class="val"><joda:format value="${system.created}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					<c:if test="${not empty system.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${system.changedBy.firstName} ${system.changedBy.lastName}</td>
						<td class="val"><joda:format value="${system.changed}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			<c:url value="/admin/cpr/basicrequirements/edit/${basicRequirementId}" var="formUrl"/>
			
			<form:form commandName="basicRequirement" method="post" action="${formUrl}"  >
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<p>
                   	<label>
                   		<strong><em class="red">*</em>
                   			<spring:message code="cpr.br.name" />
                   		</strong>
                   	</label>
                       <span class="field">
                       	<form:input path="name" maxlength="150" cssClass="mw600" />
                      </span>
                </p>
			  	<p>
                   	<label>
                   		<spring:message code="cpr.br.description" />
                   	</label>
                    <span class="field">  
                     	<form:textarea path="description"  cssClass="mceEditor bigEditorSize" />
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