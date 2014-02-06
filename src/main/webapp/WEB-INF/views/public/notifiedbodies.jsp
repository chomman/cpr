<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>
			<a:localizedValue object="${model.webpage}" fieldName="title" />
		</title>
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
	 						<!--  <th class="c city"><spring:message code="no.city" /></th>-->
	 						<th class="eta">&nbsp;</th>
	 						<th class="c">NANDO</th>
		 				</tr>
		 			</c:if>
		 		 
		 		 	<tr class="hove">
		 		 		<td class="c">
		 		 			<a:url href="/subjekt/${nb.id}">
		 		 				${nb.noCode}
		 		 				<c:if test="${not empty nb.aoCode}">
		 		 					(${nb.aoCode})
		 		 				</c:if>
		 		 			</a:url>	
		 		 		</td>
		 		 		<td>${nb.name}</td>
		 		 		<!-- 
		 		 		<td class="c">
		 		 			<c:if test="${not empty nb.city}">
		 		 			${nb.city}
		 		 			</c:if>
		 		 		</td>
		 		 		-->
		 		 		<td >
		 		 			<c:if test="${nb.etaCertificationAllowed}">
								<span class="true tt" title="<spring:message code="eta" />">ETA</span>
							</c:if>
		 		 		</td>
		 		 		<td class="c w100">
		 		 			
		 					<c:if test="${not empty nb.nandoCode}">
			 					<a target="_blank" href="${model.noaoUrl}${nb.nandoCode}">
			 						<spring:message code="more.info" />
			 					</a> 
		 					</c:if>
		 					<c:if test="${empty nb.nandoCode}">
			 					-
		 					</c:if>
		 					
		 		 		</td>
		 		 	</tr>
				</c:forEach>
					</table>
				</div>	
			</c:if>
			 <article>
					<a:localizedValue object="${model.webpage}" fieldName="bottomText" />
			 </article>
				
			 
		</div>
	</body>
</html>