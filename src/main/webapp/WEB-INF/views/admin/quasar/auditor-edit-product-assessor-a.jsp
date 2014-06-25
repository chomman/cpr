<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>


<div class="qs-result-box ${model.function.areAllRequirementsValid ? 'qs-valid' : 'qs-invalid'}">
	<span class="qs-global">
		<spring:message code="auditor.function" />:
		<strong>
			<c:if test="${model.function.areAllRequirementsValid}">
				<spring:message code="yes" />
			</c:if>
			<c:if test="${not model.function.areAllRequirementsValid}">
				<spring:message code="no" />
			</c:if>
		</strong>
	</span>
	<ul>
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

		