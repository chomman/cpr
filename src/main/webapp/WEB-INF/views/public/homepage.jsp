<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
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
			 			<c:if test="${not empty model.articles}">
							<c:forEach items="${model.articles}" var="article">
								<div class="home-news">
					 				<strong>
					 					<a:url href="${model.articleUrl}/${article.code}"  cssClass="blue-color">${article.title}</a:url>
					 				</strong>
					 				<p>${fn:substring(article.header, 0, 120)} ...</p>
					 				<a:url href="${model.articleUrl}/${article.code}"  cssClass="blue-color">
					 					<spring:message code="view.detail" /> &raquo; 
					 				</a:url>
					 				<div class="clear"></div>
				 				</div>
				 			</c:forEach>
			 			</c:if>
			 		</div>

			 		<div class="hompage-right">
			 			<strong class="head"><spring:message code="homepage.newest.standards" /></strong>
			 			<c:if test="${not empty model.standards}">
				 			<c:forEach items="${model.standards}" var="standard">
								<div class="norm">
				 				<span class="edit"><joda:format value="${standard.changed}" pattern="dd.MM.yyyy"/></span>
				 				<a:url href="/ehn/${standard.id}"  cssClass="blue-color link">
					 				${standard.standardId}
					 			</a:url>
				 				</div>	
				 			</c:forEach>
			 			</c:if>
			 		</div>
										
			 		<div class="clear"></div>
			 </div>
			 
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>