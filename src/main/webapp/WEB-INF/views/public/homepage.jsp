<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:set var="editable" value="false" scope="application"/>
<sec:authorize access="isAuthenticated()"> 
	<c:set var="editable" value="true" scope="application" />
</sec:authorize>

<!DOCTYPE html>
<html>
	<head>
		<title>
			<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />
		</title>
	</head>
	<body>

		<div id="main-content">
				<article>
					<h1><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></h1>
					<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="content" />
				</article>
				
			 <div id="homepage">
			 		<div class="hompage-left">
			 			<strong class="head"><spring:message code="homepage.newest.articles" /></strong>
			 			<c:if test="${not empty webpageModel.articles}">
							<c:forEach items="${webpageModel.articles}" var="article">
								<div class="home-news">
					 				<strong>
					 					<a:url href="${webpageModel.articleUrl}/${article.code}"  cssClass="blue-color">${article.title}</a:url>
					 				</strong>
					 				<p>${fn:substring(article.header, 0, 120)} ...</p>
					 				<a:url href="${webpageModel.articleUrl}/${article.code}"  cssClass="blue-color">
					 					<spring:message code="view.detail" /> &raquo; 
					 				</a:url>
					 				<div class="clear"></div>
				 				</div>
				 			</c:forEach>
			 			</c:if>
			 		</div>

			 		<div class="hompage-right">
			 			<strong class="head"><spring:message code="homepage.newest.standards" /></strong>
			 			<c:if test="${not empty webpageModel.standards}">
				 			<c:forEach items="${webpageModel.standards}" var="standard">
								<div class="norm">
				 				<span class="edit"><joda:format value="${standard.changed}" pattern="dd.MM.yyyy"/></span>
				 					<a:standardUrl standard="${standard}" cssClass="blue-color link" editable="${editable}"/>
				 				</div>	
				 			</c:forEach>
			 			</c:if>
			 		</div>
										
			 		<div class="clear"></div>
			 </div>
			 
			
				
			 
		</div>
	</body>
</html>