<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="@portalSecurity.hasValidRegistration()" var="hasValidRegistration"  />
<!DOCTYPE HTML>
<html>
	<head>
		<title><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></title>
	</head>
	<body>
		<section>
			<c:if test="${empty webpageModel.items}">
				<p class="status alert">Not found</p>
			</c:if>
			<c:if test="${not empty webpageModel.items}">
				<c:forEach items="${webpageModel.items}" var="webpage">
					<p> 
						<webpage:a webpage="${webpage}" />
					</p>
				</c:forEach>
			</c:if>
			
				
		</section>			
	</body>
</html>