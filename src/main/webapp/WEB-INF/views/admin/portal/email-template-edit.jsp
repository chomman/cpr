<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<c:set var="isWebmaster" value="false" />
<sec:authorize access="hasAnyRole('ROLE_WEBMASTER')">
	<c:set var="isWebmaster" value="true" />
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="admin.emailTemplate.edit"/></title>
	<script>
		$(function() {
		
		});
	</script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
		
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  u&raquo;
				 <a:adminurl href="/portal/email-templates"><spring:message code="admin.emailTemplate" /></a:adminurl>  &raquo;
				 <span><spring:message code="admin.emailTemplate.edit"/></span> 
			</div>
			<h1><spring:message code="admin.emailTemplate.edit"/></h1>
	
			<div id="content">
			
				<form:form commandName="emailTemplate" method="post" cssClass="valid" >
								
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
					
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
					</c:if>
					
					<c:if test="${isWebmaster}">
						<p>
				       	<label>
				       		<strong><em class="red">*</em>
				       			<spring:message code="admin.emailTemplate.name" />  
				       		</strong>
				       	</label>
				           <span class="field">
				           	<form:input path="name" cssClass="mw500 required" />
				           </span>
				       </p>
				       <p>
				       	<label>
				       		<strong><em class="red">*</em>
				       			<spring:message code="admin.emailTemplate.name" />  
				       		</strong>
				       	</label>
				           <span class="field">
				           	<form:input path="code" cssClass="mw300 required" />
				           </span>
				       </p>
			       </c:if>
			       
			       <p>
			       	<label>
			       		<strong><em class="red">*</em>
			       			<spring:message code="admin.emailTemplate.subject" />  
			       		</strong>
			       	</label>
			           <span class="field">
			           	<form:input path="subject" cssClass="mw500 required" />
			           </span>
			       </p>
			       <p class="pj-content-type  pj-type">
			       		<label>
				       		<strong><em class="red">*</em>
				       			<spring:message code="admin.emailTemplate.body" />  
				       		</strong>
			       		</label>
			           <span class="field full-width">
			           	<form:textarea path="body" cssClass="wisiwig" />
			           </span>
			       </p> 
			       
			       <c:if test="${isWebmaster}">
			        <p class="pj-content-type  pj-type">
			       		<label>
				       		<spring:message  code="admin.emailTemplate.variables" />
			       		</label>
			           <span class="field full-width">
			           	<form:textarea path="variables" cssClass="wisiwig" />
			           </span>
			       </p> 
			       </c:if>
			       
			       <c:if test="${not isWebmaster}">
			       	<p class="form-head"><spring:message  code="admin.emailTemplate.variables" /></p>
			       	<div class="pj-email-vars">
			       		${emailTemplate.variables}
			       	</div>
			       </c:if>
			       
			    	<form:hidden path="id" />
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