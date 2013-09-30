<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${id == 0}">
			<spring:message code="cns.terminology.addNew" arguments="${model.csn.csnId}" />
		</c:if>
		<c:if test="${id != 0}">
			<spring:message code="cns.terminology.edit" arguments="${model.csn.csnId};${csnTerminology.title}" argumentSeparator=";"/>
		</c:if>
	</title>
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<c:url value="/resources/admin/css/tinymce.css" var="cssLoc"/>
	<script> 
		
			tinymce.init({
					selector:'textarea', 
					language : "cs",
					height : 500,
					content_css :  '${cssLoc}',
					plugins: "image,link,table",
					convert_urls: false

			});
	</script>
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
			 <a href="<c:url value="/admin/csn/edit/${model.csn.id}" />"><spring:message code="csn.edit" arguments="${model.csn.csnId}" /></a> &raquo;
			 <span>
				 <c:if test="${id == 0}">
					<spring:message code="cns.terminology.addNew" arguments="${model.csn.csnId}" />
				</c:if>
				<c:if test="${id != 0}">
					<spring:message code="cns.terminology.edit" arguments="${model.csn.csnId};${csnTerminology.title}" argumentSeparator=";" />
				</c:if>
			</span>
		</div>
		<h1>
			<c:if test="${id == 0}">
			<spring:message code="cns.terminology.addNew" arguments="${model.csn.csnId}" />
		</c:if>
		<c:if test="${id != 0}">
			<spring:message code="cns.terminology.edit" arguments="${model.csn.csnId};${csnTerminology.title}" argumentSeparator=";" />
		</c:if>
		</h1>

		<div id="content">
			<ul class="sub-nav">
				<li><a class="csn-back" href="<c:url value="/admin/csn/edit/${model.csn.id}"  />">&laquo; <spring:message code="csn.terminology.back" /></a></li>
				<li><a href="<c:url value="/admin/csn"  />"><spring:message code="csn.list" /></a></li>
				<li><a href="<c:url value="/admin/csn/edit/0"  />"><spring:message code="csn.add" /></a></li>
				<li><a href="<c:url value="/admin/csn/${model.csn.id}/terminology/edit/0"  />"><spring:message code="csn.terminology.add" /></a></li>
			</ul>
		
			<c:if test="${not empty csnTerminology.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${csnTerminology.createdBy.firstName} ${csnTerminology.createdBy.lastName}</td>
						<td class="val"><joda:format value="${model.csn.created}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					<c:if test="${not empty csnTerminology.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${csnTerminology.changedBy.firstName} ${csnTerminology.changedBy.lastName}</td>
						<td class="val"><joda:format value="${csnTerminology.changed}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<c:url value="/admin/csn/${model.csn.id}/terminology/edit/${id}" var="formUrl"/>					
			<form:form  modelAttribute="csnTerminology" method="post" cssStyle="valid"  action="${formUrl}">
				
				<div id="ajax-result"></div>
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="csn.terminology.created" /></p>
				</c:if>
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
				<p class="form-head"><spring:message code="csn.basic.info" /><p>
                <p>
                	<label>
                		<strong>
                			<em class="red">*</em>
                			<spring:message code="csn.terminology.title" />:
                		</strong>
                	</label>
                   <span class="field">
                    	<form:input path="title" maxlength="255" cssClass="mw500 required" />
                    </span>
                </p>
                               
                <p>
				    <label >
				    	<strong>
                			<em class="red">*</em>
				 		<spring:message code="csn.terminology.lang" />:
				 		</strong>
				 	</label>
				     <span class="field">  
				     	<select name="language">
				     		<c:if test="${id == 0}">
				     		<option value="0">
				     			<spring:message code="form.select" />
				     		</option>
				     		</c:if>
				     		<c:forEach items="${model.csnTerminologyLanguage}" var="i">
				     			<option value="${i.code}" <c:if test="${i.code == csnTerminology.language.code}"> selected="selected"</c:if>>
				     				<spring:message code="${i.name}" />
				     			</option>
				     		</c:forEach>
				     	</select>
				     </span>
				 </p>
				 <p>
				    <label >
				 		<spring:message code="cns.terminology.section" />:
				 	</label>
				     <span class="field">  
				     	<form:input path="section" maxlength="25"/>
				     </span>
				 </p>
				 <p class="form-head"><spring:message code="csn.terminology.head.content" /><p>
				 <p>
				 <textarea rows="10" cols="10" name="content">${csnTerminology.content}</textarea>
               	</p>
				
                <form:hidden path="id"/>
                <input type="hidden" name="csn" value="${csnTerminology.csn.id}" />
                <p class="button-box modern">
                	 <c:if test="${id == 0}">
                	 	<input type="submit" class="button" value="<spring:message code="csn.terminology.form.add" />" />
                	 </c:if>
                	 <c:if test="${id != 0}">
                	 	<input type="submit" class="button ajaxSave" value="<spring:message code="form.save" />" />
                	 </c:if>
                	 
                </p>
			</form:form>
			
						
		</div>	
	</div>
	<div id="fileDir" class="hidden">${model.fileDir}</div>
	<div class="clear"></div>	
</div>

</body>
</html>