<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ taglib prefix="quasar"  uri="http://nlfnorm.cz/quasar"%>
<c:forEach items="${model.logs}" var="i">
	<tr class="qs-log-status-${i.status.id}">  
		<td class="w100 qs-status">
			<span class="qs-log-status"><spring:message code="${i.status.code}" /></span>
		</td>	
		<td>
			<a:adminurl href="/quasar/dossier-report/${i.id}">
					<spring:message code="documentationLog" /> - 
					<strong><joda:format value="${i.created}" pattern="dd.MM.yyyy"/></strong>
			</a:adminurl>
			<c:if test="${i.revision > 1}">
				(<spring:message code="auditLog.auditLog.revision" /> ${i.revision})
			</c:if>
			<c:if test="${i.status.locked}">
				<strong>&nbsp; (<spring:message code="locked" />)</strong>
			</c:if>
			<c:if test="${i.auditor.id != i.createdBy.id}">
				<span class="created-by">Created by: <strong>${i.createdBy.name}</strong></span>
			</c:if>
		</td>
		<c:if test="${model.isQuasarAdmin}">
			<td><quasar:auditor auditor="${i.auditor}" /></td> 
		</c:if>
		<td class="last-edit">
			<joda:format value="${i.changed}" pattern="dd.MM.yyyy HH:mm"/>
			<span class="created-by"> / ${i.changedBy.name} </span> 
		</td>
		<td class="edit">
			<c:if test="${not i.status.locked}">
				<a:adminurl href="/quasar/dossier-report/${i.id}">
					<spring:message code="quasar.edit" />
				</a:adminurl>
			</c:if>
			<c:if test="${i.status.locked}">
				<a:adminurl href="/quasar/dossier-report/${i.id}">
					<spring:message code="form.view" />
				</a:adminurl>
			</c:if>
		</td>
	</tr>
</c:forEach>