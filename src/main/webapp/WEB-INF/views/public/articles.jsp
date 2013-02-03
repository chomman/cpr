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
		<span class="bc-info">Nacházíte se:</span>  <a href="">Home</a> &raquo; <a href="">Aktuality</a>
	</div> 

		<div id="main-content">
				<article>
					${model.webpage.topText}
				</article>
			
			 <div id="aktuality">
			 	<c:forEach items="${model.articles}" var="article">
					<div class="home-news">
		 				<strong><a href="" class="blue-color">${article.title}</a></strong>
		 				<p>${article.header}</p>
		 				<a href=""  class="blue-color link"><spring:message code="view.detail" /> &raquo; </a>
		 				<div class="clear"></div>
	 				</div>
	 			</c:forEach>
			 </div>
			 
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>