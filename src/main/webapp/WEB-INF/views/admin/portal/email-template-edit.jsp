<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<c:set var="isWebmaster" value="false" />
<sec:authorize access="hasAnyRole('ROLE_WEBMASTER')">
	<c:set var="isWebmaster" value="true" />
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="admin.emailTemplate.edit"/></title>
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<script>
	 $(function() {
			tinyMCE.init({
				selector: "textarea.wisiwig",
				language : "cs",
				width : '100%',
				forced_root_block : "",
				force_br_newlines : true,
				force_p_newlines : false,
				content_css : getBasePath() + 'resources/admin/css/tinymce.css',
				plugins: "link,table,autoresize,fullscreen",
				convert_urls: false,
				entity_encoding : 'raw',
				toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright | bullist numlist | link ",
				autoresize_min_height: 200,
				autoresize_max_height: 700,
				setup: function(editor) {
					if(editor.id === "variablesDescription"){
				        editor.on('keyup', function(e) {
				            $('#pj-email-vars').html(editor.getContent()); 
				        });
					}
			    }
			});
	   
		});
	</script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
		
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  u&raquo;
				 <a:adminurl href="/portal/email-templates"><spring:message code="admin.emailTemplate" /></a:adminurl>  &raquo;
				 <span><spring:message code="admin.emailTemplate.edit"/></span> 
			</div>
			<h1><spring:message code="admin.emailTemplate.edit"/></h1>
	
			<div id="content">
				
				<c:url value="/admin/portal/email-template/${empty emailTemplate.id ? 0 : emailTemplate.id }" var="formUrl"/>
				
				<form:form commandName="emailTemplate" method="post" cssClass="valid" action="${formUrl}" >
								
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
					
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
					</c:if>
					<c:if test="${not empty emailSent}">
						<p class="msg ok"><spring:message code="admin.emailTemplate.sent" />:
							<strong>${emailSent}</strong>
						</p>
					</c:if>
					
					
					
					<c:if test="${isWebmaster}">
						<p>
				       	<label>
				       		<strong><em class="red">*</em>
				       			<spring:message code="admin.emailTemplate.name" />  
				       		</strong>
				       	</label>
				           <span class="field">
				           	<form:input path="name" cssClass="mw500 required" />
				           </span>
				       </p>
				       <p>
				       	<label>
				       		<strong><em class="red">*</em>
				       			Kód:
				       		</strong>
				       	</label>
				           <span class="field">
				           	<form:input path="code" cssClass="mw500 required" />
				           </span>
				       </p>
			       </c:if>
			       
			       <p>
			       	<label>
			       		<strong><em class="red">*</em>
			       			<spring:message code="admin.emailTemplate.subject" />  
			       		</strong>
			       	</label>
			           <span class="field">
			           	<form:input path="subject" cssClass="mw500 required" />
			           </span>
			       </p>
			       <p class="pj-content-type  pj-type">
			       		<label>
				       		<strong><em class="red">*</em>
				       			<spring:message code="admin.emailTemplate.body" />  
				       		</strong>
			       		</label>
			           <span class="field full-width">
			           	<form:textarea path="body" cssClass="wisiwig" />
			           </span>
			       </p> 
			       <p>
			       	<label>
			       		<spring:message code="admin.emailTemplate.forward" />  
			       	</label>
			           <span class="field">
			           	<form:input path="bcc" cssClass="mw300" />
			           	<span class="mini-info inline">
			           		Emaily oddělte čárkou
			           	</span>
			           </span>
			       </p>
			       
			       <c:if test="${isWebmaster}">
				       <p>
				       	<label>
				       		Seznam proměnných, oddělené čárkou:
				       	</label>
				           <span class="field">
				           	<form:input path="variables" cssClass="mw500" maxlength="255" />
				           </span>
				       </p>
				        <p class="pj-content-type  pj-type ">
				       		<label>
					       		<spring:message  code="admin.emailTemplate.variables" />
				       		</label>
				           <span class="field full-width">
				           	<form:textarea path="variablesDescription" cssClass="wisiwig" id="variablesDescription" />
				           </span>
				       </p> 
			       </c:if>
			       

			    
			     
			       
			    	<form:hidden path="id" />
			       <p class="button-box">
			       	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
			       </p>
				</form:form>	
				
			   	<p class="form-head"><spring:message  code="admin.emailTemplate.variables" /></p>
		       	<div id="pj-email-vars">
		       		${emailTemplate.variablesDescription}
		       	</div>
			      
			    <form method="get" class="valid">
			    	 <p>
				       	<label>
				       		<strong><em class="red">*</em>
				       			Poslat testovací email na adresu:
				       		</strong>
				       	</label>
				           <span class="field">
				           	<input type="text" name="email" class="mw300 required email" />
				           	<input type="hidden" name="templateId" value="${emailTemplate.id}" > 
				           </span>
				       </p>
			    	 <p class="button-box">
				       	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
				       </p>
			    </form>   	
			       	
				<span class="note"><spring:message code="form.required" /></span>	
			</div>	
			
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>