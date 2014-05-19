<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="@portalSecurity.hasValidRegistration()" var="hasValidRegistration"  />
<!DOCTYPE HTML>
<html>
	<head>
		<title><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></title>
	</head>
	<body>
		<section>
		
			<form  action="<webpage:link webpage="${webpageModel.rootwebpage}" />/search">
				<input value="${webpageModel.q}"  type="text" name="q" class="query" />
				<input type="submit" value="<spring:message code="portal.search" />" /> 
			</form>
						
			<c:if test="${empty webpageModel.items}">
				<p class="status alert">
					<spring:message code="portal.noSearchResutls" arguments="${webpageModel.q}" />
				</p>
			</c:if>
			<c:if test="${not empty webpageModel.items}">
				<h1><spring:message code="portal.searchResutls" arguments="${webpageModel.q}" /></h1>
				<c:forEach items="${webpageModel.items}" var="i">
					<div class="sr"> 
						<h3>
							<a href="<webpage:link webpage="${i}" />">
								<webpage:filedVal webpage="${i}" fieldName="title" />
							</a>
						</h3>
						<p>
						<c:if test="${fn:length(i.descriptionInLang) gt 150}">
							${fn:substring(i.descriptionInLang, 0, 150)}...
						</c:if>
						<c:if test="${fn:length(i.descriptionInLang) lt 151}">
							${i.descriptionInLang}
						</c:if>
						</p>
						<span>
							<joda:format value="${i.created}" pattern="dd.MM.yyyy"/>
						</span>
					</div>
				</c:forEach>
			</c:if>
			
				
		</section>			
	</body>
</html>