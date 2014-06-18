<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="auditor.edit" />: ${model.auditor.name}</title>
		<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
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
			  <a:adminurl href="/quasar/manage/auditors"><spring:message code="auditors" /></a:adminurl>  &raquo;
			  <span>${model.auditor.name}</span>
		</div>
		<h1><spring:message code="auditor.edit" />:&nbsp; <strong>${model.auditor.name}</strong></h1>

		<div id="content" class="qs">
			
			<jsp:include page="navs/auditor-nav.jsp" />
			<jsp:include page="changed.jsp" />
			<c:url value="/admin/quasar/manage/auditor/${command.id}" var="url" />
			<form:form  commandName="command" method="post" cssClass="valid" action="${url}?action=1" > 
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<p class="form-head"><spring:message code="auditor.head.login" /></p>
				<div class="input-wrapp">
					<label>
						<strong><em class="red">*</em>
						<spring:message code="auditor.email" />:
						</strong>
					</label>
					<div class="field">
						<form:input path="email" maxlength="50" cssClass="w300 required email" />
					</div>
				</div>
				<div class="input-wrapp">
					<label>
						<spring:message code="isActivated" />:
						
					</label>
					<div class="field">
						<form:checkbox path="enabled" />
						<span class="inline"><small>Unchecking this field, you can disable user's access.</small></span> 
					</div>
				</div>
				
				<p class="form-head"><spring:message code="auditor.head.personalInfo" /></p>
				<div class="input-wrapp smaller">
					<label>
						<strong><em class="red">*</em>
                      		<spring:message code="auditor.itcId" />:
                      	</strong>
					</label>
					<div class="field">
						<form:input path="itcId" cssClass="w50 c required numeric" maxlength="5" />
					</div>
				</div>
				<div class="input-wrapp smaller">
					<label>
						<spring:message code="auditor.degrees" />:
					</label>
					<div class="field">
						<form:input path="degrees" cssClass="w100 " maxlength="25" />
					</div>
				</div>
				<div class="input-wrapp smaller">
					<label>
						<strong><em class="red">*</em>
						<spring:message code="auditor.name" />:
						</strong>
					</label>
					<div class="field">
						<form:input path="firstName" cssClass="mw150 required" maxlength="50" />
						<form:input path="lastName" cssClass="mw150 required" maxlength="50" />
					</div>
				</div>
				<div class="input-wrapp smaller">
					<label>
						<spring:message code="auditor.reassessmentDate" />:
					</label>
					<div class="field">
						<form:input path="reassessmentDate" cssClass="date " maxlength="10" />
					</div>
				</div>
				<div class="input-wrapp smaller">
					<label>
						<spring:message code="auditor.country" />:
					</label>
					<div class="field">
						<form:select path="country" cssClass="chosenSmall">
							<option> --- <spring:message code="auditor.select" /> ---</option>
							<c:forEach items="${model.countries}" var="i">
								<option value="${i.id}" ${i.id eq command.country.id ? 'selected="selected"' : '' }>${i.countryName}</option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="input-wrapp smaller">
					<label>
						<spring:message code="auditor.partner" />:
					</label>
					<div class="field">
						<form:select path="country" cssClass="chosenSmall">
							<option> --- <spring:message code="auditor.select" /> ---</option>
							<c:forEach items="${model.partners}" var="i">
								<option value="${i.id}" ${i.id eq command.partner.id ? 'selected="selected"' : '' }>${i.name}</option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<p class="form-head"><spring:message code="auditor.head.formalRequirements" /></p>
                <div class="${model.auditor.areFormalAndLegalReqiurementValid ? 'qs-valid' : 'qs-invalid'}">
                	<div class="input-wrapp smaller">
						<label>
							<spring:message code="auditor.ecrCardSigned" />:
						</label>
						<div class="field">
							<form:checkbox path="ecrCardSigned" />
						</div>
					</div>
					<div class="input-wrapp smaller">
						<label>
							<spring:message code="auditor.confidentialitySigned" />:
						</label>
						<div class="field">
							<form:checkbox path="confidentialitySigned" />
						</div>
					</div>
					<div class="input-wrapp smaller">
						<label>
							<spring:message code="auditor.cvDelivered" />:
						</label>
						<div class="field">
							<form:checkbox path="cvDelivered" />
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
									<form:select path="education['1'].eductionLevel" id="educationLevel1" class="chosenSmall">
										<option> --- <spring:message code="auditor.select" /> ---</option>
										<c:forEach items="${model.educationsLevels}" var="i">
											<option ${command.education['1'].eductionLevel eq i ? 'selected="selected"' :'' }
											value="${i.id}">${i.name}</option>
										</c:forEach>
									</form:select>
								</div>
								<div class="clear"></div>
							</div>
							
							<div class="qs-input-wrapp">
								<label>
									<spring:message code="auditor.fieldOfStudy" /> (1):
								</label>
								<div class="qs-filed">
									<form:select path="education['1'].education1" id="factiveMd1" class="chosenSmall">
										<option> --- <spring:message code="auditor.select" /> ---</option>
										<c:forEach items="${model.fieldsOfEducationActiveMd}" var="i">
											<option ${command.education['1'].education1 eq i ? 'selected="selected"' :'' }
											value="${i.id}">${i.name}</option>
										</c:forEach>
									</form:select>
								</div>
								<div class="clear"></div>
							</div>
							
							<div class="qs-input-wrapp">
								<label>
									<spring:message code="auditor.fieldOfStudy" /> (2):
								</label>
								<div class="qs-filed">
									<form:select path="education['1'].education2" id="factiveMd2" class="chosenSmall">
										<option> --- <spring:message code="auditor.select" /> ---</option>
										<c:forEach items="${model.fieldsOfEducationActiveMd}" var="i">
											<option ${command.education['1'].education2 eq i ? 'selected="selected"' :'' }
											value="${i.id}">${i.name}</option>
										</c:forEach>
									</form:select>
								</div>
								<div class="clear"></div>
							</div>
							
							
							<div class="qs-input-wrapp">
								<label>
									<spring:message code="auditor.psgStudy" />:
								</label>
								<div class="qs-filed">
									<form:select path="education['1'].postgreduateStudy" id="psg1" class="chosenSmall">
										<option> --- <spring:message code="auditor.select" /> ---</option>
										<c:forEach items="${model.fieldsOfEducationActiveMd}" var="i">
											<option ${command.education['1'].postgreduateStudy eq i ? 'selected="selected"' :'' }
											value="${i.id}">${i.name}</option>
										</c:forEach>
									</form:select>
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
									<form:select path="education['2'].eductionLevel" id="educationLevel2" class="chosenSmall">
										<option> --- <spring:message code="auditor.select" /> ---</option>
										<c:forEach items="${model.educationsLevels}" var="i">
											<option ${command.education['1'].eductionLevel eq i ? 'selected="selected"' :'' }
											value="${i.id}">${i.name}</option>
										</c:forEach>
									</form:select>
								</div>
								<div class="clear"></div>
							</div>
							
							<div class="qs-input-wrapp">
								<label>
									<spring:message code="auditor.fieldOfStudy" /> (1):
								</label>
								<div class="qs-filed">
									<form:select path="education['2'].education1" id="factiveMd1" class="chosenSmall">
										<option> --- <spring:message code="auditor.select" /> ---</option>
										<c:forEach items="${model.fieldsOfEducationNonActiveMd}" var="i">
											<option ${command.education['2'].education1 eq i ? 'selected="selected"' :'' }
											value="${i.id}">${i.name}</option>
										</c:forEach>
									</form:select>
								</div>
								<div class="clear"></div>
							</div>
							
							<div class="qs-input-wrapp">
								<label>
									<spring:message code="auditor.fieldOfStudy" /> (2):
								</label>
								<div class="qs-filed">
									<form:select path="education['2'].education2" id="factiveMd2" class="chosenSmall">
										<option> --- <spring:message code="auditor.select" /> ---</option>
										<c:forEach items="${model.fieldsOfEducationNonActiveMd}" var="i">
											<option ${command.education['2'].education2 eq i ? 'selected="selected"' :'' }
											value="${i.id}">${i.name}</option>
										</c:forEach>
									</form:select>
								</div>
								<div class="clear"></div>
							</div>
							
							
							<div class="qs-input-wrapp">
								<label>
									<spring:message code="auditor.psgStudy" />:
								</label>
								<div class="qs-filed">
									<form:select path="education['2'].postgreduateStudy" id="psg1" class="chosenSmall">
										<option> --- <spring:message code="auditor.select" /> ---</option>
										<c:forEach items="${model.fieldsOfEducationNonActiveMd}" var="i">
											<option ${command.education['2'].postgreduateStudy eq i ? 'selected="selected"' :'' }
											value="${i.id}">${i.name}</option>
										</c:forEach>
									</form:select>
								</div>
								<div class="clear"></div>
							</div>
						
					</div>
					
					<div class="clear"></div>
					
					<div>
						<p class="mini-info"><spring:message  code="auditor.info.education"/></p>
					</div>
                 </div>
                 
                 <form:hidden path="id" />
                    <p class="button-box">
                    	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                    </p>        
			</form:form>
			
			 <p class="form-head"><spring:message code="auditor.head.workExperience" /></p>
			 
			<c:if test="${empty model.auditor.auditorExperiences}">
				<p class="msg alert">
					<spring:message code="auditor.noExperience" arguments="${model.auditor.name}" />
				</p>
			</c:if>
			<table class="data">
				<c:forEach items="${model.auditor.auditorExperiences}" var="i">
					<tr id="e${i.id}">
						<td class="b gs-exp-name">
							${i.experience.name}
						</td>
						<td class="b gs-exp-changed">
						<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
							/ ${i.changedBy.name}
						</td>
						<td class="b gs-exp-input">
							<input type="text" class="w50 c" value="${i.years }" />
						</td>
						<td class="b gs-exp-btn">
							Save
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:if test="${not empty model.unassignedExperiences}">
				<!--  Experience form -->	
				<form:form modelAttribute="auditorExperience"  cssClass="inline-form valid" action="${url}/experience" method="post"  >
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				 	<div class="inline-field">
				 		<span class="inline-label">
				 			<spring:message code="category" />:
				 		</span>
				 		<select class="required chosenSmall" name="experience">
							<option> --- <spring:message code="auditor.select" /> ---</option>
							<c:forEach items="${model.unassignedExperiences}" var="i">
		                              <option value="${i.id}"> ${i.name}</option>
		                      </c:forEach>
						</select>
				 	</div>
				 	<div class="inline-field">
				 		<span class="inline-label">
				 			<spring:message code="noOfYears" />:
				 		</span>
				 		<form:input path="years" class="required numeric w40 c" maxlength="2" />
				 	</div>
				 	<form:hidden path="auditor"/>
				 	<input type="submit" class="lang mandate-add-btn" value="<spring:message code="assign" />" />
				 </form:form>
			 </c:if>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>