<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
	</head>
	<body>

		<article>
			<h1 class="pj-head"><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></h1>
			<div id="article">
				
				<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="content" />
			</div> 
		</article>
		
		
		
			
		
	</body>
</html>

