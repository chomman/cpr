<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages.add" /></title>
	
	<script>
	$(function() {
		$('select').on("change", updateInfo);
		updateInfo();
	});
	function updateInfo(){
		var $this = $('form select option:selected');
		$('.mini-info').html($this.attr('title'));
		return false;
	}
	</script>
</head>
<body>
	<div id="wrapper">
	<div class="pj-webpages">
		<h1><spring:message code="webpages.add" /></h1>

		<div id="content">
			<form:form modelAttribute="webpage" cssClass="valid add-webpage">
				
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
			
				 <p class="form-head">
				 	<spring:message code="webpage.addingInto" />: 
				 	<b>
					 	<c:if test="${empty model.parentWebpage}">
					 		<spring:message code="webpage.mainmenu" />
					 	</c:if>
					 	<c:if test="${not empty model.parentWebpage}">
					 		<a:webpageValue object="${model.parentWebpage}" fieldName="name" />
					 	</c:if>
				 	</b>
				 </p>
				 <p>
                	<label>
                		<strong><em class="red">*</em>
                			Název položky menu:
                		</strong>	
                	</label>
	                <span class="field">  
					     <form:input path="localized['cs'].name" cssClass="mw300 required" maxlength="200" />
					 </span>
				 </p>
				 
				 <p>
                	<label>
                		<strong><em class="red">*</em>
                			Typ sekcie:
                		</strong>	
                	</label>
                	<span class="field">  
		                <form:select path="webpageType" cssClass="chosenSmall  required">
		                	<c:forEach items="${model.webpageTypes}" var="i">
		                		<option value="${i}" data-id="${i.id}" title="<spring:message code="${i.description}" />">
		                			<spring:message code="${i.code}" />
		                		</option>
		                	</c:forEach>
		                </form:select>
		                <span class="mini-info inline"></span>
	                 </span>
				 </p>
				 
				 <p class="button-box">
                	 <input type="submit" class="button radius default" value="<spring:message code="form.saveandcontinue" />" />
                </p>
				 
			</form:form>	
	
								
			
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>