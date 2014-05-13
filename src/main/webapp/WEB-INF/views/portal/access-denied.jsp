<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="@portalSecurity.hasValidRegistration()" var="hasValidRegistration"  />
<!DOCTYPE HTML>
<html>
	<head>
		<title><spring:message code="accessDenied" /></title>
	</head>
	<body>
		<section class="access-denied">
				<h1 class="pj-head"><spring:message code="accessDenied" /></h1>
				
				
				<p class="status alert" style="margin:25px 0;">
					<spring:message code="accessDenied.descr" />
				</p>
				
				<uL class="access-denied-nav"> 
					 <li>
						<webpage:a webpage="${webpageModel.registrationPage}" cssClass="online-pub-preview pj-radius"  />
					</li>
					<li>
						<a href="#" class="show-loginbox online-pub-preview pj-radius"><spring:message code="portal.login" /></a>
					</li>
				</ul>
		</section>
						
	</body>
</html>