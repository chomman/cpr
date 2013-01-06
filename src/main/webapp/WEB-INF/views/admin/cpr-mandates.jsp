<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="menu.cpr.mandates" /></title>
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
			 <span><spring:message code="menu.cpr.mandates" /></span>
		</div>
		<h1><spring:message code="menu.cpr.mandates" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li><a class="active" href="<c:url value="/admin/cpr/mandates"  />">
						<spring:message code="cpr.mandates.view" />
					</a>
				</li>
				<li>
					<a href="<c:url value="/admin/cpr/mandates/edit/0"  />">
						<spring:message code="cpr.mandates.add" />
					</a>
				</li>
			</ul>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			
			<c:if test="${not empty model.paginationLinks}" >
				<c:forEach items="${model.paginationLinks}" var="i">
					<c:if test="${not empty i.url}">
						<a href="<c:url value="${i.url}"  />">${i.anchor}</a>
					</c:if>
					<c:if test="${empty i.url}">
						<span>${i.anchor}</span>
					</c:if>
				</c:forEach>
			</c:if>
				
			<c:if test="${not empty model.mandates}">
				
				
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="cpr.mandates.name" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.mandates}" var="i">
						 	<tr>
						 		<td>${i.mandateName}</td>
						 		<td class="last-edit">
						 			<c:if test="${empty i.changedBy}">
						 				<joda:format value="${i.created}" pattern="dd.MM.yyyy / hh:mm"/>
						 			</c:if>
						 			<c:if test="${not empty i.changedBy}">
						 				<joda:format value="${i.changed}" pattern="dd.MM.yyyy / hh:mm"/>
						 			</c:if>
						 		</td>
						 		<td class="edit">
						 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/cpr/mandates/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/cpr/mandates/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.mandates}">
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