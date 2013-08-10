<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="menu.csn" /></title>
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
			</ul>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
				
			
				
			<c:if test="${not empty model.csns}">
				
				<div class="search-box" >
					<span title="<spring:message code="form.quicksearch.title" />" class="tt"><spring:message code="form.quicksearch" />:</span>
					<input id="quick-search" type="text" />
				</div>
			
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="csn.form.name" /></th>
							<th><spring:message code="csn.form.published" /></th>
							<th><spring:message code="csn.form.czechName" /></th>
							<th><spring:message code="csn.form.category" /></th>
							<th><spring:message code="published" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.csns}" var="i">
						 	<tr>
						 		<td>${i.csnId}</td>
						 		<td>${i.published}</td>
						 		<td>${i.czechName}</td>
						 		<td>${i.csnCategory.name}</td>
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
						 				<joda:format value="${i.created}" pattern="${dateTimeFormat}"/>
						 			</c:if>
						 			<c:if test="${not empty i.changedBy}">
						 				<joda:format value="${i.changed}" pattern="${dateTimeFormat}"/>
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