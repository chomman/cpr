<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<tr class="${i.enabled ? '' : 'is-disabled'} ${empty i.parent ? ' is-first-level' : ''}"> 
	<td class="w100 code c">
		<c:if test="${empty i.parent }">
			${i.code}
		</c:if>
	</td>
	<td class="w100 code c">
		<c:if test="${not empty i.parent }">
			${i.code}
		</c:if>
	</td>		
	<td>${i.specification}</td>
	<td class="w50 c ${i.forProductAssesorA ? 'scope-yes' : 'scope-no'}">
		${i.forProductAssesorA ? 'Yes' : 'No'}
	</td>	
	<td class="w50 c ${i.forProductAssesorR ? 'scope-yes' : 'scope-no'}">
		${i.forProductAssesorR ? 'Yes' : 'No'}
	</td>	
	<td class="w50 c ${i.forProductSpecialist ? 'scope-yes' : 'scope-no'}">
		${i.forProductSpecialist ? 'Yes' : 'No'}
	</td>				
	<td class="last-edit">
		<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
	</td>
	<td class="w100">
		<c:if test="${i.enabled}">
			<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
				Yes
			</span>
		</c:if>
		<c:if test="${not i.enabled}">
			<span class="published no tt" title="<spring:message code="published.no.title" />" >
				No
			</span>
		</c:if>
	</td>		
	<td class="edit">
		<a:adminurl href="/quasar/manage/nando-code/${i.id}">
			<spring:message code="quasar.edit" />
		</a:adminurl>
	</td>
</tr>