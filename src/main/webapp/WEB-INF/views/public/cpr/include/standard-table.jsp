<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<c:if test="${not empty model.standards}">
											
		<table class="standards">
			<thead>
				<tr>
					<th><spring:message code="cpr.standard.id" /></th>
					<tH><spring:message code="cpr.standard.name" /></th>
					<th><spring:message code="standard.validity" /></th>
					<th><spring:message code="standard.noao" /></th>
					<th><spring:message code="assessment" /></th>
					<th><spring:message code="standard.standardGroups" /></th>
				</tr>
			</thead>
			<tbody>
				 <c:forEach items="${model.standards}" var="i">
				 	<tr 
				 	class="<c:if test="${not empty i.standardChanges}">has-change</c:if> <c:if test="${not empty i.standardStatus }">${i.standardStatus.cssClass}</c:if>" 
				 	<c:if test="${not empty i.standardStatus and i.standardStatus.id != 1}"> title="<spring:message code='${i.standardStatus.name}' />" </c:if>
				 	>
				 		
				 		<td class="norm">
				 		
				 			<span><a href="<c:url value="/ehn/${i.id}" />" class="tt" title="<spring:message code="moreinfo" />" >${i.standardId}</a></span>
				 			<c:if test="${not empty i.replaceStandard}">
				 				<span <c:if test="${not empty i.standardStatus and i.standardStatus.id == 2}">class="s-cancelated"</c:if> >${i.standardId}</span>
				 			</c:if>
				 		</td>
				 		<td>${i.czechName}</td>
				 		<td class="validity c ">
				 			<c:if test="${not empty i.startValidity}">
				 				<joda:format value="${i.startValidity}" pattern="${commonPublic.dateTimeFormat}"/>
				 			</c:if>
				 			<c:if test="${not empty i.stopValidity}">
				 			 -	<joda:format value="${i.stopValidity}" pattern="${commonPublic.dateTimeFormat}"/>
				 			</c:if>
				 		</td>
				 		<td class="aono c">
				 			<c:if test="${not empty i.notifiedBodies}">
				 				<c:forEach items="${i.notifiedBodies}" var="j" >
				 					<span>
				 					<c:if test="${not empty j.nandoCode}">
					 					<a target="_blank" class="tt" title="${j.name}" href="${model.noaoUrl}${j.nandoCode}">
					 						${j.noCode} <c:if test="${not empty j.aoCode }">(${j.aoCode})</c:if>
					 					</a> 
				 					</c:if>
				 					<c:if test="${empty j.nandoCode}">
					 					${j.noCode} <c:if test="${not empty j.aoCode }">(${j.aoCode})</c:if>
				 					</c:if>
				 					</span>
				 				</c:forEach>
				 			</c:if>
				 			<c:if test="${empty i.notifiedBodies}">
				 				-
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
				 		<td class="standardGroups c">
				 			<c:if test="${not empty i.standardGroups}">
				 				<c:forEach items="${i.standardGroups}" var="j" varStatus="status">
				 					<a class="tt" title="${j.czechName}"
				 					 href="<c:url value="/skupina/${j.code}" />">
				 						<strong>${j.code}</strong>
				 					</a>
				 					<c:if test="${not status.last}">, </c:if> 
				 				</c:forEach>
				 			</c:if>
				 			<c:if test="${empty i.standardGroups}">
				 				- 
				 			</c:if>
				 		</td>
				 		
				 	</tr>
				 	<c:if test="${not empty i.standardChanges}">
				 		<c:forEach items="${i.standardChanges}" var="j">
					 		<tr class="standard-change">
					 			<td class="standard-change-code">
					 				<span>${j.changeCode}</span>
					 			</td>
					 			<td>&nbsp;</td>
					 			<td class="validity c ">
							 		<c:if test="${not empty j.startValidity}">
							 				<joda:format value="${j.startValidity}" pattern="${commonPublic.dateTimeFormat}"/>
							 		</c:if>
							 		<c:if test="${not empty j.stopValidity}">
							 			 -	<joda:format value="${j.stopValidity}" pattern="${commonPublic.dateTimeFormat}"/>
							 		</c:if>
					 			</td>
					 			<td>&nbsp;</td>
					 			<td>&nbsp;</td>
					 			<td>&nbsp;</td>
					 		</tr>
				 		</c:forEach>
				 	</c:if>
				 </c:forEach>
			</tbody>
		</table>
		
</c:if>