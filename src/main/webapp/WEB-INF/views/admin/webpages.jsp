<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="webpages" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/webpages-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <span><spring:message code="webpages" /></span>
		</div>
		<h1><spring:message code="webpages" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li><a class="active" href="<c:url value="/admin/webpages"  />"><spring:message code="webpages.view" /></a></li>
				<li><a href="<c:url value="/admin/webpages/edit/0"  />"><spring:message code="webpages.add" /></a></li>
			</ul>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			<c:if test="${not empty isNotEmptyError}">
				<p class="msg error"><spring:message code="cpr.group.isnotempty" /></p>
			</c:if>
			
			
				
			<c:if test="${not empty model.webpages}">
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="webpage.name" /></th>
							<th class="c"><spring:message code="webpage.category.table" /></th>
							<th><spring:message code="published" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<c:if test="${isLoggedWebmaster}">
							<th><spring:message code="form.delete" /></th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.webpages}" var="i">
						 	<tr>
						 		<td>${i.nameCzech}</td>
						 		<td>${i.webpageCategory.name}</td>
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
						 		<td class="edit">
						 			<a href="<c:url value="/admin/webpages/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<c:if test="${isLoggedWebmaster}">
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/webpages/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 		</c:if>
						 	</tr>
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