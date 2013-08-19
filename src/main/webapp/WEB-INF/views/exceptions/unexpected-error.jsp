<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="error.pageNotFound" /></title>
	</head>
<body>
	
		<div id="error-box">
			<div class="msg error">
				<h2><spring:message code="error.pageNotFound" /></h2>	
				<a href="<c:url value="/" />">Zobrazit hlavní stránku  &raquo;</a>
			</div>
		
		</div>

</body>
</html>