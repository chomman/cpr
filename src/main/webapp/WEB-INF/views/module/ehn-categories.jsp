<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<script>
	$(function(){
		$('.ehn-c-ojeu a').each(function(){
			var $this = $(this);
			$this.addClass("file pdf min").attr("target","_blank");
		});
		$('table').find('td.ehn-c-ojeu').each(function(){
			$td = $(this);
			$td.html($td.html().replace(">,",">"));
			$td.html($td.html().replace(/<br>/g,""));
		});
	});
</script>
<c:if test="${not empty model.standardCategories}">
	<table class="ehn-categories ">
		<thead>
			<tr>
				<th><spring:message code="standardCategory.code" /></th>
				<th><spring:message code="standardCategory.name" /></th>
				<th><spring:message code="standardCategory.specialiyation" /></th>
				<th><spring:message code="standardCategory.ojeu" /></th>
				<th>&nbsp;</th>
				<th><spring:message code="regulation.eu" /></th>
				<th><spring:message code="regulation.cs" /></th>
				<th><spring:message code="regulation.sk" /></th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach items="${model.standardCategories}" var="i">
			 	<tr>
			 		<td class="c code">
			 			${!empty i.code ? i.code : '-'}
			 		</td>
			 		<td class="ehn-c-name">
				 		<a:url href="/ehn/kategorie/${i.id}" >
				 			<a:localizedValue object="${i}" fieldName="name" />
				 		</a:url>
			 		</td>
			 		<td class="ehn-c-specialization">
				 		<a:localizedValue object="${i}" fieldName="specialization" />
			 		</td>
			 		<td class="c ehn-c-ojeu">
			 			<a:localizedValue object="${i}" fieldName="ojeuPublication" />
			 		</td>
			 		<td class="w40 c">
			 			<c:if test="${!empty i.noaoUrl}">
			 				<a class="tooltip" title="<spring:message code="standardCategory.noaoNando" />" 
			 					href="${i.noaoUrl}" target="_blank">NO/AO</a>
			 			</c:if>
			 			<c:if test="${empty i.noaoUrl}">
			 				<spring:message code="standardCategory.noao.none" />
			 			</c:if>
			 		</td>
			 		<td class="ehn-regulation">
			 			<c:forEach items="${i.regulations}" var="j">
			 				<c:if test="${j.euRegulation}">
			 					<span>
			 						<a 
			 						href="<a:localizedValue object="${j.euRegulationContent}" fieldName="pdf" />" 
			 						title="<a:localizedValue object="${j.euRegulationContent}" fieldName="name" />"
			 						class="file pdf min tooltip">
			 						${j.code}
			 						</a>
			 					</span>
			 				</c:if>
			 			</c:forEach>
			 		</td>
			 		<td class="ehn-regulation">
			 			<c:forEach items="${i.regulations}" var="j">
			 				<c:if test="${j.csRegulation}">
			 					<span>
			 						<a 
			 						href="<a:localizedValue object="${j.csRegulationContent}" fieldName="pdf" />" 
			 						title="<a:localizedValue object="${j.csRegulationContent}" fieldName="name" />"
			 						class="file pdf min tooltip">
			 						${j.code}
			 						</a>
			 					</span>
			 				</c:if>
			 			</c:forEach>
			 		</td>
			 		<td class="ehn-regulation">
			 			<c:forEach items="${i.regulations}" var="j">
			 				<c:if test="${j.skRegulation}">
			 					<span>
			 						<a 
			 						href="<a:localizedValue object="${j.skRegulationContent}" fieldName="pdf" />" 
			 						title="<a:localizedValue object="${j.skRegulationContent}" fieldName="name" />"
			 						class="file pdf min tooltip">
			 						${j.code}
			 						</a>
			 					</span>
			 				</c:if>
			 			</c:forEach>
			 		</td>
			 	</tr>
			 </c:forEach>
		</tbody>
	</table>
</c:if>
<c:if test="${empty model.standardCategories}">
	<p class="msg alert">
		<spring:message code="alert.empty" />
	</p>
</c:if>
