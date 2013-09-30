<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="menu.news" /></title>
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
			 <span><spring:message code="menu.users" /></span>
		</div>
		<h1><spring:message code="menu.users" /></h1>

		<div id="content">
						
			<form class="filter user" action="<c:url value="/admin/users" />" method="get">
				<div>
					<span class="long"><spring:message code="form.orderby" />:</span>
					<select name="orderBy">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
						</c:forEach>
					</select>
					<span><spring:message code="user.registratedFrom" /></span>
					<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span>do:</span>
					<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
					
				</div>
				<div>
					<span class="long"><spring:message code="form.name" /></span>
					<input type="text" class="query" name="query"   value="${model.params.query}" />
					<span><spring:message code="user.activated" /></span>
					<select name="enabled" class="enabled">
							<option value=""  <c:if test="${empty model.params.enabled}" >selected="selected"</c:if> ><spring:message code="notmatter" /></option>
							<option value="1" <c:if test="${model.params.enabled}" >selected="selected"</c:if> ><spring:message code="yes"/></option>
							<option value="0" <c:if test="${not empty model.params.enabled and not model.params.enabled}" >selected="selected"</c:if> ><spring:message code="no"/></option>
					</select>
					<input type="submit" value="Filtrovat" class="btn" />
				</div>
			</form>
			
			<c:if test="${not empty successDelete}">
				<c:if test="${successDelete == 1}">
					<p class="msg ok"><spring:message code="user.success.delete" /></p>
				</c:if>
				<c:if test="${successDelete == 2}">
					<p class="msg error"><spring:message code="user.error2.delete" /></p>
				</c:if>
				<c:if test="${successDelete == 3}">
					<p class="msg error"><spring:message code="user.error3.delete" /></p>
				</c:if>
				<c:if test="${successDelete == 4}">
					<p class="msg error"><spring:message code="user.error4.delete" /></p>
				</c:if>
			</c:if>
				
				<c:if test="${not empty model.users}">
				
				
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
							<tH><spring:message code="user.username" /></th>
							<tH><spring:message code="user.firstname" /> a <spring:message code="user.lastname" /></th>
							<th><spring:message code="user.registratedFrom" /></th>
							<th><spring:message code="user.activated" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<sec:authorize access="hasAnyRole('ROLE_WEBMASTER','ROLE_ADMIN')">
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
							</sec:authorize>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.users}" var="i">
						 	<tr>
						 		<td>${i.email}</td>
						 		<td>${i.firstName} ${i.lastName}</td>
						 		<td><joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/></td>
						 		
						 		
						 		<td class="w100">
						 			<c:if test="${i.enabled}">
						 				<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
						 					<spring:message code="yes" />
						 				</span>
						 			</c:if>
						 			<c:if test="${not i.enabled}">
						 				<span class="published no tt" title="<spring:message code="published.no.title" />" >
						 					<spring:message code="no" />
						 				</span>
						 			</c:if>
						 		</td>
						 		<td class="last-edit">
						 			<c:if test="${empty i.changedBy}">
						 				<joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/>
						 			</c:if>
						 			<c:if test="${not empty i.changedBy}">
						 				<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
						 			</c:if>
						 		</td>
						 		<sec:authorize access="hasAnyRole('ROLE_WEBMASTER','ROLE_ADMIN')">
						 		<td class="edit">
						 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/user/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/user/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 		</sec:authorize>
						 		
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.users}">
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