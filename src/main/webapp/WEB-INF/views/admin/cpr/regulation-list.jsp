<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="regulations" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
			<jsp:include page="include/cpr-nav.jsp" />
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl> &raquo;
			 <span><spring:message code="regulations" /></span>
		</div>
		<h1>EU / ČR / SK <spring:message code="regulations" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li>
					<a:adminurl href="/cpr/regulations" activeTab="true">
						<spring:message code="regulation.view" />
					</a:adminurl>
				</li>
				<li>
					<a:adminurl href="/cpr/regulation/0">
						<spring:message code="regulation.add" />
					</a:adminurl>
				</li>
			</ul>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			
				
			<c:if test="${not empty model.regulations}">
				
				<div class="search-box" >
					<span title="<spring:message code="form.quicksearch.title" />" class="tt"><spring:message code="form.quicksearch" />:</span>
					<input id="quick-search" type="text" />
				</div>
			
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="regulation.code" /></th>
							<th><spring:message code="regulation.eu" /></th>
							<th><spring:message code="regulation.cs" /></th>
							<th><spring:message code="regulation.sk" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.regulations}" var="i">
						 	<tr>
						 		<td class="c">
						 			<a:adminurl href="/cpr/regulation/${i.id}">
						 				${i.code}
						 			</a:adminurl>
						 		</td>
						 		<td class="w50 c ${i.euRegulation ? 'scope-yes' : 'scope-no'}">
									${i.euRegulation ? 'Ano' : 'Ne'}
								</td>
								<td class="w50 c ${i.csRegulation ? 'scope-yes' : 'scope-no'}">
									${i.csRegulation ? 'Ano' : 'Ne'}
								</td>	
						 		<td class="w50 c ${i.skRegulation ? 'scope-yes' : 'scope-no'}">
									${i.skRegulation ? 'Ano' : 'Ne'}
								</td>
						 		<td class="edit">
						 			<a:adminurl href="/cpr/regulation/${i.id}">
						 				<spring:message code="form.edit" />
						 			</a:adminurl>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="${deleteUrl}${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${empty model.regulations}">
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