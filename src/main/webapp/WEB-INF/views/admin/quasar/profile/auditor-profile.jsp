<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="auditor.profile" />: ${model.auditor.name}</title>
		<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
		<script src="<c:url value="/resources/public/js/picker.jquery.js" />"></script>
		<script src="<c:url value="/resources/admin/js/scripts.quasar.js" />"></script>
	</head>
<body>
	<div id="wrapper">
		<div id="breadcrumb">
			  <a:adminurl href="/quasar/dashboard"><spring:message code="menu.home" /></a:adminurl>  &raquo;
			  <span><spring:message code="auditor.profile" /> - ${model.auditor.name}</span>
		</div>
		<h1><spring:message code="auditor.profile" /> - <strong>${model.auditor.nameWithDegree}</strong></h1>
		<div id="content" class="qs" data-auditor="${model.auditor.id}">
			
			<jsp:include page="../navs/profile-nav.jsp" />
			<c:if test="${model.subTab == 1}">
				<jsp:include page="../tabs/auditor-personal-data.jsp" />
			</c:if>
			<c:if test="${model.subTab == 2}">
				<jsp:include page="../tabs/auditor-edit-qs-auditor.jsp" />
			</c:if>
			<c:if test="${model.subTab == 3}">
				<jsp:include page="../tabs/auditor-edit-product-assessor-a.jsp" />
			</c:if>
			<c:if test="${model.subTab == 4}">
				<jsp:include page="../tabs/auditor-edit-product-assessor-r.jsp" />
			</c:if>
			<c:if test="${model.subTab == 5}">
				<jsp:include page="../tabs/auditor-edit-product-specialist.jsp" />
			</c:if>
			<c:if test="${model.subTab == 6}">
				<jsp:include page="../tabs/auditor-edit-output.jsp" />
			</c:if>
		</div>	
	</div>
</body>
</html> 