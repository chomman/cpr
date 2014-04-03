<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<tr data-tt-id="${node.id}" <c:if test="${not empty node.parent}">data-tt-parent-id="${node.parent.id}"</c:if> class="radius">
		<td>
			<span class="pj-webpate-type-${node.webpageType.id}">
				<span class="w-name">${node.defaultName}</span>
			</span>
			
		</td>
		<td class="w100">
			<c:if test="${node.enabled}">
				<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
					<spring:message code="yes" />
				</span>
			</c:if>
			<c:if test="${not node.enabled}">
				<span class="published no tt" title="<spring:message code="published.no.title" />" >
					<spring:message code="no" />
				</span>
			</c:if>
		</td>
		<td class="last-edit">
			<c:if test="${empty node.changedBy}">
				<joda:format value="${node.created}" pattern="${common.dateTimeFormat}"/>
			</c:if>
			<c:if test="${not empty i.changedBy}">
				<joda:format value="${node.changed}" pattern="${common.dateTimeFormat}"/>
			</c:if>
		</td>
		<td class="edit">
			<a href="<c:url value="/admin/webpages/edit/${node.id}"  />">
				<spring:message code="form.edit" />
			</a>
		</td>
 </tr>