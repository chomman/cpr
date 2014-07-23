<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<script type="text/javascript">
$(function(){ 
	$('input[name=aid]').remotePicker({
    	sourceUrl : getBasePath() +"admin/quasar/auditors?adminsOnly=false",
    	useDefaultCallBack : true
    });
	$(document).on('click','a.create-dr', function(){
		$('form.dr').removeClass('hidden');
		$(this).remove();
		return false;
	});
	$(document).on('selected', function(){
		$('.mandate-add-btn').removeClass('hidden');
	});
	$(document).on('unselected', function(){
		$('.mandate-add-btn').addClass('hidden');
	});
	$(document).on('click', 'form.dr a', function(){
		if($('input[name=aid]').attr('type') === 'text'){
			return false;
		}
		$('.dr').submit();
		return false;
	});
});
</script>
<script src="<c:url value="/resources/public/js/picker.jquery.js" />"></script>
<c:url var="url" value="/admin/quasar/dossier-report/0" />
<a class="create-dr" href=""><spring:message code="new" /> <spring:message code="dossierReport" /> to concrete Worker</a>
<form action="${url}" class="dr hidden">
<p>
	<label>Search concrete auditor:</label>
    <span class="field">
  		<input type="text" name="aid" class="w200" />
  		<a href="" class="hidden lang mandate-add-btn">
  			<spring:message code="new" />
  			<spring:message code="dossierReport" />
  		</a> 
    </span>
</p>
</form><br />
