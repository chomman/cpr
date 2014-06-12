<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<script>
	$(function(){ 
		$(document).on('submit', '.valid', validate );
	});
</script>


<form:form commandName="user" method="post" cssClass="valid form"  >
 		
 	<strong class="form-head"><spring:message code="portaluser.profile.nav.changePass" /></strong>
	
	<form:errors path="*" delimiter="<br/>" element="p" cssClass="status error status-ico"  />
	
	<c:if test="${not empty successCreate}">
		<p class="status ok"><span class="status-ico"></span><spring:message code="success.create" /></p>
	</c:if> 
	
	
	<form:label path="currentPassword"  cssClass="with-info">
		<span class="label"><spring:message code="changeUserPass.currentPassword" />:</span>  
		<input type="password" class="required w300 more6" maxlength="50" name="currentPassword" /> 
		<span class="miniinfo"><spring:message code="password.info" /></span>
	</form:label>
	
	<form:label path="newPassword"  cssClass="with-info">
		<span class="label"><spring:message code="changeUserPass.newPassword" />:</span>  
		<input type="password" class="required w300 more6" maxlength="50" name="newPassword" />
	</form:label>
	
	
	<form:label path="confirmPassword"> 
		<span class="label"><spring:message code="changeUserPass.confirmPassword" />:</span>
		<input type="password" class="required w300 more6" maxlength="50" name="confirmPassword" />
	</form:label>
	

	<form:hidden path="userId"/>
	
	<span class="pj-gray">
		<span>&nbsp;</span>
		<input type="submit" value="<spring:message code="form.save" />" class="button pj-radius6"/>
	</span>
</form:form>
