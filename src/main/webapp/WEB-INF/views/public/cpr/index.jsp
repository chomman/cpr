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
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo;
			<span>${model.webpage.name}</span>
	</div> 

		<div id="main-content">
			 
			<article>
			${model.webpage.topText}
			</article>	
				
				
				
				<div class="cpr-items">
						<c:forEach items="${model.submenu}" var="item">
							<div>
								<h2>
									<a:url href="${item.code}" cssClass="blue-color" title="${item.title}">${item.name}</a:url>
								</h2>
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