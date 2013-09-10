<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="csn.category.import" /></title>
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
			 <span><spring:message code="csn.category.import" /></span>
		</div>
		<h1><spring:message code="csn.category.edit" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/csn/categories"  />"><spring:message code="csn.category.list" /></a></li>
				<li><a href="<c:url value="/admin/csn/category/edit/0"  />"><spring:message code="csn.category.new" /></a></li>
				<li><a class="active" href="<c:url value="/admin/csn/category/import"  />"><spring:message code="csn.category.import" /></a></li>
			</ul>
		
			<c:url value="/admin/csn/category/import" var="formUrl"/>
			<form:form  modelAttribute="uploadForm" method="post" action="${formUrl}"  cssClass="csnFileUpload" enctype="multipart/form-data">	

								
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="csn.category.import.success" arguments="${model.count}" /></p>
				</c:if>
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />

				 <c:if test="${not empty importFailed}">
				 	<p class="msg error"><spring:message code="csn.category.import.failed" /></p>
				 </c:if>
				 
				 <p>
					<label>
						<spring:message code="csn.category.import.chooseFile" />:
					</label>
					<span class="field">
						<input type="file" name="fileData" />
						<input type="submit" class="button radius" value="<spring:message code="form.file.upload"  /> &raquo;" />
					</span>	
              		</p>
			
			</form:form>
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>