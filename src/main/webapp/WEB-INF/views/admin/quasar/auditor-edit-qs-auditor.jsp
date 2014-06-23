<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>


<div class="qs-result-box ${model.function.areAllRequirementsValid ? 'qs-valid' : 'qs-invalid'}">
	<span class="qs-global">
		QS Auditor requirements:
		<c:if test="${model.function.areAllRequirementsValid}">
			<spring:message code="complaint" />
		</c:if>
		<c:if test="${not model.function.areAllRequirementsValid}">
			<spring:message code="nonComplaint" />
		</c:if>
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
		<li class="${model.function.generalRequiremets ? 'qs-valid' : 'qs-invalid'}">
			<span>
				2. <spring:message code="auditor.function.educationExperience" />:
				<strong>
				<c:if test="${model.function.generalRequiremets}">
					<spring:message code="complaint" />
				</c:if>
				<c:if test="${not model.function.generalRequiremets}">
					<spring:message code="nonComplaint" />
				</c:if>
				</strong>
			</span>
		</li>
		<li class="${model.function.trainingAuditing ? 'qs-valid' : 'qs-invalid'}">
			<span>
				3. <spring:message code="auditor.function.trainingAuditing" />:
				<strong>
				<c:if test="${model.function.trainingAuditing}">
					<spring:message code="complaint" />
				</c:if>
				<c:if test="${not model.function.trainingAuditing}">
					<spring:message code="nonComplaint" />
				</c:if>
				</strong>
			</span>
		</li>
	</ul>

</div>