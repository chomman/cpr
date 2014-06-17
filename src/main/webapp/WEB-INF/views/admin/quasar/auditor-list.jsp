<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="auditors" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/quasar.css" />" />
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
				 <span><spring:message code="auditors" /></span>
			</div>
			<h1><spring:message code="auditors" /></h1>
	
			<div id="content">
								
				<jsp:include page="navs/auditor-code-nav.jsp" />
				
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
				</c:if>
				
							
				<c:if test="${not empty model.auditors}">
										
					<table class="data">
						<thead>
							<tr>
								<th><spring:message code="auditor.itcId" /></th>
								<th><spring:message code="auditor.name" /></th>
								<th>Changed</th>
								<th>Activated</th>
								<th>&nbsp;</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.auditors}" var="i">
								<tr class="${i.enabled ? '' : 'is-disabled'}"> 
									<td class="w50 code c">${i.itcId}</td>	 
									<td>
									<a:adminurl href="/quasar/manage/auditor/${i.id}">
										${i.firstName} ${i.lastName} <c:if test="${not empty i.degrees}">, ${i.degrees}</c:if>
									</a:adminurl>
									</td>
									<td class="last-edit">
										<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
									</td>
									<td class="w100">
										<c:if test="${i.enabled}">
											<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
												Yes
											</span>
										</c:if>
										<c:if test="${not i.enabled}">
											<span class="published no tt" title="<spring:message code="published.no.title" />" >
												No
											</span>
										</c:if>
									</td>		
									<td class="edit">
										<a:adminurl href="/quasar/manage/auditor/${i.id}">
											<spring:message code="quasar.edit" />
										</a:adminurl>
									</td>
								</tr>
							
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				
				<c:if test="${empty model.auditors}">
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