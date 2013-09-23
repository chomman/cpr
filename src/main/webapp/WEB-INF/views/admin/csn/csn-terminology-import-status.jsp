<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<c:if test="${not empty log}">
				
	<c:if test="${log.importStatus.id == 'SUCCESS'}">
		<table class="import success">
			<tr class="msg">
				<td><spring:message code="csn.terminology.import.success" argumentSeparator=";" arguments="${log.czCount};${log.enCount}" /></td>
			</tr>
			<tr class="csn">
				<td>
					<spring:message code="csn.terminology.import.into" />:
					<a href="<c:url value="/admin/csn/edit/${log.csn.id}"  />">
						<strong>${log.csn.csnId}</strong>
					</a>
				</td>
			</tr>
			<tr  class="more">
				<td>
					<a href="<c:url value="/admin/csn/terminology/log/${log.id}"  />">
						<spring:message code="csn.terminology.import.moreinfo" /> &raquo;
					</a>
				</td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${log.importStatus.id == 'INCOMPLETE'}">
		<table class="import alert">
			<tr class="msg">
				<td><spring:message code="csn.terminology.import.incomplete" argumentSeparator=";" arguments="${log.czCount};${log.enCount}" /></td>
			</tr>
			<tr class="csn">
				<td>
					<spring:message code="csn.terminology.import.into" />:
					<a href="<c:url value="/admin/csn/edit/${log.csn.id}"  />">
						<strong>${log.csn.csnId}</strong>
					</a>
				</td>
			</tr>
			<tr  class="more">
				<td>
					<a href="<c:url value="/admin/csn/terminology/log/${log.id}"  />">
						<spring:message code="csn.terminology.import.moreinfo" /> &raquo;
					</a>
				</td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${log.importStatus.id == 'FAILED'}">
		<table class="import failed">
			<tr class="msg">
				<td><spring:message code="csn.terminology.import.failed" /></td>
			</tr>
			<tr class="csn">
				<td>
					<a href="<c:url value="/admin/csn/edit/${log.csn.id}"  />">
						<strong>${log.csn.csnId}</strong>
					</a>
				</td>
			</tr>
			<tr class="more">
				<td>
					<a href="<c:url value="/admin/csn/terminology/log/${log.id}"  />">
						<spring:message code="csn.terminology.import.moreinfo" /> &raquo;
					</a>
				</td>
			</tr>
		</table>
	</c:if>
	
</c:if>