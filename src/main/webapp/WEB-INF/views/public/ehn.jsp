<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.standard.standardId} - ${fn:substring(model.standard.name, 0, 60)}</title>
		<meta name="description" content="${model.standard.standardId} - ${model.standard.name}" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo;  
			<a:url title="${model.webpage.title}" href="${model.webpage.code}">${model.webpage.name}</a:url> &raquo;
			<span>${model.standard.standardId}</span>
	</div> 
	
	
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
					<tr>
						<td class="key"><strong><spring:message code="standard.group" /></strong>:</td>
						<td>
							<c:forEach items="${model.standard.standardGroups}" var="i">
								<a:url cssClass="block-item" href="/cpr/skupina/${i.code}">${i.code} - ${i.name}</a:url> 
							</c:forEach>
						</td>
					</tr>
				</c:if>
				
				
				<c:if test="${not empty model.standard.startValidity}">
					<tr>
						<td class="key"><strong><spring:message code="cpr.standard.validity.from"/>:</strong></td>
						<td><joda:format value="${model.standard.startValidity}" pattern="dd.MM.yyyy"/>
							<c:if test="${not empty model.standard.stopValidity}">
								&nbsp; - &nbsp; <joda:format value="${model.standard.stopValidity}" pattern="dd.MM.yyyy"/>
							</c:if>
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
									<c:if test="${not empty csn.csnOnlineId }">
										<a class="file pdf" target="_blank" href="${fn:replace(commonPublic.settings.csnOnlineUrl, '{0}', csn.csnOnlineId)}">
											${csn.csnName}
										</a>
									</c:if>
									<c:if test="${empty csn.csnOnlineId }">
										<span class="file pdf">${csn.csnName}</span>
									</c:if>
								</td>
								<td>${csn.note}</td>
							</tr>
					 		<c:forEach items="${csn.standardCsnChanges}" var="j">
						 		<tr class="standard-change csn-change">
						 			<td class="standard-change-code">
						 				<span>
							 				<c:if test="${not empty j.csnOnlineId }">
												<a class="file pdf min" target="_blank" href="${fn:replace(commonPublic.settings.csnOnlineUrl, '{0}', j.csnOnlineId)}">
													${j.changeCode}
												</a>
											</c:if>
											<c:if test="${empty j.csnOnlineId }">
												${j.changeCode}>
											</c:if>
										</span>
						 			</td>					
						 			<td>${csn.note}</td>
						 		</tr>
					 		</c:forEach>
						</c:forEach>
					</table>
					<p class="msg info"><spring:message code="csnonline.info" /></p>
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
						<c:forEach items="${model.standard.notifiedBodies}" var="nb">
							<tr>
								<td>
									<c:if test="${not empty nb.nandoCode}">		
										<a target="_blank" href="${model.noaoUrl}${nb.nandoCode}" title="NANDO database">
						 					${nb.noCode} <c:if test="${not empty nb.aoCode}">(${nb.aoCode})</c:if>
						 				</a>		
								 	</c:if>
								 	<c:if test="${empty nb.nandoCode}">		
										${nb.noCode} <c:if test="${not empty nb.aoCode}">(${nb.aoCode})</c:if>	
								 	</c:if>
								</td>
				 		 		<td>${nb.name}</td>
				 		 		<td class="c">
				 		 			<c:if test="${not empty nb.address.city}">
				 		 			${nb.address.city}
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
					<h3><spring:message code="cpr.as.title" /> </h3>
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
			
			<!-- Mandary  -->
			<c:if test="${not empty model.standard.standardGroups}">
				<div class="public-box">
					<h3><spring:message code="cpr.mandates.title" /> </h3>
					<table class="ehn-as">
						<c:forEach items="${model.standard.standardGroups}" var="i">
							
							<c:forEach items="${j.standardGroupMandates}"  var="j">
							<tr>
								<td>
									<a class="file pdf" href="${j.mandate.mandateFileUrl}">
										${j.mandate..mandateName}
									</a>
								</td>
							</tr>
							</c:forEach>
							 
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