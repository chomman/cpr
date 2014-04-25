<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<form:form commandName="user" method="post" cssClass="valid form"  >
	<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
	
	
	<strong class="form-head"><spring:message code="portalUser.head.loginInfo" /></strong>
	
	<form:label path="email">
			<span class="label"><spring:message code="portalUser.email" />: </span>
		<form:input path="email" cssClass="required email w300" maxlength="50"/>
	</form:label>
	
	
	
	<form:label path="password">
		<span class="label"><spring:message code="portalUser.password" />:</span>
		<form:password path="password" cssClass="required w300 more6" maxlength="50"/>
	</form:label>
	
	
	<form:label path="confirmPassword">
		<span class="label"><spring:message code="portalUser.confirmPassword" />:</span>
		<form:password path="confirmPassword" cssClass="required w300 more6" maxlength="50"/>
	</form:label>
	
	
	<strong class="form-head"><spring:message code="portalUser.head.otherInfo" /></strong>
	 
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
		<form:input path="userInfo.phone" cssClass="required w300 more6" maxlength="25"/>
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
		<form:input path="userInfo.zip" cssClass="required w80" maxlength="50"/>
	</form:label>
	

	<form:label path="userInfo.companyName">
		<span class="label"><spring:message code="portalUser.companyName" />:</span>
		<form:input path="userInfo.companyName" cssClass="w300" maxlength="50"/>
	</form:label>
	

	<form:label path="userInfo.ico">
		<span class="label"><spring:message code="portalUser.ico" />:</span>
		<form:input path="userInfo.ico" maxlength="8"/>
	</form:label>
	
	
	<form:label path="userInfo.dic">
		<span class="label"><spring:message code="portalUser.dic" />:</span>
		<form:input path="userInfo.dic"  maxlength="12"/>
	</form:label>
	
	<span class="pj-gray">
		<span>&nbsp;</span>
		<input type="submit" value="Odeslat" class="button pj-radius6"/>
		<span class="vop">Odesláním formuláře souhlasíte s obchodními podmínkami</span>
	</span>
</form:form>
<div id="status"></div>