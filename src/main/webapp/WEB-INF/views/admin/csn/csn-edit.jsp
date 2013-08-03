<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="csn.add" /></title>
	<script src="<c:url value="/resources/admin/tiny_mce/tiny_mce.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="csn-nav.jsp" />
	</div>
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/csn" />"><spring:message code="menu.csn" /></a> &raquo;
			 <span><spring:message code="csn.add" /></span>
		</div>
		<h1><spring:message code="csn.add" /></h1>

		<div id="content">
			<c:if test="${not empty csn.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${csn.createdBy.firstName} ${csn.createdBy.lastName}</td>
						<td class="val"><joda:format value="${csn.created}" pattern="${dateTimeFormat}"/></td>
					</tr>
					<c:if test="${not empty csn.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${csn.changedBy.firstName} ${csn.changedBy.lastName}</td>
						<td class="val"><joda:format value="${csn.changed}" pattern="${dateTimeFormat}"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<c:url value="/admin/csn/edit/${id}" var="formUrl"/>					
			<form:form  modelAttribute="csn" method="post" cssStyle="valid"  action="${formUrl}" enctype="multipart/form-data">
				
				<div id="ajax-result"></div>
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
				<p class="form-head"><spring:message code="csn.basic.info" /><p>
                <p>
                	<label>
                		<strong>
                			<em class="red">*</em>
                			<spring:message code="csn.form.name" />:
                		</strong>
                	</label>
                   <span class="field">
                    	<form:input path="csnId" maxlength="50" cssClass="required" />
                    </span>
                </p>
                  <p>
                	<label>
                		<spring:message code="csn.form.published" />:
                	</label>
                    <span class="field">
                    	<input type="text" class="date" id="published" maxlength="10" />
                    </span>
                </p>
                
                <p>
				    <label >
				    	<strong>
                			<em class="red">*</em>
				 		<spring:message code="csn.form.czechName" />:
				 		</strong>
				 	</label>
				     <span class="field">  
				     	<form:input  htmlEscape="true" path="czechName"  cssClass="mw500 required"  />
				     </span>
				 </p>
				 <p>
				    <label >
				 		<spring:message code="csn.form.englishName" />:
				 	</label>
				     <span class="field">  
				     	<form:input  htmlEscape="true" path="englishName" cssClass="mw500" />
				     </span>
				 </p>

				 
				 <p class="form-head"><spring:message code="csn.basic.info.other" /><p>
				  <p>
                	<label>
                		<spring:message code="csn.form.symbol" />:
                	</label>
                   <span class="field">
                    	<form:input  htmlEscape="true" path="classificationSymbol" maxlength="10" />
                    </span>
                </p>
				 <p>
                	<label>
                		<spring:message code="csn.form.isc" />:
                	</label>
                   <span class="field">
                    	<form:input  htmlEscape="true" path="ics" maxlength="255 mw500" />
                    </span>
                </p>
               
				
                <form:hidden path="id"/>
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