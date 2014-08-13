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
			<jsp:include page="../../include/standard-table.jsp" />	
		</article>
	</div>
	</body>
</html>