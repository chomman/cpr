<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages.edit" /></title>
	<script src="<c:url value="/resources/admin/tiny_mce/tiny_mce.js" />"></script>
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
			<c:if test="${not empty webpage.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${webpage.createdBy.firstName} ${webpage.createdBy.lastName}</td>
						<td class="val"><joda:format value="${webpage.created}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					<c:if test="${not empty webpage.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${webpage.changedBy.firstName} ${webpage.changedBy.lastName}</td>
						<td class="val"><joda:format value="${webpage.changed}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<c:url value="/admin/webpages/edit/${webpageId}" var="formUrl"  />					
			<form:form  commandName="webpage" method="post" action="${formUrl}" cssClass="valid">
				
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<p class="form-head"><spring:message code="webpage.head.basic" /><p>
                <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="webpage.name" />
                		</strong>	
                		<small><spring:message code="webpage.name.info"/></small>
                	</label>
                    <span class="field">
                    	<form:input path="name" maxlength="60" cssClass="w300 required" />
                    </span>
                </p>
                 <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="webpage.code" />
                		</strong>	
                		<small><spring:message code="webpage.code.info"/></small>
                	</label>
                    <span class="field">
                    	<form:input path="code" maxlength="250" cssClass="mw500 required" />
                    </span>
                </p>
                <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="webpage.category" />
                		</strong>	
                	</label>
                <span class="field">  
				     <form:select path="webpageCategory" cssClass="mw500 smaller required">
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
				 <p class="form-head"><spring:message code="webpage.head.seo" /><p>
				 <p>
                	<label>
                		<spring:message code="webpage.title" />
                		<small><spring:message code="webpage.title.info" /></small>
                	</label>
                    <span class="field">
                    	<form:input path="title" maxlength="150" cssClass="required" />
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
				 <p class="form-head"><spring:message code="webpage.head.content" /><p>
				 <p>
				    <label>
				 		<spring:message code="webpage.toptext" />
				 	</label>
				     <span class="field">  								
				     	<form:textarea path="topText" cssClass="mceEditor " />
				     </span>
				 </p>
				 <p>
				    <label>
				 		<spring:message code="webpage.bottomText" />
				 	</label>
				     <span class="field">  								
				     	<form:textarea path="bottomText" cssClass="mceEditor " />
				     </span>
				 </p>
                <form:hidden path="id"/>
                <form:hidden path="timestamp"/>
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