<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="settings.exceptions" /></title>
	</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/settings-nav.jsp" />
		
	</div>	
	<div id="right">
		
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/settings" />"><spring:message code="menu.settings" /></a> &raquo;
			 <a href="<c:url value="/admin/settings/exceptions" />"><spring:message code="settings.exceptions" /></a> &raquo;
			 <span><spring:message code="settings.exceptions" /></span>
		</div>
		<h1>${model.exception.type}</h1>

		<div id="content">

			<table class="exception">
				<tr>
					<td><strong>ID:</strong></td>
					<td>${model.exception.id}</td>
				</tr>
				<tr>
					<td><strong>Type:</strong></td>
					<td>${model.exception.type}</td>
				</tr>
				<tr>
					<td><strong>User:</strong></td>
					<td>
						<c:if test="${not empty model.exception.user }">
							${model.exception.user.email} - <em>${model.exception.user.firstName} ${model.exception.user.lastName}</em>
						</c:if> 
						<c:if test="${empty model.exception.user }">
							-
						</c:if> 
					</td>
				</tr>
				<tr>
					<td><strong>Time:</strong></td>
					<td><joda:format value="${model.exception.created}" pattern="dd.MM.yyyy / HH:mm:ss"/></td>
				</tr>
				<tr>
					<td><strong>Request method:</strong></td>
					<td>
						<c:if test="${not empty model.exception.mehtod}">
							${model.exception.mehtod}
						</c:if> 
					</td>
				</tr>
				<tr>
					<td><strong>Request url:</strong></td>
					<td>
						<c:if test="${not empty model.exception.url}">
							${model.exception.url}
						</c:if> 
					</td>
				</tr>
				<tr>
					<td><strong>Message:</strong></td>
					<td>
						<c:if test="${not empty model.exception.message}">
							${model.exception.message}
						</c:if> 
					</td>
				</tr>
				<tr>
					<td><strong>Referer:</strong></td>
					<td>
						<c:if test="${not empty model.exception.referer}">
							${model.exception.referer}
						</c:if> 
					</td>
				</tr>
				<tr>
					<td><strong>Query params:</strong></td>
					<td>
						<c:if test="${not empty model.exception.queryParams}">
							${model.exception.queryParams}
						</c:if> 
					</td>
				</tr>
				<tr>
					<td><strong>Request params:</strong></td>
					<td>
						<c:if test="${not empty model.exception.requestParams}">
							${model.exception.requestParams}
						</c:if> 
					</td>
				</tr>
				<tr>
					<td><strong>Request headesrs:</strong></td>
					<td>
						<c:if test="${not empty model.exception.requestHeaders}">
							${model.exception.requestHeaders}
						</c:if> 
					</td>
				</tr>
				<tr>
					<td><strong>StackTrace:</strong></td>
					<td>
						<c:if test="${not empty model.exception.stackTrace}">
							${model.exception.stackTrace}
						</c:if> 
					</td>
				</tr>
			</table>
			
		
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>
</sec:authorize>