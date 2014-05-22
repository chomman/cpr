<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>File manager</title>
	<meta charset="utf-8" />
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<link rel="stylesheet"	href="<c:url value="/resources/admin/css/file-manager.css" />" />
	<script src="<c:url value="/resources/admin/js/stupidtable.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/file-manager.js" />"></script>
	
</head>
<body class="${uploadType == 2 ? 'docs-upload' : ''}">
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
	<div id="wrapper" >

	<c:if test="${not empty images}">
		<c:forEach items="${images}" var="i">
		<div class="imgBox rm">
			<div class="h">
				<span><spring:message code="filemanager.select" /></span>
				<img src="<c:url value="/image/s/80/${i}" /> " alt="${i}" />
			</div>
			<a href="#" class="delete" data-url="${i}"><spring:message code="form.delete" /></a>
		</div>
		</c:forEach>
		<div class="clear"></div>
	</c:if>
	
	<c:if test="${not empty documets}">
		<table>
			 <thead>
				<tr>
					<th title="Setřídit podle názvu" class="sort" data-sort="string-ins">Název souboru</th>
					<th>Velkost</th>
					<th>Vybrat</th>
					<th>Odstranit</th>
				</tr>
			 </thead>
		
			 <tbody>
			 	<c:forEach items="${documets}" var="i">
				<tr class="rm">
					<td class="pj-file l">
						<a class="document file ${i.extension}" data-url="${i.dir}/${i.name}">${i.name}</a>
					</td>
					<td class="pj-size c">${i.size}</td>
					<td class="pj-sel c"><a class="document" data-url="${i.dir}/${i.name}" href="#">Vybrat</a></td>
					<td class="pj-del c"><a class="delete" data-url="${i.dir}/${i.name}" href="#">Odstranit</a></td>
				</tr>
				</c:forEach>
			 </tbody>
		
		</table>
	</c:if>
		
	
	</div>
	
	<div id="base" class="hidden"><c:url value="/"></c:url></div>
	<div id="selector" class="hidden">${selector}</div>
	<div id="loader"></div>
</body>
</html>