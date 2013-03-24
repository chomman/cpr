<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

		<h2 class="dop"><em><spring:message code="dop" /></em><span><strong>č.</strong> ${model.dop.numberOfDeclaration}</span></h2>
		
		<table class="dop-items">
			
			
			<tr>
				<td class="no">1.</td>
				<td class="label"><spring:message code="dop.productid" /></td>
				<td class="val"><c:out escapeXml="true" value="${model.dop.productId}" /></td>
			</tr>
			
			<tr>
				<td class="no">2.</td>
				<td class="label"><spring:message code="dop.serialid" /></td>
				<td class="val"><c:out escapeXml="true" value="${model.dop.serialId}" /></td>
			</tr>
			
			<tr>
				<td class="no">3.</td>
				<td class="label"><spring:message code="dop.intendedUse" /></td>
				<td class="val"><c:out escapeXml="true" value="${model.dop.intendedUse}" /></td>
			</tr>
			
			<tr>
				<td class="no">4.</td>
				<td class="label"><spring:message code="dop.manufacturer" /></td>
				<td class="val"><c:out escapeXml="true" value="${model.dop.manufacturer}" /></td>
			</tr>	
			
			<tr>
				<td class="no">5.</td>
				<td class="label"><spring:message code="dop.authorisedRepresentative" /></td>
				<td class="val"><c:out escapeXml="true" value="${model.dop.authorisedRepresentative}" /></td>
			</tr>	
			
			<tr>
				<td class="no">6.</td>
				<td class="label"><spring:message code="dop.assessmentSystem" /></td>
				<td class="val">
				${model.dop.assessmentSystem.name} 
				<c:if test="${not empty model.dop.assessmentSystemNote}"><em>(<c:out escapeXml="true" value="${model.dop.assessmentSystemNote}" />)</em></c:if>
				<c:if test="${model.dop.cumulative}">
					<br /> ${model.dop.assessmentSystem2.name}
					<c:if test="${not empty model.dop.assessmentSystemNote2}"><em>(<c:out escapeXml="true" value="${model.dop.assessmentSystemNote2}" />)</em></c:if>
				</c:if>
				</td>
			</tr>	
		</table>
		<table class="no-border">
			<tr>
				<td class="no">7.</td>
				<td>
					<strong><spring:message code="dop.point7" /></strong>
					
					${model.point7}
				</td>
			</tr>
			
			<tr>
				<td class="no">8.</td>
				<td>
					<strong><spring:message code="dop.point8" /></strong>
					<span class="center-val"><c:out escapeXml="true" value="${model.dop.eta}" /></span>
				</td>
			</tr>
		</table>	
		
		
		<strong>9.</strong>
		<table id="dop">
			<thead>
				<tr>
					<th><spring:message code="dop.table.essentialCharacteristics" /></th>
					<th><spring:message code="dop.table.performance" /></th>
					<th><spring:message code="dop.table.ehn" /></th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${model.dop.essentialCharacteristics}" var="i" varStatus="status">
					<tr>
						<td class="name">${i.requirement.name}</td>
						<td class="value"><c:out escapeXml="true" value="${i.value}" /></td>
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
