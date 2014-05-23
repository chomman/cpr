<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<c:url value="/admin/file-manager.htm" var="fmUrl" />
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
		<ul>
			<li><a class="add-folder" data-class="form-dir" href="#" title="Vytvořit složku"></a></li>
			<li><a class="add-file" data-class="form-file" href="#" title="Přidat soubor"></a></li>
		</ul>
		<form:form method="POST" enctype="multipart/form-data" modelAttribute="command" cssClass="form-file hidden" >
			<label>
				<spring:message code="form.file.select" />:
			</label>
			<input type="file" name="fileData" />
			
			<c:if test="${not empty hasErrors}">
				<span class="error"><spring:message code="error.image.extetion" /></span>
			</c:if>
			<input type="submit" class="button radius" value="<spring:message code="form.file.upload"  /> &raquo;" />
		</form:form>
		
		<form:form method="POST" modelAttribute="command" cssClass="form-dir ${empty dirExists ? 'hidden' : '' }" >
			<label>
				Název složky:
			</label>
			<form:input path="newDir" />
			<input type="submit" class="button radius" value="Vytvořit"  />
			<c:if test="${not empty dirExists}"><span class="error">Složka se zadaným názvem existuje.</span></c:if>
		</form:form>
	</div>
	<div id="wrapper" >

	
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
			 	<c:if test="${not empty currentDir}"> 
						<td class="pj-file l">
							<a class="up" 
							href="${fmUrl}${parentUrl}"><b>...</b></a>
						</td>
						<td class="pj-size c">&lt;Složka&gt;</td>
						<td class="pj-sel c">&nbsp;</td>
						<td class="pj-del c">&nbsp;</td>
					</c:if>
					
			 
			 	<c:forEach items="${documets}" var="i">
				<tr class="rm">
					<c:if test="${i.isDir}">
						<td class="pj-file l" data-sort-value="0${i.name}">
							<a class="dir" href="${fmUrl}${url}/${i.name}">${i.name}</a>
						</td>
						<td class="pj-size c">&lt;Složka&gt;</td>
						<td class="pj-sel c">&nbsp;</td>
						<td class="pj-del c"><a class="delete" data-url="${i.dir}/${i.name}" href="#">Odstranit</a></td>
					</c:if>
					
					<c:if test="${not i.isDir and uploadType == 2}">
						<td class="pj-file l" data-sort-value="1${i.name}">
							<a class="document file ${i.extension}" data-url="${i.dir}/${i.name}">${i.name}</a>
						</td>
						<td class="pj-size c">${i.size}</td>
						<td class="pj-sel c"><a class="document" data-url="${i.dir}/${i.name}" href="#">Vybrat</a></td>
						<td class="pj-del c"><a class="delete" data-url="${i.dir}/${i.name}" href="#">Odstranit</a></td>
					</c:if>
					
					<c:if test="${not i.isDir and uploadType == 1}">
						<td class="pj-file l" data-sort-value="1${i.name}">
							<a class="document image file ${i.extension}" data-url="${i.dir}/${i.name}">
							<img src="<c:url value="/image/s/35/${i.name}" /> " alt="${i}" />
							${i.name}</a>
						</td>
						<td class="pj-size c">${i.size}</td>
						<td class="pj-sel c"><a class="document" data-url="${i.dir}/${i.name}" href="#">Vybrat</a></td>
						<td class="pj-del c"><a class="delete" data-url="${i.dir}/${i.name}" href="#">Odstranit</a></td>
					</c:if>
				</tr>
				</c:forEach>
			 </tbody>
		</table>
		
	
	</div>
	
	<div id="base" class="hidden"><c:url value="/"></c:url></div>
	<div id="selector" class="hidden">${selector}</div>
	<div id="currentDir" class="hidden">${currentDir}</div>
	<div id="loader"></div>
</body>
</html>