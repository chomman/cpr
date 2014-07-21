<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
		<link rel="stylesheet" href="<c:url value="/resources/admin/quasar/css/jquery.raty.css" />" />
		<script src="<c:url value="/resources/admin/quasar/js/auditor.js" />"></script>
		<script src="<c:url value="/resources/admin/quasar/js/jquery.raty.js" />"></script>
		
	<h3 class="qs-function">${model.auditor.nameWithDegree}</h3>
	<form>
	<p class="form-head"><spring:message code="auditor.head.personalInfo" /></p>
	<div class="input-wrapp smaller">
		<label> <spring:message code="auditor.rating" />:
		</label>
		<div class="field">
			<div data-rating="${model.auditor.rating}" id="rating-wrapp"></div>
			<div id="rating-text"></div>
			<div class="clear"></div>
		</div>
	</div>
	
		<div class="input-wrapp smaller">
		<label>
			<spring:message code="auditor.itcId" />:
		</label>
		<div class="field">
			${model.auditor.itcId}
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label>
			<spring:message code="auditor.inTraining" />:
			
		</label>
		<div class="field">
			<c:if test="${model.auditor.inTraining}">
				<strong><spring:message code="yes" /></strong>
			</c:if>
			<c:if test="${not model.auditor.inTraining}">
				<strong><spring:message code="no" /></strong>
			</c:if>
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label>
			<spring:message code="auditor.reassessmentDate" />:
		</label>
		<div class="field">
			<c:if test="${not empty model.auditor.reassessmentDate}">
				<joda:format value="${model.auditor.reassessmentDate}" pattern="dd.MM.yyyy"/>
			</c:if>
			<c:if test="${empty model.auditor.reassessmentDate}">
				-
			</c:if>
		</div>
	</div>
	<c:if test="${not empty model.auditor.country}">
	<div class="input-wrapp smaller">
		<label>
			<spring:message code="auditor.country" />:
		</label>
		<div class="field">${model.auditor.country.countryName}</div>
	</div>
	</c:if>
	<c:if test="${not empty model.auditor.partner}">
	<div class="input-wrapp smaller">
		<label>
			<spring:message code="auditor.partner" />:
		</label>
		<div class="field">${model.auditor.partner.name}</div>
	</div>
	</c:if>
		<div class="input-wrapp">
		<label>
			E-mails:
		</label>
		<div class="field">
			<strong>${model.auditor.email}</strong>
			<c:if test="${not empty model.auditor.otherEmails}">
				, ${model.auditor.otherEmails}
			</c:if>
		</div>
	</div>
	<p class="form-head"><spring:message code="auditor.head.formalRequirements" /></p>
             <div class="${model.auditor.areFormalAndLegalReqiurementValid ? 'qs-valid' : 'qs-invalid'}">
             	<div class="input-wrapp smaller">
			<label>
				<spring:message code="auditor.ecrCardSigned" />:
			</label>
			<div class="field">
				<c:if test="${model.auditor.ecrCardSigned}">
					<strong><spring:message code="yes" /></strong>
				</c:if>
				<c:if test="${not model.auditor.ecrCardSigned}">
					<strong><spring:message code="no" /></strong>
				</c:if>
			</div>
		</div>
		<div class="input-wrapp smaller">
			<label>
				<spring:message code="auditor.confidentialitySigned" />:
			</label>
			<div class="field">
				<c:if test="${model.auditor.confidentialitySigned}">
					<strong><spring:message code="yes" /></strong>
				</c:if>
				<c:if test="${not model.auditor.confidentialitySigned}">
					<strong><spring:message code="no" /></strong>
				</c:if>
			</div>
		</div>
		<div class="input-wrapp smaller">
			<label>
				<spring:message code="auditor.cvDelivered" />:
			</label>
			<div class="field">
				<c:if test="${model.auditor.cvDelivered}">
					<strong><spring:message code="yes" /></strong>
				</c:if>
				<c:if test="${not model.auditor.cvDelivered}">
					<strong><spring:message code="no" /></strong>
				</c:if>
			</div>
		</div>
        </div>     
             <p class="form-head"><spring:message code="auditor.head.generalQualification" /></p>
              <div class="qs-fields-wrapp">
               	<div class="qs-left-bx">
                  	<h4><spring:message code="auditor.head.education.amd" /></h4>
                  	<div class="qs-input-wrapp">
					<label>
						<spring:message code="auditor.educationLevel" />:
					</label>
					<div class="qs-filed">
						<c:if test="${not empty model.auditor.education['1'].eductionLevel}">
							${model.auditor.education['1'].eductionLevel.name}
						</c:if>
						<c:if test="${ empty model.auditor.education['1'].eductionLevel}">
							-
						</c:if>
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="qs-input-wrapp">
					<label>
						<spring:message code="auditor.fieldOfStudy" /> (1):
					</label>
					<div class="qs-filed">
						<c:if test="${not empty model.auditor.education['1'].education1}">
							${empty model.auditor.education['1'].education1.name}
						</c:if>
						<c:if test="${empty model.auditor.education['1'].education1}">-</c:if>
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="qs-input-wrapp">
					<label>
						<spring:message code="auditor.fieldOfStudy" /> (2):
					</label>
					<div class="qs-filed">
						<c:if test="${not empty model.auditor.education['1'].education2}">
							${model.auditor.education['1'].education2.name}
						</c:if>
						<c:if test="${empty model.auditor.education['1'].education2}">-</c:if>
					</div>
					<div class="clear"></div>
				</div>
				
				
				<div class="qs-input-wrapp">
					<label>
						<spring:message code="auditor.psgStudy" />:
					</label>
					<div class="qs-filed">
						<c:if test="${not empty model.auditor.education['1'].postgreduateStudy}">
							${model.auditor.education['1'].postgreduateStudy.name}
						</c:if>
						<c:if test="${empty model.auditor.education['1'].postgreduateStudy}">-</c:if>
					</div>
					<div class="clear"></div>
				</div>
				
             	</div>
                <div class="qs-right-bx">
                		<h4><spring:message code="auditor.head.education.nonamd" /></h4>
                		<div class="qs-input-wrapp">
					<label>
						<spring:message code="auditor.educationLevel" />:
					</label>
					<div class="qs-filed">
						<c:if test="${not empty model.auditor.education['2'].eductionLevel}">
							${model.auditor.education['2'].eductionLevel.name}
						</c:if>
						<c:if test="${ empty model.auditor.education['2'].eductionLevel}">
							-
						</c:if>
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="qs-input-wrapp">
					<label>
						<spring:message code="auditor.fieldOfStudy" /> (1):
					</label>
					<div class="qs-filed">
						<c:if test="${not empty model.auditor.education['2'].education1}">
							${model.auditor.education['2'].education1.name}
						</c:if>
						<c:if test="${empty model.auditor.education['2'].education1}">-</c:if>
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="qs-input-wrapp">
					<label>
						<spring:message code="auditor.fieldOfStudy" /> (2):
					</label>
					<div class="qs-filed">
						<c:if test="${not empty model.auditor.education['2'].education2}">
							${model.auditor.education['2'].education2.name}
						</c:if>
						<c:if test="${empty model.auditor.education['2'].education2}">-</c:if>
					</div>
					<div class="clear"></div>
				</div>
				
				
				<div class="qs-input-wrapp">
					<label>
						<spring:message code="auditor.psgStudy" />:
					</label>
					<div class="qs-filed">
						<c:if test="${not empty model.auditor.education['2'].postgreduateStudy}">
							${model.auditor.education['2'].postgreduateStudy.name}
						</c:if>
						<c:if test="${empty model.auditor.education['2'].postgreduateStudy}">-</c:if>
					</div>
					<div class="clear"></div>
				</div>
			
		</div>
		
		<div class="clear"></div>
		
		<div>
			<p class="mini-info"><spring:message  code="auditor.info.education"/></p>
		</div>
              </div>
             
             
             <!-- TRAINING -->
 	<p class="form-head"><spring:message code="auditor.head.training" /></p>
	<div class="qs-fields-wrapp">
		<div class="qs-left-bx">
			<div class="input-wrapp smaller">
				<label>
					<spring:message code="auditor.aprovedForIso9001" />:
				</label>
				<div class="field">
					<c:if test="${model.auditor.aprovedForIso9001}">
						<strong><spring:message code="yes" /></strong>
					</c:if>
					<c:if test="${not model.auditor.aprovedForIso9001}">
						<strong><spring:message code="no" /></strong>
					</c:if>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.iso9001" />:</label>
				<div class="field">
					${model.auditor.trainingIso9001InHours} <spring:message code="hours" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.mdd" />:</label>
				<div class="field">
					${model.auditor.mdd} <spring:message code="hours" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.totalAudits" />:</label>
				<div class="field">
					${model.auditor.totalAudits}
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.totalAuditdays" />:</label>
				<div class="field">
					${model.auditor.totalAuditdays}
				</div>
			</div>
		</div>
		<div class="qs-right-bx qs-border-right">
			<div class="input-wrapp smaller">
				<label>
					<spring:message code="auditor.aprovedForIso13485" />:
				</label>
				<div class="field">
					<c:if test="${model.auditor.aprovedForIso13485}">
						<strong><spring:message code="yes" /></strong>
					</c:if>
					<c:if test="${not model.auditor.aprovedForIso13485}">
						<strong><spring:message code="no" /></strong>
					</c:if>
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.iso13485" />:</label>
				<div class="field">
					${model.auditor.iso13485} <spring:message code="hours" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.ivd" />:</label>
				<div class="field">
					${model.auditor.ivd} <spring:message code="hours" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.nb1023Procedures" />:</label>
				<div class="field">
					${model.auditor.nb1023Procedures}
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label>&nbsp;</label>
				<div class="field">&nbsp;</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
 	
 	<div class="qs-fields-wrapp">
		<div class="qs-left-bx">
		<p class="form-head mini"><spring:message code="auditor.productAssessorR" /></p>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.tfTrainingInHours" />:</label>
				<div class="field">
					${model.auditor.tfTrainingInHours} <spring:message code="hours" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.totalTfReviews" />:</label>
				<div class="field">
					${model.auditor.totalTfReviews} 
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.noTfReviewsForSterile" />:</label>
				<div class="field">
					${model.auditor.noTfReviewsForSterileMd} 
				</div>
			</div>
		</div>
		<div class="qs-right-bx qs-border-right">
		<p class="form-head mini"><spring:message code="auditor.productSpecialist" /></p>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.ddTrainingInHours" />:</label>
				<div class="field">
					${model.auditor.ddTrainingInHours} <spring:message code="hours" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.totalDdReviews" />:</label>
				<div class="field">
					${model.auditor.totalDdReviews}
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label><spring:message code="auditor.noDdReviewsForSterile" />:</label>
				<div class="field">
					${model.auditor.noDdReviewsForSterileMd}
				</div>
			</div>			
		</div>
		<div class="clear"></div>
	</div>	
	

<!-- SPECIAL TRAINING -->
<p class="form-head"><spring:message code="auditor.head.specialTraining" /></p>
<c:if test="${empty model.auditor.specialTrainings}">
	<p class="msg alert">
		<spring:message code="auditor.noTraining" arguments="${model.auditor.name}" />
	</p>
</c:if>
<c:if test="${not empty model.auditor.specialTrainings}">
	<table class="data">
		<tr class="gs-head-min">
			<th><spring:message code="topic" /></th>
			<th><spring:message code="hours" /></th>
			<th><spring:message code="changed" /></th>
		</tr>
		<c:forEach items="${model.auditor.specialTrainings}" var="i">
			<tr>
				<td class="b gs-exp-name">
					${i.name}
				</td>
				<td class="b gs-exp-input c">
					<strong>${i.hours}</strong>
				</td>
				<td class="b gs-exp-changed">
				<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
					/ ${i.changedBy.name}
				</td>
				
			</tr>
		</c:forEach>
	</table>
</c:if>
</form>
<br />

<!-- WORK EXPERIENCE -->
<p class="form-head"><spring:message code="auditor.head.workExperience" /></p>
<c:if test="${empty model.auditor.auditorExperiences}">
	<p class="msg alert">
		<spring:message code="auditor.noExperience" arguments="${model.auditor.name}" />
	</p>
</c:if>
<c:if test="${not empty model.auditor.auditorExperiences}">
	<table class="data">
		<tr class="gs-head-min">
			<th><spring:message code="category" /></th>
			<th><spring:message code="changed" /></th>
			<th><spring:message code="years" /></th>
		</tr>
		<c:forEach items="${model.auditor.auditorExperiences}" var="i">
			<tr>
				<td class="b gs-exp-name">
					${i.experience.name}
				</td>
				<td class="b gs-exp-changed">
				<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
					/ ${i.changedBy.name}
				</td>
				<td class="b c gs-exp-input">
					${i.years}
				</td>
				
			</tr>
		</c:forEach>
	</table>
	<table class="qs-total-exp">
		<tr>
			<td class="qs-label"><spring:message code="auditor.experience.total" />:</td>
			<td class="qs-years"><span>${model.auditor.totalWorkExperience}</span> <em><spring:message code="years" /></em></td>
		</tr>
		<tr>
			<td class="qs-label"><spring:message code="auditor.experience.totalMd" />:</td>
			<td class="qs-years"><span>${model.auditor.totalWorkExperienceInMedicalDevices}</span> <em><spring:message code="years" /></em></td> 
		</tr>
	</table>
</c:if>




