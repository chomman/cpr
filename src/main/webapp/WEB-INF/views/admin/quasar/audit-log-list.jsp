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
		<div id="left">
			<jsp:include page="../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
				 <span><spring:message code="auditLogs" /></span>
			</div>
			<h1><spring:message code="auditLogs" /></h1>
	
			<div id="content">
													
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
								<th><spring:message code="auditor.name" /></th>					
								<th>Created</th>
								<th>&nbsp;</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.logs}" var="i">
								<tr class="qs-log-status-${model.status.id}"> 
									<td class="w100 status">
										<spring:message code="${model.status.code}" />
									</td>	 
									<td>
									<a:adminurl href="/quasar/manage/auditor/${i.adutior.name}" cssClass="${i.intenalAuditor ? 'qs-internal' : 'qs-external'}"
									title="${i.intenalAuditor ? 'Internal auditor' : 'External auditor'}"  >
										${i.nameWithDegree}
									</a:adminurl>
									</td>				
									<td class="last-edit">
										<joda:format value="${i.created}" pattern="${common.created}"/>
									</td>
									<td class="edit">
										<a:adminurl href="/quasar/manage/audit-log/${i.id}">
											<spring:message code="quasar.edit" />
										</a:adminurl>
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
		<div class="clear"></div>	
	</div>
</body>
</html>