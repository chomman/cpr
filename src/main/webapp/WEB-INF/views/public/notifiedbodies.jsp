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
			
			
			<c:if test="${model.webpage.webpageContent.id == 6}">
				<div id="noao">
				<table>
				<c:forEach items="${model.notifiedBodies}" var="nb"  varStatus="status">
		 			<c:if test="${prev != nb.country.id }">
		 				<c:set value="${nb.country.id}" var="prev" />
		 				<tr class="thhead">
			 				<td>
			 					<span>
			 						<strong class="head">${nb.country.countryName}</strong>
			 					</span>
			 				</td>
		 				</tr>
		 				<tr>
	 						<th class="c code"><spring:message code="cpr.nb.code" /></th>
	 						<th class="l name"><spring:message code="cpr.nb.name" /></th>
	 						<th class="c city"><spring:message code="no.city" /></th>
	 						<th class="eta">&nbsp;</th>
		 				</tr>
		 			</c:if>
		 		 
		 		 	<tr class="hove">
		 		 		<td class="c"><a href="<c:url value="/subjekt/${nb.code}" />">${nb.notifiedBodyCode}</a></td>
		 		 		<td>${nb.name}</td>
		 		 		<td class="c">
		 		 			<c:if test="${not empty nb.address.city}">
		 		 			${nb.address.city}
		 		 			</c:if>
		 		 		</td>
		 		 		<td >
		 		 			<c:if test="${nb.etaCertificationAllowed}">
								<span class="true tt" title="<spring:message code="eta" />">ETA</span>
							</c:if>
		 		 		</td>
		 		 	</tr>
				</c:forEach>
					</table>
				</div>	
			</c:if>
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>