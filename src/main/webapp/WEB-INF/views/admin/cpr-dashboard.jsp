<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="dashboard.cpr.title" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <span><spring:message code="menu.cpr" /></span>
		</div>
		<h1><spring:message code="dashboard.cpr.title" /></h1>

		<div id="content">
			
			<jsp:include page="include/cpr-nav.jsp" />
			

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>