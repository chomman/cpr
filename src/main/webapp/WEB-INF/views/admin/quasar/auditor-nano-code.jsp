<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:set value="" var="scope" />
<c:forEach items="${model.codes}" var="i"> 
	<c:set value="${model.function.areAllRequirementsValid and i.grantedForProductAssessorA}" var="isGranted" />
	<div class="qs-item radius ${isGranted and i.granted ? 'qs-valid' : 'qs-invalid'}">
		<div class="qs-code-wrapp">
				<form method="post" id="f${i.id}">
				<div class="qs-name">
					<div class="qs-inline-edit" data-id="${i.id}">
						<spring:message code="quasar.edit" />
					</div>
					<div class="reason-detail ${empty i.productAssessorAReasonDetails ? 'qs-field' : ''}">
						<span class="val h">
							<c:if test="${not empty i.productAssessorAReasonDetails}">
								<h5>Reason details</h5>
								<span>${i.productAssessorAReasonDetails}</span>
							</c:if>
						</span>
						<div class="qs-field">
							<h5>Reason details</h5>
							<textarea name="productAssessorAReasonDetails" rows="5" cols="5" placeholder="Describe reason...">${i.reasonOfRefusal}</textarea>
						</div>
					</div>
					<div class="qs-code">
						<div class="eac">${i.nandoCode.code}</div>
					</div>
					<div class="is-granted ${isGranted ? 'qs-valid' : 'qs-invalid'}">
						<c:if test="${isGranted}">
							<spring:message code="granted" />
							<c:set var="scope" value="${scope}(${i.nandoCode.code}), " />
						</c:if>
						<c:if test="${not isGranted}">
							<spring:message code="refused" />
						</c:if>
					</div>
					<h3>${i.nandoCode.name}</h3>
					<table>
						<tr>
							<td class="k"><spring:message code="categoryTraining" />:</td>
							<td class="v">
								<span class="val h">${i.productAssessorATraining}</span>
								<span class="qs-field">
									<input type="text" value="${i.productAssessorATraining}" name="productAssessorATraining" class="w30 c required numeric">
								</span>
							</td>
						</tr>
						<tr>
							<td class="k"><spring:message code="noOfNbAudits" />:</td>
							<td class="v">
								<span class="val h">${i.numberOfNbAudits}</span>
								<span class="qs-field">
									<input type="text" value="${i.numberOfNbAudits}" name="numberOfNbAudits" class="w30 c required numeric">
								</span>
							</td>
						</tr>
						<tr>
							<td class="k"><spring:message code="noOfIsoAudits" />:</td>
							<td class="v">
								<span class="val h">${i.numberOfIso13485Audits}</span>
								<span class="qs-field">
									<input type="text" value="${i.numberOfIso13485Audits}" name="numberOfIso13485Audits" class="w30 c required numeric">
								</span>
							</td>
						</tr>
						<tr class="${not i.productAssessorARefused ? 'qs-hide' : ''}">
							<td class="k"><spring:message code="isRefused" />:</td>
							<td class="v">
								<span class="val h">
									<c:if test="${i.productAssessorARefused}">
										<strong><spring:message code="yes" /></strong>
									</c:if>
									<c:if test="${not i.productAssessorARefused}">
										<spring:message code="no" />
									</c:if>
								</span>
								<span class="qs-field">
									<input type="checkbox" class="ch-refused" name="productAssessorARefused" ${i.productAssessorARefused ? 'checked="checked"' : ''}  />
								</span>
							</td>
						</tr>
						
						<tr class="${not i.productAssessorAApproved ? 'qs-hide' : ''}">
							<td class="k"><spring:message code="specificReasonForTheApproval" />:</td>
							<td class="v">
								<span class="val h">
									<c:if test="${i.productAssessorAApproved}">
										<strong><spring:message code="yes" /></strong>
									</c:if>
									<c:if test="${not i.productAssessorAApproved}">
										<spring:message code="no" />
									</c:if>
								</span>
								<span class="qs-field">
									<input type="checkbox" class="ch-approved" name="productAssessorAApproved" ${i.productAssessorAApproved ? 'checked="checked"' : ''}  />
								</span>
							</td>
						</tr>
						
						<tr class="${empty i.productAssessorAApprovedBy ? 'qs-hide' : ''}">
							<td class="k"><spring:message code="categoryApprovalByAnotherNb" />:</td>
							<td class="v h">
								<span class="val h">
									<c:if test="${not empty i.productAssessorAApprovedBy}">
									<a:adminurl href="/cpr/notifiedbodies/edit/${i.productAssessorAApprovedBy.id}">
										<strong>${i.productAssessorAApprovedBy.noCode}</strong>	
									</a:adminurl>
									</c:if>
									<c:if test="${empty i.productAssessorAApprovedBy}">
										<span class="val">
											<spring:message code="no" />
											</span>
										</c:if>
								</span>
								<span class="qs-field">
								
									<input name="productAssessorAApprovedBy" type="text" class="mw150"
									<c:if test="${not empty i.productAssessorAApprovedBy}">
										 data-json="${i.productAssessorAApprovedBy.id}##${i.productAssessorAApprovedBy.noCode}"
									</c:if>
									 />
									 <a class="lang mandate-add-btn">
										<spring:message code="form.save" />
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