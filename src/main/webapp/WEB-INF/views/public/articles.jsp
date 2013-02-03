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
			
			 <div id="aktuality">
			 	<c:forEach items="${model.articles}" var="article">
					<div class="news">
		 				<h2><a href="" class="blue-color">${article.title}</a></h2>
		 				<span><spring:message code="published" />: 
			 				<c:if test="${not empty article.publishedSince}">
			 					<joda:format value="${article.publishedSince}" pattern="dd.MM.yyyy"/>
			 				</c:if>
			 				<c:if test="${empty article.publishedSince}">
			 					<joda:format value="${article.created}" pattern="dd.MM.yyyy"/>
			 				</c:if>
		 				</span>
		 				<p>${article.header}</p>
		 				<a href="<c:url value="${model.webpage.code}/${article.code}" />"  title="<spring:message code="view.detail" />" class="detail"><spring:message code="view.detail" /> &raquo; </a>
		 				<div class="clear"></div>
	 				</div>
	 			</c:forEach>
			 </div>
			 
			 <!-- STRANKOVANIE -->
			<c:if test="${not empty model.paginationLinks}" >
				<div class="pagination">
				<c:forEach items="${model.paginationLinks}" var="i">
					<c:if test="${not empty i.url}">
						<a title="Stánka č. ${i.anchor}"  href="<c:url value="${i.url}"  />">${i.anchor}</a>
					</c:if>
					<c:if test="${empty i.url}">
						<span>${i.anchor}</span>
					</c:if>
				</c:forEach>
				</div>
			</c:if>
				
				
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>