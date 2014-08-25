<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><a:localizedValue object="${model.standardCategory}" fieldName="name" /></title>
		<meta name="keywords" content="${model.standardCategory.code}, <a:localizedValue object="${model.standardCategory}" fieldName="name" />" />
		<meta name="description" content="<a:localizedValue object="${model.standardCategory}" fieldName="name" />" />
		<script type="text/javascript">
		 $(function(){
			 $('table.standards').scrollPagination({
			    	url : getBasePath() +  (isCzechLocale() ? '' : getLocale() + '/') + 'async/standards',
			    	loadingMessage : '<spring:message code="loadingItems" />'
			    }); 
		 });
		</script>		
	</head>
	<body>
			
		<div id="bc">
			<span class="bc-info"><spring:message code="location" />:</span>
			<c:if test="${not empty webpageModel.webpage.parent}">
				<webpage:a webpage="${webpageModel.webpage.parent}" /> &raquo;
			</c:if>
			<c:if test="${not empty webpageModel.webpage}">
				<webpage:a webpage="${webpageModel.webpage}" /> &raquo;
			</c:if>
			<span><a:localizedValue object="${model.standardCategory}" maxLength="45" fieldName="name" />...</span>
		</div> 	
		
		<div id="main-content">
			<article>
				<hgroup>
					<h1>
						<spring:message code="standardCategory.name" />: 
						<a:localizedValue object="${model.standardCategory}" fieldName="name" />
					</h1>
					<h2>
						<spring:message code="standardCategory.specialiyation" />:
						<a:localizedValue object="${model.standardCategory}" fieldName="specialization" />
					</h2>
				</hgroup>
			</article>	
			<table class="group-info">
				<c:if test="${not empty model.standardCategory.code}">
				<tr> 
					<td class="key">
						<strong><spring:message code="standardCategory.code" />:</strong>
					</td>
					<td>
						${model.standardCategory.code}
					</td>
				</tr>
				</c:if>
				<tr> 
					<td class="key">
						<strong><spring:message code="standardCategory.ojeu" />:</strong>
					</td>
					<td>
						<a:localizedValue object="${model.standardCategory}" fieldName="ojeuPublication" />
					</td>
				</tr>
				<c:if test="${not empty model.standardCategory.noaoUrl}">
					<tr class="tooltip" title="<spring:message code="standardCategory.noaoNando" />"> 
						<td class="key">
							<strong>NO/AO:</strong>
						</td>
						<td>
							<a href="${model.standardCategory.noaoUrl}" target="_blank">
								<spring:message code="standardCategory.noaoNando.view" />
							</a>
						</td>
					</tr>
				</c:if>
			</table>
			
			<c:if test="${not empty model.standardCategory.regulations}">
					<table class="regulations">
						<tr>
							<th><spring:message code="regulation.eu" /></th>
							<th><spring:message code="regulation.cs" /></th>
							<th><spring:message code="regulation.sk" /></th>
						</tr>
						<c:forEach items="${model.standardCategory.regulations}" var="i">
							<tr>
								<td>
								<c:if test="${i.euRegulation}">
									<span class="block-item">
										<a href="<a:localizedValue object="${i.euRegulationContent}" fieldName="pdf" />" 
											target="_blank" class="file pdf">
										<a:localizedValue object="${i.euRegulationContent}" fieldName="name" />
										</a>
									</span>
									<span class="pj-reg-descr">
									<a:localizedValue object="${i.euRegulationContent}" fieldName="description" />	
									</span>
								</c:if>
								</td>
								<td>
								<c:if test="${i.csRegulation}">
									<span class="block-item">
										<a href="<a:localizedValue object="${i.csRegulationContent}" fieldName="pdf" />" 
											target="_blank" class="file pdf">
										<a:localizedValue object="${i.csRegulationContent}" fieldName="name" />
										</a>
									</span>
									<span class="pj-reg-descr">
									<a:localizedValue object="${i.csRegulationContent}" fieldName="description" />	
									</span>
								</c:if>
								</td>
								<td>
								<c:if test="${i.skRegulation}">
									<span class="block-item">
										<a href="<a:localizedValue object="${i.skRegulationContent}" fieldName="pdf" />" 
											target="_blank" class="file pdf">
										<a:localizedValue object="${i.skRegulationContent}" fieldName="name" />
										</a>
									</span>
									<span class="pj-reg-descr">
									<a:localizedValue object="${i.skRegulationContent}" fieldName="description" />	
									</span>
								</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<jsp:include page="../../include/standard-table.jsp" />
		</div> 
		<div id="strParams" class="hidden">${model.strParams}</div>
	</body>
</html>


