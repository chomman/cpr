<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.nb" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<c:if test="${not quasarView }">
			<jsp:include page="include/cpr-nav.jsp" />
		</c:if>
		<c:if test="${quasarView }">
			<jsp:include page="../include/quasar-nav.jsp" />
		</c:if>
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl> &raquo;
			<c:if test="${not quasarView }">
				<a:adminurl href="/cpr"><spring:message code="menu.cpr" /></a:adminurl> &raquo;
			</c:if>
			 <span><spring:message code="cpr.nb" /></span>
		</div>
		<h1><spring:message code="cpr.nb" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li><a class="active" href="<c:url value="${listUrl}"  />"><spring:message code="cpr.nb.view" /></a></li>
				<li><a href="<c:url value="${editUrl}0"  />"><spring:message code="cpr.nb.add" /></a></li>
			</ul>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			
				
			<c:if test="${not empty model.notifiedBodies}">
				
				<div class="search-box" >
					<span title="<spring:message code="form.quicksearch.title" />" class="tt"><spring:message code="form.quicksearch" />:</span>
					<input id="quick-search" type="text" />
				</div>
			
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="cpr.nb.code" /></th>
							<th><spring:message code="cpr.nb.name" /></th>
							<th><spring:message code="published" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.notifiedBodies}" var="i">
						 	<tr>
						 		<td>
						 			${i.noCode}
						 			<c:if test="${not empty i.aoCode}">
						 				(${i.aoCode})
						 			</c:if>
						 		</td>
						 		<td>${i.name}</td>
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
						 			<a href="<c:url value="${editUrl}${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
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
			
			<c:if test="${empty model.notifiedBodies}">
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