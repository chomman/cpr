<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></title>
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
			 <span><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></span>
		</div>
		<h1><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></h1>

		<div id="content">
							
					
				<ul class="sub-nav">
					<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
					<li><a href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
				</ul>

				<div id="tabs">
					
					<strong class="active-tab-head"><spring:message code="cpr.standard.tab.1" /></strong>
					<div class="active-tab">
						
					
					<c:if test="${not empty model.showStandardChangeForm}">
						<jsp:include page="include/standard-edit1-standardchange.jsp" />
					</c:if>
					<c:if test="${empty model.showStandardChangeForm}">
						<jsp:include page="include/standard-edit1-basicinfo.jsp" />
					</c:if>
					
					
					</div> <!-- END ACTIVE TAB -->
					
						<jsp:include page="include/cpr-standard-menu2.jsp" />
						<jsp:include page="include/cpr-standard-menu3.jsp" />
						<jsp:include page="include/cpr-standard-menu4.jsp" />
						<jsp:include page="include/cpr-standard-menu5.jsp" />
						<jsp:include page="include/cpr-standard-menu6.jsp" />
						
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>