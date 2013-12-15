<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.group.import" /></title>
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
			 <span><spring:message code="cpr.group.import" /></span>
		</div>
		<h1><spring:message code="cpr.group.import" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
						<li><a class="active" href="<c:url value="/admin/cpr/groups"  />"><spring:message code="cpr.groups.view" /></a></li>
						<li><a href="<c:url value="/admin/cpr/groups/edit/0"  />"><spring:message code="cpr.groups.add" /></a></li>
						<li><a href="<c:url value="/admin/cpr/groups/import"  />"><spring:message code="cpr.group.import" /></a></li>
					</ul>
			
			<c:if test="${not empty successCreate}">
				<p class="msg ok"><spring:message code="success.create" /></p>
			</c:if>
			
			<c:url value="/admin/cpr/groups/import" var="formUrl"/>
			<form:form  method="post" action="${formUrl}" >
				<input type="submit" class="lang mandate-add-btn" value="<spring:message code="import.run" />" />
			</form:form>

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>