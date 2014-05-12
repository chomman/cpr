<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<script>
		$(function(){ 
			$(document).on('submit', '.valid', validate );
		});
</script>
	<form:form commandName="user" method="post" cssClass="valid form"  >
  		<strong class="form-head"><spring:message code="portaluser.profile.nav.personalInfo" /></strong>
		<form:errors path="*" delimiter="<br/>" element="p" cssClass="status error status-ico"  />
		<c:if test="${not empty successCreate}">
			<p class="status ok"><span class="status-ico"></span><spring:message code="success.create" /></p>
		</c:if> 
		<form:label path="firstName">
			<span class="label"><spring:message code="portalUser.firstName" />:</span>
			<form:input path="firstName" cssClass="required w300" maxlength="50"/>
		</form:label>
		
	
		<form:label path="lastName">
			<span class="label"><spring:message code="portalUser.lastName" />:</span>
			<form:input path="lastName" cssClass="required w300" maxlength="50"/>
		</form:label>
		
		
		<form:label path="userInfo.phone">
			<span class="label"><spring:message code="portalUser.phone" />:</span>
			<form:input path="userInfo.phone" cssClass="required w300 phone" maxlength="25"/>
		</form:label>
		
		
	
		<form:label path="userInfo.city">
			<span class="label"><spring:message code="portalUser.city" />:</span>
			<form:input path="userInfo.city" cssClass="required w300" maxlength="50"/>
		</form:label>
		
		
		<form:label path="userInfo.street">
			<span class="label"><spring:message code="portalUser.street" />:</span>
			<form:input path="userInfo.street" cssClass="required w300" maxlength="50"/>
		</form:label>
	
		
	
		<form:label path="userInfo.zip">
			<span class="label"><spring:message code="portalUser.zip" />:</span>
			<form:input path="userInfo.zip" cssClass="required zip w80" maxlength="50"/>
		</form:label>
		
	
		<form:label path="userInfo.companyName" cssClass="pj-company">
			<span class="label"><spring:message code="portalUser.companyName" />:</span>
			<form:input path="userInfo.companyName" cssClass="w300" maxlength="50"/>
		</form:label>
		
	
		<form:label path="userInfo.ico" cssClass="pj-company">
			<span class="label"><spring:message code="portalUser.ico" />:</span>
			<form:input path="userInfo.ico" maxlength="8" cssClass="numeric" />
		</form:label>
		
		
		<form:label path="userInfo.dic" cssClass="pj-company">
			<span class="label"><spring:message code="portalUser.dic" />:</span>
			<form:input path="userInfo.dic"  maxlength="12"/>
		</form:label>
		
		<span class="pj-gray">
			<span>&nbsp;</span>
			<input type="submit" value="<spring:message code="form.save" />" class="button pj-radius6"/>
		</span>
	</form:form>
