<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="user.roles.detail" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/user-nav.jsp" />
		
	</div>	
	<div id="right">
		
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/users" />"><spring:message code="menu.users" /></a> &raquo;
			 <span><spring:message code="user.roles.detail" /></span>
		</div>
		<h1><spring:message code="user.roles.detail" /></h1>

		<div id="content">
			<c:if test="${not empty model.roles}">
				<c:forEach items="${model.roles}" var="role">
						<div class="role">
							<div class="hbox">
								<h3>${role.name}</h3>
							</div>
							<div class="role-lable">
								${role.longDescription}
							</div>
						</div>
							
				</c:forEach>
			</c:if>

		
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>