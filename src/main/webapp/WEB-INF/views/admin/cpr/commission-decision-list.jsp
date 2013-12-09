<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.br" /></title>
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
			 <span><spring:message code="cpr.commisiondecision.name" /></span>
		</div>
		<h1><spring:message code="cpr.commisiondecision.name" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li><a class="active" href="<c:url value="${model.url}"  />"><spring:message code="cpr.commisiondecision.view" /></a></li>
				<li><a href="<c:url value="${model.url}/edit/0"  />"><spring:message code="cpr.commisiondecision.create" /></a></li>
			</ul>
			
			<c:if test="${not empty model.successDelete and model.successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			<c:if test="${not empty model.successDelete and not model.successDelete}">
				<p class="msg error"><spring:message code="error.delete" /></p>
			</c:if>
			

			<c:if test="${not empty model.commissionDecisions}">
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="form.name" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.commissionDecisions}" var="i">
						 	<tr>
						 		<td>
							 		<a href="<c:url value="${model.url}/edit/${i.id}"  />">
							 			${i.czechLabel}
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
						 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="${model.url}/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="${model.url}/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.commissionDecisions}">
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