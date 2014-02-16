<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
	
	<form:form htmlEscape="true" commandName="standard" method="post" action="${formUrl}"  cssClass="valid" >					
	
	<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
	
	<p class="form-head"><spring:message code="csn.basic.info" /></p>
	
	<c:if test="${not empty updatedStandard}">
		<p class="msg info">
			<spring:message code="cpr.csn.referencedStandard" />
			<strong>
				<a href="<c:url value="/admin/cpr/standard/edit/${updatedStandard.id}" />">
					${updatedStandard.standardId}
				</a>
			</strong>
			
			<c:if test="${not empty updatedStandard.standardStatus }">
				(<spring:message code="${updatedStandard.standardStatus.name}" />)
			</c:if>
			
		</p>
	</c:if>
	
	<c:if test="${not empty successCreate}">
		<p class="msg ok"><spring:message code="success.create" /></p>
	</c:if>
	<p>
	 	<label>
	 		<strong><em class="red">*</em>
	 			<spring:message code="cpr.standard.id" />:
	 		</strong>
	 	</label>
	     <span class="field">
	     	<form:input path="standardId"  cssClass="w200 required" maxlength="45" />
	     </span>
	 </p>
	 <p>
	 	<label>
	 		<em class="red">*</em>
	 		<spring:message code="cpr.standard.name" />:
	 	</label>
	     <span class="field">
	     	<form:input path="czechName" cssClass="mw500 required" maxlength="255" />
	     </span>
	 </p>
	 <p>
	 	<label>
	 		<spring:message code="cpr.standard.englishName" />:
	 	</label>
	     <span class="field">
	     	<form:input path="englishName" cssClass="mw500" maxlength="255" />
	     </span>
	 </p>
	 
	 <p>
	 	<label>
	 		<spring:message code="cpr.standard.validity.from" />:
	 	</label>
	     <span class="field">  
	     	<form:input path="startValidity" maxlength="25" cssClass="date"  />
	     </span>
	 </p>
	 
	 <p>
	 	<label class="tt" title="<spring:message code="cpr.standard.validity.to.descr" />">
	 		<spring:message code="cpr.standard.validity.to" />:
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
	 <p <c:if test="${not empty add}">class="hidden"</c:if> >
	 	<label>
	 		<spring:message code="cpr.standard.status" />:
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
	 <p>
	 	<label>
	 		<spring:message code="standardStatus.date" />:
	 	</label>
	     <span class="field">  
	     	<form:input path="statusDate" maxlength="25" cssClass="date"  />
	     </span>
	 </p>  
	  <p>
	 	<label>
	 		<spring:message code="cpr.standard.releaseDate" />:
	 	</label>
	     <span class="field">  
	     	<form:input path="released" maxlength="25" cssClass="date-month"  /><span>formát: <strong>MM.RRRR</strong></span>
	     </span>
	 </p>        
	<p <c:if test="${not empty add}">class="hidden"</c:if> >
	    <label title="<spring:message code="publish.descr" />" class="tt">
	 		<spring:message code="publish" />:
	 	</label>
	     <span class="field">  
	     	<form:checkbox path="enabled" />
	     </span>
	 </p>
	
	 <form:hidden path="id"  />
	 <form:hidden path="timestamp"/>
	 <p class="button-box">
	 	 <input type="submit" class="button default" value="<spring:message code="form.save" />" />
	 	 <c:if test="${not empty add}">
	 	 	<span class="mini-info"><spring:message code="form.moreinfo" /></span>
	 	 </c:if>
	 </p>
 </form:form>
 
 