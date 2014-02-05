<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><a:localizedValue object="${model.webpage}" fieldName="title" /></title>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo; 
			<span><a:localizedValue object="${model.webpage}" fieldName="name" /></span>
	</div> 

		<div id="main-content">
				<article>
					<a:localizedValue object="${model.webpage}" fieldName="topText" />
				</article>
			
			 <div id="aktuality">
			 	<c:forEach items="${model.articles}" var="article">
					<div class="news">
		 				<h2>
		 					<a:url cssClass="blue-color" href="${model.webpage.code}/${article.code}">${article.title}</a:url>
		 				</h2>
		 				<span><spring:message code="published" />: 
			 				<c:if test="${not empty article.publishedSince}">
			 					<joda:format value="${article.publishedSince}" pattern="dd.MM.yyyy"/>
			 				</c:if>
			 				<c:if test="${empty article.publishedSince }">
			 					<joda:format value="${article.created}" pattern="dd.MM.yyyy"/>
			 				</c:if>
			 			
		 				</span>
		 				<p>${article.header}</p>
		 				<a:url cssClass="detail" href="${model.webpage.code}/${article.code}"><spring:message code="view.detail" /> &raquo;</a:url>
		 				<div class="clear"></div>
	 				</div>
	 			</c:forEach>
			 </div>
			 
			 <!-- STRANKOVANIE -->
			<c:if test="${not empty model.paginationLinks}" >
				<div class="pagination">
				<c:forEach items="${model.paginationLinks}" var="i">
					<c:if test="${not empty i.url}">
						<a:url href="${i.url}">${i.anchor}</a:url>
					</c:if>
					<c:if test="${empty i.url}">
						<span>${i.anchor}</span>
					</c:if>
				</c:forEach>
				</div>
			</c:if>
				
				
			 <article>
					<a:localizedValue object="${model.webpage}" fieldName="bottomText" />
			 </article>
				
			 
		</div>
	</body>
</html>