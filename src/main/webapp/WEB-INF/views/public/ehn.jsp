<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.standard.standardId} - ${model.standard.czechName}</title>
		<meta name="description" content="${model.standard.standardId} - ${model.standard.czechName}" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo;
			<a title="${model.webpage.title}" href="<c:url value="${model.webpage.code}" />">${model.webpage.name}</a> &raquo; 
			<span>${model.standard.standardId} - ${model.standard.czechName}</span>
	</div> 
	
	
	<div id="main-content">
		<article>
			<hgroup>
				<h1>${model.standard.standardId}</h1>
				<h2>${model.standard.czechName}</h2>
			</hgroup>
			
			
			
			<table>
				
				<c:if test="${not empty model.standard.replacedStandardId}">
					<tr>
						<td class="key"><strong><spring:message code="cpr.standard.replacedStandardId"/>:</strong></td>
						<td>${model.standard.replacedStandardId}</td>
					</tr>
				</c:if>
				
				<tr>
					<td class="key"><strong><spring:message code="standard.group" /></strong>:</td>
					<td>
						<c:forEach items="${model.standard.standardGroups}" var="i">
							<a class="block-item" href="<c:url value="/cpr/skupina/${i.code}" />">${i.code} - ${i.czechName}</a> 
						</c:forEach>
					</td>
				</tr>
				
				
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
			
			
			<!-- AO/NO  -->
			<c:if test="${not empty model.standard.notifiedBodies}">
				<div class="public-box">
					<h3><spring:message code="standard.noao" /> </h3>
					<table class="ehn-no">
						<c:forEach items="${model.standard.notifiedBodies}" var="nb">
							<tr>
								<td>${nb.noCode} (${nb.aoCode})</td>
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