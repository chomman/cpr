<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>CPR</title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="">Test page</a> &raquo;
			 <a href="">Test page 2</a> &raquo;
			 <span>Ovládací panel</span>
		</div>
		<h1><spring:message code="settings" /></h1>

		<div id="content">
			

	

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>