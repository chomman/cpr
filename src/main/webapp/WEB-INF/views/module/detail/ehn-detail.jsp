<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:set var="editable" value="false"/>
<sec:authorize access="hasRole('ROLE_ADMIN')"> 
	<c:set var="editable" value="true" />
</sec:authorize>

<!DOCTYPE html>
<html>
	<head>
		<title>${model.standard.standardId} - ${fn:substring(model.standard.name, 0, 60)}</title>
		<meta name="description" content="${model.standard.standardId} - ${model.standard.name}" />
		<meta name="keywords" content="${model.standard.standardId}" />
	</head>
	<body>
		
	<div id="main-content">
		<article>
			<hgroup>
				<h1>${model.standard.standardId}</h1>
				<h2>${model.standard.name}</h2>
			</hgroup>

		
			<table>
				
				<c:if test="${not empty model.standard.replacedStandardId}">
					<tr>
						<td class="key"><strong><spring:message code="cpr.standard.replacedStandardId"/>:</strong></td>
						<td>${model.standard.replacedStandardId}</td>
					</tr>
				</c:if>
				
				<c:if test="${not empty model.standard.standardGroups}">
					<tr class="tooltip" title="<spring:message code="standard.help.standardGroups"/>">
						<td class="key"><strong><spring:message code="ehn.standardGroups" /></strong>:</td>
						<td>
							<c:forEach items="${model.standard.standardGroups}" var="i">
								<a:url cssClass="block-item" href="/cpr/skupina/${i.code}">${i.code} - ${i.name}</a:url> 
							</c:forEach>
						</td>
					</tr>
						
					<tr> 
						<td class="key">
							<strong><spring:message code="commissiondecision" /></strong>
						</td>
						<td>
							<c:forEach items="${model.standard.standardGroups}" var="i">
								<span class="block-item">
									<a class="file pdf min inline" target="_blank" href="${i.commissionDecision.url}">
									${i.commissionDecision.label}
									</a>
									<c:if test="${not empty i.commissionDecision.draftAmendmentLabel}">
										, (<a class="file pdf min inline" target="_blank" href="${i.commissionDecision.draftAmendmentUrl}">
										${i.commissionDecision.draftAmendmentLabel}
									</a>)
									</c:if>
								</span>
							</c:forEach>
						</td>
					</tr>
					
					<tr>
						<td class="key">
							<strong><spring:message code="ehn.mandates" /></strong>
						</td>
						<td>
							<c:forEach items="${model.standard.standardGroups}" var="i">
								<c:forEach items="${i.mandates}" var="m">
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
							</c:forEach>
						</td>
					</tr>
						
					
				</c:if>
				
				
				<c:if test="${not empty model.standard.startValidity}">
					<tr class="tooltip" title="<spring:message code="standard.help.validity"/>"> 
						<td class="key"><strong><spring:message code="standard.validity"/>:</strong></td>
						<td>
							<joda:format value="${model.standard.startValidity}" pattern="dd.MM.yyyy"/>
						</td>
					</tr>
				</c:if>
				
				<c:if test="${not empty model.standard.stopValidity}">
					<tr class="tooltip" title="<spring:message code="standard.help.endofvalidity"/>"> 
						<td class="key"><strong><spring:message code="standard.endofvalidity"/>:</strong></td>
						<td>
							<joda:format value="${model.standard.stopValidity}" pattern="dd.MM.yyyy"/>
						</td>
					</tr>
				</c:if>
				
				<c:if test="${not empty model.standard.released}">
					<tr>
						<td class="key"><strong><spring:message code="standard.releaseDate"/>:</strong></td>
						<td>
							<joda:format value="${model.standard.released}" pattern="MMMM, yyyy"/>	
						</td>
					</tr>
				</c:if>

			</table>
			<div class="clear"></div>
			<!-- CSNs  -->
			
			<c:if test="${not empty model.standard.standardCsns}">
				<div class="public-box">
					<h3><spring:message code="csn" /> </h3>
					<table class="csn">
						<c:forEach items="${model.standard.standardCsns}" var="csn">
							<tr>
								<td class="name key">
									<a:standardCsnUrl object="${csn}" editable="${editable}" />
								</td>
								<td>
								<c:if test="${not empty csn.standardStatus and csn.standardStatus.id != 1}">
									(<spring:message code="${csn.standardStatus.name}" />)
								</c:if>
								${csn.note}
								</td>
							</tr>
					 		<c:forEach items="${csn.standardCsnChanges}" var="j">
						 		<tr class="standard-change csn-change">
						 			<td class="name key">
						 				<a:standardCsnUrl object="${j}" editable="${editable}" />
						 			</td>					
						 			<td >${csn.note}</td>
						 		</tr>
					 		</c:forEach>
						</c:forEach>
					</table>
					<p class="msg info"><spring:message code="standard.help.csn" /></p>
				</div>
			</c:if>
			
			<c:if test="${not empty model.nb.nandoCode}">		
				<a target="_blank" href="${model.noaoUrl}${model.nb.nandoCode}" title="NANDO database">
 					${nb.noCode} <c:if test="${not empty nb.aoCode}">(${nb.aoCode})</c:if>
 				</a>		
		 	</c:if>
			<!-- AO/NO  -->
			<c:if test="${not empty model.standard.notifiedBodies}">
				<div class="public-box">
					<h3><spring:message code="standard.noao" /> </h3>
					<table class="ehn-no">
						<c:forEach items="${model.standard.notifiedBodies}" var="snb">
							<tr>
								<td>
									<a:noaoUrl object="${snb}" editable="${editable}" codesOnly="false" />
				 		 		</td>
				 		 		<td class="c">
				 		 			<c:if test="${not empty snb.notifiedBody.city}">
				 		 			${snb.notifiedBody.city}
				 		 			</c:if>
				 		 		</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
			
			
			<!-- Systemy PS  -->
			<c:if test="${not empty model.standard.assessmentSystems}">
				<div class="public-box"> 
					<h3 class="tooltip" title="<spring:message code="standard.help.assessment"/>"><spring:message code="cpr.as.title" /> </h3>
					<table class="ehn-as">
						<c:forEach items="${model.standard.assessmentSystems}" var="as">
							<tr>
								<td>
									<a title="${as.name}"  href="<c:url value="/cpr/as/${as.id}" />">${as.name}</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
			
			
			<!-- TEXT   -->
			<c:if test="${not empty model.standared.text}">
				<div class="descr">
					${model.standared.text}
				</div>
			</c:if>
			
		</article>	
	</div>
	</body>
</html>