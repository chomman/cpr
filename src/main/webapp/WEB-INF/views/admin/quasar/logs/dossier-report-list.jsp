<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ taglib prefix="quasar"  uri="http://nlfnorm.cz/quasar"%>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="documentationLogs" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
	<script src="<c:url value="/resources/admin/js/scripts.quasar.js" />"></script>

</head>
<body>
	<div id="wrapper">
		<c:if test="${model.isQuasarAdmin}">
		<div id="left">
			<jsp:include page="../../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
		</c:if>
			<div id="breadcrumb">
				<c:if test="${model.isQuasarAdmin}">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				</c:if>
				 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
				 <span><spring:message code="documentationLogs" /></span>
			</div>
			<h1><spring:message code="documentationLogs" /></h1>
	
			<div id="content">
				
				<c:if test="${common.user.auditor}">									
				<ul class="sub-nav auditor-nav">
					<li>
						<a:adminurl href="/quasar/dossier-reports">
							<spring:message code="quasar.show" />
						</a:adminurl>
					</li>
					<li>
						<a:adminurl href="/quasar/dossier-report/0">
							<spring:message code="quasar.add" />
						</a:adminurl>
					</li>
				</ul>
				</c:if>
				
				<form class="filter user" method="get">
				<div>
					<span class="long filter-label"><spring:message code="logStatus" />:</span>
					<select name="status" class="chosenMini">
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
				</div>
				<c:if test="${model.isQuasarAdmin}">
					<div>
						<span class="long filter-label"><spring:message code="auditor.partner" />:</span>
						<select name="partner" class="chosenSmall">
							<option value=""><spring:message code="notmatter" /></option>
							<c:forEach items="${model.partners}" var="i">
								<option value="${i.id}" ${model.params.partner eq i.id ? 'selected="selected"' : ''}>${i.name}</option>
							</c:forEach>
						</select>
						<input type="submit" value="Filter" class="btn" />
					</div>
				</c:if>
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
							<c:if test="${model.isQuasarAdmin}">
								<th><spring:message code="auditor.edit" /></th>
							</c:if>
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
									<a:adminurl href="/quasar/dossier-report/${i.id}">
											<spring:message code="documentationLog" /> - 
											<strong><joda:format value="${i.created}" pattern="dd.MM.yyyy"/></strong>
									</a:adminurl>
									<c:if test="${i.revision > 1}">
										(<spring:message code="auditLog.auditLog.revision" /> ${i.revision})
									</c:if>
									<c:if test="${i.status.locked}">
										<strong>&nbsp; (<spring:message code="locked" />)</strong>
									</c:if>
								</td>
								<c:if test="${model.isQuasarAdmin}">
									<td><quasar:auditor auditor="${i.auditor}" /></td> 
								</c:if>
								<td class="last-edit">
									<joda:format value="${i.changed}" pattern="dd.MM.yyyy HH:mm"/> / ${i.changedBy.name} 
								</td>
								<td class="edit">
									<c:if test="${not i.status.locked}">
										<a:adminurl href="/quasar/dossier-report/${i.id}">
											<spring:message code="quasar.edit" />
										</a:adminurl>
									</c:if>
									<c:if test="${i.status.locked}">
										<a:adminurl href="/quasar/dossier-report/${i.id}">
											<spring:message code="form.view" />
										</a:adminurl>
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
	<c:if test="${model.isQuasarAdmin}">
		</div>
		<div class="clear"></div>	
	</c:if>
	</div>
</body>
</html>