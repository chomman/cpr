<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
	<c:url value="/admin/cpr/standard/edit/${standardId}" var="formUrl"/>
	<form:form htmlEscape="true" commandName="standardChange" method="post" action="${formUrl}/standard-change/${standardChange.id}"  cssClass="valid" >
	
		<p class="form-head">
			<c:if test="${standardChange.id == 0}">
				<spring:message code="cpr.standard.changes.adding" arguments="${standard.standardId}" />
			</c:if>
			<c:if test="${standardChange.id != 0}">
				<spring:message code="cpr.standard.changes.editing" arguments="${standard.standardId}" />
			</c:if>
		</p>
	
	<c:if test="${not empty successCreate}">
		<p class="msg ok"><spring:message code="success.create" /></p>
	</c:if>
	
	<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
	
	<p>
	 	<label>
	 		<strong><em class="red">*</em>
	 			<spring:message code="cpr.standard.changes.id" />
	 		</strong>
	 	</label>
	     <span class="field">
	     	<form:input path="changeCode"  cssClass="w200 required" />
	     </span>
	 </p>
	
	 <p>
	 	<label>
	 		<spring:message code="cpr.standard.validity.from" />
	 	</label>
	     <span class="field">  
	     	<form:input path="startValidity" maxlength="25" cssClass="date"  />
	     	<span class="sublabel">do</span>
	     	<form:input path="stopValidity" maxlength="25" cssClass="date"  />
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
	 	 
	  <p>
	 	<label>
	 		<spring:message code="dop.as.note" />
	 	</label>
	     <span class="field">  
	     	<form:textarea path="note" cssClass="mw500" />
	     </span>
	 </p>
	
	 <form:hidden path="id"  />
	 <p class="button-box">
	 	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
	 	 <a href="${formUrl}" class="button cancel"><spring:message code="dop.cancel" /></a>
	 </p>
 </form:form>
 
 