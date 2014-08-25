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

		
			
				<c:if test="${not empty model.standard.standardCategory}">
					<table>
					<tr> 
						<td class="key">
							<strong><spring:message code="standardCategory.name" /> ${model.standard.standardId}</strong>
						</td>
						<td>
							<strong class="tooltip" title="<spring:message code="standardCategory.code" />">${model.standard.standardCategory.code}</strong>
							${not empty model.standard.standardCategory.code ? ' | ' : ''}
							<a:localizedValue object="${model.standard.standardCategory}" fieldName="name" />
						</td>
					</tr>
					<tr> 
						<td class="key">
							<strong><spring:message code="standardCategory.specialiyation" /> ${model.standard.standardId}</strong>
						</td>
						<td>
							<a:localizedValue object="${model.standard.standardCategory}" fieldName="specialization" />
						</td>
					</tr>
					<tr> 
						<td class="key">
							<strong><spring:message code="standardCategory.ojeu" /></strong>
						</td>
						<td>
							<a:localizedValue object="${model.standard.standardCategory}" fieldName="ojeuPublication" />
						</td>
					</tr>
					<c:if test="${not empty model.standard.standardCategory.noaoUrl}">
						<tr class="tooltip" title="<spring:message code="standardCategory.noaoNando" />"> 
							<td class="key">
								<strong>NO/AO:</strong>
							</td>
							<td>
								<a href="${model.standard.standardCategory.noaoUrl}" target="_blank">
									<spring:message code="standardCategory.noaoNando.view" /> <strong>${model.standard.standardId}</strong>
								</a>
							</td>
						</tr>
					</c:if>
				</table>
				</c:if>
				
				<c:if test="${not empty model.standard.standardCategory.regulations}">
					<table class="regulations">
						<tr>
							<th><spring:message code="regulation.eu" /></th>
							<th><spring:message code="regulation.cs" /></th>
							<th><spring:message code="regulation.sk" /></th>
						</tr>
						<c:forEach items="${model.standard.standardCategory.regulations}" var="i">
							<tr>
								<td>
								<c:if test="${i.euRegulation}">
									<span class="block-item">
										<a href="<a:localizedValue object="${i.euRegulationContent}" fieldName="pdf" />" 
											target="_blank" class="file pdf">
										<a:localizedValue object="${i.euRegulationContent}" fieldName="name" />
										</a>
									</span>
									<span class="pj-reg-descr">
									<a:localizedValue object="${i.euRegulationContent}" fieldName="description" />	
									</span>
								</c:if>
								</td>
								<td>
								<c:if test="${i.csRegulation}">
									<span class="block-item">
										<a href="<a:localizedValue object="${i.csRegulationContent}" fieldName="pdf" />" 
											target="_blank" class="file pdf">
										<a:localizedValue object="${i.csRegulationContent}" fieldName="name" />
										</a>
									</span>
									<span class="pj-reg-descr">
									<a:localizedValue object="${i.csRegulationContent}" fieldName="description" />	
									</span>
								</c:if>
								</td>
								<td>
								<c:if test="${i.skRegulation}">
									<span class="block-item">
										<a href="<a:localizedValue object="${i.skRegulationContent}" fieldName="pdf" />" 
											target="_blank" class="file pdf">
										<a:localizedValue object="${i.skRegulationContent}" fieldName="name" />
										</a>
									</span>
									<span class="pj-reg-descr">
									<a:localizedValue object="${i.skRegulationContent}" fieldName="description" />	
									</span>
								</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				
				<table>
				<c:if test="${not empty model.standard.standardGroups}">
					<tr class="tooltip" title="<spring:message code="standard.help.standardGroups"/>">
						<td class="key"><strong><spring:message code="standard.standardGroups" /></strong>:</td>
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