<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.notifiedBody.notifiedBodyCode} - ${fn:substring(model.notifiedBody.name, 0, 60)}</title>
		<meta name="description" content="${model.webpage.description}" />
		<script src="https://maps.googleapis.com/maps/api/js?v=3.5&amp;sensor=false"></script>
		<script src="<c:url value="/resources/public/js/google.maps.js" />"></script>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo;
			<a title="${model.webpage.name}" href="<c:url value="${model.webpage.code}" />">${model.webpage.name}</a> &raquo; 
			<span>${model.notifiedBody.name}</span>
	</div> 

		<div id="main-content">
			<article itemscope itemtype="http://data-vocabulary.org/Organization">
				<hgroup>
					<h1><spring:message code="subject" arguments="${model.notifiedBody.notifiedBodyCode}" /></h1>
					<h2 itemprop="name">${model.notifiedBody.name}</h2>
				</hgroup>
				
				<div class="nb-left">
					<strong class="head"><spring:message code="address" /></strong>
					<ul itemprop="address" itemscope  itemtype="http://data-vocabulary.org/Address">
						<li class="bigger">${model.notifiedBody.name}</li>
						<li><strong><spring:message code="address.street" />: </strong><span id="street" itemprop="street-address">${model.notifiedBody.address.street}</span></li>
						<li><strong><spring:message code="address.city" />: </strong><span id="city" itemprop="locality">${model.notifiedBody.address.city}</span>,<span id="zip" itemprop="postal-code">${model.notifiedBody.address.zip}</span></li>
						<li><strong><spring:message code="address.country" />: </strong><span id="country" itemprop="country-name">${model.notifiedBody.country.countryName}</span></li>
					</ul>
					
					<strong class="head"><spring:message code="contact.info" /></strong>
					<ul>
						<li><strong><spring:message code="form.phone" />: </strong><span itemprop="tel">${model.notifiedBody.phone}</span></li>
						<li><strong><spring:message code="form.fax" />: </strong>${model.notifiedBody.fax}</li>
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
					
					<ul class="ceta">
						<li><strong><spring:message code="eta" />:</strong>
							<c:if test="${model.notifiedBody.etaCertificationAllowed}">
								<spring:message code="yes" />
							</c:if>	
							<c:if test="${not model.notifiedBody.etaCertificationAllowed}">
								<spring:message code="no" />
							</c:if>	
						</li>
					</ul>
					
		
				
				</div>
				<div class="nb-right">
					<div id="map" style="width: 500px; height: 430px;"></div>
				</div>	
				<div class="clear"></div>
				
				${model.notifiedBody.description}
			</article>
			
			
			 
		</div>
	</body>
</html>