<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.edit" arguments="${csn.csnName}" /></title>
<script src="<c:url value="/resources/admin/js/standard-csn.picker.js" />"></script>
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
			 <a href="<c:url value="/admin/cpr/standard-change" />"><spring:message code="menu.cpr.csn" /></a> &raquo;
			 <span><spring:message code="cpr.standard.edit" arguments="${csn.csnName}" /></span>
		</div>
		<h1><spring:message code="cpr.standard.edit" arguments="${csn.csnName}" /></h1>
		<div id="content">
							
				<ul class="sub-nav">
					<li><a href="<c:url value="/admin/cpr/standard-change"  />"><spring:message code="csn.list" /></a></li>
					<li><a href="<c:url value="/admin/cpr/standard-change/edit/0"  />"><spring:message code="csn.add" /></a></li>
				</ul>
								
				<!--  FORM  -->
				<c:url value="/admin/cpr/standard-change/edit/${csn.id}" var="formUrl"/>
				<jsp:include page="include/standard-csn-form.jsp"></jsp:include>
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>