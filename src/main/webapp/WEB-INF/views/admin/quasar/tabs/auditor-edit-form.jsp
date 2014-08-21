<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<c:url value="/admin/quasar/manage/auditor/${command.id}" var="url" />
<form:form commandName="command" method="post" cssClass="valid"
	action="${url}?action=1">
	<form:errors path="*" delimiter="<br/>" element="p"
		cssClass="msg error" />
	<c:if test="${not empty successCreate}">
		<p class="msg ok">
			<spring:message code="success.create" />
		</p>
	</c:if>

	<p class="form-head">
		<spring:message code="auditor.head.login" />
	</p>
	<div class="input-wrapp">
		<label> <strong><em class="red">*</em> <spring:message
					code="auditor.email" />: </strong>
		</label>
		<div class="field">
			<form:input path="email" maxlength="50"
				cssClass="w300 required email" />
		</div>
	</div>
	<div class="input-wrapp">
		<label> <spring:message code="isActivated" />:
		</label>
		<div class="field">
			<form:checkbox path="enabled" />
			<span class="inline"><small>Unchecking this field, you
					can disable user's access.</small></span>
		</div>
	</div>

	<p class="form-head">
		<spring:message code="auditor.head.personalInfo" />
	</p>
	<div class="input-wrapp smaller">
		<label> <spring:message code="auditor.rating" />:
		</label>
		<div class="field">
			<div data-rating="${command.rating}" id="rating-wrapp"></div>
			<div id="rating-text"></div>
			<div class="audit-log-rating">
				<span>Audit logs rating (avg):</span>
				<c:if test="${not empty model.avgAuditLogRating}">
					<strong id="auditLogRating" title="" class="tt like-input">${model.avgAuditLogRating}</strong>
				</c:if>
				<c:if test="${empty model.avgAuditLogRating}">
					<p class="mini-info inline">
						<spring:message code="auditLog.rating.none" />
					</p>
				</c:if>
							
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label> 
			<strong>
			<em class="red">*</em>
			<spring:message code="auditor.itcId" />:
			</strong>
		</label>
		<div class="field">
			<form:input path="itcId" cssClass="w50 c required numeric"
				maxlength="5" />
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label> <strong><em class="red">*</em> <spring:message
					code="auditor.name" />: </strong>
		</label>
		<div class="field">
			<form:input path="degrees" cssClass="w100 " maxlength="25"
				placeholder="Degree" />
			&nbsp;
			<form:input path="firstName" cssClass="w100 required" maxlength="50"
				placeholder="First name" />
			<form:input path="lastName" cssClass="w100 required" maxlength="50"
				placeholder="Last name" />
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label> <spring:message code="auditor.inTraining" />:

		</label>
		<div class="field">
			<form:checkbox path="inTraining" />
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label> <spring:message code="auditor.reassessmentDate" />:
		</label>
		<div class="field">
			<form:input path="reassessmentDate" cssClass="w100 date" maxlength="10" />
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label> <spring:message code="auditor.country" />:
		</label>
		<div class="field">
			<form:select path="country" cssClass="chosenSmall">
				<option value="">---
					<spring:message code="auditor.select" /> ---
				</option>
				<c:forEach items="${model.countries}" var="i">
					<option value="${i.id}"
						${i.id eq command.country.id ? 'selected="selected"' : '' }>${i.countryName}</option>
				</c:forEach>
			</form:select>
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label> <spring:message code="auditor.partner" />:
		</label>
		<div class="field">
			<form:select path="partner" cssClass="chosenSmall">
				<option value="">---
					<spring:message code="auditor.select" /> ---
				</option>
				<c:forEach items="${model.partners}" var="i">
					<option value="${i.id}"
						${i.id eq command.partner.id ? 'selected="selected"' : '' }>${i.name}</option>
				</c:forEach>
			</form:select>
		</div>
	</div>
	<div class="input-wrapp">
		<label class="tt" title="Using this field, you can setup other worker's e-mail, which will be used for notification emails forwarding. Use commas to separate multiple e-mail addresses ">
			<spring:message code="auditor.otherEmails" />:
		</label>
		<div class="field">
			<form:input path="otherEmails" maxlength="100" cssClass="mw500 " />
		</div>
	</div>
	<p class="form-head">
		<spring:message code="auditor.head.formalRequirements" />
	</p>
	<div
		class="${model.auditor.areFormalAndLegalReqiurementValid ? 'qs-valid' : 'qs-invalid'}">
		<div class="input-wrapp smaller">
			<label> <spring:message code="auditor.ecrCardSigned" />:
			</label>
			<div class="field">
				<form:checkbox path="ecrCardSigned" />
			</div>
		</div>
		<div class="input-wrapp smaller">
			<label> <spring:message code="auditor.confidentialitySigned" />:
			</label>
			<div class="field">
				<form:checkbox path="confidentialitySigned" />
			</div>
		</div>
		<div class="input-wrapp smaller">
			<label> <spring:message code="auditor.cvDelivered" />:
			</label>
			<div class="field">
				<form:checkbox path="cvDelivered" />
			</div>
		</div>
	</div>
	<p class="form-head">
		<spring:message code="auditor.head.generalQualification" />
	</p>
	<div class="qs-fields-wrapp">
		<div class="qs-left-bx">
			<h4>
				<spring:message code="auditor.head.education.amd" />
			</h4>
			<div class="qs-input-wrapp">
				<label> <spring:message code="auditor.educationLevel" />:
				</label>
				<div class="qs-filed">
					<form:select path="education['1'].eductionLevel"
						id="educationLevel1" class="chosenSmall">
						<option value="">---
							<spring:message code="auditor.select" /> ---
						</option>
						<c:forEach items="${model.educationsLevels}" var="i">
							<option
								${command.education['1'].eductionLevel eq i ? 'selected="selected"' :'' }
								value="${i.id}">${i.name}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="clear"></div>
			</div>

			<div class="qs-input-wrapp">
				<label> <spring:message code="auditor.fieldOfStudy" /> (1):
				</label>
				<div class="qs-filed">
					<form:select path="education['1'].education1" id="factiveMd1"
						class="chosenSmall">
						<option value="">---
							<spring:message code="auditor.select" /> ---
						</option>
						<c:forEach items="${model.fieldsOfEducationActiveMd}" var="i">
							<option
								${command.education['1'].education1 eq i ? 'selected="selected"' :'' }
								value="${i.id}">${i.name}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="clear"></div>
			</div>

			<div class="qs-input-wrapp">
				<label> <spring:message code="auditor.fieldOfStudy" /> (2):
				</label>
				<div class="qs-filed">
					<form:select path="education['1'].education2" id="factiveMd2"
						class="chosenSmall">
						<option value="">---
							<spring:message code="auditor.select" /> ---
						</option>
						<c:forEach items="${model.fieldsOfEducationActiveMd}" var="i">
							<option
								${command.education['1'].education2 eq i ? 'selected="selected"' :'' }
								value="${i.id}">${i.name}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="clear"></div>
			</div>


			<div class="qs-input-wrapp">
				<label> <spring:message code="auditor.psgStudy" />:
				</label>
				<div class="qs-filed">
					<form:select path="education['1'].postgreduateStudy" id="psg1"
						class="chosenSmall">
						<option value="">---
							<spring:message code="auditor.select" /> ---
						</option>
						<c:forEach items="${model.fieldsOfEducationActiveMd}" var="i">
							<option
								${command.education['1'].postgreduateStudy eq i ? 'selected="selected"' :'' }
								value="${i.id}">${i.name}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="clear"></div>
			</div>

		</div>
		<div class="qs-right-bx">
			<h4>
				<spring:message code="auditor.head.education.nonamd" />
			</h4>
			<div class="qs-input-wrapp">
				<label> <spring:message code="auditor.educationLevel" />:
				</label>
				<div class="qs-filed">
					<form:select path="education['2'].eductionLevel"
						id="educationLevel2" class="chosenSmall">
						<option value="">---
							<spring:message code="auditor.select" /> ---
						</option>
						<c:forEach items="${model.educationsLevels}" var="i">
							<option
								${command.education['1'].eductionLevel eq i ? 'selected="selected"' :'' }
								value="${i.id}">${i.name}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="clear"></div>
			</div>

			<div class="qs-input-wrapp">
				<label> <spring:message code="auditor.fieldOfStudy" /> (1):
				</label>
				<div class="qs-filed">
					<form:select path="education['2'].education1" id="factiveMd1"
						class="chosenSmall">
						<option value="">---
							<spring:message code="auditor.select" /> ---
						</option>
						<c:forEach items="${model.fieldsOfEducationNonActiveMd}" var="i">
							<option
								${command.education['2'].education1 eq i ? 'selected="selected"' :'' }
								value="${i.id}">${i.name}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="clear"></div>
			</div>

			<div class="qs-input-wrapp">
				<label> <spring:message code="auditor.fieldOfStudy" /> (2):
				</label>
				<div class="qs-filed">
					<form:select path="education['2'].education2" id="factiveMd2"
						class="chosenSmall">
						<option value="">---
							<spring:message code="auditor.select" /> ---
						</option>
						<c:forEach items="${model.fieldsOfEducationNonActiveMd}" var="i">
							<option
								${command.education['2'].education2 eq i ? 'selected="selected"' :'' }
								value="${i.id}">${i.name}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="clear"></div>
			</div>


			<div class="qs-input-wrapp">
				<label> <spring:message code="auditor.psgStudy" />:
				</label>
				<div class="qs-filed">
					<form:select path="education['2'].postgreduateStudy" id="psg1"
						class="chosenSmall">
						<option value="">---
							<spring:message code="auditor.select" /> ---
						</option>
						<c:forEach items="${model.fieldsOfEducationNonActiveMd}" var="i">
							<option
								${command.education['2'].postgreduateStudy eq i ? 'selected="selected"' :'' }
								value="${i.id}">${i.name}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="clear"></div>
			</div>

		</div>

		<div class="clear"></div>

		<div>
			<p class="mini-info">
				<spring:message code="auditor.info.education" />
			</p>
		</div>
	</div>


	<!-- TRAINING -->
	<p class="form-head">
		<spring:message code="auditor.head.training" />
	</p>
	<div class="qs-fields-wrapp">
		<div class="qs-left-bx">
			<div class="input-wrapp smaller">
				<label> <spring:message code="auditor.aprovedForIso9001" />:

				</label>
				<div class="field">
					<form:checkbox path="aprovedForIso9001" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.iso9001" />: </strong>
				</label>
				<div class="field">
					<form:input path="trainingIso9001InHours"
						cssClass="w50 c required numeric" maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.mdd" />: </strong>
				</label>
				<div class="field">
					<form:input path="mdd" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.totalAudits" />: </strong>
				</label>
				<div class="field">
					<form:input path="totalAudits" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong> <em class="red">*</em> <spring:message
							code="auditor.totalAuditdays" />:
				</strong>
				</label>
				<div class="field">
					<form:input path="totalAuditdays" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
		</div>
		<div class="qs-right-bx qs-border-right">
			<div class="input-wrapp smaller">
				<label> <spring:message code="auditor.aprovedForIso13485" />:

				</label>
				<div class="field">
					<form:checkbox path="aprovedForIso13485" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.iso13485" />: </strong>
				</label>
				<div class="field">
					<form:input path="iso13485" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.ivd" />: </strong>
				</label>
				<div class="field">
					<form:input path="ivd" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.aimd" />: </strong>
				</label>
				<div class="field">
					<form:input path="aimd" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong><em class="red">*</em> <spring:message
							code="auditor.nb1023Procedures" />: </strong>
				</label>
				<div class="field">
					<form:input path="nb1023Procedures"
						cssClass="w50 c required numeric" maxlength="4" />
				</div>
			</div>
			
		</div>
		<div class="clear"></div>
	</div>

	<div class="qs-fields-wrapp">
		<div class="qs-left-bx">
			<p class="form-head mini">
				<spring:message code="auditor.productAssessorR" />
			</p>
			<div class="input-wrapp smaller">
				<label> <strong> <em class="red">*</em> <spring:message
							code="auditor.tfTrainingInHours" />:
				</strong>
				</label>
				<div class="field">
					<form:input path="tfTrainingInHours"
						cssClass="w50 c required numeric" maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong> <em class="red">*</em> <spring:message
							code="auditor.totalTfReviews" />:
				</strong>
				</label>
				<div class="field">
					<form:input path="totalTfReviews" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label class="tt" title="The value is taken form worker's NANDO code: MDS 7006. You can change this value by switching to Product Assessor-R tab.">
					<spring:message code="auditor.noTfReviewsForSterile" />:
				</label>
				<div class="field">
					<span class="like-input">${model.sterileNandoCode.numberOfTfReviews}</span>
				</div>
			</div>
		</div>
		<div class="qs-right-bx qs-border-right">
			<p class="form-head mini">
				<spring:message code="auditor.productSpecialist" />
			</p>
			<div class="input-wrapp smaller">
				<label> <strong> <em class="red">*</em> <spring:message
							code="auditor.ddTrainingInHours" />:
				</strong>
				</label>
				<div class="field">
					<form:input path="ddTrainingInHours"
						cssClass="w50 c required numeric" maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label> <strong> <em class="red">*</em> <spring:message
							code="auditor.totalDdReviews" />:
				</strong>
				</label>
				<div class="field">
					<form:input path="totalDdReviews" cssClass="w50 c required numeric"
						maxlength="4" />
				</div>
			</div>
			<div class="input-wrapp smaller">
				<label class="tt" title="The value is taken form worker's NANDO code: MDS 7006. You can change this value by switching to Product Specialist tab.">
					<spring:message code="auditor.noDdReviewsForSterile" />:
				</label>
				<div class="field">
					<span class="like-input">${model.sterileNandoCode.numberOfDdReviews}</span>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		
		
		
		<!-- THE RECENT ACTIVITIES  -->
		<p class="form-head mini">
			<spring:message code="auditor.function.activities" />	
		</p>
		<div class="${model.settings.minQsAuditorAuditDaysInRecentYear > command.auditDaysInRecentYear and 
			 		  model.settings.minQsAuditorAuditDaysInRecentYear > model.auditDaysIntRecentyear
			  ? 'qs-invalid' : 'qs-valid'}" >
			<div class="input-wrapp smaller">
				<label class="tt" title="If given initial value is greater than calculated value from Audit logs, will be used.
					 Otherwise will be use (greater value) counted form APPROVED Audit logs. 
					 Current treashold for Qs Auditor function is set to: ${model.settings.minQsAuditorAuditDaysInRecentYear}. 
					 For ProductAssessor-A is set to:  ${model.settings.minProductAssessorAAuditDaysInRecentYear}. You can change this values in QUASAR SETTINGS section. "> 
						<strong> <em class="red">*</em> 
						<spring:message code="auditor.auditDaysInRecentYear" /> (initial value):
						</strong>
				</label>
				<div class="field">
					<form:input path="auditDaysInRecentYear" cssClass="w50 c required numeric" maxlength="4" />
					<span class="qs-field-separator">
						Value form approved Audit logs: &nbsp; &nbsp; &nbsp; &nbsp;
						<span class="like-input">${model.auditDaysIntRecentyear}</span>
						<span class="qs-help-ico tt " title="
						Value is calculated form approved Audit logs, which were created between: 
						<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
						<joda:format value="${model.oneYearAgo}" pattern="dd.MM.yyyy" />
						"></span>
					</span>
				</div>
			</div>
		</div>
		<div class="${model.settings.minTrainingHoursInRecentYear > command.trainingHoursInRecentYear and 
			 		  model.settings.minTrainingHoursInRecentYear > model.trainingHoursInRecentyear
			  ? 'qs-invalid' : 'qs-valid'}" >
			<div class="input-wrapp smaller">
				<label class="tt" title="If given initial value is greater than calculated value from Training logs, 
				will be used. Otherwise will be used (greater value) counted form APPROVED Training logs.
				<spring:message code="auditor.tt.treashold" arguments="${model.settings.minTrainingHoursInRecentYear}" />
				"> 
						<strong> <em class="red">*</em> 
						<spring:message code="auditor.trainingHoursInRecentYear" /> (initial value):
						</strong>
				</label>
				<div class="field">
					<form:input path="trainingHoursInRecentYear" cssClass="w50 c required numeric" maxlength="4" />
					<span class="qs-field-separator">
						Value form approved Training logs: &nbsp;
						<span class="like-input">${model.trainingHoursInRecentyear}</span>
						<span class="qs-help-ico tt " title="
						Value is calculated form approved Training logs, which were created between: 
						<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
						<joda:format value="${model.oneYearAgo}" pattern="dd.MM.yyyy" />.
						Values, which are counted: MDD + IVD + AIMD + ISO 13485 + NB 1023 procedures + Category-specifict training. ISO 9001 training is excluded.
						"></span>
					</span>
				</div>
			</div>
		</div>
		
		<div class="qs-fields-wrapp">
			<div class="qs-left-bx">
				<div class="${ model.countOfTfInRecentYear >= model.settings.minTfReviewsInRecentYear or
							  (
							  (model.countOfTfInRecentYear + model.countOfDdInRecentYear) >= model.settings.minTfReviewsInRecentYear and 
							  (model.countOfTfInRecentYear >= model.countOfDdInRecentYear) 	
							  )
					   ? 'qs-valid' : 'qs-invalid'}" >
					<div class="input-wrapp smaller">
						<label class="tt" title="
							<spring:message code="auditor.tt.dossierReport" /> 
							<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
							<joda:format value="${model.oneYearAgo}" pattern="dd.MM.yyyy" />. 
							<spring:message code="auditor.tt.treashold" arguments="${model.settings.minTfReviewsInRecentYear}" />
						"> 
							<spring:message code="auditor.countOfTfInRecentYear" />:
						</label>
						<div class="field">
							<span class="like-input">${model.countOfTfInRecentYear}</span>
						</div>
					</div>
				</div>
				<div class="${model.settings.minDdReviewsInRecentYear > model.countOfDdInRecentYear  ? 'qs-invalid' : 'qs-valid'}" >
					<div class="input-wrapp smaller">
						<label class="tt" title="
							<spring:message code="auditor.tt.dossierReport" /> 
							<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
							<joda:format value="${model.oneYearAgo}" pattern="dd.MM.yyyy" />. 
							<spring:message code="auditor.tt.treashold" arguments="${model.settings.minDdReviewsInRecentYear}" />
						"> 
							<spring:message code="auditor.countOfDdInRecentYear" />:
						</label>
						<div class="field">
							<span class="like-input">${model.countOfDdInRecentYear}</span>
						</div>
					</div>
				</div>
			</div>
			<div class="qs-right-bx qs-border-right">
				<div class="${ (model.countOfTfInRecentThreeYears >= model.settings.minTfReviewsInRecentThreeYears) or 
								( 
									(model.countOfTfInRecentThreeYears + model.countOfDdInRecentThreeYears) >= model.settings.minTfReviewsInRecentThreeYears and
									model.countOfTfInRecentThreeYears >= model.countOfDdInRecentThreeYears
								)
							   ? 'qs-valid' : 'qs-invalid'}" >
					<div class="input-wrapp smaller">
						<label class="tt" title="
								<spring:message code="auditor.tt.dossierReport" /> 
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.threeYearsAgo}" pattern="dd.MM.yyyy" />.
								<spring:message code="auditor.tt.treashold" arguments="${model.settings.minTfReviewsInRecentThreeYears}" />
							"> 
							<spring:message code="auditor.countOfTfInRecentThreeYears" />:
						</label>
						<div class="field">
							<span class="like-input">${model.countOfTfInRecentThreeYears}</span>
						</div>
					</div>
				</div>
				<div class="${model.settings.minDdReviewsInRecentThreeYears > model.countOfDdInRecentThreeYears  ? 'qs-invalid' : 'qs-valid'}" >	
					<div class="input-wrapp smaller">
						<label class="tt" title="
								<spring:message code="auditor.tt.dossierReport" /> 
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.threeYearsAgo}" pattern="dd.MM.yyyy" />. 
								<spring:message code="auditor.tt.treashold" arguments="${model.settings.minDdReviewsInRecentThreeYears}" />
							"> 
							<spring:message code="auditor.countOfDdInRecentThreeYears" />:
						</label>
						<div class="field">
							<span class="like-input">${model.countOfDdInRecentThreeYears}</span>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<form:hidden path="id" />
	<p class="button-box">
		<input type="submit" class="button"
			value="<spring:message code="form.save" />" />
	</p>
</form:form>


<!-- SPECIAL TRAINING -->

<p class="form-head">
	<spring:message code="auditor.head.specialTraining" />
</p>
<c:if test="${empty model.auditor.specialTrainings}">
	<p class="msg alert">
		<spring:message code="auditor.noTraining"
			arguments="${model.auditor.name}" />
	</p>
</c:if>
<c:if test="${not empty model.auditor.specialTrainings}">
	<table class="data">
		<tr class="gs-head-min">
			<th><spring:message code="topic" /></th>
			<th><spring:message code="hours" /></th>
			<th><spring:message code="changed" /></th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${model.auditor.specialTrainings}" var="i">
			<tr>
				<td class="b gs-exp-name">${i.name}</td>
				<td class="b gs-exp-input c"><strong>${i.hours}</strong></td>
				<td class="b gs-exp-changed"><joda:format value="${i.changed}"
						pattern="${common.dateTimeFormat}" /> / ${i.changedBy.name}</td>
				<td class="b gs-exp-btn delete"><a class="confirmMessage"
					href="${url}/special-training/${i.id}"
					data-message="<spring:message code="quasar.delete.confirm" />">
						<spring:message code="quasar.delete" />
				</a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<!--  Special training form -->
<form class="inline-form valid" action="${url}/special-training"
	method="post">
	<div class="inline-field">
		<span class="inline-label"> <spring:message code="topic" />:
		</span> <input type="text" name="name" class="w300 required" />
	</div>
	<div class="inline-field">
		<span class="inline-label"> <spring:message code="hours" />:
		</span> <input type="text" name="hours" class="required numeric w40 c"
			maxlength="2" />
	</div>
	<input type="hidden" name="auditor" value="${command.id}" /> <input
		type="submit" class="lang mandate-add-btn"
		value="<spring:message code="assign" />" />
</form>

<br />

<!-- WORK EXPERIENCE -->
<p class="form-head">
	<spring:message code="auditor.head.workExperience" />
</p>
<c:if test="${empty model.auditor.auditorExperiences}">
	<p class="msg alert">
		<spring:message code="auditor.noExperience"
			arguments="${model.auditor.name}" />
	</p>
</c:if>
<c:if test="${not empty model.auditor.auditorExperiences}">
	<table class="data">
		<tr class="gs-head-min">
			<th><spring:message code="category" /></th>
			<th><spring:message code="changed" /></th>
			<th><spring:message code="years" /></th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${model.auditor.auditorExperiences}" var="i">
			<tr>
				<td class="b gs-exp-name">${i.experience.name}</td>
				<td class="b gs-exp-changed"><joda:format value="${i.changed}"
						pattern="${common.dateTimeFormat}" /> / ${i.changedBy.name}</td>
				<td class="b gs-exp-input">
					<form action="${url}/experience" method="post" class="valid">
						<input type="text" name="years" class="w50 c required numeric"
							value="${i.years}" /> <input type="hidden" name="id"
							value="${i.id}" /> <input type="hidden" name="auditor"
							value="${model.auditor.id}" /> <input type="hidden"
							name="experience" value="${i.experience.id}" />
					</form>
				</td>
				<td class="b gs-exp-btn"><a class="lang mandate-add-btn qs-btn">
						<spring:message code="form.save" />
				</a></td>
			</tr>
		</c:forEach>
	</table>
	<table class="qs-total-exp">
		<tr>
			<td class="qs-label"><spring:message
					code="auditor.experience.total" />:</td>
			<td class="qs-years"><span>${model.auditor.totalWorkExperience}</span>
				<em><spring:message code="years" /></em></td>
		</tr>
		<tr>
			<td class="qs-label"><spring:message
					code="auditor.experience.totalMd" />:</td>
			<td class="qs-years"><span>${model.auditor.totalWorkExperienceInMedicalDevices}</span>
				<em><spring:message code="years" /></em></td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty model.unassignedExperiences}">
	<!--  Experience form -->
	<form:form modelAttribute="auditorExperience"
		cssClass="inline-form valid" action="${url}/experience" method="post">
		<div class="inline-field">
			<span class="inline-label"> <spring:message code="category" />:
			</span> <select class="required chosenSmall" name="experience">
				<option>---
					<spring:message code="auditor.select" /> ---
				</option>
				<c:forEach items="${model.unassignedExperiences}" var="i">
					<option value="${i.id}">${i.name}</option>
				</c:forEach>
			</select>
		</div>
		<div class="inline-field">
			<span class="inline-label"> <spring:message code="noOfYears" />:
			</span>
			<form:input path="years" class="required numeric w40 c" maxlength="2" />
		</div>
		<form:hidden path="auditor" />
		<input type="submit" class="lang mandate-add-btn"
			value="<spring:message code="assign" />" />
	</form:form>
</c:if>
<a href="#" id="loadFileManager" class="lang mandate-add-btn qs-btn"><spring:message
		code="manageFiles" arguments="${model.auditor.name}" /> &raquo;</a>
<div class="fileManagment hidden" data-id="${model.auditor.id}">
	<p class="form-head">
		<spring:message code="manageFiles" arguments="${model.auditor.name}" />
	</p>
	<div></div>
</div>