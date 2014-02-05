<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages.edit" /></title>
	<script src="/cpr/resources/admin/tinymce/tinymce.min.js"></script>
	<script src="<c:url value="/resources/admin/js/webpage.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="include/webpages-nav.jsp" />
	</div>
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/webpages" />"><spring:message code="webpages" /></a> &raquo;
			 <span><spring:message code="webpages.edit" /></span>
		</div>
		<h1><spring:message code="webpages.edit" /></h1>

		<div id="content">
		
			<div id="fileDir" class="hidden">webpage-${webpageId}</div>
			<c:url value="/admin/webpages/edit/${webpageId}" var="formUrl"  />					
			<form:form  commandName="webpage" method="post" action="${formUrl}" cssClass="valid">
				
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<p class="form-head"><spring:message code="webpage.head.basic" /><p>
              
                <c:if test="${isLoggedWebmaster}">
	                 <p>
	                	<label class="tt" title="<spring:message code="webpage.code.info"/>">
	                		<spring:message code="webpage.code" />
	                	</label>
	                    <span class="field">
	                    	<form:input  htmlEscape="true" path="code"  maxlength="250" cssClass="mw500" />
	                    </span>
	                </p>
                </c:if>
                <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="webpage.category" />
                		</strong>	
                	</label>
                <span class="field">  
				     <form:select path="webpageCategory" cssClass="mw500 smaller"> 
						  <option value=""><spring:message code="form.select"/></option>
						  <form:options items="${model.categories}" itemValue="id" itemLabel="name" />
					</form:select>
				 </span>
				 </p>
                <p>
				    <label title="<spring:message code="publish.descr" />" class="tt">
				 		<spring:message code="publish" />
				 	</label>
				     <span class="field">  
				     	<form:checkbox path="enabled" />
				     </span>
				 </p>
				 	<c:if test="${isLoggedWebmaster}">
					 	<p>
					 	<label>
					 		<spring:message code="webpage.content" />
					 	</label>
						<span class="field content">  				
							<span class="msg info"><spring:message code="webpage.content.info" /></span>	
							 <form:select path="webpageContent" cssClass="mw500">
								  <form:options items="${model.contents}" itemValue="id" itemLabel="name" />
							</form:select>		
						</span>
						</p>
					</c:if>	
					<c:if test="${not isLoggedWebmaster and webpage.id != 0}">
					 	<p>
					 	<label>
					 		<spring:message code="webpage.content.notwebmaset" />
					 	</label>
						<span class="field content">  				
							<span class="msg info"><spring:message code="webpage.content.info" /></span>	
							<span class="webpageContentName">${webpage.webpageContent.name}</span>
							<span class="webpageContentDescr">${webpage.webpageContent.description}</span>		
						</span>
						</p>
					</c:if>		 
				
				<!-- CONTENT -->	
				
				 <p class="form-head"><spring:message code="webpage.head.content" /><p>
				 <div id="ajax-result"></div>
				 <p>
                	<label>
                		<strong>
                			<spring:message code="webpage.locale" />:
                		</strong>	
                		
                	</label>
                     <span class="field">
                    	<a href="#" data-lang="cs" class="disabled">Česká</a>
                    	<a href="#" data-lang="en" class="lang en processSave">Anglická</a>
                    </span>
                </p> 
                <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="webpage.name" />
                		</strong>	
                		<small><spring:message code="webpage.name.info" /></small>
                	</label>
                    <span class="field">
                    	<form:input  htmlEscape="true" path="name" maxlength="60" cssClass="w300 required" 
                    		data-err-msg="Hodnota:  Název sekce, musí být vyplněna" />
                    </span>
                </p>
				 <p>
                	<label>
	                	<strong><em class="red">*</em>
	                		<spring:message code="webpage.title" />
	                	</strong>
                		<small><spring:message code="webpage.title.info" /></small>
                	</label>
                    <span class="field">
                    	<form:input  htmlEscape="true" path="title" maxlength="150" cssClass="required"
                    		data-err-msg="Hodnota: Titulek sekce, musí být vyplněna" />
                    </span>
                </p>
				 <p>
				    <label>
				 		<spring:message code="webpage.descr" />
                		<small><spring:message code="webpage.descr.info" /></small>
				 	</label>
				     <span class="field counter">  
				     	<form:textarea path="description" cssClass="dscrbox" />
				     	<span id="chars"></span>
				     </span>
				 </p>
				 <p>
				    <label>
				 		<spring:message code="webpage.toptext" />
				 	</label>
				     <span class="field">  								
				     	<form:textarea path="topText" cssClass="wisiwig" />
				     </span>
				 </p>
				
				 
				 
				 <p>
				    <label>
				 		<spring:message code="webpage.bottomText" />
				 	</label>
				     <span class="field">  								
				     	<form:textarea path="bottomText" cssClass="wisiwig " />
				     </span>
				 </p>
                <form:hidden path="id"/>
                <input type="hidden" name="locale" value="cs" />
                <p class="button-box">
                	 <input type="submit" class="button ajax" value="<spring:message code="form.save" />" />
                </p>
			</form:form>
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>
<div id="loader" class="webpage"></div>
</body>
</html>