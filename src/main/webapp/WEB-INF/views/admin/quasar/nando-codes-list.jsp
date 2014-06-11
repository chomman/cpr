<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="nandoCodes" /></title>
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
				 <span><spring:message code="admin.portal.services" /></span>
			</div>
			<h1><spring:message code="nandoCodes" /></h1>
	
			<div id="content">
								
				<jsp:include page="navs/nando-code-nav.jsp" />
				
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
				</c:if>
				
							
				<c:if test="${not empty model.nandoCodes}">
										
					<table class="data">
						<thead>
							<tr>
								<tH><spring:message code="nandoCode.firstLevel" /></th>
								<tH><spring:message code="nandoCode.secondLevel" /></th>
								<th><spring:message code="nandoCode.specification" /></th>
								<th><spring:message code="nandoCode.productAssessorAScope" /></th>
								<th><spring:message code="nandoCode.productAssessorRScope" /></th>
								<th><spring:message code="nandoCode.productSpecialist" /></th>
								<th><spring:message code="form.edit" /></th>
								<th><spring:message code="form.delete" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${model.nandoCodes}" var="i">
							 	<jsp:include page="nando-code-list-item.jsp" />
							 	<c:if test="${not empty i.children}">
									<c:forEach items="${i.children}" var="i">
										<jsp:include page="nando-code-list-item.jsp" />		
									</c:forEach> 		
							 	</c:if>
							 </c:forEach>
						</tbody>
					</table>
				</c:if>
				
				<c:if test="${empty model.nandoCodes}">
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