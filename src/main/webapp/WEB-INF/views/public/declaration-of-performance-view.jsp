<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="dop" /></title>
		<meta name="robots" content="noindex,follow"/>
		<link rel="stylesheet" media="print" href="<c:url value="/resources/public/css/print.css" />" />
		<script src="<c:url value="/resources/public/js/storage.js" />"></script>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span><spring:message code="dop" /></span>
	</div> 

		<div id="main-content">

			<div id="localStorage"></div>				
				<c:if test="${model.success}">
					<script>var newDop = { token :  '${model.dop.token}', ehn : '${model.dop.standard.standardId}', system : '${model.dop.assessmentSystem.name}', created : new Date()  }; </script>
				</c:if>
				
				<c:if test="${dopNotFound}">
					<p class="error msg"><spring:message code="dop.dopNotFound" /> </p>
				</c:if>
				
				<c:if test="${empty dopNotFound}">
				
				<div id="dop-menu">
					<span><spring:message code="options"  />: </span> 
					<a class="edit" href="<c:url value="/dop/edit/${model.dop.token}" />" ><spring:message code="form.edit" /></a>
					<a class="delete" href="<c:url value="/dop/delete/${model.dop.token}" />" title="${model.dop.token}" ><spring:message code="form.delete" /></a>
					<a class="print" href="#"><spring:message  code="export.print" /></a>
					<a class="pdf" href="<c:url value="/dop/export/pdf/${model.dop.token}" />"><spring:message  code="export.pdf" /></a>
				</div>
				
				<h2 class="dop"><em><spring:message code="dop" /></em><span><strong>č.</strong> ${model.dop.numberOfDeclaration}</span></h2>
				
				<table class="dop-items">
					
					
					<tr>
						<td class="no">1.</td>
						<td class="label"><spring:message code="dop.productid" /></td>
						<td class="val">${model.dop.productId}</td>
					</tr>
					
					<tr>
						<td class="no">2.</td>
						<td class="label"><spring:message code="dop.serialid" /></td>
						<td class="val">${model.dop.serialId}</td>
					</tr>
					
					<tr>
						<td class="no">3.</td>
						<td class="label"><spring:message code="dop.intendedUse" /></td>
						<td class="val">${model.dop.intendedUse}</td>
					</tr>
					
					<tr>
						<td class="no">4.</td>
						<td class="label"><spring:message code="dop.manufacturer" /></td>
						<td class="val">${model.dop.manufacturer}</td>
					</tr>	
					
					<tr>
						<td class="no">5.</td>
						<td class="label"><spring:message code="dop.authorisedRepresentative" /></td>
						<td class="val">${model.dop.authorisedRepresentative}</td>
					</tr>	
					
					<tr>
						<td class="no">6.</td>
						<td class="label"><spring:message code="dop.assessmentSystem" /></td>
						<td class="val">${model.dop.assessmentSystem.name}</td>
					</tr>	
					
					<tr>
						<td class="no">7.</td>
						<td class="label"><spring:message code="dop.aono" /></td>
						<td class="val"><strong>${model.dop.notifiedBody.notifiedBodyCode} ${model.dop.notifiedBody.name}</strong><br />
							${model.dop.notifiedBody.address.city}, ${model.dop.notifiedBody.address.street} ${model.dop.notifiedBody.address.zip}, ${model.dop.notifiedBody.country.countryName}
						</td>
					</tr>
					
					<tr>
						<td class="no">8.</td>
						<td class="label"><spring:message code="dop.ehn" /></td>
						<td class="val">
							<a href="<c:url value="/ehn/${model.dop.standard.code}" />" target="_blank">${model.dop.standard.standardId}</a>
							(<em>${model.dop.standard.standardName}</em>)
						</td>
					</tr>
					
				</table>	
				
				
				<strong>9.</strong>
				<table id="dop">
					<thead>
						<tr>
							<th><spring:message code="dop.table.essentialCharacteristics" /></th>
							<th><spring:message code="cpr.requirement.level" /></th>
							<th><spring:message code="dop.table.performance" /></th>
							<th><spring:message code="dop.table.ehn" /></th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${model.dop.essentialCharacteristics}" var="i" varStatus="status">
							<tr>
								<td class="name">${i.requirement.name}</td>
								<td class="level">${i.requirement.levels}</td>
								<td class="value">${i.value}</td>
								<td class="ehn">
									<a href="<c:url value="/ehn/${model.standard.code}" />" target="_blank">${model.dop.standard.standardId}</a> 
									<em>${i.requirement.section}</em>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
						
				<table class="dop-items">
					<tr>
						<td class="no">10.</td>
						<td><strong><spring:message code="dop.10" /><br /><spring:message code="dop.11" /></strong></td>
					</tr>
				</table>
				
				
				<table class="one-colls">
					<tr>
						<td><spring:message code="dop.sig.function" /></td>
					</tr>
				</table>	
					
				<table class="two-colls">
					<tr>
						<td><spring:message code="dop.sig.left" /></td>
						<td><spring:message code="dop.sig.right" /></td>
					</tr>
				</table>				
			
				</c:if>
			 
		</div>
	</body>
</html>