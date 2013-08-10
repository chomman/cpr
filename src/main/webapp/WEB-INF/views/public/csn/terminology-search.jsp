<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span>${model.webpage.name}</span>
	</div> 

		<div id="main-content">
			<article>
				${model.webpage.topText}
			</article>
			
			 <div id="sarchTerminology">
			 	<form>
			 		<input type="text" name="term" /> 
			 	</form>
			 </div>
				
				
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>