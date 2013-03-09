<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>


				<form:form  htmlEscape="true"  modelAttribute="declarationOfPerformance" id="dop" cssClass="valid" method="post" action="${formUrl}" >
				
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
						<label><spring:message code="dop.ehn" /></label>
						<span class="norm-wrapper">
						<a href="<c:url value="/ehn/${model.standard.code}" />" target="_blank">${model.standard.standardId}</a>
						(<em>${model.standard.standardName}</em>)
						</span>
						<div class="clear"></div>
					</div>
					
					
					<div class="dopitems">
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
								<c:forEach items="${declarationOfPerformance.characteristics}" var="i" varStatus="status">
									<tr>
										<td class="name">${i.requirement.name}</td>
										<td class="level">${i.requirement.levels}</td>
										<td class="value">
											<c:if test="${i.requirement.npd}">
												<form:input path="characteristics[${status.index}].value"    />
											</c:if>
											<c:if test="${not i.requirement.npd}">
												<form:input path="characteristics[${status.index}].value"  cssClass="tt-form required" title="${i.requirement.note}"  />
											</c:if>
											<form:hidden path="characteristics[${status.index}].requirement.id" />
										</td>
										<td class="ehn">
											<a href="<c:url value="/ehn/${model.standard.code}" />" target="_blank">${model.standard.standardId}</a> 
											<em>${i.requirement.section}</em>
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
					<span><em><spring:message code="cpr.requirement.npd" /></em></span>
					 <c:if test="${declarationOfPerformance.declarationOfPerformance.id == 0}">
					 	<input type="submit" class="button" value="<spring:message code="dop.generate" />" />
					 </c:if>
					 <c:if test="${declarationOfPerformance.declarationOfPerformance.id != 0}">
					 	<input type="submit" class="button" value="<spring:message code="dop.edit" />" />
					 	<a class="back" href="<c:url value="/dop/${declarationOfPerformance.declarationOfPerformance.token}" />"><spring:message code="dop.cancel" /></a>
					 </c:if>
						<div class="clear"></div>
					</div>
					<form:hidden path="declarationOfPerformance.id"/>
					<form:hidden path="declarationOfPerformance.standard.code"/>
				</form:form>
				<div id="status"></div>