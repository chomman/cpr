<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="cpr.groups.edit" /></title>
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
			 <a href="<c:url value="/admin/cpr/groups" />"><spring:message code="cpr.groups.title" /></a> &raquo;
			 <span><spring:message code="cpr.groups.edit" /></span>
		</div>
		<h1><spring:message code="cpr.groups.edit" /></h1>

		<div id="content">
			
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
			
			
			<c:if test="${empty notFoundError}">
					
					
					<ul class="sub-nav">
						<li><a href="<c:url value="/admin/cpr/groups"  />"><spring:message code="cpr.groups.view" /></a></li>
						<li><a class="active" href="<c:url value="/admin/cpr/groups/edit/0"  />"><spring:message code="cpr.groups.add" /></a></li>
					</ul>
				
					<c:if test="${not empty standardGroup.createdBy}">
						<table class="info">
							<tr>
								<td class="key"><spring:message code="meta.created" /></td>
								<td class="val">${standardGroup.createdBy.firstName} ${standardGroup.createdBy.lastName}</td>
								<td class="val"><joda:format value="${standardGroup.created}" pattern="dd.MM.yyyy / hh:mm"/></td>
							</tr>
							<c:if test="${not empty standardGroup.changedBy}">
							<tr>
								<td class="key"><spring:message code="meta.edited" /></td>
								<td class="val">${standardGroup.changedBy.firstName} ${standardGroup.changedBy.lastName}</td>
								<td class="val"><joda:format value="${standardGroup.changed}" pattern="dd.MM.yyyy / hh:mm"/></td>
							</tr>
							</c:if>
						</table>
					</c:if>
					<script type="text/javascript"> 
						$(function() { initWISIWIG("580", "250"); });
					</script>
					<c:url value="/admin/cpr/groups/edit/${standardGroupId}" var="formUrl"/>
					<form:form commandName="standardGroup" method="post" action="${formUrl}"  >
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
						
						<p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="form.name" /> <spring:message code="cpr.groups" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input htmlEscape="true" path="groupName" maxlength="255" cssClass="mw500" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<spring:message code="form.code" /> <spring:message code="cpr.groups" />
                        		<small>Kód skupiny podle CPR.</small>
                        	</label>
                            <span class="field">
                            	<form:input htmlEscape="true" path="groupCode" cssClass="w100" maxlength="15" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<spring:message code="cpr.groups.fileUrl" />
                        	</label>
                            <span class="field">  
                            	<form:input  htmlEscape="true" path="commissionDecisionUrl"  maxlength="255"  cssClass="mw500" />
                            </span>
                        </p>
                        
                        <p>
                        	<label>
                        		<spring:message code="cpr.group.url.title" />
                        	</label>
                            <span class="field">  
                            	<form:input htmlEscape="true" path="urlTitle" maxlength="25"  />
                            </span>
                        </p>
						<p class="form-head"><spring:message code="cpr.nb.description" /><p>
						  <p>
	                      	<label>
	                      		<spring:message code="cpr.nb.description" />
	                      		<small>Podrobnejší popis skupiny</small>
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