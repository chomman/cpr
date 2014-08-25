<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="editable" value="false" />
<sec:authorize access="hasRole('ROLE_PORTAL_ADMIN')"> 
	<c:set var="editable" value="true"  />
</sec:authorize>
<c:if test="${not empty model.standards}">
	
	<c:if test="${not empty isPreview and isPreview and not empty model.standard and not model.standard.enabled}">
		<p class="msg alert">
			<spring:message code="standard.alert.published" arguments="${model.standard.standardId}" />
		</p>
	</c:if>
	
	<c:if test="${empty model.async or (not empty isPreview and isPreview)}">
		<c:if test="${not model.isCprView}">
			<script>
				$(function(){
					 refreshTable();
					 $(document).on('pageloaded', refreshTable);
				});
				function refreshTable(){
					$('td.tmp').each(function(){
						var $td = $(this);
						$td.find('a').each(function(){
							var $a = $(this);
							$a.addClass("file pdf min").attr("target","_blank");
						});
						$td.html($td.html().replace(">,",">"));
						$td.html($td.html().replace(/<br>/g,""));
						$td.removeClass('tmp');
					});
				}
			</script>
		</c:if>
		<table class="standards">
			<thead>
				<tr>
					<th><spring:message code="standard.id" /></th>
					<th class="tooltip" title="<spring:message code="standard.help.csn" />">
						<span class="tooltip-wrapp" style="padding-top:30px">
							<span class="tooltip-ico"></span>
							<spring:message code="csn" />
						</span>
					</th>
					<th><spring:message code="standard.name" /></th>
					<th class="tooltip" title="<spring:message code="standard.help.validity" />">
						<span class="tooltip-wrapp" style="padding-top:15px"> 
							<span class="tooltip-ico"></span>
							<spring:message code="standard.validity" />
						</span>
						
					</th>
					<th class="tooltip" title="<spring:message code="standard.help.endofvalidity" />">
						<span class="tooltip-wrapp" style="padding-top:10px">
							<span class="tooltip-ico"></span>
							<spring:message code="standard.endofvalidity" />
						</span>
						
					</th>
					<c:if test="${model.isCprView}">
					<th class="tooltip" title="<spring:message code="standard.help.noao" />">
						<span class="tooltip-wrapp" style="padding-top:20px">
							<span class="tooltip-ico"></span>
							<spring:message code="standard.noao" />
						</span>
					</th>
					<th class="tooltip" title="<spring:message code="standard.help.assessment" />">
						<span class="tooltip-wrapp" style="padding-top:20px">
							<span class="tooltip-ico"></span>
							<spring:message code="assessment" />
						</span>
					</th>
					<th class="tooltip" title="<spring:message code="standard.help.standardGroups" />">
						<span class="tooltip-wrapp" style="padding-top:20px">
							<span class="tooltip-ico"></span>
							<spring:message code="standard.standardGroups" />
						</span>
					</th>
					</c:if>
					<c:if test="${not model.isCprView}">
						<th class="c ehn-ojeu">
							<spring:message code="standardCategory.ojeu" />
						</th>
						<th class="ehn-regulations">
							<spring:message code="regulations" />
						</th>
					</c:if>
				</tr>
			</thead>
			<tbody class="pagi-content">
	</c:if>
				 <c:forEach items="${model.standards}" var="i">
				 	<tr class="<c:if test="${i.hasChanges}">has-change</c:if> s-row-${i.statusClass}" >
				 	
				 	
				 		<!-- EHn --> 
				 		<td class="norm">
				 			<span>
				 				<a:standardUrl standard="${i}" editable="${editable}"  />
				 			</span>
				 			<c:if test="${not empty i.standardStatus and i.standardStatus.id != 1}">
		 						<span class="block"><spring:message code="${i.standardStatus.name}" /></span>
		 					</c:if>
				 			<c:if test="${not empty i.replaceStandard}">	
			 					<span class="block pj-separator">
				 					<c:if test="${i.isCanceled}">
					 					<spring:message code="replaced" />
				 					</c:if>
				 					<c:if test="${not i.isCanceled and i.replaceStandard.isCanceled}">
					 					<spring:message code="replace" />
				 					</c:if>
				 					<c:if test="${csn.replaceStandardCsn.isConcurrentValid}">
					 					<spring:message code="validConcurrent" />
				 					</c:if>
			 					</span>
				 					<a:standardUrl standard="${i.replaceStandard}" editable="${editable}" />
				 					<c:if test="${not empty i.replaceStandard.standardStatus and i.replaceStandard.standardStatus.id != 1}">
				 						<span class="block"><spring:message code="${i.replaceStandard.standardStatus.name}" /></span>
				 					</c:if>
		 					</c:if>
		 					
				 		</td>
				 		
				 		
				 		
				 		<!-- CSN --> 
				 		<td class="csns">
				 			<c:set var="prevCsn" value="${null}" />
					 		<c:forEach items="${i.standardCsns}" var="csn" >
								<c:if test="${empty prevCsn or prevCsn.id != csn.id }" >
									<a:standardCsnUrl object="${csn}" editable="${editable}" />
							 		<c:forEach items="${csn.standardCsnChanges}" var="j">
							 			<a:standardCsnUrl object="${j}" editable="${editable}" />
							 		</c:forEach>
							 			<c:if test="${not empty csn.standardStatus and csn.standardStatus.id != 1}">
					 						<span class="block"><spring:message code="${csn.standardStatus.name}" /></span>
					 					</c:if>
									 	<c:if test="${not empty csn.replaceStandardCsn }">	
						 					<span class="block pj-separator">
						 					<c:if test="${csn.isCanceled}">
							 					<spring:message code="replaced" />
						 					</c:if>
						 					<c:if test="${csn.replaceStandardCsn.isCanceled}">
							 					<spring:message code="replace" />
						 					</c:if>
						 					<c:if test="${csn.replaceStandardCsn.isConcurrentValid}">
							 					<spring:message code="validConcurrent" />
						 					</c:if>
						 					</span>
						 					<a:standardCsnUrl object="${csn.replaceStandardCsn}" editable="${editable}" />
						 					<c:set var="prevCsn" value="${csn.replaceStandardCsn}" />
					 					</c:if>
					 					<c:if test="${not empty csn.replaceStandardCsn.standardStatus and csn.replaceStandardCsn.standardStatus.id != 1}">
					 						<span class="block"><spring:message code="${csn.replaceStandardCsn.standardStatus.name}" /></span>
					 					</c:if>
					 					<c:if test="${empty csn.replaceStandardCsn }">
					 						<c:set var="prevCsn" value="${null}" />
					 					</c:if>						 					
				 				</c:if>
							</c:forEach>
				 		</td>
				 		
				 		<!-- Name --> 
				 		<td class="s-name">
				 			<span class="s-czechName" >${i.name}</span>
				 		</td>
				 		
				 		
				 		<!-- VALIDITY -->
				 		<td class="validity c ">
				 			<c:if test="${not empty i.startValidity}">
								<joda:format value="${i.startValidity}" pattern="dd.MM.yyyy"/>
				 			</c:if>
				 		</td>
				 		<td class="validity c ">
				 			<c:if test="${not empty i.stopValidity}">
								<joda:format value="${i.stopValidity}" pattern="dd.MM.yyyy"/>
				 			</c:if>
				 		</td>
				 		
				 		<c:if test="${model.isCprView}">
					 		<!-- NOAO -->
					 		<td class="aono c">
					 			<c:if test="${not empty i.notifiedBodies}">
					 				<c:forEach items="${i.notifiedBodies}" var="j" >
					 					<span class="block">
					 					<a:noaoUrl object="${j}" editable="${editable}" buildNandoUrl="true" />
					 					</span>
					 				</c:forEach>
					 			</c:if>
					 			<c:if test="${empty i.notifiedBodies}">
					 				-
					 			</c:if>
					 		</td>
					 		
					 		<!-- assesment systems -->
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
					 		
					 		
					 		<!-- STANDARD GROUPS -->
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
				 		</c:if>
				 		<c:if test="${not model.isCprView}">
				 			<td class="ehn-ojeu tmp">
								<c:if test="${not empty i.standardCategory}">
									<a:localizedValue object="${i.standardCategory}" fieldName="ojeuPublication" />
								</c:if>
							</td>
							<td class="ehn-regulations">
								<c:if test="${not empty i.standardCategory}">
									<c:forEach items="${i.standardCategory.regulations}" var="j">
										<span class="ehn-regulation eu-${j.euRegulation} cs-${j.csRegulation} sk-${j.skRegulation}">
				 						<span class="ico"></span>
				 						<a 
				 						href="<a:localizedValue object="${j.csRegulationContent}" fieldName="pdf" />" 
				 						title="<a:localizedValue object="${j.regulationContent}" fieldName="name" />"
				 						class="file pdf min tooltip">
				 						${j.code}
				 						</a>
				 					</span>
			 						</c:forEach>
								</c:if>
							</td>
				 		</c:if>
				 		
				 	</tr>
				 	<c:if test="${not empty i.standardChanges}">
				 		<c:forEach items="${i.standardChanges}" var="j">
					 		<tr class="standard-change">
					 			<td class="standard-change-code">
					 				<span>${j.changeCode}</span>
					 			</td>
					 			<td>&nbsp;</td>
					 			<td>&nbsp;</td>
					 			<td class="validity c ">
							 		<c:if test="${not empty j.startValidity}">
										<joda:format value="${j.startValidity}" pattern="dd.MM.yyyy"/>
							 		</c:if>
							 	</td>
							 	<td class="validity c ">
							 		<c:if test="${not empty j.stopValidity}">
										<joda:format value="${j.stopValidity}" pattern="dd.MM.yyyy"/>
							 		</c:if>
					 			</td>
					 			<td>&nbsp;</td>
					 			<td>&nbsp;</td>
					 			<c:if test="${model.isCprView}">
					 			<td>&nbsp;</td>
					 			</c:if>
					 		</tr>
				 		</c:forEach>
				 	</c:if>
				 </c:forEach>
	 <c:if test="${empty model.async or (not empty isPreview and isPreview)}">
			</tbody>
		</table>
	</c:if>
</c:if>