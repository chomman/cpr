<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>
	<c:if test="${empty csn.id or csn.id == 0}">
		<spring:message code="csn.add" />
	</c:if>
	<c:if test="${not empty csn.id and csn.id > 0}">
		<spring:message code="cpr.standard.edit" arguments="${csn.csnName}" />
	</c:if>
</title>
<script src="<c:url value="/resources/admin/js/standard-csn.picker.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
				<a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl> &raquo;
				<a:adminurl href="/cpr"><spring:message code="menu.cpr" /></a:adminurl> &raquo;
				<a:adminurl href="/cpr/standard-change"><spring:message code="menu.cpr.csn" /></a:adminurl> &raquo;
			 <span>
			 	<c:if test="${empty csn.id or csn.id == 0}">
					<spring:message code="csn.add" />
				</c:if>
				<c:if test="${not empty csn.id and csn.id > 0}">
					<spring:message code="cpr.standard.edit" arguments="${csn.csnName}" />
				</c:if>
			 </span>
		</div>
		<h1>
		<c:if test="${empty csn.id or csn.id == 0}">
			<spring:message code="csn.add" />
		</c:if>
		<c:if test="${not empty csn.id and csn.id > 0}">
			<spring:message code="cpr.standard.edit" arguments="${csn.csnName}" />
		</c:if>
		</h1>
		<div id="content">
							
				<ul class="sub-nav">
					<li>
						<a:adminurl href="/cpr/standard-csn"><spring:message code="csn.list" /></a:adminurl>
					</li>
					<li><a <c:if test="${empty csn.id or csn.id == 0}">class="active"</c:if> href="<a:adminurl href="/cpr/standard-csn/edit/0" linkOnly="true" />"><spring:message code="csn.add" /></a></li>
					<c:if test="${not empty model.standard}">
						<li><a data-fancybox-type="ajax" class="standard-preview" href="<c:url value="/preview/standard/${model.standard.id}"  />"><spring:message code="form.preview" /></a></li>
					</c:if>
				</ul>
								
				<c:if test="${not empty model.standards}">
					<p class="form-head"><spring:message code="cpr.csn.assignedStandards" arguments="${csn.csnName}" />:</p>
					<p>
						<ul class="assigned-standards">
							<c:forEach items="${model.standards}" var="i">
								<li class="transparent ${i.statusClass}">
									<a:adminurl href="/cpr/standard/edit/${i.id}">
										${i.standardId}
									</a:adminurl>
									<span>- ${i.czechName}</span>
								</li>
							</c:forEach>
						</ul>
					</p>
				</c:if>								
								
				<!--  FORM  -->
				<p class="form-head"><spring:message code="basic.info" /></p>
				<c:url value="/admin/cpr/standard-csn/edit/${csn.id}" var="formUrl"/>
				<jsp:include page="include/standard-csn-form.jsp"></jsp:include>
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>