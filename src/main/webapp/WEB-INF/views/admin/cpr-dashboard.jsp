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
			
			<ul class="cpr-dashboard">
				
				<li>
					<div class="hbox">
						<a class="main-item" href="<c:url value="/admin/cpr/standards" />" >
							<spring:message code="menu.cpr.norm" />
						</a>
					</div>
					
					<span class="label" ><spring:message code="cpr.dashboard.standard" /></span>
					
					<ul class="innter-nav">
						<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li> 
						<li><a href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li> 
						<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.search" /></a></li> 
						<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.requirement.add" /></a></li>   
					</ul>
				</li>
				
				
				<li>
					<div class="hbox">
						<a class="main-item"  href="<c:url value="/admin/cpr/groups" />" >
							<spring:message code="menu.cpr.groups" />
						</a>
					</div>
					<span class="label"><spring:message code="cpr.dashboard.groups" /></span>
					
					<ul class="innter-nav">
						<li><a href="<c:url value="/admin/cpr/groups/edit/0" />"><spring:message code="cpr.groups.add" /></a></li> 
						<li><a href="<c:url value="/admin/cpr/groups" />"><spring:message code="cpr.groups.view" /></a></li> 
						<li><a href="<c:url value="/admin/cpr/groups" />"><spring:message code="cpr.groups.edit" /></a></li>
					</ul>
				</li>
				
				
				<li>
					<div class="hbox">
						<a class="main-item"  href="<c:url value="/admin/cpr/notifiedbodies" />" >
							<spring:message code="menu.cpr.aono" />
						</a>
					</div>
					<span class="label"><spring:message code="cpr.dashboard.notifiedbodies" /></span>
					
					<ul class="innter-nav">
						<li><a href="<c:url value="/admin/cpr/notifiedbodies" />"><spring:message code="cpr.nb.add" /></a></li> 
						<li><a href="<c:url value="/admin/cpr/notifiedbodies/edit/0" />"><spring:message code="cpr.nb.view" /></a></li>  
					</ul>
				</li>
				
				
				<li>
					<div class="hbox">
						<a class="main-item"  href="<c:url value="/admin/cpr/assessmentsystems" />" >
							<spring:message code="menu.cpr.pps" />
						</a>
					</div>
					<span class="label"><spring:message code="cpr.dashboard.as" /></span>
					<ul class="innter-nav">
						<li><a href="<c:url value="/admin/cpr/assessmentsystems" />"><spring:message code="cpr.as.view" /></a></li> 
						<li><a href="<c:url value="/admin/cpr/assessmentsystems/edit/0" />"><spring:message code="cpr.as.add" /></a></li>  
					</ul>
				</li>
				
				
				<li>
					<div class="hbox">
						<a class="main-item"  href="<c:url value="/admin/cpr/mandates" />" >
							<spring:message code="menu.cpr.mandates" />
						</a>
					</div>
					
					<span class="label"><spring:message code="cpr.dashboard.mandates" /></span>
					<ul class="innter-nav">
						<li><a href="<c:url value="/admin/cpr/mandates"/>"><spring:message code="cpr.mandates.view" /></a></li> 
						<li><a href="<c:url value="/admin/cpr/mandates/edit/0" />"><spring:message code="cpr.mandates.add" /></a></li>  
					</ul>
				</li>
				
				
				<li>
					<div class="hbox">
						<a class="main-item"  href="<c:url value="/admin/cpr/basicrequirements" />" >
							<spring:message code="menu.cpr.requrements" />
						</a>
					</div>
					<span class="label"><spring:message code="cpr.dashboard.br" /></span>
					
					<ul class="innter-nav">
						<li><a href="<c:url value="/admin/cpr/basicrequirements"  />"><spring:message code="cpr.br.view" /></a></li> 
						<li><a href="<c:url value="/admin/cpr/basicrequirements/edit/0"  />"><spring:message code="cpr.br.add" /></a></li>  
					</ul>
				</li>
			</ul>
			

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>