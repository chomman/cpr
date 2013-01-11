<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></title>
<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.multiselect.css" />" />
<script src="<c:url value="/resources/admin/js/jquery.multiselect.min.js" />"></script>
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
					<a class="tab tt"  
						href="<c:url value="/admin/cpr/standard/edit/${standardId}" />" >
						<span>1</span> - <spring:message code="cpr.standard.tab.1" />
					</a>
					<a class="tab tt" title="<spring:message code="cpr.standard.tab.2.title" />" 
						href='<c:url value="/admin/cpr/standard/edit/${standardId}/requirements?country=1" />' >
						<span>2</span> - <spring:message code="cpr.standard.tab.2" />
					</a>
					<strong class="active-tab-head"><span>3</span> - <spring:message code="cpr.standard.tab.3" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					<script>
					$(document).ready(function(){
						   $("#example").multiselect();
						});
					</script>
					<select id="example" name="example" multiple="multiple">
					<option value="1">Option 1</option>
					<option value="2">Option 2</option>
					<option value="3">Option 3</option>
					<option value="4">Option 4</option>
					<option value="5">Option 5</option>
					</select>
						
					</div> <!-- END ACTIVE TAB -->
					<a class="tab tt" title="<spring:message code="cpr.standard.tab.4.title" />" href="#" >
						<span>4</span> - <spring:message code="cpr.standard.tab.4" />
					</a>
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>