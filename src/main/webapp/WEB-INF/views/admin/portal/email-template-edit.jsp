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
				toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright | bullist numlist | link ",
				autoresize_min_height: 200,
				autoresize_max_height: 700,
				setup: function(editor) {
					if(editor.id === "variables"){
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
			
				<form:form commandName="emailTemplate" method="post" cssClass="valid" >
								
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
					
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
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
				       			<spring:message code="admin.emailTemplate.name" />  
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
			       
			       <c:if test="${isWebmaster}">
			        <p class="pj-content-type  pj-type ">
			       		<label>
				       		<spring:message  code="admin.emailTemplate.variables" />
			       		</label>
			           <span class="field full-width">
			           	<form:textarea path="variables" cssClass="wisiwig" id="variables" />
			           </span>
			       </p> 
			       </c:if>
			       

			       	<p class="form-head"><spring:message  code="admin.emailTemplate.variables" /></p>
			       	<div id="pj-email-vars">
			       		${emailTemplate.variables}
			       	</div>
			     
			       
			    	<form:hidden path="id" />
			       <p class="button-box">
			       	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
			       </p>
				</form:form>	
				
				<span class="note"><spring:message code="form.required" /></span>	
			</div>	
			
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>