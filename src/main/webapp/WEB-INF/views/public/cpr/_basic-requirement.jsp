<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
		<meta name="description" content="${model.webpage.description}" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<a title="${model.parentWebpage.title}" href="<c:url value="${model.parentWebpage.code}" />">${model.parentWebpage.name}</a> &raquo;
			<span>${model.webpage.name}</span>
	</div> 

		<div id="main-content">
			 
			 <jsp:include page="../include/left-panel.jsp" />
			 
			 <div class="right-panel">
			 		
			 		<article>
						${model.webpage.topText}
					</article>	

						<c:if test="${not empty model.webpage.webpageContent and model.webpage.webpageContent.id > 1}">
							<jsp:include page="../contents/basic-requrement.jsp" />
						</c:if>
						
					<article>
						${model.webpage.bottomText}
					</article>
			 	
			 </div>
			 <div class="clear"></div>
			
		</div>
	</body>
</html>