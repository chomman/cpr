<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="csn.csvimport" /></title>
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
			 <a href="<c:url value="/admin/csn" />"><spring:message code="menu.csn" /></a> &raquo;
			 <span><spring:message code="csv.import" /></span>
		
		</div>
		
		<h1><spring:message code="csn.csvimport" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/csn"  />"><spring:message code="csn.list" /></a></li>
				<li><a href="<c:url value="/admin/csn/edit/0"  />"><spring:message code="csn.add" /></a></li>
				<li><a class="active" href="<c:url value="/admin/csn/csvimport"  />"><spring:message code="csn.csvimport" /></a></li>
			</ul>
			
			<c:if test="${not empty model.success}">
				<c:if test="${model.success}">
					<div class="logResult <c:if test="${model.log.successCount > 0}" >importSuccess</c:if><c:if test="${model.log.successCount == 0}" >importFailed</c:if>">
						<p><spring:message code="csn.csvimport.countOfSuccessully" arguments="${model.log.successCount}" /></p>
						<p><spring:message code="csn.csvimport.countOfFailed" arguments="${model.log.failedCount}" /></p>
						${model.log.info}
					</div>
				</c:if>
				<c:if test="${not model.success}">
					<p class="msg error"><spring:message code="csv.import.failed" /></p>
				</c:if>
			</c:if>
			
			<c:url value="/admin/csn/csvimport" var="formUrl"/>
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
						<spring:message code="csv.import.chooseFile" />:
					</label>
					<span class="field">
						<input type="file" name="fileData" />
						<input type="submit" class="button radius" value="<spring:message code="form.file.upload"  /> &raquo;" />
					</span>	
              		</p>
			
			</form:form>
		
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>