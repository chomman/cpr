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
			<spring:message code="cns.terminology.edit" arguments="${model.csn.csnId};${csnTerminology.csnId}" />
		</c:if>
	</title>
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<script src="<c:url value="/resources/admin/jquery.fileManager.js" />"></script>
	<c:url value="/resources/admin/css/tinymce.css" var="cssLoc"/>
	<link rel="stylesheet" href="<c:url value="/resources/admin/filemanager/jquery.fileManager.css" />" />
	<script> 
		
			tinymce.init({
					selector:'textarea', 
					language : "cs",
					height : 500,
					content_css :  '${cssLoc}',
					plugins: "image",
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
					<spring:message code="cns.terminology.edit" arguments="${model.csn.csnId};${csnTerminology.csnId}" />
				</c:if>
			</span>
		</div>
		<h1>
			<c:if test="${id == 0}">
			<spring:message code="cns.terminology.addNew" arguments="${model.csn.csnId}" />
		</c:if>
		<c:if test="${id != 0}">
			<spring:message code="cns.terminology.edit" arguments="${model.csn.csnId};${csnTerminology.csnId}" />
		</c:if>
		</h1>

		<div id="content">
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/csn"  />"><spring:message code="csn.list" /></a></li>
				<li><a <c:if test="${id == 0}"> class="active"</c:if> href="<c:url value="/admin/csn/edit/0"  />"><spring:message code="csn.add" /></a></li>
			</ul>
		
			<c:if test="${not empty csnTerminology.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${csnTerminology.createdBy.firstName} ${csnTerminology.createdBy.lastName}</td>
						<td class="val"><joda:format value="${model.csn.created}" pattern="${dateTimeFormat}"/></td>
					</tr>
					<c:if test="${not empty csnTerminology.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${csnTerminology.changedBy.firstName} ${csnTerminology.changedBy.lastName}</td>
						<td class="val"><joda:format value="${csnTerminology.changed}" pattern="${dateTimeFormat}"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<c:url value="/admin/csn/edit/${model.csn.id}/terminology/edit/${id}" var="formUrl"/>					
			<form:form  modelAttribute="csnTerminology" method="post" cssStyle="valid"  action="${formUrl}">
				
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
				     		<option value="0">
				     			<spring:message code="form.select" />
				     		</option>
				     		<c:forEach items="${model.csnTerminologyLanguage}" var="i">
				     			<option value="${i.code}"><c:if test="${i.code == csnTerminology.language.code}"> selected="selected"</c:if>
				     				<spring:message code="${i.name}" />
				     			</option>
				     		</c:forEach>
				     	</select>
				     </span>
				 </p>
				 <p class="form-head"><spring:message code="csn.terminology.head.content" /><p>
				 <p>
				 <textarea rows="10" cols="10" name="content">${csnTerminology.content}</textarea>
               	</p>
				
                <form:hidden path="id"/>
                <p class="button-box">
                	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                </p>
			</form:form>
			
						
		</div>	
	</div>
	<div id="fileDir" class="hidden">${model.fileDir}</div>
	<div class="clear"></div>	
</div>

</body>
</html>