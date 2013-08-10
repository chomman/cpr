<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>File manager</title>
<meta charset="utf-8" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/admin/css/file-manager.css" />" />
<script src="<c:url value="/resources/admin/js/file-manager.js" />"></script>
</head>
<body>
	<div class="upload">
		<form:form method="POST" enctype="multipart/form-data" modelAttribute="command" >
			<label>
				<spring:message code="form.file.select" />:
			</label>
			<input type="file" name="fileData" />
			
			<c:if test="${not empty hasErrors}">
				<span class="error"><spring:message code="error.image.extetion" /></span>
			</c:if>
			
			<input type="submit" class="button radius" value="<spring:message code="form.file.upload"  /> &raquo;" />
		</form:form>
	</div>
	<div id="wrapper">

	<c:if test="${not empty images}">
		<c:forEach items="${images}" var="i">
		<div class="imgBox">
			<div class="h">
				<span><spring:message code="filemanager.select" /></span>
				<img src="<c:url value="/image/s/100/${i}" /> " alt="${i}" />
			</div>
			<a href="#" title="${i}"><spring:message code="form.delete" /></a>
		</div>
		</c:forEach>
		<div class="clear"></div>
	</c:if>
		
	
	</div>
	<div id="base" class="hidden"><c:url value="/"></c:url></div>
	<div id="selector" class="hidden">${selector}</div>
</body>
</html>