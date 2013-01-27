<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="menu.news" /></title>
		<script src="<c:url value="/resources/admin/js/article.autocomplete.js" />"></script>
	</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/article-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <span><spring:message code="menu.news" /></span>
		</div>
		<h1><spring:message code="menu.news" /></h1>

		<div id="content">
			
			<form class="filter article" action="<c:url value="/admin/articles" />" method="get">
				<div>
					<span class="long"><spring:message code="form.orderby" />:</span>
					<select name="orderBy">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
						</c:forEach>
					</select>
					<span class="fixed"><spring:message code="published" /></span>
					<select name="enabled" class="enabled">
							<option value=""  <c:if test="${empty model.params.enabled}" >selected="selected"</c:if> ><spring:message code="notmatter" /></option>
							<option value="1" <c:if test="${model.params.enabled}" >selected="selected"</c:if> ><spring:message code="yes"/></option>
							<option value="0" <c:if test="${not empty model.params.enabled and not model.params.enabled}" >selected="selected"</c:if> ><spring:message code="no"/></option>
					</select>	
				</div>
				<div>
					<span class="long"><spring:message code="published" /> od: </span>
					<input type="text" class="date"  name="publishedSince" value="<joda:format value="${model.params.publishedSince}" pattern="dd.MM.yyyy"/>" />
					<span class="fixed">do:</span>
					<input type="text" class="date" name="publishedUntil"  value="<joda:format value="${model.params.publishedUntil}" pattern="dd.MM.yyyy"/>" />
					
				</div>
				<div>
					<span class="long"><spring:message code="form.name" /></span>
					<input type="text" class="query" name="query"   value="${model.params.query}" />
					
					<input type="submit" value="Filtrovat" class="btn" />
				</div>
			</form>
			
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
				
				<c:if test="${not empty model.articles}">
				
				
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
							<tH><spring:message code="article.title" /></th>
							<tH><spring:message code="article.released" /></th>
							<th><spring:message code="published" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.articles}" var="i">
						 	<tr>
						 		<td>${i.title}</td>
						 		<td>
						 			<c:if test="${not empty i.publishedSince }" >
						 				<joda:format value="${i.publishedSince}" pattern="dd.MM.yyyy / HH:mm"/>
						 			</c:if>
						 			<c:if test="${empty i.publishedSince}" >
						 				<joda:format value="${i.created}" pattern="dd.MM.yyyy / HH:mm"/>
						 			</c:if>
						 		</td>
						 		
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
						 				<joda:format value="${i.created}" pattern="dd.MM.yyyy / HH:mm"/>
						 			</c:if>
						 			<c:if test="${not empty i.changedBy}">
						 				<joda:format value="${i.changed}" pattern="dd.MM.yyyy / HH:mm"/>
						 			</c:if>
						 		</td>
						 		<td class="edit">
						 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/article/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/article/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.articles}">
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