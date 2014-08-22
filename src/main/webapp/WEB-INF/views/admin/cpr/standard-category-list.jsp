<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="standardCategories" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
			<jsp:include page="include/cpr-nav.jsp" />
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl> &raquo;
			 <span><spring:message code="standardCategories" /></span>
		</div>
		<h1><spring:message code="standardCategories" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li>
					<a:adminurl href="/cpr/standard-category/0">
						<spring:message code="standardCategory.add" />
					</a:adminurl>
				</li>
			</ul>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			
				
			<c:if test="${not empty model.categories}">
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="standardCategory.code" /></th>
							<th><spring:message code="standardCategory.name" /></th>
							<th><spring:message code="standardCategory.regulations" /></th>
							<th><spring:message code="form.edit" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.categories}" var="i">
						 	<tr>
						 		<td class="c w100">
						 			<strong>${not empty i.code ? i.code : '-'}</strong>
						 		</td>
						 		<td class="">
						 			<a:adminurl href="/cpr/standard-category/${i.id}">
						 				${i.nameCzech}
						 			</a:adminurl>
						 		</td>
						 		<td class="">
						 			<c:if test="${not empty i.regulations}">
						 				<c:forEach items="${i.regulations}" var="j" varStatus="s">
						 					<a:adminurl href="/cpr/regulation/${j.id}">
						 						${j.code}
						 					</a:adminurl>
						 						${!s.last ? ' &nbsp;/&nbsp; ' : ''}
						 				</c:forEach>
						 			</c:if>
						 		</td>
						 		<td class="edit">
						 			<a:adminurl href="/cpr/regulation/${i.id}">
						 				<spring:message code="form.edit" />
						 			</a:adminurl>
						 		</td>
						 		
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${empty model.categories}">
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