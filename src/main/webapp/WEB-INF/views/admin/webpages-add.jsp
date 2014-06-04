<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages.add" /></title>
	
	<script>
	$(function() {
		$('select').selectTip();
	});
	</script>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/webpages.css" />" />
	<script src="<c:url value="/resources/admin/js/jquery.selectTip.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div class="pj-webpages">
		<div id="content">
			
			<div id="breadcrumb">
				<a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				<a:adminurl href="/webpages"><spring:message code="webpages" /></a:adminurl>  &raquo;
				 <span>
				 <spring:message code="webpages.add" />
				 </span>
			</div>
			<div class="pj-nav margin-bottom">
				<span class="pj-nav-label"><spring:message code="webpages" /></span>
				<span class="pj-nav-label2"><spring:message code="options" />:</span>
				<a:adminurl href="/webpages" cssClass="btn-webpage radius link-ico" >
				<spring:message code="webpages.view" /> <span class="ico set"></span>
			</a:adminurl>
			</div>
			
			<form:form modelAttribute="webpage" cssClass="valid add-webpage">
				
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
			
				 <p class="form-head">
				 	<spring:message code="webpage.addingInto" />: 
				 	<b>
					 	<c:if test="${empty model.parentWebpage}">
					 		<spring:message code="webpage.mainmenu" />
					 	</c:if>
					 	<c:if test="${not empty model.parentWebpage}">
					 		${model.parentWebpage.defaultName}
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
					     <form:input path="localized['cs'].name" cssClass="mw500 required" maxlength="200" />
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
	
								
			
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>