<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${fn:substring(model.group.groupName, 0, 90)}</title>		
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo;
			<a title="${model.parentWebpage.title}" href="<c:url value="${model.parentWebpage.code}" />">${model.parentWebpage.name}</a> &raquo;
			<a title="${model.webpage.title}" href="<c:url value="${model.webpage.code}" />">${model.webpage.name}</a> &raquo;
			<span>${fn:substring(model.group.groupName, 0, 75)}...</span>
	</div> 
		<a class="back" title="${model.webpage.title}" href="<c:url value="${model.webpage.code}" />">&laquo; <spring:message code="backto"/> ${model.webpage.name}</a> 
		<div id="main-content">
			 
				
					<article>
						<hgroup>
							<h1><spring:message code="group.code" arguments="${model.group.groupCode}" /></h1>
							<h2>${model.group.groupName}</h2>
						</hgroup>
					
						<p>${model.group.description}</p>
					</article>	
					
					<c:if test="${not empty model.standards}">
							
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
									 		<td class="norm"><a href="<c:url value="/ehn/${i.code}" />" class="tt" title="<spring:message code="moreinfo" />" >${i.standardId}</a></td>
									 		<td>${i.standardName}</td>
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
			</div>
			 
	</body>
</html>


