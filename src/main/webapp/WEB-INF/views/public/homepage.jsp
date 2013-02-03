<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
		<meta name="description" content="${model.webpage.description}" />
	</head>
	<body>
		
	<!-- <div id="bc">
		<span class="bc-info">Nacházíte se:</span>  <a href="">Home</a> &raquo; <a href="">Aktuality</a>
	</div> -->

		<div id="main-content">
				<article>
					${model.webpage.topText}
				</article>
			
			 <div id="homepage">
			 		<div class="hompage-left">
			 			<strong class="head"><spring:message code="homepage.newest.articles" /></strong>
			 			
						<c:forEach items="${model.articles}" var="article">
							<div class="home-news">
				 				<strong><a href="" class="blue-color">${article.title}</a></strong>
				 				<p>${fn:substring(article.header, 0, 120)} ...</p>
				 				<a href=""  class="blue-color link"><spring:message code="view.detail" /> &raquo; </a>
				 				<div class="clear"></div>
			 				</div>
			 			</c:forEach>
			 		</div>

			 		<div class="hompage-right">
			 			<strong class="head"><spring:message code="homepage.newest.standards" /></strong>
			 			<c:forEach items="${model.standards}" var="standard">
							<div class="norm">
			 				<span class="edit"><joda:format value="${standard.changed}" pattern="dd.MM.yyyy"/></span>
			 				<a href="" class="blue-color link">${standard.standardId}</a>
			 				 <span>&nbsp; ${fn:substring(standard.standardName, 0, 90)} ...</span>
			 				</div>	
			 			</c:forEach>
			 		</div>
										
			 		<div class="clear"></div>
			 </div>
			 
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>