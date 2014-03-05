<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="cpr.report" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a:adminurl href="/"><spring:message code="menu.home" /> </a:adminurl>  &raquo;
			 <span><spring:message code="cpr.report.list" /></span>
		</div>
		<h1><spring:message code="cpr.report" /></h1>

		<div id="content">
					
			<ul class="sub-nav">
				<li>
					<a:adminurl href="/cpr/reports" cssClass="active">
						<spring:message code="cpr.report.list" />
					</a:adminurl>
				</li>
				<li>
					<a:adminurl href="/cpr/report/add">
						<spring:message code="cpr.report.add" />
					</a:adminurl>
				</li>
			</ul>

		
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
								
			<c:if test="${not empty model.reports}">
								
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="cpr.report.period" /></th>
							<th><spring:message code="published" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.reports}" var="i">
							<tr>
								<td>
							 		<a:adminurl href="/cpr/report/edit/${i.id}">
							 		</a:adminurl>
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
						 				<joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/>
						 			</c:if>
						 			<c:if test="${not empty i.changedBy}">
						 				<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
						 			</c:if>
						 		</td>
						 		<td class="edit">
						 			<a:adminurl href="/cpr/report/edit/${i.id}">
						 				<spring:message code="form.edit" />
							 		</a:adminurl>
						 		</td>
						 		<td class="delete">
						 			<a:adminurl href="/cpr/report/delete/${i.id}" cssClass="confirm">
						 				<spring:message code="form.delete" />
							 		</a:adminurl>
						 			
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.reports}">
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