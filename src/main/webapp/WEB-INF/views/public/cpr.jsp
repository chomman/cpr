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
			<span>${model.webpage.name}</span>
	</div> 

		<div id="main-content">
			 
			<article>
			${model.webpage.topText}
			</article>	
				
				
				<div class="cpr-items">
						<c:forEach items="${model.submenu}" var="item">
							<div>
								<h2><a title="${item.title}"  href="<c:url value="${item.code}" />">${item.name}</a></h2>
								<p>${item.description}</p>
							</div>
						</c:forEach>
				</div> 
				
				
			 <article>
				${model.webpage.bottomText}
			 </article>
		</div>
	</body>
</html>