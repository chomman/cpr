<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<h1>${model.webpage.title}</h1>

<c:if test="${not empty model.webpage.topText}">
	<article>${model.webpage.topText}</article>
</c:if>
<c:if test="${not empty model.standardGroups}">
				
	<table class="groups ">
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
				 			<a:url href="/cpr/skupina/${i.code}" >${i.name}</a:url>
				 		</span>
				 		<!-- 
				 		<span class="english">
				 			<a href="<c:url value="/cpr/skupina/${i.code}" />">${i.englishName}</a>
				 		</span>
				 		 -->
			 		</td>
			 		<td class="mandate">
				 		<c:if test="${not empty i.mandates}" >
				 			<c:forEach items="${i.mandates}" var="m">
				 				<span class="main">
				 					<a target="_blank" class="file pdf min" href="${m.mandateFileUrl}">${m.mandateName}</a>
				 				</span>
			 				</c:forEach>
			 			</c:if>
			 		</td>
			 		<td class="mandate">
			 			<c:if test="${not empty i.mandates}" >
				 			<c:forEach items="${i.mandates}" var="m">
				 				<c:if test="${not empty m.changes}">
				 					<c:forEach items="${m.changes}" var="ch">
						 				<span class="complement">
						 					<a class="file pdf min" target="_blank" href="${ch.mandateFileUrl}">${ch.mandateName}</a>
						 				</span>		 		
						 		 	</c:forEach>
				 				</c:if>
			 				</c:forEach>
			 			</c:if>
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
<c:if test="${empty model.standardGroups}">
	<p class="msg alert">
		<spring:message code="alert.empty" />
	</p>
</c:if>

<c:if test="${not empty model.webpage.bottomText}">
	<article>${model.webpage.bottomText}</article>
</c:if>