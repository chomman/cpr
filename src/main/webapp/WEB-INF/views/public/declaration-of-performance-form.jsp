<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
		<meta name="description" content="${model.webpage.description}" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span>${model.webpage.name}</span>
	</div> 

		<div id="main-content">
			<article>
				${model.webpage.topText}
			</article>
			<c:if test="${model.webpage.webpageContent.id == 8}">
				
				<c:url value="${model.webpage.code}" var="formUrl"/>	
				<form:form  htmlEscape="true"  modelAttribute="declarationOfPerformance" id="dop" method="post" action="${formUrl}" >
				
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
					
					<div class="center item">
						<label><spring:message code="dop.numberofdeclaration" /></label>
						<form:input cssClass="w300 required" path="declarationOfPerformance.numberOfDeclaration" />
						<div class="clear"></div>
					</div>
					
					
					<div class="fitem">
						<span class="no">1</span>
						<label><spring:message code="dop.productid" /></label>
						<form:input cssClass="w300 required" path="declarationOfPerformance.productId" />
						<div class="clear"></div>
						<p><spring:message code="dop.productid.info" /></p>
					</div>
					
					
					<div class="fitem">
						<span class="no">2</span>
						<label><spring:message code="dop.serialid" /></label>
						<form:input cssClass="w300 required" path="declarationOfPerformance.serialId" />
						<div class="clear"></div>
						<p><spring:message code="dop.serialid.info" /></p>
					</div>
					
					
					<div class="fitem">
						<span class="no">3</span>
						<label><spring:message code="dop.intendedUse" /></label>
						<form:textarea cssClass="w600 h80 required" path="declarationOfPerformance.intendedUse" rows="3" />
						<div class="clear"></div>
						<p><spring:message code="dop.intendedUse.info" /></p>
					</div>
					
					
					<div class="fitem">
						<span class="no">4</span>
						<label><spring:message code="dop.manufacturer" /></label>
						<form:textarea cssClass="w600 h80 required" path="declarationOfPerformance.manufacturer" rows="3" />
						<div class="clear"></div>
						<p><spring:message code="dop.manufacturer.info" /></p>
					</div>
					
					
					<div class="fitem">
						<span class="no">5</span>
						<label><spring:message code="dop.authorisedRepresentative" /></label>
						<form:textarea cssClass="w600 h80 required" path="declarationOfPerformance.authorisedRepresentative" rows="3" />
						<div class="clear"></div>
					</div>
					
					<div class="fitem">
						<span class="no">6</span>
						<label><spring:message code="dop.assessmentSystem" /></label>
						<form:select  path="declarationOfPerformance.assessmentSystem" cssClass="w300 required">
							<form:options items="${model.assessmentSystems}" itemValue="id" itemLabel="name" />
						</form:select>
						<div class="clear"></div>
					</div>
					
					<div class="fitem">
						<span class="no">7</span>
						<label><spring:message code="dop.notifiedBody" /></label>
						<form:select path="declarationOfPerformance.notifiedBody" cssClass="w600" >
						
							 <c:forEach items="${model.notifiedBodies}" var="nb" varStatus="s" >
					 			<c:if test="${prev != nb.country.id }">
					 				<optgroup label="${nb.country.countryName}"></optgroup>
					 				<c:set value="${nb.country.id}" var="prev" />
					 			</c:if>
					 				<option value="${nb.id}" 
						 				<c:forEach items="${model.standardnotifiedBodies}" var="i">
						 					<c:if test="${i.id ==  nb.id}"> selected="selected" </c:if>
						 				</c:forEach> 
					 				>
					 				${nb.notifiedBodyCode} - ${nb.name}
					 				</option>			 			
							</c:forEach>
						 </form:select>
						<div class="clear"></div>
					</div>
					
					<div class="fitem">
						<span class="no">8</span>
						<label><spring:message code="dop.assessmentSystem" /></label>
						<form:select  path="declarationOfPerformance.assessmentSystem" cssClass="w300 required">
							<form:options items="${model.assessmentSystems}" itemValue="id" itemLabel="name" />
						</form:select>
						<div class="clear"></div>
					</div>
					
					
					<div class="dop-items">
						<span class="no">9</span>
						<table>
							<thead>
								<tr>
									<th><spring:message code="dop.table.essentialCharacteristics" /></th>
									<th><spring:message code="cpr.requirement.level" /></th>
									<th><spring:message code="dop.table.performance" /></th>
									<th><spring:message code="dop.table.ehn" /></th>
								</tr>
							</thead>
							
							<tbody>
								<c:forEach items="${model.requiremets}" var="i" varStatus="status">
									<tr>
										<td class="name">${i.name}</td>
										<td class="level">${i.levels}</td>
										<td class="value">
											<form:input path="characteristics[${status.index}].value"  cssClass="tt-form" title="${i.note}"  />
											<input type="hidden" name="characteristics[${status.index}].requirement.id" value="${i.id}" />
										</td>
										<td class="ehn">
											<a href="<c:url value="/ehn/${model.standard.code}" />" target="_blank">${model.standard.standardId}</a> 
											<em>${i.section}</em>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
					<div class="fitem">
						<span class="no">10</span>
						<span class="big"><strong><spring:message code="dop.10" /></strong></span>
						<div class="clear"></div>
					</div>
					
					
					<div>
					 <input type="submit" class="button" value="<spring:message code="dop.generate" />" />
						<div class="clear"></div>
					</div>
					<form:hidden path="declarationOfPerformance.id"/>
					<form:hidden path="declarationOfPerformance.standard.code"/>
				</form:form>
			
			
			</c:if>
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>