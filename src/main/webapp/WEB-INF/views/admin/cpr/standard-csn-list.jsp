<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="menu.cpr.norm" /></title>
<script src="<c:url value="/resources/public/js/picker.jquery.js" />"></script>
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
			 <span><spring:message code="menu.cpr.norm" /></span>
		</div>
		<h1><spring:message code="cpr.groups.title" /></h1>

		<div id="content">
						
			<ul class="sub-nav">
				<li><a class="active" href="<c:url value="/admin/cpr/standard-csn"  />"><spring:message code="cpr.standard.view" /></a></li>
				<li><a href="<c:url value="/admin/cpr/standard-csn/edit/0"  />"><spring:message code="cpr.standard.add" /></a></li>
				<li><a:url extraAttr="target;_blank" href="${model.webpage.code}"><a:localizedValue object="${model.webpage}" fieldName="name" /></a:url></li>
			</ul>
			
			<form class="filter" method="get">
				<div>
					<span class="filter-label long"><spring:message code="form.name" />/Označení</span>
					<input type="text" class="query " name="query"   value="${model.params.query}" />
					
					<input type="submit" value="Filtrovat" class="btn filter-btn-standard" />
				</div>
			</form>
			
			
			<div class="items-count">
				<span><spring:message code="items.count" arguments="${model.count}" /></span>
			</div>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
						
			<c:if test="${not empty model.csns}">
				
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
							<th><spring:message code="cpr.csn.name" /></th>
							<th><spring:message code="cpr.csn.onlineid" /></th>
							<th><spring:message code="csn.form.symbol" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.csns}" var="i">
						 	<c:if test="${not empty i.standardStatus }">
						 		<tr class="${i.standardStatus.cssClass}">
						 	</c:if>
						 	<c:if test="${empty i.standardStatus }">
						 		<tr>
						 	</c:if>
						 		<td class="standarardId">
							 		<a title="Zobrazit a upraviť položku" href="<c:url value="/admin/cpr/standard-csn/edit/${i.id}"  />">
							 			${i.csnName}
							 		</a>
						 		</td>
						 		<td class="w100 c">
						 			<c:if test="${not empty i.csnOnlineId}">
						 				<a class="file pdf" target="_blank" href="${fn:replace(model.csnOnlineUrl, '{0}', i.csnOnlineId)}">
						 					${i.csnOnlineId}
						 				</a>
						 			</c:if>
						 			<c:if test="${empty i.csnOnlineId}">
						 				-
						 			</c:if>
						 		</td>
						 		<td class="w100 c">
						 			<c:if test="${not empty i.classificationSymbol}">
						 					${i.classificationSymbol}
						 			</c:if>
						 			<c:if test="${empty i.classificationSymbol}">
						 				-
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
						 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/cpr/standard-csn/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/cpr/standard-csn/delete/${i.id}"  />">
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