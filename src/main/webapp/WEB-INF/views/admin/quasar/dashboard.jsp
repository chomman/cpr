<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_WEBMASTER')" >	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="quasar.long" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="../include/quasar-nav.jsp" />
		
	</div>	
	<div id="right">
	
		<div id="breadcrumb">
			 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl> &raquo; 
			 <span><spring:message code="quasar.long" /></span>
		</div>
		
		
		<h1><spring:message code="quasar.long" /></h1>

		<div id="content">
			
			
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>