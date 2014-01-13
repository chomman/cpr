<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
		<meta name="description" content="${model.webpage.description}" />
		<link rel="stylesheet" href="<c:url value="/resources/public/css/common.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/admin/css/chosen.css" />" />
		<script src="<c:url value="/resources/public/js/ehn.autocomplete.js" />"></script>
		<script src="<c:url value="/resources/admin/js/chosen.jquery.min.js" />"></script>
		<script src="<c:url value="/resources/public/js/picker.jquery.js" />"></script>
		<script type="text/javascript">  
		$(document).ready(function() {    
			$(".chosenSmall").chosen({
		    	 width : "200px"
		     });
		     $(".chosenMini").chosen({
		    	 width : "110px"
		     });
		     $('.groups').chosen({
		    	 width : "700px"
		     });
		    if(isStandardAdvancedSarch()){
		    	showAdvancedForm();
		    }
		    $('.filter').on('click', '.filter-advanced-btn', showAdvancedForm);
		    function showAdvancedForm(){
		    	loadFilterData(); 
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
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<a title="${model.parentWebpage.title}" href="<c:url value="${model.parentWebpage.code}" />">${model.parentWebpage.name}</a> &raquo;
			<span>${model.webpage.name}</span>
	</div> 

			<div id="main-content">
				<c:if test="${not empty model.webpage.title}" >
				<h1>${model.webpage.title}</h1>
			</c:if>
			
			<c:if test="${not empty model.webpage.topText}" >
				<h1>${model.webpage.topText}</h1>
			</c:if>
			
			<form class="filter"  method="get">
				<div class="filter-advanced">
					<span class="filter-label long"><spring:message code="form.groups" />:</span>
					<select name="standardGroup" style="width:600px;" class="groups async" data-items="standardGroups">
						<option value=""><spring:message code="cpr.standard.filter.default" /></option>
					</select>
					<div class="clear"></div>
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
					<div class="clear"></div>
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
					<div class="clear"></div>
				</div>
				
				<div class="filter-advanced">
					<span class="filter-label long"><spring:message code="cpr.nb.filter" />:</span>
					<input type="text" class="query-aono mw500" name="notifiedBody" />
					<div class="clear"></div>
				</div>
				<div>
					<span class="filter-label long"><spring:message code="form.name" />/Označení</span>
					<input type="text" class="query " name="query"   value="${model.params.query}" />
					
					<input type="submit" value="Filtrovat" class="btn filter-btn-standard radius" />
					<a href="#" class="filter-advanced-btn">
						<spring:message code="cpr.standard.filter.advanced" />
					</a>
					<div class="clear"></div>
				</div>
			</form>
			
			<c:if test="${empty model.standards}">
				<p class="msg alert">
					<spring:message code="alert.empty" />
				</p>
			</c:if>
			
			
			<jsp:include page="include/standard-table.jsp" />
			
			<c:if test="${not empty model.webpage.bottomText}" >
				<h1>${model.webpage.bottomText}</h1>
			</c:if>

			
		</div>
	</body>
</html>


