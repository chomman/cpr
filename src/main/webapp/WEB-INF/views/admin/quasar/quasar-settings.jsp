<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="quasarSettings" /></title>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
		
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
				 <span><spring:message code="quasarSettings" /></span>
			</div>
			<h1><spring:message code="quasarSettings" /></h1>
	
			<div id="content">
				
				<form:form commandName="quasarSettings" method="post" cssClass="valid" >
							
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
					</c:if>
					
					<p class="form-head"><spring:message code="auditor.qsAuditor" /> training</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.classRoom" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="qsAuditorClassRoomTraining" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.Iso13485" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="qsAuditorIso13485Training" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.mdTraining" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="qsAuditorMdTraining" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.nb1023Procedures" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="qsAuditorNb1023Procedures" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.totalAudits" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="qsAuditorNoAudits" maxlength="3" cssClass="w40 c required numeric" />
						</span>
					</p>   
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.minAuditDaysInRecentYear" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="minQsAuditorAuditDaysInRecentYear" maxlength="3" cssClass="w40 c required numeric" />
							<span>
								Interval: 
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.oneYearAgo}" pattern="dd.MM.yyyy" />
							</span>
						</span>
					</p>
					<p class="form-head"><spring:message code="auditor.productAssessorA" /> training</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.mdTraining" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="productAssessorAMdTraining" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.ivd" />:
							</strong>  
							<small>For activity in the IVD sector</small>
						</label>
						<span class="field">
							<form:input path="productAssessorAIvdTraining" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.nb1023Procedures" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="productAssessorANb1023Procedures" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.totalAudits" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="productAssessorANoAudits" maxlength="3" cssClass="w40 c required numeric" />
						</span>
					</p>
					 <p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.minAuditDaysInRecentYear" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="minProductAssessorAAuditDaysInRecentYear" maxlength="3" cssClass="w40 c required numeric" />
							<span>
								Interval: 
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.oneYearAgo}" pattern="dd.MM.yyyy" />
							</span>
						</span>
					</p>
					
					<p class="form-head"><spring:message code="auditor.productAssessorR" /> training</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.mdTraining" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="productAssessorRMdTraining" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p> 
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.ivd" />:
							</strong>  
							<small>For activity in the IVD sector</small>
						</label>
						<span class="field">
							<form:input path="productAssessorRIvdTraining" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.trainintInReviews" arguments="TF" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="productAssessorRTfTrainingReview" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.totalReviews" arguments="TF" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="productAssessorRTfTotal" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.minTfReviewsInRecentYear" arguments="DD" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="minTfReviewsInRecentYear" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
							
							<span>
								Current interval: 
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.oneYearAgo}" pattern="dd.MM.yyyy" />
							</span>
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.minTfReviewsInRecentThreeYears" arguments="DD" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="minTfReviewsInRecentThreeYears" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
							<span>
								Current interval: 
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.threeYearsAgo}" pattern="dd.MM.yyyy" />
							</span>
						</span>
					</p>
					<p class="form-head"><spring:message code="auditor.productSpecialist" /> training</p>
					<p>
						<label>
							<strong><em class="red">*</em>
							<spring:message code="quasarSettings.mdTraining" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="productSpecialistMdTraining" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p> 
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.ivd" />:
							</strong>  
							<small>For activity in the IVD sector</small>
						</label>
						<span class="field">
							<form:input path="productSpecialistIvdTraining" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.trainintInReviews" arguments="DD" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="productSpecialistDdTrainingReview" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.totalReviews" arguments="DD" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="productSpecialistDdTotal" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
						</span>
					</p>
					
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.minDdReviewsInRecentYear" arguments="DD" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="minDdReviewsInRecentYear" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
							<span>
								Current interval: 
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.oneYearAgo}" pattern="dd.MM.yyyy" />
							</span>
						</span>
					</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.minDdReviewsInRecentThreeYears" arguments="DD" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="minDdReviewsInRecentThreeYears" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
							<span>
								Current interval: 
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.threeYearsAgo}" pattern="dd.MM.yyyy" />
							</span>
						</span>
					</p>
					<p class="form-head">
						<spring:message code="auditor.function.activities" /> (All functions)
					</p>            
					<p>
						<label>
							<strong><em class="red">*</em>
								<spring:message code="quasarSettings.minTrainingHoursInRecentYear" />:
							</strong>  
						</label>
						<span class="field">
							<form:input path="minTrainingHoursInRecentYear" maxlength="3" cssClass="w40 c required numeric" />
							<spring:message code="hours" />
							<span>
								Current interval: 
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.oneYearAgo}" pattern="dd.MM.yyyy" />
							</span>
						</span>
					</p>
					
					<p class="form-head">Other settings</p>
					<p>
						<label>
							<strong><em class="red">*</em>
								Administrator's e-mail:
							</strong>  
							<small>For notifications about (re)approval requests</small>
						</label>
						<span class="field">
							<form:input path="notificationEmail" maxlength="50" cssClass="mw300 required email" />
						</span>
					</p>  
					
					<p>
						<label>
							<strong><em class="red">*</em>
								The recent activities interval settings:
							</strong>  
						</label>
						<span class="field">
							<form:select path="use365DaysInterval">
								<option ${quasarSettings.use365DaysInterval ? 'selected="selected"' : '' }  
								value="true">Use current date - 365 days interval e.g.:(
								<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
								<joda:format value="${model.yearAgo}" pattern="dd.MM.yyyy" />
								)</option>
								<option ${not quasarSettings.use365DaysInterval ? 'selected="selected"' : '' } 
								value="false">User whole last year include current e.g.:(
									<joda:format value="${model.today}" pattern="dd.MM.yyyy" /> - 
									01.01.<joda:format value="${model.wholeLastYear}" pattern="yyyy" />
								)</option>
							</form:select>
						</span>
					</p>  
					
					
					  
                    <form:hidden path="id" />
                       <p class="button-box">
                       	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                       </p>
				</form:form>
				<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
			</div>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>