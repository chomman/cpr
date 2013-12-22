<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.mandates.edit" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr" />"><spring:message code="menu.cpr" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr/mandates" />"><spring:message code="menu.cpr.mandates" /></a> &raquo;
			 <span><spring:message code="cpr.mandates.edit" /></span>
		</div>
		<h1><spring:message code="cpr.mandates.edit" /></h1>

		<div id="content">
			
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
			
			
			<c:if test="${empty notFoundError}">
					
					
				<ul class="sub-nav">
					<li><a href="<c:url value="/admin/cpr/mandates"  />">
							<spring:message code="cpr.mandates.view" />
						</a>
					</li>
					<li>
						<a class="active" href="<c:url value="/admin/cpr/mandates/edit/0"  />">
							<spring:message code="cpr.mandates.add" />
						</a>
					</li>
				</ul>
				
					<c:if test="${not empty mandate.createdBy}">
						<table class="info">
							<tr>
								<td class="key"><spring:message code="meta.created" /></td>
								<td class="val">${mandate.createdBy.firstName} ${mandate.createdBy.lastName}</td>
								<td class="val"><joda:format value="${mandate.created}" pattern="${common.dateTimeFormat}"/></td>
							</tr>
							<c:if test="${not empty mandate.changedBy}">
							<tr>
								<td class="key"><spring:message code="meta.edited" /></td>
								<td class="val">${mandate.changedBy.firstName} ${mandate.changedBy.lastName}</td>
								<td class="val"><joda:format value="${mandate.changed}" pattern="${common.dateTimeFormat}"/></td>
							</tr>
							</c:if>
						</table>
					</c:if>
					<c:url value="/admin/cpr/mandates/edit/${mandateId}" var="formUrl"/>
					
					<form:form commandName="mandate" method="post" action="${formUrl}" cssClass="valid" >
						
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
						
						<p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="cpr.mandates.name" />                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input  htmlEscape="true" path="mandateName" maxlength="25" cssClass="required" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<spring:message code="cpr.mandates.url"  />
                        	</label>
                            <span class="field">
                            	<form:input  htmlEscape="true" path="mandateFileUrl" cssClass="mw500 more7" maxlength="255" />
                            </span>
                        </p>
                       
                       
                        <form:hidden path="id" />
                        <p class="button-box">
                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                        </p>
					</form:form>
					
					
					<!--  MANDATE CHANGES -->
					<c:if test="${mandateId != 0}">
							
							<!-- assigned MANDATES -->
							<c:if test="${not empty mandate.changes}">
								<p class="form-head"><spring:message code="cpr.mandates.changes.assigned" /></p>
								<table class="data">
									<c:forEach items="${mandate.changes}" var="i">
										<tr>
											<td>${i.mandateName}</td>
											<td class="delete big">
												<a class="confirmUnassignment"  href="${formUrl}/change/delete/${i.id}">
									 				<spring:message code="cpr.group.unassigment" />
									 			</a>
											</td>
										</tr>
									</c:forEach>
								</table>
								
							</c:if>
							
							
							<!-- assign new MANDATE -->
							<div>
								<p class="form-head"><spring:message code="cpr.mandates.changes" /></p>
								<form:form cssClass="inline-form" commandName="mandateForm" method="post" action="${formUrl}/change/add"  >
									<div class="inline-field">
										<form:select path="mandate" cssClass="chosenSmall">
											<option value="" ><spring:message code="form.select" /></option>
											<form:options items="${model.mandates}" itemLabel="mandateName" itemValue="id" />
										</form:select>
									</div>
									<input type="submit" class="lang mandate-add-btn" value="<spring:message code="cpr.mandates.changes.add" />" />
								</form:form>
							</div>
					</c:if>
			</c:if>
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>