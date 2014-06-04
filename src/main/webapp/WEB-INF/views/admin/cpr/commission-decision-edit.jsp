<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="cpr.br.edit" /></title>
	</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		
		<!-- BREADCRUMB -->
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr" />"><spring:message code="menu.cpr" /></a> &raquo;
			 <a href="<c:url value="${model.url}" />"><spring:message code="cpr.commisiondecision.name" /></a> &raquo;
			 <span>
			 <c:if test="${ empty model.id or  model.id == 0}">
			 	<spring:message code="cpr.commisiondecision.create" />
			 </c:if>
			  <c:if test="${not empty model.id and  model.id != 0}">
			 	<spring:message code="cpr.commisiondecision.edit" />
			 </c:if>
			 </span>
		</div>
		
		
		<!-- HEAD TITLE -->
		<h1>
		 	<c:if test="${ empty model.id or model.id == 0}">
				<spring:message code="cpr.commisiondecision.create" />
			</c:if>
			<c:if test="${not empty model.id and model.id != 0}">
				<spring:message code="cpr.commisiondecision.edit" />
			</c:if>
		</h1>

		<div id="content">
			
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
			
			
		<c:if test="${empty notFoundError}">
					
			<ul class="sub-nav">
				<li><a href="<c:url value="${model.url}"  />"><spring:message code="cpr.commisiondecision.view" /></a></li>
				<li><a class="active"  href="<c:url value="${model.url}/edit/0"  />"><spring:message code="cpr.commisiondecision.create" /></a></li>
			</ul>
				
			<c:if test="${not empty commissionDecision.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${commissionDecision.createdBy.firstName} ${commissionDecision.createdBy.lastName}</td>
						<td class="val"><joda:format value="${commissionDecision.created}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					<c:if test="${not empty commissionDecision.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${commissionDecision.changedBy.firstName} ${commissionDecision.changedBy.lastName}</td>
						<td class="val"><joda:format value="${commissionDecision.changed}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<c:url value="${model.url}/edit/${model.id}" var="formUrl"/>
			<form:form modelAttribute="commissionDecision" method="post" action="${formUrl}"  >
				
				<c:if test="${not empty success}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<p class="form-head"><spring:message code="cpr.commisiondecision.cz" /></p>
				<p>
                   	<label>
                   		<strong><em class="red">*</em>
                   			<spring:message code="cpr.commisiondecision.label" />
                   		</strong>
                   	</label>
                       <span class="field">
                       	<form:input htmlEscape="true" path="czechLabel" maxlength="150" cssClass="w100" />
                      </span>
                </p>
                <p>
                   	<label>
                   		<spring:message code="cpr.commisiondecision.url" />
                   	</label>
                       <span class="field">
                       	<form:input htmlEscape="true" path="czechFileUrl" maxlength="150" cssClass="mw600" />
                      </span>
                </p>
               <p class="form-head"><spring:message code="cpr.commisiondecision.en" /></p>
				<p>
                   	<label>
                   		<spring:message code="cpr.commisiondecision.label" />
                   	</label>
                       <span class="field">
                       	<form:input htmlEscape="true" path="englishLabel" maxlength="150" cssClass="w100" />
                      </span>
                </p>
                <p>
                   	<label>
                   		<spring:message code="cpr.commisiondecision.url" />
                   	</label>
                       <span class="field">
                       	<form:input htmlEscape="true" path="englishFileUrl" maxlength="150" cssClass="mw600" />
                      </span>
                </p>
                <p class="form-head"><spring:message code="csn.basic.info.other" /></p>
                <p>
                   	<label>
                   		<spring:message code="cpr.commisiondecision.draft" />   		
                   	</label>
                       <span class="field">
                       	<form:input htmlEscape="true" path="draftAmendmentUrl" maxlength="150" cssClass="mw600" />
                      </span>
                </p>
			  
                <form:hidden path="id" />
                <p class="button-box">
                	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                </p>
			</form:form>
	</c:if>
		
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>