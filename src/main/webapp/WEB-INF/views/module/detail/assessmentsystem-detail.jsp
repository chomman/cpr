<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>
			<a:localizedValue object="${model.assessmentSystem}" fieldName="name" />
		</title>
		<meta name="keywords" content="<a:localizedValue object="${model.assessmentSystem}" fieldName="name" />, CPR" />
	</head>
	<body>
		
		<div id="bc">
			<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo; 
			<c:if test="${not empty webpageModel.webpage.parent}">
					<webpage:a webpage="${webpageModel.webpage.parent}" /> &raquo;
			</c:if>
			<c:if test="${not empty webpageModel.webpage}">
				<webpage:a webpage="${webpageModel.webpage}" /> &raquo;
			</c:if>	
			<span><a:localizedValue object="${model.assessmentSystem}" fieldName="name" /></span>
		</div> 
	
		<div id="main-content">
	 		<article class="full-width">
	 			<h1><a:localizedValue object="${model.assessmentSystem}" fieldName="name" /></h1>
	 			<a:localizedValue object="${model.assessmentSystem}" fieldName="description" />
	 		</article>
		</div>
	</body>
</html>