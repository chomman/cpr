<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="error.notFoundError" /></title>
	</head>
<body>
	
		<div id="error-box">
			<div class="error-box">
				<h2 class="msg error"><spring:message code="error.notFoundError" /></h2>
				
				<p>${exception.message}</p>
				
				<a href="<c:url value="/admin/" />">Zobrazit ovládací panel</a>
			</div>
		
		</div>

</body>
</html>