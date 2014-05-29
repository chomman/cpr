<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.assessmentSystem.name}</title>
		<meta name="keywords" content="${model.assessmentSystem.name}, CPR" />
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
			<span>${model.assessmentSystem.name}</span>
		</div> 
	
		<div id="main-content">
	 		<article class="full-width">
	 			<h1>${model.assessmentSystem.name}</h1>
	 			${model.assessmentSystem.description}
	 		</article>
		</div>
	</body>
</html>