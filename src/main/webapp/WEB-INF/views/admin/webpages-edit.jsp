<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages.edit" /></title>
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/webpage.js" />"></script>
</head>
<body>
	<div id="wrapper">
		<div id="content">
		<h1><spring:message code="webpages" /></h1>
		<a:adminurl href="/webpages" cssClass="btn-webpage st1 radius link-ico" >
			<spring:message code="webpages.view" /> <span class="ico set"></span>
		</a:adminurl>
		<a:adminurl href="/webpage/add/0" cssClass="btn-webpage tt st2 radius link-ico" title="Do hlavního menu">
			<spring:message code="webpages.add" /> <span class="ico plus"></span>
		</a:adminurl>
			
			
			
		</div>	
	</div>
<div id="fileDir" class="hidden">webpage-${webpageId}</div>
<div id="loader" class="webpage"></div>
</body>
</html>