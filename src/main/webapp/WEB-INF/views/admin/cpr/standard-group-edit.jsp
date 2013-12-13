<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="cpr.groups.edit" /></title>
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
			 <a href="<c:url value="/admin/cpr/groups" />"><spring:message code="cpr.groups.title" /></a> &raquo;
			 <span><spring:message code="cpr.groups.edit" /></span>
		</div>
		<h1><spring:message code="cpr.groups.edit" /></h1>

		<div id="content">
			
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
			
			
			<c:if test="${empty notFoundError}">
					
					
					<!-- SUB NAV -->
					<ul class="sub-nav">
						<li><a href="<c:url value="/admin/cpr/groups"  />"><spring:message code="cpr.groups.view" /></a></li>
						<li><a class="active" href="<c:url value="/admin/cpr/groups/edit/0"  />"><spring:message code="cpr.groups.add" /></a></li>
					</ul>
				
					
					<!-- AUTHOR/EDITOR INFOR -->
					<c:if test="${not empty standardGroup.createdBy}">
						<table class="info">
							<tr>
								<td class="key"><spring:message code="meta.created" /></td>
								<td class="val">${standardGroup.createdBy.firstName} ${standardGroup.createdBy.lastName}</td>
								<td class="val"><joda:format value="${standardGroup.created}" pattern="${common.dateTimeFormat}"/></td>
							</tr>
							<c:if test="${not empty standardGroup.changedBy}">
							<tr>
								<td class="key"><spring:message code="meta.edited" /></td>
								<td class="val">${standardGroup.changedBy.firstName} ${standardGroup.changedBy.lastName}</td>
								<td class="val"><joda:format value="${standardGroup.changed}" pattern="${common.dateTimeFormat}"/></td>
							</tr>
							</c:if>
						</table>
					</c:if>
					
					
					<!-- FROM  -->
					<c:url value="/admin/cpr/groups/edit/${standardGroupId}" var="formUrl"/>
					<form:form commandName="standardGroup" method="post" action="${formUrl}"  >
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
						
						<p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="cpr.group.czechName" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input htmlEscape="true" path="czechName" maxlength="255" cssClass="mw500" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<spring:message code="cpr.group.englishName" />
                        	</label>
                            <span class="field">
                            	<form:input htmlEscape="true" path="englishName" maxlength="255" cssClass="mw500" />
                            </span>
                        </p>
                        
                        <p>
                        	<label>
                        		<spring:message code="cpr.group.code" />                       		
                        	</label>
                            <span class="field">
                            	<form:input htmlEscape="true" path="code" cssClass="w100" maxlength="15" />
                            </span>
                        </p>
                        
                        <p>
                        	<label>
                        		<spring:message code="cpr.commisiondecision.name" />                       		
                        	</label>
                            <span class="field">
                            	<form:select path="commissionDecision" cssClass="maxw600 chosen" >
                            		<option value="" ><spring:message code="form.select" /></option>
                            		<c:forEach items="${model.commissionDecisions}" var="i">
                            			<option 
	                            			<c:if test="${standardGroup.commissionDecision.id == i.id}">selected="selected"</c:if>
	                            			value="${i.id}">${i.czechLabel}<c:if test="${not empty i.englishLabel}">/${i.englishLabel}</c:if>
                            			</option>
                            		</c:forEach>
                            	</form:select>
                            </span>
                        </p>
                        
                        <p>
							<label title="<spring:message code="publish.descr" />" class="tt">
						 		<spring:message code="publish" />
						 	</label>
							<span class="field">  
						     	<form:checkbox path="enabled" />
						    </span>
						 </p>
	
	                      
                        <form:hidden path="id" />
                        <p class="button-box">
                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                        </p>
					</form:form>
			</c:if>
			
			<!--  MANDATY -->
			<c:if test="${standardGroupId != 0}">
				
				<!-- assigned MANDATES -->
				<c:if test="${not empty standardGroup.standardGroupMandates}">
					<p class="form-head"><spring:message code="cpr.group.assignedMandates" /></p>
					<table class="data">
						<c:forEach items="${standardGroup.standardGroupMandates}" var="i">
							<tr>
								<td>
									<span 	<c:if test="${i.complement}">class="is-complement"</c:if>
											<c:if test="${not i.complement}">class="is-not-complement"</c:if> >
										${i.mandate.mandateName} 
										<c:if test="${i.complement}">
											<em>(<spring:message code="cpr.group.complement" />)</em>
										</c:if>
									</span>
								</td>
								<td class="delete big">
									<a class="confirmUnassignment"  href="${formUrl}/mandate/delete/${i.id}">
						 				<spring:message code="cpr.group.unassigment" />
						 			</a>
								</td>
							</tr>
						</c:forEach>
					</table>
					
				</c:if>
				
				
				<!-- assign new MANDATE -->
				<div>
					<p class="form-head"><spring:message code="cpr.group.mandates" /></p>
					<form:form cssClass="inline-form" commandName="standardGroupMandate" method="post" action="${formUrl}/mandate/add"  >
						<div class="inline-field">
							<form:select path="mandate" cssClass="chosenSmall">
								<option value="" ><spring:message code="form.select" /></option>
								<form:options items="${model.mandates}" itemLabel="mandateName" itemValue="id" />
							</form:select>
						</div>
						<div class="inline-field">
							<form:label path="complement">
								<spring:message code="cpr.group.complement" />:
							</form:label>
							<form:checkbox path="complement" />
						</div>
						<input type="submit" class="lang mandate-add-btn" value="<spring:message code="cpr.group.add" />" />
					</form:form>
				</div>
			</c:if>
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>