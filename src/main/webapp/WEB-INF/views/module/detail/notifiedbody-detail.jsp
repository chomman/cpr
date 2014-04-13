<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>
			${model.notifiedBody.noCode} 
			<c:if test="${not empty model.notifiedBody.aoCode}">
				(${model.notifiedBody.aoCode})
			</c:if> 
			- ${fn:substring(model.notifiedBody.name, 0, 60)}
		</title>
		<script src="https://maps.googleapis.com/maps/api/js?v=3.5&amp;sensor=false"></script>
		<script src="<c:url value="/resources/public/js/google.maps.js" />"></script>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo;
			<c:if test="${not empty model.webpage}">
				<webpage:a webpage="${model.webpage}" /> &raquo;
			</c:if>
			<span>${model.notifiedBody.name}</span>
	</div> 

		<div id="main-content">
			<article itemscope itemtype="http://data-vocabulary.org/Organization">
				<hgroup>
					<h1>
						<spring:message code="subject" arguments="${model.notifiedBody.noCode}" /> 
						<c:if test="${not empty model.notifiedBody.aoCode}">
							(${model.notifiedBody.aoCode})
						</c:if> 
					</h1>
					<h2 itemprop="name">${model.notifiedBody.name}</h2>
				</hgroup>
				
				<div class="nb-left">
					<strong class="head"><spring:message code="address" /></strong>
					<ul itemprop="address" itemscope  itemtype="http://data-vocabulary.org/Address">
						<li class="bigger">${model.notifiedBody.name}</li>
						<c:if test="${not empty model.notifiedBody.street}">
							<li><strong><spring:message code="address.street" />: </strong><span id="street" itemprop="street-address">${model.notifiedBody.street}</span></li>
						</c:if>
						<c:if test="${not empty model.notifiedBody.city}">
							<li>
								<strong><spring:message code="address.city" />: </strong>
								<span id="city" itemprop="locality">${model.notifiedBody.city}</span>,
								<span id="zip" itemprop="postal-code">${model.notifiedBody.zip}</span>
							</li>
						</c:if>
						<li><strong><spring:message code="address.country" />: </strong><span id="country" itemprop="country-name">${model.notifiedBody.country.countryName}</span></li>
						<c:if test="${not empty model.notifiedBody.nandoCode}">
			 				<li>
								<a target="_blank" href="${model.noaoUrl}${model.notifiedBody.nandoCode}" title="NANDO database">
				 					<strong><spring:message code="moreinfo" /></strong>
				 				</a>
			 				</li> 
		 				</c:if>
						
						
					</ul>
					
					<strong class="head"><spring:message code="contact.info" /></strong>
					<ul>
						<c:if test="${not empty model.notifiedBody.phone}">
							<li><strong><spring:message code="form.phone" />: </strong><span itemprop="tel">${model.notifiedBody.phone}</span></li>
						</c:if>
						<c:if test="${not empty model.notifiedBody.fax}">
							<li><strong><spring:message code="form.fax" />: </strong>${model.notifiedBody.fax}</li>
						</c:if>
						<c:if test="${not empty model.notifiedBody.email}">
						<li><strong><spring:message code="form.email" />: </strong>
							<a href="mailto:${model.notifiedBody.email}" >${model.notifiedBody.email}</a>
						</li>
						</c:if>	
						<c:if test="${not empty model.notifiedBody.webpage}">
							<li><strong><spring:message code="form.web" />: </strong>
								<a itemprop="url" href="http://${model.notifiedBody.webpage}" target="_blank">${model.notifiedBody.webpage}</a>
							</li>
						</c:if>	
						
					</ul>
					
					<c:if test="${not empty model.notifiedBody.etaCertificationAllowed}">
						<!-- <ul class="ceta">
							<li><strong><spring:message code="eta" />:</strong>
								<c:if test="${model.notifiedBody.etaCertificationAllowed}">
									<spring:message code="yes" />
								</c:if>	
								<c:if test="${not model.notifiedBody.etaCertificationAllowed}">
									<spring:message code="no" />
								</c:if>	
							</li>
						</ul>
						 -->
					</c:if>	
					
				
				</div>
				<c:if test="${not empty model.notifiedBody.street and not empty model.notifiedBody.city}">
					<div class="nb-right">
						<div id="map" style="width: 500px; height: 430px;"></div>
					</div>	
				</c:if>
				<div class="clear"></div>
				
				${model.notifiedBody.description}
			</article>
			
			<jsp:include page="../../include/standard-table.jsp" />
			 
		</div>
	</body>
</html>