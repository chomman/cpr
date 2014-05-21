<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="@portalSecurity.hasValidRegistration()" var="hasValidRegistration"  />
<!DOCTYPE HTML>
<html>
	<head>
		<title><spring:message code="forgottenPassowrd.restore" /></title>
		<meta name="robots" content="noindex, follow">
	</head>
	<body>
		<section class="pj-forgotten-pass" >
							
		<h1 class="pj-head"><spring:message code="forgottenPassowrd.restore" /></h1>
					
		<c:if test="${not empty error}">
				<p class="status status-ico error">${error}</p>
			</c:if>
			<c:if test="${not empty success}">
				<p class="status status-ico ok">
					<spring:message code="forgottenPassowrd.success" arguments="${email}" />
				</p>
		</c:if>
				
			<c:if test="${empty success}">	
				<div class="pj-left">
					<h2>
						<spring:message code="forgottenPassowrd" />
					</h2> 
					<p>
						<spring:message code="forgottenPassowrd.descr" />
					</p>
				</div>
				<div class="pj-right">
					<form method="post" class="valid form" action="${url}"  >
						<label>
							<span class="label"><spring:message code="portalUser.email" />:</span>
							<input type="text" name="email" class="required w300" maxlength="50" value="${not empty email ? email : '' }"/>
						</label>
						
						<input type="submit" value="<spring:message code="form.submit" />" class="button pj-radius6"/>
					</form>
				</div>
				<div class="clear"></div>
			</c:if>
		</section>
						
	</body>
</html>