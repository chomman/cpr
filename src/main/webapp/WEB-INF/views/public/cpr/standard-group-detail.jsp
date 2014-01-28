<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${fn:substring(model.group.name, 0, 90)}</title>		
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo;  
			<a:url title="${model.parentWebpage.title}" href="${model.parentWebpage.code}">${model.parentWebpage.name}</a:url> &raquo;
			<a:url title="${model.webpage.title}" href="${model.webpage.code}">${model.webpage.name}</a:url> &raquo;
			<span>${fn:substring(model.group.name, 0, 55)}...</span>
	</div> 
		<a:url cssClass="back" title="${model.webpage.title}" href="${model.webpage.code}">&laquo; <spring:message code="backto"/> ${model.webpage.name}</a:url>
		<div id="main-content">
			 
				
					<article>
						<hgroup>
							<h1><spring:message code="group.code" arguments="${model.group.code}" /></h1>
							<h2>${model.group.name}</h2>
						</hgroup>
					
					
					</article>	
					
					<jsp:include page="include/standard-table.jsp" />
			</div>
			 
	</body>
</html>


