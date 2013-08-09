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
	<div id="wrapper">

	<c:if test="${not empty images}">
		<c:forEach items="${images}" var="i">
		<img src="<c:url value="/image/s/100/${i}" /> " alt="" />
		</c:forEach>
	
	</c:if>
	
	<img src='<c:url value="/image/square/100/test.png"></c:url>' alt="" />
	
		<div class="upload">
			<form:form method="POST" enctype="multipart/form-data" modelAttribute="command" >
				
				<span>
					<form:errors path="*" cssClass="error" />
				</span>
				<label>
					<spring:message code="form.file.select" />
				</label>
				<input type="file" name="files[0]" />
				
				<input type="submit" value="<spring:message code="form.file.upload"  />" />
			</form:form>
		</div>
	</div>
	<div id="base"><c:url value="/"></c:url></div>
</body>
</html>