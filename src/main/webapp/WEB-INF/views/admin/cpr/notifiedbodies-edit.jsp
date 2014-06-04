<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="cpr.nb.edit" /></title>
		<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
		<script src="<c:url value="/resources/admin/js/wisiwig.init.js" />"></script>
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
			 <a href="<c:url value="/admin/cpr/notifiebodies" />"><spring:message code="cpr.nb" /></a> &raquo;
			 <span><spring:message code="cpr.nb.edit" /></span>
		</div>
		<h1><spring:message code="cpr.nb.edit" /></h1>

		<div id="content">
			
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
			
			
		<c:if test="${empty notFoundError}">
					
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/cpr/notifiedbodies"  />"><spring:message code="cpr.nb.view" /></a></li>
				<li><a class="active" href="<c:url value="/admin/cpr/notifiedbodies/edit/0"  />"><spring:message code="cpr.nb.add" /></a></li>
			</ul>
				
			<c:if test="${not empty notifiedBody.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${notifiedBody.createdBy.firstName} ${notifiedBody.createdBy.lastName}</td>
						<td class="val"><joda:format value="${notifiedBody.created}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					<c:if test="${not empty notifiedBody.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${notifiedBody.changedBy.firstName} ${notifiedBody.changedBy.lastName}</td>
						<td class="val"><joda:format value="${notifiedBody.changed}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<script type="text/javascript"> 
				$(function() { initWISIWIG("580", "250"); });
			</script>
			<c:url value="/admin/cpr/notifiedbodies/edit/${notifiedBodyId}" var="formUrl"/>
			<form:form commandName="notifiedBody" method="post" action="${formUrl}" cssClass="valid"  >
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="cpr.nb.name" />:
                      		</strong>
                      	</label>
                          <span class="field">
                          	<form:input htmlEscape="true" path="name" maxlength="255" cssClass="mw500  required" />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="cpr.nb.code" />:
                      		</strong>
                      	</label>
                          <span class="field">
                          	<form:input  htmlEscape="true" path="noCode" maxlength="25" cssClass="w200  required" />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<spring:message code="cpr.nb.aoCode" />:
                      	</label>
                          <span class="field">
                          	<form:input  htmlEscape="true" path="aoCode" maxlength="25" cssClass="w200  " />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<spring:message code="cpr.nb.nandoCode" />:
                      	</label>
                          <span class="field">
                          	<form:input  htmlEscape="true" path="nandoCode" cssClass="w200  " />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<spring:message code="cpr.nb.eta" />:
                      	</label>
                          <span class="field">
                          	<form:checkbox path="etaCertificationAllowed"/>
                          </span>
                      </p>
                       <p>
					    <label title="<spring:message code="publish.descr" />" class="tt">
					 		<spring:message code="publish" />:
					 	</label>
					     <span class="field">  
					     	<form:checkbox path="enabled" />
					     </span>
					 </p>
                      <p class="form-head">Kontaktní informace<p>:
                      <p>
                      	<label>
                      		<spring:message code="form.phone" />
                      	</label>
                          <span class="field">  
                          	<form:input  htmlEscape="true" path="phone"  maxlength="20"  cssClass="w200 more7" />
                          </span>
                      </p>
                      
                      <p>
                      	<label>
                      		<spring:message code="form.fax" />:
                      	</label>
                          <span class="field">  
                          	<form:input  htmlEscape="true" path="fax"  maxlength="20"  cssClass="w200 more7" />
                          </span>
                      </p>
                      
                      <p>
                      	<label>
                      		<spring:message code="form.email" />:
                      	</label>
                          <span class="field">  
                          	<form:input  htmlEscape="true" path="email"  maxlength="45"  cssClass="w200 email" />
                          </span>
                      </p>
                      
                      <p>
                      	<label>
                      		<spring:message code="form.web" />:
                      	</label>
                          <span class="field">  
                          	<form:input htmlEscape="true"  path="webpage"  maxlength="50"  cssClass="w200 more7" />
                          </span>
                      </p>
                      <p class="form-head">Adresa<p>
                       <p>
                      	<label>
                      		<spring:message code="address.city" />:
                      	</label>
                          <span class="field">  
                          	<form:input htmlEscape="true"  path="city"  maxlength="50"  cssClass="w200" />
                          </span>
                      </p>
                       <p>
                      	<label>
                      		<spring:message code="address.street" /> a <spring:message code="address.zip" />
                      	</label>
                          <span class="field">  
                          	<form:input htmlEscape="true" path="street"  maxlength="100" cssClass="w300" /> 
                          	<form:input htmlEscape="true" path="zip"  maxlength="6" cssClass="w100" />
                          </span>
                      </p>
                       
                      
                      <p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="address.country" />:
                      		</strong>
                      	</label>
                          <span class="field">  
                          	 <form:select path="country" cssClass="mw300 required">
			                      <option value=""><spring:message code="form.select"/> </option>
			                      <form:options items="${model.countries}" itemValue="id" itemLabel="countryName" />
			                 </form:select>
                          </span>
                      </p>
					  <p class="form-head"><spring:message code="cpr.nb.description" /><p>
					  <p>
                      	<label>
                      		<spring:message code="cpr.nb.description" />:
                      	</label>
                          <span class="field">  
                          	<form:textarea path="description"  cssClass="wisiwig defaultSize" />
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