<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<tr ${i.enabled ? '' : 'class="is-disabled"' }>
	<td class="w100 prod c">
		<c:if test="${empty i.parent }">
			${i.code}
		</c:if>
	</td>
	<td class="pid">
		<c:if test="${not empty i.parent }">
			${i.code}
		</c:if>
	</td>
	<td>
		<webpage:price price="${i.priceCzk}" /> /
		<webpage:price price="${i.priceEur}" isEuro="true" />
	</td>			
	<td class="w100">
		<c:if test="${i.enabled}">
			<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
				<spring:message code="yes" />
			</span>
		</c:if>
		<c:if test="${not i.enabled}">
			<span class="published no tt" title="<spring:message code="published.no.title" />" >
				<spring:message code="no" />
			</span>
		</c:if>
	</td>				
	<td class="edit">
		<a:adminurl href="/portal/product/${i.id}">
			<spring:message code="form.edit" />
		</a:adminurl>
	</td>
	<td class="delete">
			<a:adminurl href="/portal/product/delete/${i.id}" cssClass="confirm">
				<spring:message code="form.delete" />
			</a:adminurl>
	</td>
</tr>