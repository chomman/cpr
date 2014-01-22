<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasAnyRole('ROLE_WEBMASTER','ROLE_ADMIN')">	
	<c:set var="isAdmin" value="true"/>
</sec:authorize>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="userlog" /></title>
		<script src="<c:url value="/resources/admin/js/user.autocomplete.js" />"></script>
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
			 <span><spring:message code="userlog" /></span>
		</div>
		<h1><spring:message code="userlog" /></h1>

		<div id="content">
			<c:if test="${isAdmin}">
		
			<form class="filter log" action="<c:url value="/admin/user/logs" />" method="get">
				<div>
					<span class="long filter-label"><spring:message code="form.orderby" />:</span>
					<select name="orderBy" class="chosenSmall">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
						</c:forEach>
					</select>
					<span class="filter-label"><spring:message code="date" /> od:</span>
					<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span class="filter-label">do:</span>
					<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
					
				</div>
				<div>
					<span class="long filter-label"><spring:message code="userlog.user" /></span>
					<input type="text" class="query" name="query"   value="${model.params.query}" />
					<input type="submit" value="Filtrovat" class="btn" />
				</div>
			</form>
			
			
				
				<c:if test="${not empty model.logs}">
				
				
				<!-- STRANKOVANIE -->
				<c:if test="${not empty model.paginationLinks}" >
					<div class="pagination">
					<c:forEach items="${model.paginationLinks}" var="i">
						<c:if test="${not empty i.url}">
							<a title="Stánka č. ${i.anchor}"  class="tt"  href="<c:url value="${i.url}"  />">${i.anchor}</a>
						</c:if>
						<c:if test="${empty i.url}">
							<span>${i.anchor}</span>
						</c:if>
					</c:forEach>
					</div>
				</c:if>
				
				
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="userlog.user"/></th>
							<th><spring:message code="userlog.login" /></th>
							<th><spring:message code="userlog.logout" /></th>
							<th><spring:message code="userlog.status" /></th>
							<th><spring:message code="userlog.ip" /></th>
						</tr>
					</thead>
					<tbody>
						<c:url value="/admin/user/edit/" var="urlToUser" />
						 <c:forEach items="${model.logs}" var="i">
						 	<tr>
						 		<td>
						 			<c:if test="${not empty i.user}">
						 			 	<a class="tt link" title="Zobrazit uživatele" href="${urlToUser}${i.user.id}">${i.user.firstName} ${i.user.lastName}</a>	
						 			</c:if>
						 			<c:if test="${empty i.user}">
						 			 		${i.userName }
						 			</c:if>
						 		
						 		</td>
						 		<td><joda:format value="${i.loginDateAndTime}" pattern="dd.MM.yyyy / HH:mm:ss"/></td>
						 		<td>
						 			<c:if test="${not empty i.logoutDateAndTime}">
						 				<joda:format value="${i.logoutDateAndTime}" pattern="dd.MM.yyyy / HH:mm:ss"/>
						 			</c:if>
						 			<c:if test="${empty i.logoutDateAndTime}">
						 				-
						 			</c:if>
						 		</td>
						 		<td>
						 			<c:if test="${i.loginSuccess}">
						 				<spring:message code="yes" />
						 			</c:if>
						 			<c:if test="${not i.loginSuccess}">
						 				<spring:message code="no" />
						 			</c:if>
						 		</td>
						 		<td>${i.ipAddress}</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.logs}">
				<p class="msg alert">
					<spring:message code="alert.empty" />
				</p>
			</c:if>
			</c:if>
			<c:if test="${not isAdmin}">
				<h4>Neautorizovaný přístup</h4>
			</c:if>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>