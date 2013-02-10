<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
		<meta name="description" content="${model.webpage.description}" />
		<script src="<c:url value="/resources/public/js/tag.autocomplete.js" />"></script>
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
			
			<c:if test="${model.webpage.webpageContent.id == 7}">
				<form id="search-form" method="get">
				<span class="step"><spring:message code="dop.step1" /></span>
					<label for="query">
						<spring:message code="dop.search" />
					</label>	
						<input type="text" class="query" name="query" value="${model.query}"  />
						<input type="submit" value="Vyhledat" class="btn" />
					
				</form>
				<c:if test="${empty model.standards and not empty model.query}">
					<p class="msg alert">
						<spring:message code="alert.empty" />
					</p>
				</c:if>
				<c:if test="${not empty model.standards}">
								<table class="group-detail">
									<thead>
										<tr>
											<th><spring:message code="cpr.standard.id" /></th>
											<tH><spring:message code="cpr.standard.name" /></th>
											<th><spring:message code="assessment" /></th>
											<th class="w150"><spring:message code="dop.step2" /></th>
											
										</tr>
									</thead>
									<tbody>
										 <c:forEach items="${model.standards}" var="i">
										 	<tr>
										 		<td class="norm"><a target="_blank" href="<c:url value="/ehn/${i.code}" />" class="tt" title="<spring:message code="moreinfo" />" >${i.standardId}</a></td>
										 		<td>${i.standardName}</td>
										 		<td class="as c">
										 			<c:if test="${not empty i.assessmentSystems}">
										 				<c:forEach items="${i.assessmentSystems}" var="as"  varStatus="status">
										 					<a class="tt" title="<spring:message code="show.assessmentsystem" />" href="<c:url value="/cpr/as/${as.id}" />">${as.assessmentSystemCode}</a> 
										 					<c:if test="${not status.last}">, </c:if>  
										 				</c:forEach>
										 			</c:if>
										 			<c:if test="${empty i.assessmentSystems}">
										 				-
										 			</c:if>
										 		</td>
										 		<td class="as c">
										 			<a class="btn-next" href="<c:url value="/vygenerovat-prohlaseni/form/${i.code}" />">
										 				<spring:message code="next" /> &raquo;
										 			</a>
										 		</td>
										 	</tr>
										 </c:forEach>
									</tbody>
								</table>
						</c:if>
					</c:if>
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>