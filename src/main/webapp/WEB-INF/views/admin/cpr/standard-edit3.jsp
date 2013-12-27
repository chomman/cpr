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
					<jsp:include page="include/cpr-standard-menu2.jsp" />					

					<strong class="active-tab-head"><spring:message code="cpr.standard.tab.3" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					
					<div id="req-nav">
												
						<a href="<c:url value="/admin/cpr/standard/edit/${standardId}/csn-edit/0"  />">
							 <spring:message code="cpr.csn.add" /> +
						</a>
						
					</div>
					
					<c:if test="${not empty standard.standardCsns}">
										
						<table class="data">
							<thead>
								<tr>
									<tH><spring:message code="cpr.csn.name" /></th>
									<th><spring:message code="form.lastEdit" /></th>
									<th><spring:message code="form.edit" /></th>
									<th><spring:message code="form.delete" /></th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach items="${standard.standardCsns}" var="i">
								 	<c:if test="${not empty i.standardStatus }">
								 		<tr class="${i.standardStatus.cssClass}">
								 	</c:if>
								 	<c:if test="${empty i.standardStatus }">
								 		<tr>
								 	</c:if>
								 		<td class="standarardId">${i.csnName}</td>
								 		<td class="last-edit">
								 			<c:if test="${empty i.changedBy}">
								 				<joda:format value="${i.created}" pattern="dd.MM.yyyy / HH:mm"/>
								 			</c:if>
								 			<c:if test="${not empty i.changedBy}">
								 				<joda:format value="${i.changed}" pattern="dd.MM.yyyy / HH:mm"/>
								 			</c:if>
								 		</td>
								 		<td class="edit">												
								 			<a class="tt" title="Upraviť položku?" href="<c:url value="/admin/cpr/standard/edit/${standardId}/csn-edit/${i.id}"  />">
								 				<spring:message code="form.edit" />
								 			</a>
								 		</td>
								 		<td class="delete">
								 			<a class="confirm"  href="<c:url value="/admin/cpr/standard/csn/delete/${i.id}"  />">
								 				<spring:message code="form.delete" />
								 			</a>
								 		</td>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${empty standard.standardCsns}">
						<p class="msg alert">
							<spring:message code="alert.empty" />
						</p>
					</c:if>
						
					</div> <!-- END ACTIVE TAB -->

					
					
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