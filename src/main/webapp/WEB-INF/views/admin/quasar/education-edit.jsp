<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${empty fieldOfEducation.id}">
			<spring:message code="quasar.add" />
		</c:if>
		<c:if test="${not empty fieldOfEducation.id}">
			<spring:message code="fieldOfEducation.edit" />
		</c:if>
	</title>
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
				 <a:adminurl href="/quasar/manage/educations"><spring:message code="fieldOfEducations" /></a:adminurl>  &raquo;
				 <span>
				 	<c:if test="${empty fieldOfEducation.id}">
						<spring:message code="quasar.add" />
					</c:if>
					<c:if test="${not empty fieldOfEducation.id}">
						<spring:message code="fieldOfEducation.edit" />
					</c:if>
				 </span>
			</div>
			<h1>
				<c:if test="${empty fieldOfEducation.id}">
						<spring:message code="quasar.add" />
				</c:if>
				<c:if test="${not empty fieldOfEducation.id}">
					<spring:message code="fieldOfEducation.edit" />
				</c:if>
			</h1>
	
			<div id="content">
				
				<jsp:include page="navs/education-nav.jsp" />
				
				<form:form commandName="command" method="post" cssClass="valid" >
							
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
					</c:if>
					
					<p class="form-head"><spring:message code="baseInformations" /></p>
						<p>
                      		<label>
                      			<strong><em class="red">*</em>
                       			<spring:message code="fieldOfEducation.name" />:
                       		</strong>  
                       	</label>
                           <span class="field">
                           	<form:input path="name" maxlength="50" cssClass="mw500 required" />
                           </span>
                       </p>
                       <p class="form-head">Setup types of item</p>
                       <p>
                      		<label>
                      			<spring:message code="fieldOfEducation.forActiveMedicalDevices" />:
                       		</label>
                           <span class="field"> 
                           	<form:checkbox path="forActiveMedicalDevices"/>
                           </span>
                       </p>
                      <p>
                      		<label>
                      			<spring:message code="fieldOfEducation.forActiveMedicalDevices" />:
                       		</label>
                           <span class="field"> 
                           	<form:checkbox path="forActiveMedicalDevices"/>
                           </span>
                       </p>
                       <p>
                      		<label>
                      			<spring:message code="fieldOfEducation.specificOrCourse" />:
                       		</label>
                           <span class="field"> 
                           	<form:checkbox path="specificOrCourse"/>
                           </span>
                       </p>
                    <form:hidden path="id" />
                       <p class="button-box">
                       	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                       </p>
				</form:form>
				<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
			</div>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>