<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<script type="text/javascript">  
$(document).ready(function() {    
	$(".chosenSmall").chosen({ width : "200px" });
    $(".chosenMini").chosen({ width : "110px" });
    $('.groups').chosen({ width : "700px" });
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
    	sourceUrl : getBasePath() +"ajax/autocomplete/aono"	,
    	enabledOnly : true
    });
    $('table.standards').scrollPagination({
    	url : getBasePath() +  (getLocale() == 'cs' ? '' : getLocale() + '/') + 'async/standards',
    	loadingMessage : '<spring:message code="loadingItems" />'
    });
});
</script>

			
	<form class="filter"  method="get">
		<div class="filter-advanced">
			<span class="filter-label long"><spring:message code="filter.standard.standardGroup" />:</span>
			<select name="standardGroup" style="width:600px;" class="groups async" data-items="standardGroups">
				<option value=""><spring:message code="cpr.standard.filter.default" /></option>
			</select>
			<div class="clear"></div>
		</div>
		<div class="filter-advanced">
			<span class="filter-label long"><spring:message code="filter.standard.commisionDecision" />:</span>
			<select name="commissionDecisionId" class="async chosenSmall" data-items="commissionDecisions">
				<option value=""><spring:message code="cpr.standard.filter.default" /></option>
			</select>
		
			<span class="filter-label"> &nbsp; &nbsp; <spring:message code="filter.standard.mandate" />:</span>
			<select name="mandateId" class="async chosenSmall" data-items="mandates">
				<option value=""><spring:message code="cpr.standard.filter.default" /></option>
			</select>
			<div class="clear"></div>
		</div>
		
		<div class="filter-advanced">
			<span class="filter-label long"><spring:message code="filter.standard.assessmentSystem" />:</span>
			<select name="assessmentSystemId" class="async chosenSmall" data-items="assessmentSystems">
				<option value=""><spring:message code="cpr.standard.filter.default" /></option>
			</select>
		
			<span class="filter-label"> &nbsp; &nbsp; <spring:message code="filter.standard.standardStatus" />: &nbsp;</span>
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
			<span class="filter-label long"><spring:message code="filter.standard.notifiedBody" />:</span>
			<input type="text" class="query-aono mw500" name="notifiedBody" />
			<div class="clear"></div>
		</div>
		<div>
			<span class="filter-label long"><spring:message code="filter.standard.query" /></span>
			<input type="text" class="query" name="query"   value="${model.params.query}" />
			
			<input type="submit" value="<spring:message code="filter.standard.filter" />" class="btn filter-btn-standard radius" />
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
	
<jsp:include page="../include/standard-table.jsp" />
<div id="strParams" class="hidden">${model.strParams}</div>