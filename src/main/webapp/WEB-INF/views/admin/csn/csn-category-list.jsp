<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="csn.category" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="csn-nav.jsp" />
		
	</div>	
	<div id="right">
		
		<!-- breadcrumb  -->
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/csn" />"><spring:message code="menu.csn" /></a> &raquo;
			
			<c:if test="${not empty model.categoryNode}">
				<a href="<c:url value="/admin/csn/categories" />"><spring:message code="csn.category" /></a> &raquo;
				<span>${model.categoryNode.name}</span>
			</c:if>
			
			<c:if test="${empty model.categoryNode}">
			<span><spring:message code="csn.category" /></span>
			</c:if> 
		</div>
		
		
		
		<!-- HEAD label -->
		<h1>
			<c:if test="${not empty model.categoryNode}">
				${model.categoryNode.code} ${model.categoryNode.name}
			</c:if>
			
			<c:if test="${empty model.categoryNode}">
				<spring:message code="csn.category" />
			</c:if>
		</h1>




		<div id="content">
			
			<ul class="sub-nav">
				<li><a class="active" href="<c:url value="/admin/csn/categories"  />"><spring:message code="csn.category.list" /></a></li>
				<li><a href="<c:url value="/admin/csn/category/edit/0"  />"><spring:message code="csn.category.new" /></a></li>
				<li><a href="<c:url value="/admin/csn/category/import"  />"><spring:message code="csn.category.import" /></a></li>
			</ul>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			<c:if test="${not empty model.categoryNode}">
				<!-- Children category node -->
				<h3 class="parentNode">
					<a href="<c:url value="/admin/csn/categories" />">
						<span>&laquo;</span> ${model.categoryNode.code} - ${model.categoryNode.name}
					</a>
				</h3>
				
				<c:if test="${not empty model.categoryNode.children}">
					 	<table class="data">
							<tbody>
								 <c:forEach items="${model.categoryNode.children}" var="i">
								 	<tr>
								 		<td class="c w40">${model.categoryNode.code} ${i.code}</td>
								 		<td>
								 			${i.name}
								 		</td>
								 		<td class="edit">
								 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/csn/category/edit/${i.id}"  />">
								 				<spring:message code="form.edit" />
								 			</a>
								 		</td>
								 		<td class="delete">
								 			<a class="confirm"  href="<c:url value="/admin/csn/category/delete/${i.id}"  />">
								 				<spring:message code="form.delete" />
								 			</a>
								 		</td>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				</c:if>
				
				<c:if test="${ empty model.categoryNode.children}">
					<p class="msg alert">
						<spring:message code="alert.empty" />
					</p>
				</c:if>
			</c:if>
				
			
			
			<c:if test="${not empty model.csnCategories}">
			<!-- SUB ROOT Categories -->	
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="csn.category.code" /></th>
							<tH><spring:message code="csn.category.form.name" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.csnCategories}" var="i">
						 	<tr>
						 		<td class="c">${i.code}</td>
						 		<td>
						 			<a href="<c:url value="/admin/csn/categories?code=${i.searchCode}" />">
						 			${i.name}
						 			</a>
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
						 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/csn/category/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/csn/category/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>