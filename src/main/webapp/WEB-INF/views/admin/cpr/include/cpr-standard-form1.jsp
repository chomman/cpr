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
	     </span>
	 </p>
	 
	 <p>
	 	<label class="tt" title="<spring:message code="cpr.standard.validity.to.descr" />">
	 		<spring:message code="cpr.standard.validity.to" />
	 	</label>
	     <span class="field">  
	     	<form:input path="stopValidity" maxlength="25" cssClass="date"  />
	     </span>
	 </p>
	 <c:if test="${not empty standard.replaceStandard}">
	 	<script type="text/javascript">
			$(document).ready(function() {
				selectStandard('${standard.replaceStandard.standardId}', ${standard.replaceStandard.id});
			});
		</script>
  
	 </c:if>
	 <p>
	 	<label>
	 		<spring:message code="cpr.standard.status" />
	 	</label>
	     <span class="field standard-picker">  
	     	<form:select path="standardStatus" cssClass="chosenSmall">
	     		<c:forEach items="${model.standardStatuses}" var="i">
                       <option value="${i}" <c:if test="${i ==  standard.standardStatus}">selected="selected"</c:if> >
                       		<spring:message code="${i.name}" />
                       </option>
               </c:forEach>
	     	</form:select>
	     	<span id="standard-replaced-label" class="resetmargin"></span>	
	     	<input type="text" id="standardPicker" />	
	     	<form:hidden path="replaceStandard" id="pickerVal" />
	     </span>
	 </p>
	 
	 
	 
	<!-- 	
	<script type="text/javascript">
	$(document).ready(function() {
	    //$("#tagsField").tagit({fieldName: "tags", allowSpaces :true});
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
    -->
	                
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
 
 