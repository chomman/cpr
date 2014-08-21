<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="editable" value="false" scope="application"/>
<sec:authorize access="hasRole('ROLE_ADMIN')"> 
	<c:set var="editable" value="true" scope="application" />
</sec:authorize>
<!DOCTYPE html>
<html>
	<head>
		<title>
			<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" />
		</title>
		<meta name="description" content="${nlf:crop(webpageModel.webpage.descriptionInLang, 210)}" />
		<meta name="keywords" content="${webpageModel.webpage.jointedTags}" /> 
	</head>
	<body>
		<div id="main-content">
			<article>
				<h1><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></h1>
				<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="content" />
			</article>
			 <div id="homepage">
			 		<div class="hompage-left">
			 			<strong class="head"><spring:message code="lastNews" /></strong>
			 			<c:if test="${not empty webpageModel.news}">
							<c:forEach items="${webpageModel.news}" var="i">
								<div class="home-news">
								<span class="pj-radius"><joda:format value="${i.published}" pattern="dd.MM.yyyy" /> | </span>
					 				<strong>
					 					<webpage:a webpage="${i}" cssClass="blue-color" />
					 				</strong>
					 				<p>
					 				<c:if test="${not empty i.descriptionInLang}">
												<c:if test="${fn:length(i.descriptionInLang) gt 150}">
													${fn:substring(i.descriptionInLang, 0, 150)}...
												</c:if>
												<c:if test="${fn:length(i.descriptionInLang) lt 150}">
													${i.descriptionInLang}
												</c:if>
											</c:if>
											<c:if test="${empty i.descriptionInLang}">
												${nlf:crop(i.contentInLang, 150)} 												
									</c:if>
					 				</p>
					 				<a href="<webpage:link webpage="${i}"  />" class="blue-color">
					 					<spring:message code="view.detail" /> &raquo; 
					 				</a>
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