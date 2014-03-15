<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>
			<a:localizedValue object="${model.webpage}" fieldName="title" />
		</title>
		<script src="<c:url value="/resources/public/js/ehn.autocomplete.js" />"></script>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo;  
			<a:url href="${model.parentWebpage.code}">
					<a:localizedValue object="${model.parentWebpage}" fieldName="name" />
			</a:url> &raquo;
			<span>
				<a:localizedValue object="${model.webpage}" fieldName="name" />
			</span>
	</div> 

		<div id="main-content">
			 
			 <c:if test="${not empty model.webpage.webpageContent}">
			 	<c:if test="${model.webpage.webpageContent.id == 4}">
					<jsp:include page="include/standard-groups.jsp" />
				</c:if>
				
				<c:if test="${model.webpage.webpageContent.id == 3}">
					<jsp:include page="../contents/assessmentsystems.jsp" />
				</c:if>
				
				<c:if test="${model.webpage.webpageContent.id == 10}">
					<jsp:include page="../contents/reports.jsp" />
				</c:if>
			 </c:if>
			 
		</div>
	</body>
</html>


