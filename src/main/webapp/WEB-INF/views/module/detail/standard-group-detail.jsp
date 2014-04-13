<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${fn:substring(model.group.name, 0, 90)}</title>		
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo;  
			<c:if test="${not empty model.webpage}">
				<webpage:a webpage="${model.webpage}" /> &raquo;
			</c:if>
			<span>${fn:substring(model.group.name, 0, 45)}...</span>
	</div> 
		
		<a class="back"  href="<webpage:link webpage="${model.webpage}" />">
			&laquo; <spring:message code="backto"/>
		</a>
		
		
		<div id="main-content">
			<article>
				<hgroup>
					<h1><spring:message code="group.code" arguments="${model.group.code}" /></h1>
					<h2>${model.group.name}</h2>
				</hgroup>
			</article>	
			<table class="group-info">
			<tr>
				<td class="key">
					<strong><spring:message code="commissiondecision" /></strong>
				</td>
				<td>
					<span class="block-item">
						<a class="file pdf min inline" target="_blank" href="${model.group.commissionDecision.url}">
						${model.group.commissionDecision.label}
						</a>
						<c:if test="${not empty model.group.commissionDecision.draftAmendmentLabel}">
							, (<a class="file pdf min inline" target="_blank" href="${model.group.commissionDecision.draftAmendmentUrl}">
							${model.group.commissionDecision.draftAmendmentLabel}
						</a>)
						</c:if>
					</span>
				</td>
			</tr>
			<tr>
				<td class="key">
					<strong><spring:message code="ehn.mandates" /></strong>
				</td>
				<td>
					<c:forEach items="${model.group.mandates}" var="m">
						<span class="block-item">
							<a class="file pdf min inline" target="_blank" href="${m.mandateFileUrl}">
							${m.mandateName}
							</a>
							<c:if test="${not empty m.changes}">
								<span class="mchanges">
									<spring:message code="ehn.mandates.changes" />: 
									<c:forEach items="${m.changes}" var="j" varStatus="s">
										<a class="file pdf min inline" target="_blank" href="${j.mandateFileUrl}">
											${j.mandateName}
										</a>	
										<c:if test="${not s.last}">, </c:if>  
									</c:forEach>
								</span>
							</c:if>
						</span>
					</c:forEach>
				</td>
			</tr>
		</table>
				<jsp:include page="../../include/standard-table.jsp" />
		</div> 
	</body>
</html>


