<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:set var="editable" value="false" scope="application"/>
<sec:authorize access="isAuthenticated()"> 
	<c:set var="editable" value="true" scope="application" />
</sec:authorize>

<!DOCTYPE html>
<html>
	<head>
		<title>
			<joda:format value="${model.report.dateFrom}" pattern="MMMM, yyyy"/> -
			<spring:message code="cpr.report.standards" />
		</title>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo;  
			<c:if test="${not empty model.webpage}">
				<webpage:a webpage="${model.webpage}" /> &raquo;
			</c:if>
			<span><joda:format value="${model.report.dateFrom}" pattern="MMMM, yyyy"/></span>
	</div> 
	
	<div id="main-content">
		<article>
			<hgroup>
				<h1><spring:message code="reports" /></h1>
				<h2><spring:message code="cpr.report.period" />: 
				<joda:format value="${model.report.dateFrom}" pattern="dd.MM.yyyy"/> -
				<joda:format value="${model.report.dateTo}" pattern="dd.MM.yyyy"/></h2>
			</hgroup>
			
			<a:localizedValue object="${model.report}" fieldName="content" />
			<h3><spring:message code="reports.appendix" arguments="1" /></h3>
			<jsp:include page="../../include/standard-table.jsp" />
			<h3><spring:message code="reports.appendix" arguments="2" /></h3>
			<c:if test="${not empty model.standardCsns}">
				<table class="changed-csns">
					<tr>
						<th><spring:message code="csn.table.title" /></th>
						<th><spring:message code="csn.table.published" /></th>
						<th><spring:message code="csn.table.classification" /></th>
						<th><spring:message code="csn.table.note" /></th>
					</tr>
					<c:forEach items="${model.standardCsns}" var="csn">
						<tr>
							<td class="csns">
								<a:standardCsnUrl object="${csn}" editable="${editable}" />
							</td>
							<td>
								<c:if test="${not empty csn.released}">
									<joda:format value="${csn.released}" pattern="dd.MM.yyyy"/>
								</c:if>
								<c:if test="${empty csn.released}">
									-
								</c:if>
							</td>
							<td>
								${csn.classificationSymbol}
							</td>
							<td>
							<c:if test="${not empty csn.standardStatus and csn.standardStatus.id != 1}">
								(<spring:message code="${csn.standardStatus.name}" />)
							</c:if>
							${csn.note}
							</td>
						</tr>
				 		<c:forEach items="${csn.standardCsnChanges}" var="j">
					 		<tr class="standard-change csn-change">
					 			<td class="csns">
					 				<a:standardCsnUrl object="${j}" editable="${editable}" />
					 			</td>
					 			<td>
									<c:if test="${not empty csn.released}">
										<joda:format value="${csn.released}" pattern="dd.MM.yyyy"/>
									</c:if>
									<c:if test="${empty csn.released}">
										-
									</c:if>
								</td>
								<td>
									 &nbsp;
								</td>					
					 			<td >${j.note}</td>
					 		</tr>
				 		</c:forEach>
					</c:forEach>
				</table>
			</c:if>
		</article>
	</div>
	</body>
</html>