<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.standard.standardId} - ${model.standard.standardName}</title>
		<meta name="description" content="${model.standard.standardName}" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span>${model.standard.standardId} - ${model.standard.standardName}</span>
	</div> 
	
	
	<div id="main-content">
		<article>
			<hgroup>
				<h1>${model.standard.standardId}</h1>
				<h2>${model.standard.standardName}</h2>
			</hgroup>
			
			
			
			<table>
				
				<c:if test="${not empty model.standard.replacedStandardId}">
					<tr>
						<td><strong><spring:message code="cpr.standard.replacedStandardId"/>:</strong></td>
						<td>${model.standard.replacedStandardId}</td>
					</tr>
				</c:if>
				
				<tr>
					<td><strong><spring:message code="standard.group" /></strong>:</td>
					<td>
						<a title="${model.standard.standardGroup.groupName}" href="<c:url value="/cpr/skupina/${model.standard.standardGroup.code}" />"> 
								${model.standard.standardGroup.groupName}
						</a>
					</td>
				</tr>
				
				
				<c:if test="${not empty model.standard.startValidity}">
					<tr>
						<td><strong><spring:message code="cpr.standard.validity.from"/>:</strong></td>
						<td><joda:format value="${model.standard.startValidity}" pattern="dd.MM.yyyy"/>
							<c:if test="${not empty model.standard.stopValidity}">
								&nbsp; - &nbsp; <joda:format value="${model.standard.stopValidity}" pattern="dd.MM.yyyy"/>
							</c:if>
						</td>
					</tr>
				</c:if>
				
				
				
				<c:if test="${not empty model.standard.startConcurrentValidity}">
					<tr>
						<td><strong><spring:message code="cpr.standard.concurrentvalidity.form"/>:</strong></td>
						<td><joda:format value="${model.standard.startConcurrentValidity}" pattern="dd.MM.yyyy"/>
							<c:if test="${not empty model.standard.stopConcurrentValidity}">
								&nbsp; - &nbsp; <joda:format value="${model.standard.stopConcurrentValidity}" pattern="dd.MM.yyyy"/>
							</c:if>
						</td>
					</tr>
				</c:if>

			</table>
			
			
			<!-- CSNs  -->
			
			<c:if test="${not empty model.standard.standardCsns}">
				<div id="public-box">
					<h3><spring:message code="csn" /> </h3>
					<table class="csn">
						<c:forEach items="${model.standard.standardCsns}" var="csn">
							<tr>
								<td class="name">
									<a class="file pdf" target="_blank" href="${fn:replace(commonPublic.settings.csnOnlineUrl, '{0}', csn.csnOnlineId)}">
										${csn.csnName}
									</a>	
								</td>
								<td>${csn.note}</td>
							</tr>
						</c:forEach>
					</table>
					<p class="msg info"><spring:message code="csnonline.info" /></p>
				</div>
			</c:if>
			
			
			<!-- AO/NO  -->
			<c:if test="${not empty model.standard.notifiedBodies}">
				<div id="public-box">
					<h3><spring:message code="standard.noao" /> </h3>
					<table class="ehn-no">
						<c:forEach items="${model.standard.notifiedBodies}" var="nb">
							<tr>
								<td><a href="<c:url value="/subjekt/${nb.code}" />">${nb.notifiedBodyCode}</a></td>
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
				<div id="public-box">
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
			
			
			
		</article>	
	</div>
	</body>
</html>