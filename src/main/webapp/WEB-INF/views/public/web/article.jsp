<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></title>
		<meta name="description" content="${nlf:crop(webpageModel.webpage.descriptionInLang, 210)}" />
		<meta name="keywords" content="${webpageModel.webpage.jointedTags}" />
	</head>
	<body>
		<div id="main-content">
			<article>
				<h1><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></h1>
				<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="content" />
			</article> 
			
			
			<c:if test="${not empty webpageModel.webpage.webpageModule }">
				<jsp:include page="../../module/${webpageModel.webpage.webpageModule.jspPage}" />
			</c:if>
			
		</div>
	</body>
</html>


