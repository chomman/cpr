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
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.fancybox.css" />" />
	<script src="<c:url value="/resources/admin/js/stupidtable.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/file-manager.js" />"></script>
	<script	src="<c:url value="/resources/admin/js/jquery.fancybox.pack.js" />"></script>
	
</head>
<body class="${uploadType > 1 ? 'docs-upload' : ''}">
	<div class="upload">
		<ul>
			<li><a class="add-folder" data-class="form-dir" href="#" title="Vytvořit složku"></a></li>
			<li><a class="add-file" data-class="form-file" href="#" title="Přidat soubor"></a></li>
		</ul>
		<form:form method="POST" enctype="multipart/form-data" modelAttribute="command" cssClass="form-file ${not empty errors and fileUpload ? '' : 'hidden' }" >
			<label>
				<spring:message code="form.file.select" />:
			</label>
			<input type="file" name="fileData" />
			<input type="submit" class="button radius" value="<spring:message code="form.file.upload"  /> &raquo;" />
			<c:if test="${not empty errors}">
			<span class="error">${errors}</span>
		</c:if>
		</form:form>
		
		<form:form method="POST" modelAttribute="command" cssClass="form-dir ${not empty errors and newDir ? '' : 'hidden' }" >
			<label>
				Název složky:
			</label>
			<form:input path="newDir" />
			<input type="submit" class="button radius" value="Vytvořit"  />
			<c:if test="${not empty errors}">
			<span class="error">${errors}</span>
		</c:if>
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
					<tr>
					<td class="pj-file l">
					<a class="up" 
					href="${fmUrl}${parentUrl}"><b>...</b></a>
				</td>
				<td class="pj-size c">&lt;Složka&gt;</td>
				<td class="pj-sel c">&nbsp;</td>
				<td class="pj-del c">&nbsp;</td>
					</tr>	
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
					
					<c:if test="${not i.isDir and uploadType > 1}">
						<td class="pj-file l" data-sort-value="1${i.name}">
							<a class="document file ${i.extension}" data-url="${i.dir}/${i.name}">${i.name}</a>
						</td>
						<td class="pj-size c">${i.size}</td>
						<td class="pj-sel c"><a class="document" data-url="${i.dir}/${i.name}" href="#">Vybrat</a></td>
						<td class="pj-del c"><a class="delete" data-url="${i.dir}/${i.name}" href="#">Odstranit</a></td>
					</c:if>
					
					<c:if test="${not i.isDir and uploadType == 1}">
						<td class="pj-file l" data-sort-value="1${i.name}">
							<a title="Zobrazit náhled"
							 class="fancy ${i.extension}" data-url="${i.dir}/${i.name}" href="<c:url value="/image/r/500/${i.dir}/${i.name}" />">
								<img src="<c:url value="/image/s/35/${i.dir}/${i.name}" />" alt="" />
								${i.name}
							</a>
						</td>
						<td class="pj-size c">${i.size}</td>
						<td class="pj-sel c"><a class="image" data-url="${i.dir}/${i.name}" href="#">Vybrat</a></td>
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