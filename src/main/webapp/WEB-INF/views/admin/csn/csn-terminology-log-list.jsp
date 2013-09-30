<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="csn.terminology.log" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="csn-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/csn" />"><spring:message code="menu.csn" /></a> &raquo;
			 <span><spring:message code="csn.terminology.log" /></span>
		</div>
		<h1><spring:message code="csn.terminology.log" /></h1>

		<div id="content">
			
			
			
			<form class="filter" action="<c:url value="/admin/csn/terminology/log" />" method="get">
				<div>
					<span><spring:message code="csn.terminology.log.status" />:</span>
					<select name="importStatus" class="enabled">
							<option value=""><spring:message code="notmatter" /></option>
							<c:forEach items="${model.importStatuses}" var="i">
								<option value="${i.id}" <c:if test="${model.params.importStatus == i.id}" >selected="selected"</c:if>> 
									<spring:message code="${i.key}" />
								</option>
							</c:forEach>
					</select>	
				</div>
				<div>
					<span><spring:message code="cpr.dop.created" /> od: </span>
					<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span class="fixed">do:</span>
					<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
					
				</div>
				<div>
					<span class="long"><spring:message code="form.name" /></span>
					<input type="text" class="query " name="query"   value="${model.params.query}" />
					
					<input type="submit" value="Filtrovat" class="btn csn" />
				</div>
			</form>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
				
			
				
			<c:if test="${not empty model.logs}">
				
				<!-- STRANKOVANIE -->
				<c:if test="${not empty model.paginationLinks}" >
					<div class="pagination">
					<c:forEach items="${model.paginationLinks}" var="i">
						<c:if test="${not empty i.url}">
							<a href="<c:url value="${i.url}"  />">${i.anchor}</a>
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
							<th><spring:message code="csn.form.name" /></th>
							<th><spring:message code="csn.terminology.log.status" /></th>
							<th><spring:message code="csn.terminology.log.count" /></th>
							<th><spring:message code="csn.terminology.log.date" /></th>
							<th><spring:message code="form.view" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.logs}" var="i">
						 	<tr class="log-${i.importStatus.id}">
						 		<td>
						 			<a href="<c:url value="/admin/csn/edit/${i.csn.id}"  />">
						 				${i.csn.csnId}
						 			</a>
						 		</td>
						 		<td class="c">
							 		<c:if test="${not empty i.importStatus}">
							 			<spring:message code="${i.importStatus.key}" />
							 		</c:if>
						 		</td>
						 		<td>${i.czCount} / ${i.enCount}</td>
						 		<td class="last-edit">
						 			<joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/>
						 		</td>
						 		<td class="edit">
						 			<a href="<c:url value="/admin/csn/terminology/log/${i.id}"  />">
						 				<spring:message code="form.view" />
						 			</a>
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