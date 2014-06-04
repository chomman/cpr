<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.edit" arguments="${model.standardName}" /></title>
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
			 <a href="<c:url value="/admin/cpr/standards" />"><spring:message code="menu.cpr.norm" /></a> &raquo;
			 <span><spring:message code="cpr.standard.edit" arguments="${model.standardName}" /></span>
		</div>
		<h1><spring:message code="cpr.standard.edit" arguments="${model.standardName}" /></h1>
		<div id="content">
							
					
				<jsp:include page="include/standard-subnav.jsp" />
								
				<div id="tabs">
					
					<jsp:include page="include/cpr-standard-menu1.jsp" />
					<jsp:include page="include/cpr-standard-menu2.jsp" />

					<strong class="active-tab-head"><spring:message code="cpr.standard.tab.3" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					
					<div id="req-nav">
						<a class="view" href="<c:url value="/admin/cpr/standard/edit/${standardId}/csn" />">
							&laquo; <spring:message code="cpr.csn.view" />
						</a>
						<c:if test="${csn.id != 0}">
							<a href="<c:url value="/admin/cpr/standard-csn/${csn.id}/change/0"  />">
								 <spring:message code="cpr.csn.change.add"  arguments="${csn.csnName}"  /> +
							</a>
						</c:if>
					</div>
					
					
				
					<!--  FORM  -->
					<c:url value="/admin/cpr/standard/edit/${standardId}/csn-edit/${csn.id}" var="formUrl"/>
					<jsp:include page="include/standard-csn-form.jsp"></jsp:include>
						
					</div> <!-- END ACTIVE TAB -->

					<jsp:include page="include/cpr-standard-menu4.jsp" />
					<jsp:include page="include/cpr-standard-menu5.jsp" />
					<jsp:include page="include/cpr-standard-menu6.jsp" />
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>