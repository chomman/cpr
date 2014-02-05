<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<c:if test="${not empty model.standards}">
											
		<table class="standards">
			<thead>
				<tr>
					<th><spring:message code="standard.id" /></th>
					<tH><spring:message code="standard.name" /></th>
					<th><spring:message code="standard.validity" /></th>
					<th><spring:message code="standard.endofvalidity" /></th>
					<th><spring:message code="standard.noao" /></th>
					<th><spring:message code="assessment" /></th>
					<th><spring:message code="standard.standardGroups" /></th>
				</tr>
			</thead>
			<tbody>
				 <c:forEach items="${model.standards}" var="i">
				 	<tr 
				 	class="<c:if test="${not empty i.standardChanges}">has-change</c:if> <c:if test="${not empty i.standardStatus }">${i.standardStatus.cssClass}</c:if>" 
				 	<c:if test="${not empty i.standardStatus and i.standardStatus.id != 1}"> title="<spring:message code='${i.standardStatus.name}' />" </c:if>
				 	>
				 		
				 		<td class="norm">
				 			<span>
				 				<a:url href="/ehn/${i.id}">${i.standardId}</a:url>
				 			</span>
				 				
		 					<c:if test="${not empty i.replaceStandard and i.standardStatus.id == 2}">
			 					<span  class="s-replaced" >
			 						<spring:message code="replaced" />
			 						<a:url href="/ehn/${i.replaceStandard.id}">${i.replaceStandard.standardId}</a:url>
			 					</span>
		 					</c:if>
		 					<c:if test="${not empty i.replaceStandard and i.replaceStandard.standardStatus.id == 2}">
			 					<span class="s-replace" >
			 						<spring:message code="replace" />
			 						<a:url href="/ehn/${i.replaceStandard.id}">${i.replaceStandard.standardId}</a:url>
			 					</span>
		 					</c:if>
				 		</td>
				 		<td class="s-name">
				 			<span class="s-czechName" >${i.name}</span>
				 			
				 		</td>
				 		<td class="validity c ">
				 			<c:if test="${not empty i.startValidity}">
								<joda:format value="${i.startValidity}" pattern="${commonPublic.dateTimeFormat}"/>
				 			</c:if>
				 		</td>
				 		<td class="validity c ">
				 			<c:if test="${not empty i.stopValidity}">
								<joda:format value="${i.stopValidity}" pattern="${commonPublic.dateTimeFormat}"/>
				 			</c:if>
				 		</td>
				 		
				 		<td class="aono c">
				 			<c:if test="${not empty i.notifiedBodies}">
				 				<c:forEach items="${i.notifiedBodies}" var="j" >
				 					<span>
				 					<c:if test="${not empty j.nandoCode}">
					 					<a target="_blank" class="tt" title="${j.name}" href="${model.noaoUrl}${j.nandoCode}">
					 						${j.noCode} <c:if test="${not empty j.aoCode }">(${j.aoCode})</c:if>
					 					</a> 
				 					</c:if>
				 					<c:if test="${empty j.nandoCode}">
					 					${j.noCode} <c:if test="${not empty j.aoCode }">(${j.aoCode})</c:if>
				 					</c:if>
				 					</span>
				 				</c:forEach>
				 			</c:if>
				 			<c:if test="${empty i.notifiedBodies}">
				 				-
				 			</c:if>
				 		</td>
				 		<td class="as c">
				 			<c:if test="${not empty i.assessmentSystems}">
				 				<c:forEach items="${i.assessmentSystems}" var="as"  varStatus="status">
				 					<a:url cssClass="tt" href="/cpr/as/${as.id}">${as.assessmentSystemCode}</a:url>
				 					<c:if test="${not status.last}">, </c:if>  
				 				</c:forEach>
				 			</c:if>
				 			<c:if test="${empty i.assessmentSystems}">
				 				-
				 			</c:if>
				 		</td>
				 		<td class="standardGroups c">
				 			<c:if test="${not empty i.standardGroups}">
				 				<c:forEach items="${i.standardGroups}" var="j" varStatus="status">
				 					<a:url cssClass="tt" title="${j.name}" href="/cpr/skupina/${j.code}">${j.code}</a:url>
				 					<c:if test="${not status.last}">, </c:if> 
				 				</c:forEach>
				 			</c:if>
				 			<c:if test="${empty i.standardGroups}">
				 				- 
				 			</c:if>
				 		</td>
				 		
				 	</tr>
				 	<c:if test="${not empty i.standardChanges}">
				 		<c:forEach items="${i.standardChanges}" var="j">
					 		<tr class="standard-change">
					 			<td class="standard-change-code">
					 				<span>${j.changeCode}</span>
					 			</td>
					 			<td>&nbsp;</td>
					 			<td class="validity c ">
							 		<c:if test="${not empty j.startValidity}">
										<joda:format value="${j.startValidity}" pattern="${commonPublic.dateTimeFormat}"/>
							 		</c:if>
							 	</td>
							 	<td class="validity c ">
							 		<c:if test="${not empty j.stopValidity}">
										<joda:format value="${j.stopValidity}" pattern="${commonPublic.dateTimeFormat}"/>
							 		</c:if>
					 			</td>
					 			<td>&nbsp;</td>
					 			<td>&nbsp;</td>
					 			<td>&nbsp;</td>
					 		</tr>
				 		</c:forEach>
				 	</c:if>
				 </c:forEach>
			</tbody>
		</table>
		
</c:if>