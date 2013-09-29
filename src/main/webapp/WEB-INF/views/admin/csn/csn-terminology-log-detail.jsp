<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="csn.terminology.log.detail" /></title>
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
			 <a href="<c:url value="/admin/csn/terminology/log" />"><spring:message code="csn.terminology.log" /></a> &raquo;
			 <span><spring:message code="csn.terminology.log.detail" /></span>
		</div>
		<h1><spring:message code="csn.terminology.log.detail" /></h1>

		<div id="content">
			
			<table class="log">
				<tr>
					<td><strong><spring:message code="csn.form.name" /></strong></td>
					<td>
						<a href="<c:url value="/admin/csn/edit/${log.csn.id}"  />">
							${log.csn.csnId}:
						</a>
					</td>
				<tr>
				<tr>
					<td><strong><spring:message code="csn.terminology.log.date" />:</strong></td>
					<td>
						<joda:format value="${log.created}" pattern="dd.MM.yyyy / HH:mm:ss"/>
						${log.createdBy.firstName} ${log.createdBy.lastName}
					</td>
				</tr>
				<tr>
					<td><strong><spring:message code="csn.terminology.log.status" />:</strong></td>
					<td class="log-${log.importStatus.id}">
						<c:if test="${not empty log.importStatus}">
							<spring:message code="${log.importStatus.key}" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td><strong><spring:message code="csn.terminology.log.count" />:</strong></td>
					<td>
						${log.czCount} / ${log.enCount}
					</td>
				</tr>
				<tr>
					<td><strong><spring:message code="csn.terminology.log.duration" />:</strong></td>
					<td>
						<c:if test="${log.duration != 0}">
							${log.duration / 1000}
						</c:if>
						<c:if test="${log.duration == 0}">
							${log.duration / 1000}
						</c:if>
						s
					</td>
				</tr>
				<tr>
					<td><strong><spring:message code="csn.terminology.log.imageCount" />:</strong></td>
					<td>
						${log.imageCount}
					</td>
				</tr>
				<tr>
					<td><strong><spring:message code="csn.terminology.log.fileName" />:</strong></td>
					<td>
						${log.fileName}
					</td>
				</tr>
			</table>
			
			<div class="log-descr">
				${log.description}
			</div>
			

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>