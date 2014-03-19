<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="<c:url value="/resources/public/css/common.css" />" />		
	</head>
	<body>
		<div id="main-content">
			<c:if test="${empty model.standards}">
				<p class="msg alert">
					<spring:message code="alert.empty" />
				</p>
			</c:if>
			
			<jsp:include page="include/standard-table.jsp" />
		</div>	
	</body>
</html>


