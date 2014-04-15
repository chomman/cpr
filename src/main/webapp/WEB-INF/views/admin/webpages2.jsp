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
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.treetable.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.treetable.theme.custom.css" />" />
	<script src="<c:url value="/resources/admin/js/jquery.treetable.js" />"></script>
	<script>
	$(function () {
		 $("table.webpages").treetable();
	});
	</script>
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
			<div class="pj-nav">
				<span class="pj-nav-label"><spring:message code="webpages" /></span>
				<span class="pj-nav-label2"><spring:message code="options" />:</span>
				<a:adminurl href="/webpage/add/0" cssClass="btn-webpage tt st1 radius link-ico" title="Do hlavního menu">
					<spring:message code="webpages.add" /> <span class="ico plus"></span>
				</a:adminurl>
			</div>
			 
			
			
			
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			<c:if test="${not empty isNotEmptyError}">
				<p class="msg error"><spring:message code="cpr.group.isnotempty" /></p>
			</c:if>
			
			
				
			
		
				
			<c:if test="${not empty model.webpages}">
				<table class="webpages radius">
					<thead>
						<tr>
							<th class="gradient-gray">Název</th>
							<th class="gradient-gray"><spring:message code="published" /></th>
							<th class="gradient-gray">Modul</th>
							<th  class="gradient-gray">Autor/<spring:message code="form.lastEdit" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.webpages}" var="node"  >
						 	<c:set var="node" value="${node}" scope="request"/>
						 	<jsp:include page="webpage.node.jsp" />
						 	<jsp:include page="webpage.node.template.jsp" />
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			 
			<c:if test="${empty model.webpages}">
				<p class="msg alert">
					<spring:message code="alert.empty" />
				</p>
			</c:if>

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>