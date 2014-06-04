<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="admin.portalUser" /></title>
	<script src="<c:url value="/resources/admin/js/user.autocomplete.js" />"></script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <span><spring:message code="admin.portalUser" /></span>
			</div>
			<h1><spring:message code="admin.portalUser" /></h1>
	
			<div id="content">
				
				
								
			<form class="filter user" method="get">
				<div>
					<span class="long filter-label"><spring:message code="form.orderby" />:</span>
					<select name="orderBy" class="chosenSmall">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
						</c:forEach>
					</select>
					<span class="filter-label"><spring:message code="user.registratedFrom" /></span>
					<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span>do:</span>
					<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
					
				</div>
				<div>
					<span class="long filter-label"><spring:message code="form.name" /></span>
					<input type="text" class="query" name="query"   value="${model.params.query}" />
					<span class="filter-label"><spring:message code="user.activated" /></span>
					<select name="enabled" class="enabled chosenMini">
							<option value=""  <c:if test="${empty model.params.enabled}" >selected="selected"</c:if> ><spring:message code="notmatter" /></option>
							<option value="1" <c:if test="${model.params.enabled}" >selected="selected"</c:if> ><spring:message code="yes"/></option>
							<option value="0" <c:if test="${not empty model.params.enabled and not model.params.enabled}" >selected="selected"</c:if> ><spring:message code="no"/></option>
					</select>
					<input type="submit" value="Filtrovat" class="btn" />
				</div>
			</form>			
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
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
							<tH><spring:message code="user.firstname" /> a <spring:message code="user.lastname" /></th>
							<th><spring:message code="user.activated" /></th>
							<th><spring:message code="admin.portalUser.validity" /></th>
							<th><spring:message code="admin.portalUser.onlinePublicationsCount" /></th>
							<th><spring:message code="form.edit" /></th>
						</tr>
					</thead>
					<tbody> 
						 <c:forEach items="${model.users}" var="i">
						 	<tr <c:if test="${not empty i.userInfo.synced and not i.userInfo.synced }">class="pj-unsynced"</c:if>>
						 		<td>
						 			<strong>
						 				<c:if test="${not empty i.userInfo.synced and not i.userInfo.synced }">
						 					<span class="pj-unsync-alert tt" title="<spring:message code="admin.portalUser.unsyncAlert" />"></span>
						 				</c:if>
						 			<c:out value="${i.firstName} ${i.lastName}" />
						 			</strong>
						 			<c:if test="${not empty i.userInfo and not empty i.userInfo.companyName}">
						 				&nbsp; (<c:out value="${i.userInfo.companyName}" />)
						 			</c:if>
						 		</td>
						 								 		
						 		<td class="w100">
						 			<c:if test="${i.hasActiveRegistration}">
						 				<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
						 					<spring:message code="yes" />
						 				</span>
						 			</c:if>
						 			<c:if test="${not i.hasActiveRegistration}">
						 				<span class="published no tt" title="<spring:message code="published.no.title" />" >
						 					<spring:message code="no" />
						 				</span>
						 			</c:if>
						 		</td>
						 		<td class="last-edit">
						 			<c:if test="${not empty i.registrationValidity}">
						 				<joda:format value="${i.registrationValidity}" pattern="dd.MM.yyyy"/>
						 			</c:if>
						 			<c:if test="${empty i.registrationValidity}">
						 				-
						 			</c:if>
						 		</td>
						 		<td class="w100 c">
						 			${i.countOfActivePublications} 
						 		</td>
						 		<td class="edit">
						 			<a:adminurl href="/portal/user/${i.id}">
						 				<spring:message code="form.edit" />
						 			</a:adminurl>
						 		</td>						 		
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