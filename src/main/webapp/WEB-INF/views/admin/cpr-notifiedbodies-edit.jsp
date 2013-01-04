<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.nb.edit" /></title>
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
				<li><a class="active" href="<c:url value="/admin/cpr/notifiedbodies"  />"><spring:message code="cpr.nb.view" /></a></li>
				<li><a href="<c:url value="/admin/cpr/notifiedbodies/edit/0"  />"><spring:message code="cpr.nb.add" /></a></li>
			</ul>
				
			<c:if test="${not empty notifiedBody.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${notifiedBody.createdBy.firstName} ${notifiedBody.createdBy.lastName}</td>
						<td class="val"><joda:format value="${notifiedBody.created}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					<c:if test="${not empty notifiedBody.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${notifiedBody.changedBy.firstName} ${notifiedBody.changedBy.lastName}</td>
						<td class="val"><joda:format value="${notifiedBody.changed}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			<c:url value="/admin/cpr/notifiedbodies/edit/${notifiedBodyId}" var="formUrl"/>
			
			<form:form commandName="notifiedBody" method="post" action="${formUrl}"  >
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<p>
                      	<label>
                      		<strong><em class="red">*</em>
                      			<spring:message code="cpr.nb.name" />
                      		</strong>
                      	</label>
                          <span class="field">
                          	<form:input path="name" maxlength="255" cssClass="mw500" />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<spring:message code="cpr.nb.code" />
                      	</label>
                          <span class="field">
                          	<form:input path="notifiedBodyCode" maxlength="25" cssClass="w200" />
                          </span>
                      </p>
                      <p>
                      	<label>
                      		<spring:message code="cpr.nb.eta" />
                      	</label>
                          <span class="field">
                          	<form:checkbox path="etaCertificationAllowed"/>
                          </span>
                      </p>
                      <p class="form-head">Kontaktní informace<p>
                      <p>
                      	<label>
                      		<spring:message code="form.phone" />
                      	</label>
                          <span class="field">  
                          	<form:input path="phone"  maxlength="20"  cssClass="w200" />
                          </span>
                      </p>
                      
                      <p>
                      	<label>
                      		<spring:message code="form.fax" />
                      	</label>
                          <span class="field">  
                          	<form:input path="fax"  maxlength="20"  cssClass="w200" />
                          </span>
                      </p>
                      
                      <p>
                      	<label>
                      		<spring:message code="form.email" />
                      	</label>
                          <span class="field">  
                          	<form:input path="email"  maxlength="45"  cssClass="w200" />
                          </span>
                      </p>
                      
                      <p>
                      	<label>
                      		<spring:message code="form.web" />
                      	</label>
                          <span class="field">  
                          	<form:input path="webpage"  maxlength="50"  cssClass="w200" />
                          </span>
                      </p>
                      <p class="form-head">Adresa<p>
                       <p>
                      	<label>
                      		<spring:message code="address.city" />
                      	</label>
                          <span class="field">  
                          	<form:input path="address.city"  maxlength="50"  cssClass="w200" />
                          </span>
                      </p>
                       <p>
                      	<label>
                      		<spring:message code="address.street" /> a <spring:message code="address.zip" />
                      	</label>
                          <span class="field">  
                          	<form:input path="address.street"  maxlength="100" cssClass="w300" /> 
                          	<form:input path="address.zip"  maxlength="6" cssClass="w100" />
                          </span>
                      </p>
                       
                      
                      <p>
                      	<label>
                      		<spring:message code="address.country" />
                      	</label>
                          <span class="field">  
                          	 <form:select path="address.country" cssClass="mw300">
			                      <option value=""><spring:message code="form.select"/> </option>
			                      <c:forEach items="${model.countries}" var="country">
			                		<option value="${country.id}" <c:if test="${address.country.id == country.id}">selected="selected"</c:if>>${country.countryName}</option>
			                	</c:forEach>
			                 </form:select>
                          </span>
                      </p>
					  <p class="form-head"><spring:message code="cpr.nb.description" /><p>
					  <p>
                      	<label>
                      		<spring:message code="cpr.nb.description" />
                      	</label>
                          <span class="field">  
                          	<form:textarea path="description"  cssClass="mceEditor defaultSize" />
                          </span>
                      </p>
					  
                      <form:hidden path="id" />
                      <p class="button-box">
                      	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                      </p>
			</form:form>
	</c:if>
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>