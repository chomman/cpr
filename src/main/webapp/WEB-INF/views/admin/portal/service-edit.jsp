<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${empty service.id}">
			<spring:message code="admin.service.add" />
		</c:if>
		<c:if test="${not empty service.id}">
			<spring:message code="admin.service.edit" />
		</c:if>
	</title>
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<script type="text/javascript">
		 tinyMCE.init({
			 	selector: "textarea.wisiwig",
				language : "cs",
				height : 170,
				width : 630,
				forced_root_block : "",
				force_br_newlines : true,
				force_p_newlines : false,
				plugins: "image,link,table",
				convert_urls: false
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
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <a:adminurl href="/portal/services"><spring:message code="admin.portal.services" /></a:adminurl>  &raquo;
				 <span>
				 	<c:if test="${empty service.id}">
						<spring:message code="admin.service.add" />
					</c:if>
					<c:if test="${not empty service.id}">
						<spring:message code="admin.service.edit" />
					</c:if>
				 </span>
			</div>
			<h1>
				<c:if test="${empty service.id}">
					<spring:message code="admin.service.add" />
				</c:if>
				<c:if test="${not empty service.id}">
					<spring:message code="admin.service.edit" />
				</c:if>
			</h1>
	
			<div id="content">
				
				<jsp:include page="service-nav.jsp" />
				
				<form:form commandName="service" method="post" cssClass="valfid" >
							
							<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
							
							<c:if test="${not empty successCreate}">
								<p class="msg ok"><spring:message code="success.create" /></p>
							</c:if>
							
							<p>
	                        	<label>
	                        		<strong><em class="red">*</em>
	                        			<spring:message code="admin.service.czechName" />  
	                        		</strong>
	                        	</label>
	                            <span class="field">
	                            	<form:input path="czechName" maxlength="150" cssClass="mw500 required" />
	                            </span>
	                        </p>
	                        <p>
	                       		<label>
	                        		<spring:message code="admin.service.englishName" />  
	                        	</label>
	                            <span class="field">
	                            	<form:input path="englishName" maxlength="150" cssClass="mw500" />
	                            </span>
	                        </p>
	                         <p>
	                       		<label>
	                       			<strong><em class="red">*</em>
	                        			<spring:message code="admin.service.price" />
	                        		</strong>  
	                        	</label>
	                            <span class="field"> 
	                            	<form:input path="price" maxlength="5" cssClass="w100 required numeric" />
	                            </span>
	                        </p>
	                         <p>
	                        	<label>
	                        			<spring:message code="admin.service.enabled" /> 
	                        	</label>
	                            <span class="field">
	                            	<form:checkbox path="enabled" />
	                            </span>
	                        </p>
	                         <p>
	                        	<label>
	                        			<spring:message code="admin.service.enabled" /> 
	                        	</label>
	                            <span class="field">
	                            	<form:textarea path="description" cssClass="wisiwig" />
	                            </span>
	                        </p>
							
							
							
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