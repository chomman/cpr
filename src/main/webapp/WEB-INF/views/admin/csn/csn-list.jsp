<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="menu.csn" /></title>
	<script src="<c:url value="/resources/public/js/terminology.autocomplete.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="csn-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/csn" />"><spring:message code="menu.csn" /></a> &raquo;
			 <span><spring:message code="menu.csn" /></span>
		</div>
		<h1><spring:message code="csn.title" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li><a class="active" href="<c:url value="/admin/csn"  />"><spring:message code="csn.list" /></a></li>
				<li><a href="<c:url value="/admin/csn/edit/0"  />"><spring:message code="csn.add" /></a></li>
				<li><a href="<c:url value="/admin/csn/csvimport"  />"><spring:message code="csn.csvimport" /></a></li>
			</ul>
			
			<form class="filter" action="<c:url value="/admin/csn" />" method="get">
				<div>
					<span class="long filter-label"><spring:message code="form.orderby" />:</span>
					<select name="orderBy" class="chosenSmall">
						<c:forEach items="${model.order}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >
							<spring:message code="${i.name}" />
							</option>
						</c:forEach>
					</select>
				</div>
				<div>
					<span class="long filter-label"><spring:message code="csn.form.symbol" />:</span>
					<input type="text" class="filter-input-middle csnCategory" name="csnCategory"   value="${model.params.csnCategory}" />
				</div>
				<div>
					<span class="long filter-label"><spring:message code="cpr.csn.onlineid" />:</span>
					<input type="text" class="filter-input-middle catalogId" name="catalogId"   value="${model.params.catalogId}" />
				</div>
				<div>
					<span class="long filter-label"><spring:message code="csn.search.term" />:</span>
					<input type="text" class="filter-input-long" name="query"   value="${model.params.query}" />
					<input type="submit" value="Filtrovat" class="btn csn" />
				</div>
			</form>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
				
			
				
			<c:if test="${not empty model.csns}">
				
				<!-- STRANKOVANIE -->
				<c:if test="${not empty model.paginationLinks}" >
					<div class="pagination">
					<c:forEach items="${model.paginationLinks}" var="i">
						<c:if test="${not empty i.url}">
							<a href="<c:url value="${i.url}"  />">${i.anchor}</a>
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
							<tH><spring:message code="csn.form.name" /></th>
							<th><spring:message code="csn.form.published" /></th>
							<th><spring:message code="csn.form.czechName" /></th>
							<th><spring:message code="csn.form.category" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.csns}" var="i">
						 	<tr>
						 		<td class="mw150">
						 			<a title="Editovat" href="<c:url value="/admin/csn/edit/${i.id}"  />">${i.csnId}</a>
						 		</td>
						 		<td class="mw60 c"><joda:format value="${i.published}" pattern="MMM / yyyy"/></td>
						 		<td >${i.czechName}</td>
						 		<td >${i.csnCategory.name}</td>
						 		<td class="last-edit">
						 			<c:if test="${empty i.changedBy}">
						 				<joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/>
						 			</c:if>
						 			<c:if test="${not empty i.changedBy}">
						 				<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
						 			</c:if>
						 		</td>
						 		<td class="edit">
						 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/csn/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/csn/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.csns}">
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