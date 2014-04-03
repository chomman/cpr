<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages" /></title>
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
			
			
			<ul class="sub-nav webpages">
				<li><a class="active" href="<c:url value="/admin/webpages"  />"><spring:message code="webpages.view" /></a></li>
				<li><a href="<c:url value="/admin/webpage/add/0"  />"><spring:message code="webpages.add" /></a></li>
			</ul>
			
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
							<th>Nazev</th>
							<th>kategorie</th>
							<th><spring:message code="published" /></th>
							<th><spring:message code="form.lastEdit" /></th>
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