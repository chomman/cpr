<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="menu.cpr.norm" /></title>
<script src="<c:url value="/resources/public/js/standard.autoc omplate.js" />"></script>
<script src="<c:url value="/resources/public/js/picker.jquery.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr" />"><spring:message code="menu.cpr" /></a> &raquo;
			 <span><spring:message code="menu.cpr.norm" /></span>
		</div>
		<h1><spring:message code="cpr.groups.title" /></h1>

		<div id="content">
			
			<script type="text/javascript">  
			$(document).ready(function() {    
			      $("input.query").autocomplete({
						 source: function(request, response){  
						 	 $.getJSON( $("#base").text() +"admin/cpr/standard/autocomplete", request, function(data) {  
			                 	 response( $.map( data, function( item ) {
			                 		if(item[1].toLowerCase().indexOf(request.term) >= 0){
		                 		 		return {label: item[1], value: item[1]};
		                 		 	}
			                 		var shortText = item[2].substring(0, 65).split(" ").slice(0, -1).join(" ") + " ...";
		                 		 	return {label: shortText, value: item[2]};
									}));
			            	});  
						 },
						minLength: 2,
						select: function( event, ui ) {
							ui.item.value;
					}
				});
			      
			    loadFilterData(); 
			    
			    if(isStandardAdvancedSarch()){
			    	showAdvancedForm();
			    }
			    $('.filter').on('click', '.filter-advanced-btn', showAdvancedForm);
			    
			    function showAdvancedForm(){
			    	$('.filter-advanced-btn').remove();
			    	$('.filter-advanced').removeClass('filter-advanced');
			    	return false;
			    }
			    $('input[name=notifiedBody]').remotePicker({
			    	<c:if test="${not empty model.params.notifiedBody}">item: {id: ${model.params.notifiedBody.id}, value: '${model.params.notifiedBody.noCode} - ${model.params.notifiedBody.name}'},</c:if>    	
			    	sourceUrl : $("#base").text() +"ajax/autocomplete/aono"			    	
			    });
			});
			</script>
			
			<ul class="sub-nav">
				<li><a class="active" href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
				<li><a href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
				<c:if test="${not empty model.webpage}">
					<li><webpage:a webpage="${model.webpage}" target="_blank" /></li>
				</c:if>
			</ul>
			
			<form class="filter"  action="<c:url value="/admin/cpr/standards" />" method="get">
				<div class="filter-advanced">
					<span class="filter-label long"><spring:message code="form.orderby" />:</span>
					<select name="orderBy" class="chosenSmall">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
						</c:forEach>
					</select>
					<span class="filter-label"><spring:message code="cpr.standard.added" /></span>
					<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span class="filter-label">do:</span>
					<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
					
				</div>
				<div class="filter-advanced">
					<span class="filter-label long"><spring:message code="form.groups" />:</span>
					<select name="standardGroup" class="groups async" data-items="standardGroups">
						<option value=""><spring:message code="cpr.standard.filter.default" /></option>
					</select>
				</div>
				<div class="filter-advanced">
					<span class="filter-label long"><spring:message code="cpr.commisiondecision.name" />:</span>
					<select name="commissionDecisionId" class="async chosenSmall" data-items="commissionDecisions">
						<option value=""><spring:message code="cpr.standard.filter.default" /></option>
					</select>
				
					<span class="filter-label"> &nbsp; &nbsp; <spring:message code="cpr.standard.filter.mandate" />:</span>
					<select name="mandateId" class="async chosenSmall" data-items="mandates">
						<option value=""><spring:message code="cpr.standard.filter.default" /></option>
					</select>
				</div>
				
				<div class="filter-advanced">
					<span class="filter-label long"><spring:message code="cpr.standard.filter.as" />:</span>
					<select name="assessmentSystemId" class="async chosenSmall" data-items="assessmentSystems">
						<option value=""><spring:message code="cpr.standard.filter.default" /></option>
					</select>
				
					<span class="filter-label"> &nbsp; &nbsp; <spring:message code="cpr.standard.filter.status" />: &nbsp;</span>
					<select name="standardStatus" class="chosenSmall">
						<option value=""><spring:message code="cpr.standard.filter.default" /></option>
						<c:forEach items="${model.standardStatuses}" var="i">
	                       <option value="${i}" <c:if test="${i.code == model.params.standardStatus}">selected="selected"</c:if> >
	                       		<spring:message code="${i.name}" />
	                       </option>
		               </c:forEach>
					</select>
				</div>
				
				<div class="filter-advanced">
					<span class="filter-label long"><spring:message code="cpr.nb.filter" />:</span>
					<input type="text" class="query-aono mw500" name="notifiedBody" />
				</div>
				<div>
					<span class="filter-label long"><spring:message code="form.name" />/Označení</span>
					<input type="text" class="query " name="query"   value="${model.params.query}" />
					
					<input type="submit" value="Filtrovat" class="btn filter-btn-standard" />
					<a href="#" class="filter-advanced-btn">
						<spring:message code="cpr.standard.filter.advanced" />
					</a>
				</div>
			</form>
			
			
			<div class="items-count">
				<span><spring:message code="items.count" arguments="${model.count}" /></span>
			</div>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
						
			<c:if test="${not empty model.standards}">
				
				<!-- STRANKOVANIE -->
				<c:if test="${not empty model.paginationLinks}" >
					<div class="pagination">
					<c:forEach items="${model.paginationLinks}" var="i">
						<c:if test="${not empty i.url}">
							<a title="Stánka č. ${i.anchor}"  class="tt"  href="<c:url value="${i.url}"  />">${i.anchor}</a>
						</c:if>
						<c:if test="${empty i.url}">
							<span>${i.anchor}</span>
						</c:if>
					</c:forEach>
					</div>
				</c:if>
				
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="cpr.standard.id" /></th>
							<th><spring:message code="cpr.standard.name" /></th>
							<th><spring:message code="published" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.standards}" var="i">
						 	<c:if test="${not empty i.standardStatus }">
						 		<tr class="${i.standardStatus.cssClass}">
						 	</c:if>
						 	<c:if test="${empty i.standardStatus }">
						 		<tr>
						 	</c:if>
						 		<td class="standarardId">
							 		<a title="Zobrazit a upraviť položku" href="<c:url value="/admin/cpr/standard/edit/${i.id}"  />">
							 			${i.standardId}
							 		</a>
						 		</td>
						 		<td>${i.czechName}</td>
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
						 		<td class="last-edit">
						 			<c:if test="${empty i.changedBy}">
						 				<joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/>
						 			</c:if>
						 			<c:if test="${not empty i.changedBy}">
						 				<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
						 			</c:if>
						 		</td>
						 		<td class="edit">
						 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/cpr/standard/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/cpr/standard/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.standards}">
				<p class="msg alert">
					<spring:message code="alert.empty" />
				</p>
			</c:if>

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>