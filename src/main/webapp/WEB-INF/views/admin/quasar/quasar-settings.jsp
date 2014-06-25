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