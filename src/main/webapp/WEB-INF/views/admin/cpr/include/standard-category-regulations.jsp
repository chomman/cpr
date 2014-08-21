<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<c:if test="${not empty standardCategory.regulations}">
<p class="form-head">Přiřazené předpisy ke kategorii</p>
	<table class="data">
		<thead>
			<tr>
				<th><spring:message code="regulation.code" /></th>
				<th><spring:message code="regulation.eu" /></th>
				<th><spring:message code="regulation.cs" /></th>
				<th><spring:message code="regulation.sk" /></th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${standardCategory.regulations}" var="i">
				<tr>  
					<td class="c">
			 			<a:adminurl href="/cpr/regulation/${i.id}" extraAttr="target;_blank" cssClass="tt" title="Editovat předpis?">
			 				${i.code}
			 			</a:adminurl>
			 		</td>
			 		<td class="w50 c ${i.euRegulation ? 'scope-yes' : 'scope-no'}">
						${i.euRegulation ? 'Ano' : 'Ne'}
					</td>
					<td class="w50 c ${i.csRegulation ? 'scope-yes' : 'scope-no'}">
						${i.csRegulation ? 'Ano' : 'Ne'}
					</td>	
			 		<td class="w50 c ${i.skRegulation ? 'scope-yes' : 'scope-no'}">
						${i.skRegulation ? 'Ano' : 'Ne'}
					</td>
					<td class="unassign delete">
						<a class="confirmUnassignment" href="?action=unassign&amp;iid=${i.id}">
							Odebrat
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>