<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>


<c:if test="${not empty model.standardGroups}">
				
	<table class="groups">
		<thead>
			<tr>
				<th><spring:message code="standardGroups.code" /></th>
				<th><spring:message code="standardGroups.name" /></th>
				<th><spring:message code="standardGroups.mandate" /></th>
				<th><spring:message code="standardGroups.mandateChanges" /></th>
				<th><spring:message code="standardGroups.commissionDecisionCz" /></th>
				<th><spring:message code="standardGroups.commissionDecisionEn" /></th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach items="${model.standardGroups}" var="i">
			 	<tr>
			 		<td class="c code">${i.code}</td>
			 		<td class="groupName">
				 		<span class="czech">
				 			<a href="<c:url value="${model.parentWebpage.code}" />?standardGroup=${i.id}" >${i.czechName}</a>
				 		</span>
				 		<span class="english">
				 			<a href="<c:url value="${model.parentWebpage.code}" />?standardGroup=${i.id}" >${i.englishName}</a>
				 		</span>
			 		</td>
			 		<td class="mandate">
				 		<c:forEach items="${i.standardGroupMandates}" var="j">
				 			<c:if test="${not j.complement}">
				 				<span class="main">
				 					<a target="_blank" class="file pdf min" href="${j.mandate.mandateFileUrl}">${j.mandate.mandateName}</a>
				 				</span>
				 			</c:if>
				 		</c:forEach>
			 		</td>
			 		<td class="mandate">
				 		<c:forEach items="${i.standardGroupMandates}" var="j">
				 			<c:if test="${j.complement}">
				 				<span class="complement">
				 					<a class="file pdf min" target="_blank" href="${j.mandate.mandateFileUrl}">${j.mandate.mandateName}</a>
				 				</span>
				 			</c:if>
				 		</c:forEach>
			 		</td>
			 		<td class="commissionDecision">
				 		<c:if test="${not empty i.commissionDecision}">
				 			<span><a class="file pdf min" href="${i.commissionDecision.czechFileUrl}">${i.commissionDecision.czechLabel}</a></span>
				 		</c:if>
			 		</td>
			 		<td class="commissionDecision">
				 		<c:if test="${not empty i.commissionDecision}">
				 			<span><a class="file pdf min"  href="${i.commissionDecision.englishFileUrl}">${i.commissionDecision.englishLabel}</a></span>
				 			<c:if test="${not empty i.commissionDecision.draftAmendmentUrl}">
					 			<span><a class="file pdf min"  href="${i.commissionDecision.draftAmendmentUrl}">${i.commissionDecision.draftAmendmentLabel}</a></span>
					 		</c:if>
				 		</c:if>
			 		</td>
			 		
			 	</tr>
			 </c:forEach>
		</tbody>
	</table>
</c:if>