<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<h3 class="qs-function">${model.auditor.name} as <strong><spring:message code="nbCode" /> <spring:message code="auditor.productAssessorR" /></strong></h3>

<c:if test="${not empty successCreate}">
	<p class="msg ok"><spring:message code="success.create" /></p>
</c:if>
<c:if test="${model.isEditable}">
<c:url value="/admin/quasar/manage/auditor/${auditor.id}" var="url" />
<form:form  commandName="auditor" method="post" cssClass="submit-on-change" action="${url}/decision/4">
	<p class="form-head"><spring:message code="decisionOnTheAssessor" /> (<spring:message code="auditor.productAssessorR" />)</p>
	<div class="input-wrapp smallest">
		<label>
			<spring:message code="activeMd" />:
			
		</label>
		<div class="field">
			<form:checkbox path="specialist[2].activeMedicalDeviceSpecialist" />
		</div>
	</div>
	<div class="input-wrapp smallest">
		<label>
			<spring:message code="nonActiveMd" />:
			
		</label>
		<div class="field">
			<form:checkbox path="specialist[2].nonActiveMedicalDeviceSpecialist" />
		</div>
	</div>
	<div class="input-wrapp smallest">
		<label>
			<spring:message code="ivd" />:
			
		</label>
		<div class="field">
			<form:checkbox path="specialist[2].inVitroDiagnosticSpecialist" />
		</div>
	</div>
	<div class="input-wrapp smallest">
		<label>
			<spring:message code="approvalRecentActivities.tfdd" />:
			
		</label>
		<div class="field">
			<div class="qs-float-left" ><form:checkbox cssClass="confirm-ra" path="recentActivitiesApprovedForProductAssessorR" /></div>
			<p class="mini-info inline-block"><spring:message code="approvalRecentActivities.info" arguments="${model.auditor.name}" /></p>
		</div>
	</div>
	<form:hidden path="id"/>
</form:form>
</c:if>

<div class="qs-result-box ${model.function.areAllRequirementsValid ? 'qs-valid' : 'qs-invalid'}">
	<span class="qs-global">
		<spring:message code="auditor.function" /> (<spring:message code="auditor.productAssessorR" />):
		<strong>
			<c:if test="${model.function.areAllRequirementsValid}">
				<spring:message code="yes" />
			</c:if>
			<c:if test="${not model.function.areAllRequirementsValid}">
				<spring:message code="no" />
			</c:if>
		</strong>
		<a href="#"><spring:message code="showDetails" /></a>
	</span>
	<ul class="hidden">
		<li class="${model.function.formalLegalRequiremets ? 'qs-valid' : 'qs-invalid'}">
			<span>
				1. <spring:message code="auditor.head.formalRequirements" />:  
				<strong>
				<c:if test="${model.function.formalLegalRequiremets}">
					<spring:message code="complaint" />
				</c:if>
				<c:if test="${not model.function.formalLegalRequiremets}">
					<spring:message code="nonComplaint" />
				</c:if>
				</strong>
			</span>
		</li>
		<li class="${model.function.recentActivities ? 'qs-valid' : 'qs-invalid'}">
			<span>
				2. <spring:message code="auditor.function.activities" />:
				<strong>
				<c:if test="${model.function.recentActivities}">
					<spring:message code="complaint" />
				</c:if>
				<c:if test="${not model.function.recentActivities}">
					<spring:message code="nonComplaint" />
				</c:if>
				</strong>
			</span>
		</li>
		<li class="${model.function.areRequirementForActiveMdSatisfy ? 'qs-valid' : 'qs-invalid'}">
			<span>
				3. <spring:message code="auditor.function.activeMd" />:
				<strong>
				<c:if test="${model.function.areRequirementForActiveMdSatisfy}">
					<spring:message code="complaint" />
				</c:if>
				<c:if test="${not model.function.areRequirementForActiveMdSatisfy}">
					<spring:message code="nonComplaint" />
				</c:if>
				</strong>
			</span>
			<ul>
				<li class="${model.function.generalRequirementsActiveMd ? 'qs-valid' : 'qs-invalid'}">
					<span>
						3.1 <spring:message code="auditor.function.educationExperience" />:
						<strong>
						<c:if test="${model.function.generalRequirementsActiveMd}">
							<spring:message code="complaint" />
						</c:if>
						<c:if test="${not model.function.generalRequirementsActiveMd}">
							<spring:message code="nonComplaint" />
						</c:if>
						</strong>
					</span>
				</li>
				<li class="${model.function.auditingTrainingActiveMd ? 'qs-valid' : 'qs-invalid'}">
					<span>
						3.2 <spring:message code="auditor.function.trainingAuditing" />:
						<strong>
						<c:if test="${model.function.auditingTrainingActiveMd}">
							<spring:message code="complaint" />
						</c:if>
						<c:if test="${not model.function.auditingTrainingActiveMd}">
							<spring:message code="nonComplaint" />
						</c:if>
						</strong>
					</span>
				</li>
			</ul>
		</li>
		<li class="${model.function.areRequirementForNonActiveMdSatisfy ? 'qs-valid' : 'qs-invalid'}">
			<span>
				4. <spring:message code="auditor.function.activeNonMd" />:
				<strong>
				<c:if test="${model.function.areRequirementForActiveMdSatisfy}">
					<spring:message code="complaint" />
				</c:if>
				<c:if test="${not model.function.areRequirementForActiveMdSatisfy}">
					<spring:message code="nonComplaint" />
				</c:if>
				</strong>
			</span>
			<ul>
				<li class="${model.function.generalRequirementsNonActiveMd ? 'qs-valid' : 'qs-invalid'}">
					<span>
						4.1 <spring:message code="auditor.function.educationExperience" />:
						<strong>
						<c:if test="${model.function.generalRequirementsNonActiveMd}">
							<spring:message code="complaint" />
						</c:if>
						<c:if test="${not model.function.generalRequirementsNonActiveMd}">
							<spring:message code="nonComplaint" />
						</c:if>
						</strong>
					</span>
				</li>
				<li class="${model.function.auditingTrainingNonActiveMd ? 'qs-valid' : 'qs-invalid'}">
					<span>
						4.2 <spring:message code="auditor.function.trainingAuditing" />:
						<strong>
						<c:if test="${model.function.auditingTrainingNonActiveMd}">
							<spring:message code="complaint" />
						</c:if>
						<c:if test="${not model.function.auditingTrainingNonActiveMd}">
							<spring:message code="nonComplaint" />
						</c:if>
						</strong>
					</span>
				</li>
			</ul>
		</li>
		<li class="${model.function.areRequirementForIvdSatisfy ? 'qs-valid' : 'qs-invalid'}">
			<span>
				5. <spring:message code="auditor.function.ivd" />:
				<strong>
				<c:if test="${model.function.areRequirementForIvdSatisfy}">
					<spring:message code="complaint" />
				</c:if>
				<c:if test="${not model.function.areRequirementForIvdSatisfy}">
					<spring:message code="nonComplaint" />
				</c:if>
				</strong>
			</span>
			<ul>
				<li class="${model.function.generalRequirementsIvd ? 'qs-valid' : 'qs-invalid'}">
					<span>
						5.1 <spring:message code="auditor.function.educationExperience" />:
						<strong>
						<c:if test="${model.function.generalRequirementsIvd}">
							<spring:message code="complaint" />
						</c:if>
						<c:if test="${not model.function.generalRequirementsIvd}">
							<spring:message code="nonComplaint" />
						</c:if>
						</strong>
					</span>
				</li>
				<li class="${model.function.auditingTrainingIvd ? 'qs-valid' : 'qs-invalid'}">
					<span>
						5.2 <spring:message code="auditor.function.trainingAuditing" />:
						<strong>
						<c:if test="${model.function.auditingTrainingIvd}">
							<spring:message code="complaint" />
						</c:if>
						<c:if test="${not model.function.auditingTrainingIvd}">
							<spring:message code="nonComplaint" />
						</c:if>
						</strong>
					</span>
				</li>
			</ul>
		</li>
	</ul>
</div>

<c:set value="" var="scope" />
<c:forEach items="${model.codes}" var="i"> 
	<c:set value="${model.function.areAllRequirementsValid and i.grated}" var="isGranted" />
	<div class="qs-item radius ${isGranted ? 'qs-valid' : 'qs-invalid'}">
		<div class="qs-code-wrapp">
				<form method="post" id="f${i.auditorNandoCode.id}">
				<div class="qs-name">
					<c:if test="${model.isEditable}">
					<div class="qs-inline-edit" data-id="${i.auditorNandoCode.id}">
						<spring:message code="quasar.edit" />
					</div>
					</c:if>
					<div class="reason-detail ${empty i.auditorNandoCode.productAssessorRReasonDetails ? 'qs-field' : ''}">
						<span class="val h">
							<c:if test="${not empty i.auditorNandoCode.productAssessorRReasonDetails}">
								<h5><spring:message code="reasonDetails" /></h5>
								<span>${i.auditorNandoCode.productAssessorRReasonDetails}</span>
							</c:if>
						</span>
						<div class="qs-field">
							<h5>Reason details (max. 255 chars.)</h5>
							<textarea name="productAssessorRReasonDetails" rows="5" cols="5" placeholder="Write describe...">${i.auditorNandoCode.productAssessorRReasonDetails}</textarea>
						</div>
					</div>
					<div class="qs-code">
						<div class="eac">${i.auditorNandoCode.nandoCode.code}</div>
					</div>
					<div class="is-granted ${isGranted ? 'qs-valid' : 'qs-invalid'}">
						<c:if test="${isGranted}">
							<spring:message code="granted" />
							<c:set var="scope" value="${scope}(${i.auditorNandoCode.nandoCode.code}), " />
						</c:if>
						<c:if test="${not isGranted}">
							<spring:message code="refused" />
						</c:if>
					</div>
					<h3>${i.auditorNandoCode.nandoCode.specification}</h3>
					<table>
						<tr>
							<td class="k"><spring:message code="categoryTraining" />:</td>
							<td class="v">
								<span class="val h">${i.auditorNandoCode.categorySpecificTraining}</span>
								<span class="qs-field">
									<input type="text" value="${i.auditorNandoCode.categorySpecificTraining}" name="categorySpecificTraining" class="w30 c required numeric">
								</span>
							</td>
						</tr>
						<tr>
							<td class="k"><spring:message code="noOfTFReviews" />:</td>
							<td class="v">
								<span class="val h">${i.auditorNandoCode.numberOfTfReviews}</span>
								<span class="qs-field">
									<input type="text" value="${i.auditorNandoCode.numberOfTfReviews}" name="numberOfTfReviews" class="w30 c required numeric">
								</span>
							</td>
						</tr>
						<tr class="${not i.auditorNandoCode.productAssessorRRefused ? 'qs-hide' : ''}">
							<td class="k"><spring:message code="isRefused" />:</td>
							<td class="v">
								<span class="val h">
									<c:if test="${i.auditorNandoCode.productAssessorRRefused}">
										<strong><spring:message code="yes" /></strong>
									</c:if>
									<c:if test="${not i.auditorNandoCode.productAssessorRRefused}">
										<spring:message code="no" />
									</c:if>
								</span>
								<span class="qs-field">
									<input type="checkbox" class="ch-refused" name="productAssessorRRefused" ${i.auditorNandoCode.productAssessorRRefused ? 'checked="checked"' : ''}  />
								</span>
							</td>
						</tr>
						
						<tr class="${not i.auditorNandoCode.productAssessorRApproved ? 'qs-hide' : ''}">
							<td class="k"><spring:message code="specificReasonForTheApproval" />:</td>
							<td class="v">
								<span class="val h">
									<c:if test="${i.auditorNandoCode.productAssessorRApproved}">
										<strong><spring:message code="yes" /></strong>
									</c:if>
									<c:if test="${not i.auditorNandoCode.productAssessorRApproved}">
										<spring:message code="no" />
									</c:if>
								</span>
								<span class="qs-field">
									<input type="checkbox" class="ch-approved" name="productAssessorRApproved" ${i.auditorNandoCode.productAssessorRApproved ? 'checked="checked"' : ''}  />
								</span>
							</td>
						</tr>
						
						<tr class="${empty i.auditorNandoCode.productAssessorRApprovedBy ? 'qs-hide' : ''}">
							<td class="k"><spring:message code="categoryApprovalByAnotherNb" />:</td>
							<td class="v h">
								<span class="val h">
									<c:if test="${not empty i.auditorNandoCode.productAssessorRApprovedBy}">
									<a:adminurl href="/cpr/notifiedbodies/edit/${i.auditorNandoCode.productAssessorRApprovedBy.id}">
										<strong>${i.auditorNandoCode.productAssessorRApprovedBy.noCode}</strong>	
									</a:adminurl>
									</c:if>
									<c:if test="${empty i.auditorNandoCode.productAssessorRApprovedBy}">
										<span class="val">
											<spring:message code="no" />
											</span>
										</c:if>
								</span>
								<span class="qs-field">
								
									<input name="productAssessorRApprovedBy" type="text" class="mw150 nb-picker"
									<c:if test="${not empty i.auditorNandoCode.productAssessorRApprovedBy}">
										 data-json="${i.auditorNandoCode.productAssessorRApprovedBy.id}##${i.auditorNandoCode.productAssessorRApprovedBy.noCode}"
									</c:if>
									 />
									 <a class="lang mandate-add-btn">
										<spring:message code="form.save" />
									</a>
									<a target="_blank" class="qs-nb-new" href="<c:url value="${model.nbUrl}0" />">
										<spring:message code="new" />
									</a>
								</span>
							</td>
						</tr>
						
					</table>
				</div>
				<div class="clear"></div>
				<input type="hidden" name="id" value="${i.auditorNandoCode.id}">
				<input type="hidden" name="auditor" value="${model.auditor.id}">
				</form>
		</div>
	</div>
</c:forEach>

<div class="hbox"><h2><spring:message code="productAssessorR.scope" arguments="${model.auditor.name}" /></h2></div>
<textarea class="qs-code-scope">${scope}</textarea>		
