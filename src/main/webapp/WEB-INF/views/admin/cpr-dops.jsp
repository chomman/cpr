<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="menu.cpr.dop" /></title>
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
			 <span><spring:message code="menu.cpr.dop" /></span>
		</div>
		<h1><spring:message code="menu.cpr.dop" /></h1>

		<div id="content">
			
			<form class="filter" action="<c:url value="/admin/cpr/dop" />" method="get">
				<div>
					<span><spring:message code="cpr.dop.filter" /></span>
					<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span>do:</span>
					<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
				
					<input type="submit" value="Filtrovat" class="clearmargin btn" />
				</div>
			</form>
			
					
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
						
			<c:if test="${not empty model.dops}">
				
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
							<tH><spring:message code="cpr.dop.created" /></th>
							<th><spring:message code="cpr.dop.ehn" /></th>
							<th><spring:message code="cpr.dop.export.pdf" /></th>
							<th><spring:message code="form.view" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.dops}" var="i">
						 	<tr>
						 		<td><joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/></td>
						 		<td>
						 		<a href="<c:url value="/admin/cpr/standard/edit/${i.standard.id}" />" class="link">${i.standard.standardId}</a>
						 		<em>${i.standard.standardName}</em></td>
						 		<td>
						 			<a class="pdf" href="<c:url value="/dop/export/pdf/${i.token}" />"><spring:message code="cpr.dop.export.pdf" /></a>
						 		</td>
						 		<td class="edit">
						 			<a class="tt" title="Zobrazit DoP?" href="<c:url value="/admin/cpr/dop/${i.id}"  />">
						 				<spring:message code="form.view" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/cpr/dop/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.dops}">
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