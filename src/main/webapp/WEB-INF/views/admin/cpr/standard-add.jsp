<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.add" /></title>
<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.tagit.css" />" />
<script src="<c:url value="/resources/admin/js/tag-it.min.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr" />"><spring:message code="menu.cpr" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr/standards" />"><spring:message code="menu.cpr.norm" /></a> &raquo;
			 <span><spring:message code="cpr.standard.add" /></span>
		</div>
		<h1><spring:message code="cpr.standard.add" /></h1>

		<div id="content">
							
					
					<ul class="sub-nav">
						<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
						<li><a class="active" href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
					</ul>
				
					<c:url value="/admin/cpr/standard/edit/0" var="formUrl"/>
					<%@ include file="include/cpr-standard-form1.jsp" %>
					
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>