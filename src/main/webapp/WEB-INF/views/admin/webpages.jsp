<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/webpages.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/js/jstree/themes/default/style.css" />" />
	<script src="<c:url value="/resources/admin/js/jstree/jstree.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/webpage.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div class="pj-webpages">
		
		<div id="content">
			<div id="breadcrumb">
				<a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>
				 &raquo;
				 <span><spring:message code="webpages" /></span>
			</div>
			
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			<c:if test="${not empty isNotEmptyError}">
				<p class="msg error"><spring:message code="cpr.group.isnotempty" /></p>
			</c:if>
			
			<div class="root-node">
				www.nlfnorm.cz
				<div class="pj-webpage-nav">
					<a:adminurl href="/webpage/add/0"  cssClass="pj-ico pj-add tt" title="Přidat podstránku">
							<span></span>
					</a:adminurl>
					
					<a:adminurl href="/webpage/edit/${model.homepage.id}"  cssClass="pj-ico pj-edit tt" title="Upravit">
						<span></span>
					</a:adminurl>
					<webpage:a webpage="${model.homepage}" isPreview="true" withName="false" cssClass="pj-ico  preview tt" title="Zobrazit">
						<span></span>
					</webpage:a>
				</div>
			</div>
			<div id="jstree" class="hidden"></div>
		</div>	
	</div>
	<div class="clear"></div>	
</div>
<div id="loader" class="webpage"></div>
</body>
</html>