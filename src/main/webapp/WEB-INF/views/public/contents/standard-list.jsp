<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<form id="search-form" method="get">
		<label for="query">
			<spring:message code="standard.public.search" />
		</label>	
		<input type="text" class="query" name="query" value="${model.params.query}"  />
		<input type="submit" value="Vyhledat" class="btn" />
</form>


<c:if test="${empty model.standards}">
	<p class="msg alert">
		<spring:message code="standard.public.search.empty"  arguments="${model.params.query}" />
	</p>
</c:if>


<c:if test="${not empty model.standards}">
							
		<!-- STRANKOVANIE -->
		<c:if test="${not empty model.paginationLinks}" >
			<div class="pagination">
			<c:forEach items="${model.paginationLinks}" var="i">
				<c:if test="${not empty i.url}">
					<a href="<c:url value="${i.url}"  />">${i.anchor}</a>
				</c:if>
				<c:if test="${empty i.url}">
					<span>${i.anchor}</span>
				</c:if>
			</c:forEach>
			</div>
		</c:if>					
							
		<table class="group-detail">
			<thead>
				<tr>
					<th><spring:message code="cpr.standard.id" /></th>
					<tH><spring:message code="cpr.standard.name" /></th>
					<th><spring:message code="standard.validity" /></th>
					<th><spring:message code="assessment" /></th>
				</tr>
			</thead>
			<tbody>
				 <c:forEach items="${model.standards}" var="i">
				 	<tr>
				 		<td class="norm"><a href="<c:url value="/ehn/${i.id}" />" class="tt" title="<spring:message code="moreinfo" />" >${i.standardId}</a></td>
				 		<td>${i.czechName}</td>
				 		<td class="validity c ">
				 			<c:if test="${not empty i.startValidity}">
				 				<joda:format value="${i.startValidity}" pattern="${commonPublic.dateTimeFormat}"/>
				 			</c:if>
				 			<c:if test="${not empty i.stopValidity}">
				 			 -	<joda:format value="${i.stopValidity}" pattern="${commonPublic.dateTimeFormat}"/>
				 			</c:if>
				 		</td>
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
				 		
				 	</tr>
				 </c:forEach>
			</tbody>
		</table>
		
</c:if>