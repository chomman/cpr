<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">  
$(document).ready(function() {    
	$(".chosenSmall").chosen({ width : "200px" });
    $(".chosenMini").chosen({ width : "110px" });
    $('.groups').chosen({ width : "700px" });
  	$(".chosenCustom").chosen({ width: "270px" });
  	$("select").not(".chosenSmall,.groups,.chosenCustom, .chosenMini").chosen({
   	 width : "510px"
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
    	sourceUrl : getBasePath() +"ajax/autocomplete/aono"	,
    	enabledOnly : true
    });
    $('table.standards').scrollPagination({
    	url : getBasePath() +  (isCzechLocale() ? '' : getLocale() + '/') + 'async/standards',
    	loadingMessage : '<spring:message code="loadingItems" />'
    });
});
</script>