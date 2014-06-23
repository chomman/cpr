<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${empty eacCode.id}">
			<spring:message code="eacCode.add" />
		</c:if>
		<c:if test="${not empty eacCode.id}">
			<spring:message code="eacCode.edit" />
		</c:if>
	</title>
	<script src="<c:url value="/resources/admin/js/jquery.bundledCheckbox.js" />"></script>
	<script type="text/javascript">
		$(function(){ $('input.bundled').bundledCheckbox(); });
	</script>
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
				 <a:adminurl href="/quasar/admin/eac-codes"><spring:message code="eacCodes" /></a:adminurl>  &raquo;
				 <span>
				 	<c:if test="${empty eacCode.id}">
						<spring:message code="eacCode.add" />
					</c:if>
					<c:if test="${not empty eacCode.id}">
						<spring:message code="eacCode.edit" />
					</c:if>
				 </span>
			</div>
			<h1>
				<c:if test="${empty eacCode.id}">
						<spring:message code="eacCode.add" />
				</c:if>
				<c:if test="${not empty eacCode.id}">
					<spring:message code="eacCode.edit" />
				</c:if>
			</h1>
	
			<div id="content">
				
				<jsp:include page="navs/eac-code-nav.jsp" />
				
				<form:form commandName="eacCode" method="post" cssClass="valid" >
							
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
                       
                       <p>
                      		<label>
                      			<spring:message code="eacCode.name" />:
                       		</label>
                           <span class="field">
                           	<form:textarea path="name" cssClass="mw500" />
                           </span>
                       </p>
						<p>
							<label>
								<spring:message code="eacCode.naceCode" />:
							</label>
							<span class="field">
								<form:input path="naceCode" cssClass="mw500" maxlength="100" />
							</span>
						</p>                            
                       <p class="form-head"><spring:message code="eacCode.scope" /></p>
                       <p>
                      		<label>
                      			<spring:message code="eacCode.qsAuditor" />:
                       	</label>
                           <span class="field"> 
                           	<form:checkbox path="forQsAuditor" cssClass="bundled"/>
                           </span>
                       </p>
                         <p class="forQsAuditor">
                      		<label>
                      			<strong><em class="red">*</em>
                       			<spring:message code="eacCode.threshold" />:
                       		</strong> 
                       	</label>
                           <span class="field">
                           	<form:input path="threshold" maxlength="3" cssClass="w50 required" />
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