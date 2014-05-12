<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></title>
	</head>
	<body>
		

		<div id="main-content">
			<article>
				<h1><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></h1>
				<div id="article">
					<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="content" />
				</div> 
				
				
				<div class="pj-category-items">
					<jsp:include page="category-items.jsp" />
				</div>
			</article> 
		</div>
	</body>
</html>


