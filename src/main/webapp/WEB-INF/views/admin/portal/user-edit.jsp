<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="user.edit"/> ${user.firstName} ${user.lastName}</title>
	<script src="<c:url value="/resources/admin/js/jquery.selectTip.js" />"></script>
	<script>
		$(function() {
			$( "#tabs" ).tabs(${not empty validityChanged ? '{active : 2}' : '' });
		});
	</script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
		
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  u&raquo;
				 <a:adminurl href="/portal/users"><spring:message code="admin.portalUser" /></a:adminurl>  &raquo;
				 <span><spring:message code="user.edit"/> ${user.firstName} ${user.lastName}</span>
			</div>
			<h1><spring:message code="user.edit"/> ${user.firstName} ${user.lastName}</h1>
	
			<div id="content">
									
				<div id="tabs">
					<ul>
						<li><a href="#basic-info">Osobní údaje uživatele</a></li>
						<li><a href="#orders">Objednávky uživatele</a></li>
						<li><a href="#services">Aktivované Produkty</a></li>
					</ul>
					
					<div id="basic-info">
						<jsp:include page="user-edit-form.jsp" />
					</div> 
					
					<div id="orders">
						<jsp:include page="order-list-table.jsp" />
					</div> 
					
					<div id="services">
						<jsp:include page="user-edit-services.jsp" />
					</div>
				</div>		
				
				<span class="note"><spring:message code="form.required" /></span>	
			</div>	
			
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>