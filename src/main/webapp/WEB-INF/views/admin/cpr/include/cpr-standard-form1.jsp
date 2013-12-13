<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
	
	<form:form htmlEscape="true" commandName="standard" method="post" action="${formUrl}"  cssClass="valid" >					
	
	<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
	
	<p class="form-head"><spring:message code="csn.basic.info" /></p>
	
	
	<c:if test="${not empty successCreate}">
		<p class="msg ok"><spring:message code="success.create" /></p>
	</c:if>
	<p>
	 	<label>
	 		<strong><em class="red">*</em>
	 			<spring:message code="cpr.standard.id" />
	 		</strong>
	 	</label>
	     <span class="field">
	     	<form:input path="standardId"  cssClass="w200 required" maxlength="45" />
	     </span>
	 </p>
	 <p>
	 	<label>
	 		<spring:message code="cpr.standard.replacedStandardId" />
	 	</label>
	     <span class="field">
	     	<form:input  path="replacedStandardId" cssClass="w200" maxlength="45" />
	     </span>
	 </p>
	 <p>
	 	<label>
	 		<em class="red">*</em>
	 		<spring:message code="cpr.standard.name" />
	 	</label>
	     <span class="field">
	     	<form:input path="czechName" cssClass="mw500 required" maxlength="255" />
	     </span>
	 </p>
	 <p>
	 	<label>
	 		<spring:message code="cpr.standard.englishName" />
	 	</label>
	     <span class="field">
	     	<form:input path="englishName" cssClass="mw500" maxlength="255" />
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
	 
	 	
	<script type="text/javascript">
	$(document).ready(function() {
	    $("#tagsField").tagit({fieldName: "tags", allowSpaces :true});
	});
	</script>
    <p>
   	<label class="tt">
   		<spring:message code="cpr.standard.tags" />
   		<small><spring:message code="cpr.standard.tags.descr" /></small>
   	</label>
       <div class="field relative">  
       	<ul id="tagsField">
			    <c:forEach items="${standard.tags}" var="tag">
					<li>${tag.name}</li>
				 </c:forEach>
		</ul>
		<div class="clear"></div>
	  </div>
	</p>
	                
	<p>
	    <label title="<spring:message code="publish.descr" />" class="tt">
	 		<spring:message code="publish" />
	 	</label>
	     <span class="field">  
	     	<form:checkbox path="enabled" />
	     </span>
	 </p>
	
	 <form:hidden path="id"  />
	 <form:hidden path="timestamp"/>
	 <p class="button-box">
	 	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
	 </p>
 </form:form>
 
 