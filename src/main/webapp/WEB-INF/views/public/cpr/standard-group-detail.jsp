<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${fn:substring(model.group.czechName, 0, 90)}</title>		
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo;
			<a title="${model.parentWebpage.title}" href="<c:url value="${model.parentWebpage.code}" />">${model.parentWebpage.name}</a> &raquo;
			<a title="${model.webpage.title}" href="<c:url value="${model.webpage.code}" />">${model.webpage.name}</a> &raquo;
			<span>${fn:substring(model.group.czechName, 0, 55)}...</span>
	</div> 
		<a class="back" title="${model.webpage.title}" href="<c:url value="${model.webpage.code}" />">&laquo; <spring:message code="backto"/> ${model.webpage.name}</a> 
		<div id="main-content">
			 
				
					<article>
						<hgroup>
							<h1><spring:message code="group.code" arguments="${model.group.code}" /></h1>
							<h2>${model.group.czechName}</h2>
						</hgroup>
					
					
					</article>	
					
					<jsp:include page="include/standard-table.jsp" />
			</div>
			 
	</body>
</html>


