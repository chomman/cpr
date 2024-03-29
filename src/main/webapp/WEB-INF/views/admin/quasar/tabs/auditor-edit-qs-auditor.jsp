<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<h3 class="qs-function">${model.auditor.name} as <strong><spring:message code="nbCode" /> <spring:message code="auditor.qsAuditor" /></strong></h3>
<c:if test="${model.isEditable}">
<c:url value="/admin/quasar/manage/auditor/${auditor.id}" var="url" />
<form:form  commandName="auditor" method="post" cssClass="submit-on-change" action="${url}/decision/2">
	<div class="input-wrapp smallest">
		<label>
			<spring:message code="approvalRecentActivities.audits" />:
			
		</label>
		<div class="field">
			<div class="qs-float-left" ><form:checkbox cssClass="confirm-ra" path="recentActivitiesApprovedForQsAuditor" /></div>
			<p class="mini-info inline-block"><spring:message code="approvalRecentActivities.info" arguments="${model.auditor.name}" /></p>
		</div>
	</div>
	<form:hidden path="id"/>
</form:form>
</c:if>
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
		<li class="${model.function.recentActivities ? 'qs-valid' : 'qs-invalid'}">
			<span>
				3. <spring:message code="auditor.function.activities" />:
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
	</ul>
</div>


<c:if test="${not empty successCreate}">
	<p class="msg ok"><spring:message code="success.create" /></p>
</c:if>

<c:set value="" var="scope" />
<c:forEach items="${model.codes}" var="i"> 
	<c:set value="${model.function.areAllRequirementsValid and i.granted}" var="isGranted" />
	<div class="qs-item radius ${isGranted and i.granted ? 'qs-valid' : 'qs-invalid'}">
		<div class="qs-code-wrapp">
				<form method="post" id="f${i.id}">
				<div class="qs-name">
					<c:if test="${model.isEditable}">
					<div class="qs-inline-edit" data-id="${i.id}">
						<spring:message code="quasar.edit" />
					</div>
					</c:if>
					<div class="reason-detail ${empty i.reasonOfRefusal ? 'qs-field' : ''}">
						<span class="val h">
							<c:if test="${not empty i.reasonOfRefusal}">
								<h5><spring:message code="reasonDetails" /></h5>
								<span>${i.reasonOfRefusal}</span>
							</c:if>
						</span>
						<div class="qs-field">
							<h5>Reason details (max. 255 chars.)</h5>
							<textarea name="reasonOfRefusal" rows="5" cols="5" placeholder="Write details...">${i.reasonOfRefusal}</textarea>
						</div>
					</div>
					<div class="qs-code">
						<div class="eac">${i.eacCode.code}</div>
						<div class="nace">NACE: ${i.eacCode.naceCode}</div>
					</div>
					<div class="is-granted ${isGranted ? 'qs-valid' : 'qs-invalid'}">
						<c:if test="${isGranted}">
							<spring:message code="granted" />
							<c:set var="scope" value="${scope}(${i.eacCode.code}), " />
						</c:if>
						<c:if test="${not isGranted}">
							<spring:message code="refused" />
						</c:if>
					</div>
					<h3>${i.eacCode.name}</h3>
					<table>
						<tr>
							<td class="k"><spring:message code="qsAuditor.noNb1023Audits" />:</td>
							<td class="v">
								<span class="val h">${i.numberOfNbAudits}</span>
								<span class="qs-field">
									<input type="text" value="${i.numberOfNbAudits}" name="numberOfNbAudits" class="w30 c required numeric">
								</span>
							</td>
						</tr>
						<tr>
							<td class="k"><spring:message code="qsAuditor.noIso13485Audits" />:</td>
							<td class="v">
								<span class="val h">${i.numberOfIso13485Audits}</span>
								<span class="qs-field">
									<input type="text" value="${i.numberOfIso13485Audits}" name="numberOfIso13485Audits" class="w30 c required numeric">
								</span>
							</td>
						</tr>
						<tr>
							<td class="k"><spring:message code="qsAuditor.total" />
								<spring:message code="inTraining" />:</td>
							<td class="v in-training">
									<span class="total">${i.totalNumberOfAudits}</span>
								<c:if test="${i.noOfAuditsInTraining > 0}">
									<span title="No. of audits in training" class="tt tr">(${i.noOfAuditsInTraining})</span>
								</c:if>
							</td>
						</tr>
						
						
						<tr class="${not i.refused ? 'qs-hide' : ''}">
							<td class="k"><spring:message code="isRefused" />:</td>
							<td class="v">
								<span class="val h">
									<c:if test="${i.refused}">
										<strong><spring:message code="yes" /></strong>
									</c:if>
									<c:if test="${not i.refused}">
										<spring:message code="no" />
									</c:if>
								</span>
								<span class="qs-field">
									<input type="checkbox" class="ch-refused" name="refused" ${i.refused ? 'checked="checked"' : ''}  />
								</span>
							</td>
						</tr>
						
						<tr class="${not i.itcApproved ? 'qs-hide' : ''}">
							<td class="k"><spring:message code="qsAuditor.itcApproval" />:</td>
							<td class="v">
								<span class="val h">
									<c:if test="${i.itcApproved}">
										<strong><spring:message code="yes" /></strong>
									</c:if>
									<c:if test="${not i.itcApproved}">
										<spring:message code="no" />
									</c:if>
								</span>
								<span class="qs-field">
									<input type="checkbox" class="ch-approved" name="itcApproved" ${i.itcApproved ? 'checked="checked"' : ''}  />
								</span>
							</td>
						</tr>
						
						<tr class="${empty i.notifiedBody ? 'qs-hide' : ''}">
							<td class="k"><spring:message code="qsAuditor.notifiedBody" />:</td>
							<td class="v h">
								<span class="val h">
									<c:if test="${not empty i.notifiedBody}">
									<a:adminurl href="/cpr/notifiedbodies/edit/${i.notifiedBody.id}">
										<strong>${i.notifiedBody.noCode}</strong>	
									</a:adminurl>
									</c:if>
									<c:if test="${empty i.notifiedBody}">
										<span class="val">
											<spring:message code="no" />
											</span>
										</c:if>
								</span>
								<span class="qs-field">
								
									<input name="notifiedBody" type="text" class="mw150 nb-picker"
									<c:if test="${not empty i.notifiedBody}">
										 data-json="${i.notifiedBody.id}##${i.notifiedBody.noCode}"
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
				<input type="hidden" name="id" value="${i.id}">
				<input type="hidden" name="auditor" value="${model.auditor.id}">
				</form>
		</div>
	</div>
</c:forEach>
<div class="hbox"><h2><spring:message code="qsAuditor.scope" /></h2></div>
<textarea class="qs-code-scope">${scope}</textarea>
		