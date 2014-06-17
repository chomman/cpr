<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:if test="${not empty command.changedBy}">
	<table class="info">
		<tr>
			<td class="key"><spring:message code="meta.edited" /> by:</td>
			<td class="val">${command.changedBy.firstName} ${command.changedBy.lastName}</td>
			<td class="val">
				<c:if test="${not empty command.changed}">
					<joda:format value="${command.changed}" pattern="${common.dateTimeFormat}"/>
				</c:if>
			</td>
			
		</tr>
	</table>
</c:if>