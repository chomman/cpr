<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="@portalSecurity.hasValidRegistration()" var="hasValidRegistration"  />
<!DOCTYPE HTML>
<html>
	<head>
		<title><spring:message code="forgottenPassowrd.reset" /></title>
		<meta name="robots" content="noindex, follow">
		<script>
			$(function(){ $(document).on('submit', '.valid', validate );});
		</script>
	</head>
	<body>
		<section>
		<h1 class="pj-head"><spring:message code="forgottenPassowrd.reset" /></h1>
		
		<c:if test="${not empty success}">
			<div class="status ok status-ico">
				<spring:message code="forgottenPassowrd.reset.success" />
			</div>
		</c:if>
		
		<c:if test="${empty success}">
		
			<c:if test="${empty user}">
				<div class="status error status-ico">
					<spring:message  code="forgottenPassowrd.invalidToken" />
				</div>
				
				<a style="display:block;margin:25px;text-align:center;" href="<a:url href="${changePassUrl}" linkOnly="true" />" class="pj-link">
					<spring:message code="forgottenPassowrd.resend" />
				</a>
				
			</c:if>
			<c:if test="${not empty user}">	
				<form:form commandName="user" method="post" cssClass="valid form"  >
		 			<strong class="form-head"><spring:message code="portaluser.profile.nav.changePass" /></strong>
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="status error status-ico"  />	 
								
					<form:label path="newPassword"  cssClass="with-info">
						<span class="label"><spring:message code="changeUserPass.newPassword" />:</span>  
						<input type="password" class="required w300 more6" maxlength="50" name="newPassword" />
						<span class="miniinfo"><spring:message code="password.info" /></span>
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
			</c:if>
		</c:if>	
		</section>		
	</body>
</html>