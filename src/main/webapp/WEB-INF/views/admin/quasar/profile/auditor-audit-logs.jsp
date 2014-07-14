<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="auditLogs" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
	<script src="<c:url value="/resources/admin/js/scripts.quasar.js" />"></script>

</head>
<body>
<div id="wrapper">
		<div id="breadcrumb">
			 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
			 <span><spring:message code="auditLogs" /></span>
		</div>
		<h1><spring:message code="auditLogs" /></h1>

		<div id="content">
									
		<ul class="sub-nav auditor-nav">
			<li>
				<a:adminurl href="/quasar/audit-logs">
					<spring:message code="quasar.show" />
				</a:adminurl>
			</li>
			<li>
				<a:adminurl href="/quasar/audit-log/0">
					<spring:message code="quasar.add" />
				</a:adminurl>
			</li>
		</ul>
		<form class="filter user" method="get">
			<div>
				<span class="filter-label"><spring:message code="logStatus" />:</span>
				<select name="status" class="chosenMini">
					<option value=""><spring:message code="notmatter" /></option>
					<c:forEach items="${model.statuses}" var="i">
						<option value="${i.id}" <c:if test="${i.id == model.params.status}" >selected="selected"</c:if> >
							<spring:message code="${i.code}" />
						</option>
					</c:forEach>
				</select>
				<span class="filter-label">Created from:</span>
				<input type="text" class="date"  name="dateFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
				<span class="filter-label">to:</span>
				<input type="text" class="date" name="dateTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
				<input type="submit" value="Filter" class="btn" />
			</div>
		</form>		
					
						
			<c:if test="${not empty model.logs}">
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
							<th><spring:message code="logStatus" /></th>
							<th><spring:message code="auditLog.auditLog" /> date</th>
							<th><spring:message code="auditLog.auditDays" /></th>
							<th><spring:message code="auditLog.audits" /></th>
						
							<th>Changed</th>
							<th>&nbsp;</th> 
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.logs}" var="i">
							<tr class="qs-log-status-${i.status.id}">  
								<td class="w100 qs-status">
									<span class="qs-log-status"><spring:message code="${i.status.code}" /></span>
								</td>	 
								<td>
									<c:if test="${not i.status.locked}">
										<a:adminurl href="/quasar/audit-log/${i.id}">
											<spring:message code="auditLog.auditLog" /> - 
											<strong><joda:format value="${i.created}" pattern="dd.MM.yyyy"/></strong>
										</a:adminurl>
									</c:if>
									<c:if test="${i.status.locked}">
										<spring:message code="auditLog.auditLog" /> - 
										<strong><joda:format value="${i.created}" pattern="dd.MM.yyyy"/></strong>
									</c:if>
									<c:if test="${i.revision > 1}">
										(<spring:message code="auditLog.auditLog.revision" /> ${i.revision})
									</c:if>
								</td>
								<td class="w40 c">
								<strong>${i.sumOfAuditDays}</strong>
								</td>
								<td class="w40 c">
								<strong>${i.countOfAudits}</strong>
								</td>
								<td class="last-edit">
									<joda:format value="${i.changed}" pattern="dd.MM.yyyy HH:mm"/> / ${i.changedBy.name} 
								</td>
								<td class="edit">
									<c:if test="${not i.status.locked}">
										<a:adminurl href="/quasar/audit-log/${i.id}">
											<spring:message code="quasar.edit" />
										</a:adminurl>
									</c:if>
									<c:if test="${i.status.locked}">
										<strong>(<spring:message code="locked" />)</strong>
									</c:if>
								</td>
							</tr>
						
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.logs}">
				<p class="msg alert">
					<spring:message code="alert.empty" />
				</p>
			</c:if>

		</div>	
	</div>
</body>
</html>