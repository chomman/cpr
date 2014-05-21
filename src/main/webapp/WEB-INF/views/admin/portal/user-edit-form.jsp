<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<form:form commandName="user" method="post" cssClass="valid" >
								
	<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
	
	<c:if test="${not empty successCreate}">
		<p class="msg ok"><spring:message code="success.create" /></p>
	</c:if>
	
	<p class="form-head"><spring:message code="portaluser.profile.order.fa" /></p> 
		<p>
       	<label>
       		<strong><em class="red">*</em>
       			<spring:message code="user.username" />  
       		</strong>
       	</label>
           <span class="field">
           	<form:input path="email" cssClass="mw300 required email" />
           </span>
       </p>
       <p>
       	<label>
       		<strong><em class="red">*</em>
       			<spring:message code="admin.portal.order.firstName" />  
       		</strong>
       	</label>
           <span class="field">
           	<form:input path="firstName" cssClass="mw300 required" />
           </span>
       </p>
       <p>
       	<label>
       		<strong><em class="red">*</em>
       			<spring:message code="admin.portal.order.lastName" />  
       		</strong>
       	</label>
           <span class="field">
           	<form:input path="lastName" cssClass="mw300 required" />
           </span>
       </p>                     
        <p>
       	<label>
       		   <spring:message code="admin.portal.order.phone" />  
       	</label>
           <span class="field">
           	<form:input path="userInfo.phone" cssClass="mw300" />
           </span>
       </p>
       <p class="form-head"><spring:message code="admin.portal.order.head.address" /></p>
       <p>
       	<label>
       		<spring:message code="admin.portal.city" />  
       	</label>
           <span class="field">
           	<form:input path="userInfo.city" cssClass="mw300" />
           </span>
       </p>
       <p>
       	<label>
       		<spring:message code="admin.portal.street" />:
       	</label>
           <span class="field">
           	<form:input path="userInfo.street" cssClass="mw300" />
           	<span><spring:message code="admin.portal.zip" />:</span>
           	<form:input path="userInfo.zip" cssClass="w50" maxlength="6"/>
           </span>
       </p>
       <p class="form-head"><spring:message code="admin.portal.order.head.company" /></p>
		<p>
         <label>
            <spring:message code="admin.portal.companyName" />  
       	</label>
           <span class="field">
           	<form:input path="userInfo.companyName" cssClass="mw300" />
           </span>
       </p>
       <p>
       	<label>
       		<spring:message code="admin.portal.ico" />  
       	</label>
           <span class="field">
           	<form:input path="userInfo.ico" cssClass="w100" maxlength="8" />
           	<span><spring:message code="admin.portal.dic" /> </span>
           	<form:input path="userInfo.dic" cssClass="w200" maxlength="12" /> 
           </span>
       </p>	
    <form:hidden path="id" />
       <p class="button-box">
       	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
       </p>
</form:form>	