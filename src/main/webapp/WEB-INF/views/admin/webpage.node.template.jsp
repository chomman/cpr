<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<tr data-tt-id="${node.id}" <c:if test="${not empty node.parent}">data-tt-parent-id="${node.parent.id}"</c:if> class="radius">
		<td class="w-td-name">
			<span class="pj-ico pj-webpage-type-${node.webpageType.id}">
				<span class="w-name">
					<a href="">${node.defaultName}</a>
				</span>
				<span class="pj-webpage-nav">
					<a:adminurl href="/webpage/add/${node.id}">Pridat podstranku</a:adminurl>
					<a href="">Upraviť</a>
					<a href="">Zobraziť</a>
					<a href="">Odstraniť</a>
				</span>
			</span>
			
		</td>
		<td class="w50">
			<c:if test="${node.isPublished}">
				<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
					<spring:message code="yes" />
				</span>
			</c:if>
			<c:if test="${not node.isPublished}">
				<span class="published no tt" title="<spring:message code="published.no.title" />" >
					<spring:message code="no" />
				</span>
			</c:if>
		</td>
		<td class="last-edit c">
			<span class="pj-published-by">
				<a:adminurl href="/user/edit/${node.publishedBy.id}">
					${node.publishedBy.firstName} ${node.publishedBy.lastName}
				</a:adminurl>
			</span>
			<span class="pj-published-by">
				<joda:format value="${node.published}" pattern="${common.dateTimeFormat}"/>
			</span>
		</td>
		<td class="edit">
			
		</td>
 </tr>