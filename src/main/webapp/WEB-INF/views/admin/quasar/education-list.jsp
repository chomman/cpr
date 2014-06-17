<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="fieldOfEducations" /></title>
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
				 <span><spring:message code="fieldOfEducations" /></span>
			</div>
			<h1><spring:message code="fieldOfEducations" /></h1>
	
			<div id="content">
								
				<jsp:include page="navs/education-nav.jsp" />
				
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
				</c:if>
				
							
				<c:if test="${not empty model.fieldOfEducations}">
										
					<div class="hbox">
						<h2>
							<spring:message code="fieldOfEducation.head.fieldOfEducation" />
						</h2>
					</div>						
					<table class="data">
						<thead>
							<tr>
								<tH><spring:message code="fieldOfEducation.name" /></th>
								<tH><spring:message code="fieldOfEducation.forActiveMedicalDevices" /></th>
								<th><spring:message code="fieldOfEducation.forNonActiveMedicalDevices" /></th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.fieldOfEducations}" var="i">
								<c:if test="${not i.specificOrCourse }">
									<tr> 
										<td>${i.name}</td>
										<td class="w100 c ${i.forActiveMedicalDevices ? 'scope-yes' : 'scope-no'}">
											${i.forActiveMedicalDevices ? 'Yes' : 'No'}
										</td>	
										<td class="w100 c ${i.forNonActiveMedicalDevices ? 'scope-yes' : 'scope-no'}">
											${i.forNonActiveMedicalDevices ? 'Yes' : 'No'}
										</td>				
										<td class="edit">
											<a:adminurl href="/quasar/manage/education/${i.id}">
												<spring:message code="quasar.edit" />
											</a:adminurl>
										</td>
										<td class="delete confirmMessage" data-message="<spring:message code="quasar.delete.confirm" />">
											<a:adminurl href="/quasar/manage/education/${i.id}/delete">
												<spring:message code="quasar.delete" />
											</a:adminurl>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
					
					<div class="hbox">
						<h2><spring:message code="fieldOfEducation.head.specificOrCourses" /></h2>
					</div>
					<table class="data">
						<thead>
							<tr>
								<tH><spring:message code="fieldOfEducation.name" /></th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.fieldOfEducations}" var="i">
								<c:if test="${i.specificOrCourse }">
									<tr> 
										<td>${i.name}</td>						
										<td class="edit">
											<a:adminurl href="/quasar/manage/education/${i.id}">
												<spring:message code="quasar.edit" />
											</a:adminurl>
										</td>
										<td class="delete confirmMessage" data-message="<spring:message code="quasar.delete.confirm" />">
											<a:adminurl href="/quasar/manage/education/${i.id}/delete">
												<spring:message code="quasar.delete" />
											</a:adminurl>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				
				<c:if test="${empty model.fieldOfEducations}">
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