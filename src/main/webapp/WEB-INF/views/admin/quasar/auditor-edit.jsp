<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="auditor.edit" />: ${model.auditor.name}</title>
		<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
		<script src="<c:url value="/resources/public/js/picker.jquery.js" />"></script>
		<script src="<c:url value="/resources/admin/js/scripts.quasar.js" />"></script>
	</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="../include/quasar-nav.jsp" />
	</div>	
	<div id="right">
		

		
		<div id="breadcrumb">
			  <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
			  <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
			  <a:adminurl href="/quasar/manage/auditors"><spring:message code="auditors" /></a:adminurl>  &raquo;
			  <span>${model.auditor.name}</span>
		</div>
		<h1><spring:message code="auditor.edit" />:&nbsp; <strong>${model.auditor.name}</strong></h1>

		<div id="content" class="qs" data-auditor="${model.auditor.id}">
			
			<jsp:include page="navs/auditor-nav.jsp" />
			
			<c:if test="${model.subTab == 1}">
				<jsp:include page="changed.jsp" />
				<jsp:include page="auditor-edit-form.jsp" />
			</c:if>
			
			<c:if test="${model.subTab == 2}">
				<jsp:include page="auditor-edit-qs-auditor.jsp" />
			</c:if>
			
			<c:if test="${model.subTab == 3}">
				<jsp:include page="auditor-edit-product-assessor-a.jsp" />
			</c:if>
			 
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>