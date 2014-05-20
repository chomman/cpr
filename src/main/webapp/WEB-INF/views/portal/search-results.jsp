<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="@portalSecurity.hasValidRegistration()" var="hasValidRegistration"  />
<!DOCTYPE HTML>
<html>
	<head>
		<title><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></title>
	</head>
	<body>
		<section class="search">
		
			<form enctype="application/x-www-form-urlencoded" class="form valid"  action="<webpage:link webpage="${webpageModel.rootwebpage}" />/search">
				<input value="${webpageModel.q}"  type="text" name="q" class="query required" />
				<input type="submit" class="button pj-radius" value="<spring:message code="portal.search" />" /> 
			</form>
						
			<c:if test="${empty webpageModel.items}">
				<p class="status alert">
					<spring:message code="portal.noSearchResutls" arguments="${webpageModel.q}" />
				</p>
			</c:if>
			<c:if test="${not empty webpageModel.items}">
				<h1><spring:message code="portal.searchResutls" arguments="${webpageModel.q};${webpageModel.count}" argumentSeparator=";" /></h1>
				
				<!-- PAGINATION -->
				<c:if test="${not empty webpageModel.paginationLinks}" >
					<div class="pj-pagination">
					<c:forEach items="${webpageModel.paginationLinks}" var="i">
						<c:if test="${not empty i.url}">
							<a href="<c:url value="${i.url}"  />">${i.anchor}</a>
						</c:if>
						<c:if test="${empty i.url}">
							<span>${i.anchor}</span>
						</c:if>
					</c:forEach>
					</div>
				</c:if>
				
				<!-- SEARCH RESUTS -->
				<c:forEach items="${webpageModel.items}" var="i">
					<c:set var="isDenied"  value="${not hasValidRegistration and nlf:isOnlyForRegistrated(i)}" />
					<div class="sr"> 
						<h3>
							<a class="pj-link ${isDenied ? 'pj-locked' : ''}" href="<webpage:link webpage="${i}" />" 
								<c:if test="${isDenied}"><spring:message code="onlyWithvalidRegistration" /></c:if>
							>
								<webpage:filedVal webpage="${i}" fieldName="title" />
							</a>
						</h3>
						<p>
						<c:if test="${not empty i.descriptionInLang}">
							<c:if test="${fn:length(i.descriptionInLang) gt 250}">
								${fn:substring(i.descriptionInLang, 0, 200)}...
							</c:if>
							<c:if test="${fn:length(i.descriptionInLang) lt 251}">
								${i.descriptionInLang}
							</c:if>
						</c:if>
						<c:if test="${ empty i.descriptionInLang}">
							${nlf:crop(i.contentInLang, 250)} 												
						</c:if>
						</p>
						<span>
							<joda:format value="${i.published}" pattern="dd.MM.yyyy"/>  
							<c:if test="${isDenied}">
								| (<spring:message code="onlyWithvalidRegistration" />)
							</c:if>
							<c:if test="${not empty i.parent and i.parent.enabled}">
								| <spring:message code="portal.subcategory" />:
								<webpage:a webpage="${i.parent }" cssClass="pj-subcat" />
							</c:if>
						
						</span>
					</div>
				</c:forEach>
			</c:if>
			
				
		</section>			
	</body>
</html>