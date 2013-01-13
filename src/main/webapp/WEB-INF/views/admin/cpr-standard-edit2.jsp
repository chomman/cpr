<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr" />"><spring:message code="menu.cpr" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr/standards" />"><spring:message code="menu.cpr.norm" /></a> &raquo;
			 <span><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></span>
		</div>
		<h1><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></h1>

		<div id="content">
							
					
					<ul class="sub-nav">
						<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
						<li><a href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
					</ul>
								
				<div id="tabs">
					
					<jsp:include page="include/cpr-standard-menu1.jsp" />
					
					<strong class="active-tab-head"><spring:message code="cpr.standard.tab.2" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					
					<div id="req-nav">
						<a href="<c:url value="/admin/cpr/standard/edit/${standardId}/req/0"  />">
							 <spring:message code="cpr.requirement.add" /> +
						</a>
						
						<span class="requirement-nav">
							<spring:message code="cpr.requirement.nav" />:
						</span>
						<select id="id${standardId}">
							<c:forEach items="${model.countries}" var="country">
								<option value="${country.id}" <c:if test="${country.id == model.country.id}">selected="selected"</c:if> >${country.countryName}</option>
							</c:forEach>
						</select>
					</div>
					
					<c:if test="${not empty model.requirements}">
										
						<table class="data">
							<thead>
								<tr>
									<tH><spring:message code="cpr.requirement.name" /></th>
									<th><spring:message code="cpr.requirement.level" /></th>
									<th><spring:message code="cpr.requirement.npd" /></th>
									<th><spring:message code="cpr.requirement.section" /></th>
									<th><spring:message code="cpr.requirement.note" /></th>
									<th><spring:message code="form.edit" /></th>
									<th><spring:message code="form.delete" /></th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach items="${model.requirements}" var="i">
								 	<tr>
								 		<td>${i.name}</td>
								 		<td>${i.levels}</td>
								 		
								 		<td class="w100">
								 			<c:if test="${i.npd}">
								 				<span class="published npd tt" title="<spring:message code="cpr.requirement.npd" />" >
								 					<spring:message code="cpr.requirement.ndp.short" />
								 				</span>
								 			</c:if>
								 		</td>
								 		<td>${i.section}</td>
								 		<td>${i.note}</td>
								 		<td class="edit">												
								 			<a class="tt" title="Upraviť položku?" href="<c:url value="/admin/cpr/standard/edit/${standardId}/req/${i.id}"  />">
								 				<spring:message code="form.edit" />
								 			</a>
								 		</td>
								 		<td class="delete">
								 			<a class="confirm"  href="<c:url value="/admin/cpr/standard/requirement/delete/${i.id}?country=${model.country.id}"  />">
								 				<spring:message code="form.delete" />
								 			</a>
								 		</td>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${empty model.requirements}">
						<p class="msg alert">
							<spring:message code="alert.empty" />
						</p>
					</c:if>
						
					</div> <!-- END ACTIVE TAB -->

					
					<jsp:include page="include/cpr-standard-menu3.jsp" />
					<jsp:include page="include/cpr-standard-menu4.jsp" />
					<jsp:include page="include/cpr-standard-menu5.jsp" />
					<jsp:include page="include/cpr-standard-menu6.jsp" />
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>