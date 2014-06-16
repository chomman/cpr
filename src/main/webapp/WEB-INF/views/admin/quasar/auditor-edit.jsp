<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="auditor.edit" />: ${model.auditor.name}</title>
		<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
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
			  <span><spring:message code="auditor.edit" />: ${model.auditor.name}</span>
		</div>
		<h1><spring:message code="auditor.edit" />:&nbsp; <strong>${model.auditor.name}</strong></h1>

		<div id="content">
			
			<jsp:include page="navs/auditor-nav.jsp" />
			
			<form:form  commandName="auditor" method="post" cssClass="valid" > 
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
			
			</form:form>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>