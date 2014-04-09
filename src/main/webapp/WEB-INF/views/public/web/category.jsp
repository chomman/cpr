<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo;  
	</div> 
		<div id="main-content">
			<article>
				<h1><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></h1>
				<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="content" />
				
				
				<div class="pj-category-items">
					<jsp:include page="category-items.jsp" />
				</div>
			</article> 
		</div>
	</body>
</html>


