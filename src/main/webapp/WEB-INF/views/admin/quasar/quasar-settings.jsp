<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="quasarSettings" /></title>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
		
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
				 <span><spring:message code="quasarSettings" /></span>
			</div>
			<h1><spring:message code="quasarSettings" /></h1>
	
			<div id="content">
				
				<form:form commandName="quasarSettings" method="post" cssClass="valid" >
							
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
					</c:if>
					
					<p class="form-head"><spring:message code="baseInformations" /></p>
						<p>
                      		<label>
                      			<strong><em class="red">*</em>
                       			<spring:message code="eacCode.code" />:
                       		</strong>  
                       		<small>Pattern: EAC XX</small>
                       	</label>
                           <span class="field">
                           	<form:input path="code" maxlength="6" cssClass="w100 required" />
                           </span>
                       </p>
                         <p>
                      		<label>
                      			<spring:message code="isActivated" />:
                       	</label>
                           <span class="field"> 
                           	<form:checkbox path="enabled"/>
                           </span>
                       </p>
                                                 
                    <form:hidden path="id" />
                       <p class="button-box">
                       	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                       </p>
				</form:form>
				<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
			</div>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>