<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<form:form commandName="user" method="post" cssClass="valid" >
								
	<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
	
	<c:if test="${not empty successCreate}">
		<p class="msg ok"><spring:message code="success.create" /></p>
	</c:if>
	
	<p class="form-head"><spring:message code="admin.portalUser.validity" /></p>
	   <p>
       	<label>
       		<strong><em class="red">*</em>
       			<spring:message code="admin.portalUser.validity" /> do:  
       		</strong>
       	</label>
           <span class="field">
           	<form:input path="registrationValidity" cssClass="required date" />
           <span class="mini-info inline">
           		<spring:message code="admin.portalUser.validity.info" />
           	</span>
           </span>
       </p>
      <form:hidden path="id" />
      <p class="button-box">
      	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
      </p>
      
</form:form>	