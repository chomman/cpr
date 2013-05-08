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
			 <span><spring:message code="settings.exceptions" /></span>
		</div>
		<h1><spring:message code="settings.exceptions" /></h1>

		<div id="content">
		<c:if test="${not empty model.successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
		</c:if>
		
			<form class="filter log" action="<c:url value="/admin/settings/exceptions" />" method="get">
				<div>
					<span><spring:message code="date" /> od:</span>
					<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span>do:</span>
					<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
					
				</div>
				<div>
					<span class="long">Typ</span>
					<input type="text" class="query" name="query"   value="${model.params.query}" />
					<input type="submit" value="Filtrovat" class="btn" />
				</div>
			</form>
			
			
				
				<c:if test="${not empty model.exceptions}">
				
				
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
							<th><spring:message code="settings.exceptions.user" /></th>
							<tH><spring:message code="settings.exceptions.type"/></th>
							<th><spring:message code="settings.exceptions.create" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						<c:url value="/admin/user/edit/" var="urlToUser" />
						 <c:forEach items="${model.exceptions}" var="i">
						 	<tr>
						 		<td>
						 			<c:if test="${not empty i.user}">
						 			 	<a class="tt link" title="Zobrazit uživatele" href="${urlToUser}${i.user.id}">${i.user.firstName} ${i.user.lastName}</a>	
						 			</c:if>
						 			<c:if test="${empty i.user}">
						 			 		-
						 			</c:if>
						 		</td>
						 		<td>
						 		<a href="<c:url value="/admin/settings/exceptions" />/${i.id}">${i.type}</a></td>
						 		<td><joda:format value="${i.created}" pattern="dd.MM.yyyy / HH:mm:ss"/></td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/settings/exceptions/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.exceptions}">
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
</sec:authorize>