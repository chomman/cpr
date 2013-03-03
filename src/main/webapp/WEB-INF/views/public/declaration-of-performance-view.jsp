<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="dop" /></title>
		<meta name="robots" content="noindex,follow"/>
		<link rel="stylesheet" media="print" href="<c:url value="/resources/public/css/print.css" />" />
		<script src="<c:url value="/resources/public/js/storage.js" />"></script>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span><spring:message code="dop" /></span>
	</div> 

		<div id="main-content">

			<div id="localStorage"></div>				
				<c:if test="${model.success}">
					<script>var newDop = { token :  '${model.dop.token}', ehn : '${model.dop.standard.standardId}', system : '${model.dop.assessmentSystem.name}', created : new Date()  }; </script>
				</c:if>
				
				<c:if test="${dopNotFound}">
					<p class="error msg"><spring:message code="dop.dopNotFound" /> </p>
				</c:if>
				
				<c:if test="${empty dopNotFound}">
				
				<div id="dop-menu">
					<span><spring:message code="options"  />: </span> 
					<a class="edit" href="<c:url value="/dop/edit/${model.dop.token}" />" ><spring:message code="form.edit" /></a>
					<a class="delete" href="<c:url value="/dop/delete/${model.dop.token}" />" title="${model.dop.token}" ><spring:message code="form.delete" /></a>
					<a class="print" href="#"><spring:message  code="export.print" /></a>
					<a class="pdf" href="<c:url value="/dop/export/pdf/${model.dop.token}" />"><spring:message  code="export.pdf" /></a>
				</div>
				
					<jsp:include page="../include/dop.jsp"></jsp:include>			
			
				</c:if>
			 
		</div>
	</body>
</html>