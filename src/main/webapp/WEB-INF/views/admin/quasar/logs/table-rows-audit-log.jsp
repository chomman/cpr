<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ taglib prefix="quasar"  uri="http://nlfnorm.cz/quasar"%>
<c:forEach items="${model.logs}" var="i">
	<tr class="qs-log-status-${i.status.id}">  
		<td class="w100 qs-status">
			<span class="qs-log-status"><spring:message code="${i.status.code}" /></span>
		</td>	
		<td>
			<a:adminurl href="/quasar/audit-log/${i.id}">
					<spring:message code="auditLog.auditLog" /> - 
					<strong><joda:format value="${i.created}" pattern="dd.MM.yyyy"/></strong>
			</a:adminurl>
			<c:if test="${i.revision > 1}">
				(<spring:message code="auditLog.auditLog.revision" /> ${i.revision})
			</c:if>
			<c:if test="${i.status.locked}">
				<strong>&nbsp; (<spring:message code="locked" />)</strong>
			</c:if>
		</td>
		<td class="td-rating c">
			<c:if test="${not empty i.rating}">
				<span class="rating" data-rating="${i.rating}"></span>
				<span class="rating-descr"></span>
			</c:if>
			<c:if test="${empty i.rating}">
				<spring:message code="notRated" />
			</c:if>
		</td>
		<c:if test="${model.isQuasarAdmin}">
		<td>
			<quasar:auditor auditor="${i.auditor}" />
		</td> 
		</c:if>
		<td class="w40 c">
		<strong>${i.sumOfAuditDays}</strong>
		</td>
		<td class="w40 c">
		<strong>${i.countOfAudits}</strong>
		</td>
		<td class="last-edit">
			<joda:format value="${i.changed}" pattern="dd.MM.yyyy HH:mm"/> 
			<span class="created-by"> / ${i.changedBy.name} </span> 
		</td>
		<td class="edit">
			<c:if test="${not i.status.locked}">
				<a:adminurl href="/quasar/audit-log/${i.id}">
					<spring:message code="quasar.edit" />
				</a:adminurl>
			</c:if>
			<c:if test="${i.status.locked}">
				<a:adminurl href="/quasar/audit-log/${i.id}">
					<spring:message code="form.view" />
				</a:adminurl>
			</c:if>
		</td>
	</tr>
</c:forEach>