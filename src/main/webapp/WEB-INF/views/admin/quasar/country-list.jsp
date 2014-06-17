<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="countries" /></title>
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
				 <span><spring:message code="countries" /></span>
			</div>
			<h1><spring:message code="countries" /></h1>
	
			<div id="content">
								
				<jsp:include page="navs/country-nav.jsp" />
				
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
				</c:if>
				
							
				<c:if test="${not empty model.countries}">
										
					<table class="data">
						<thead>
							<tr>
								<th><spring:message code="country.name" /></th>				
								<th>Changed</th>
								<th>Activated</th>
								<th>&nbsp;</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.countries}" var="i">
								<tr class="${i.enabled ? '' : 'is-disabled'}"> 
									<td>${i.countryName}</td> 
									<td class="last-edit">
										<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
										${i.changedBy.firstName} ${i.changedBy.lastName}	
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
										<a:adminurl href="/quasar/manage/country/${i.id}">
											<spring:message code="quasar.edit" />
										</a:adminurl>
									</td>
								</tr>
							
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				
				<c:if test="${empty model.countries}">
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