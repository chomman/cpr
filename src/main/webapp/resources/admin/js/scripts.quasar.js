$(function(){
	$(document).on("click",".qs-btn", function(){
		$(this).parent().prev().find('form').submit();
		return false;
	});
	
	$(document).on("click", '.qs-inline-edit', showForm);
	$(document).on("click", '#loadFileManager', loadFileManager);
	
	function showForm(){
		$('.qs-editting').removeClass('qs-editting');
		var $this = $(this), 
			$form = $('#f' + $this.attr('data-id')),
			$input = $form.find('input.nb-picker').eq(0),
			opts = { sourceUrl : getBasePath() +"ajax/autocomplete/aono" };
		$form.addClass('qs-editting');
		checkBoxRefresh($form);
		$input.val('').unbind().removeData();
		if($form.find('input[type=checkbox]:checked').length > 0){
			showDetailBox($form);
		}else{
			hideDetailBox($form);
		}
		
		if(typeof $input.attr('data-json') !== 'undefined'){
			var data = $input.attr('data-json').split("##");
			if(data.length === 2){
				opts.item = { id: data[0], value: data[1]};
			}
		}else{
			opts.item = null;
		}
		$input.remotePicker(opts);
		return false;
	}
	
	$(document).on('click','.qs-item .qs-field a.mandate-add-btn', function(){
		$(this).parents('form').submit();
		return false;
	});
	
	$(document).on("change", ".ch-refused, .ch-approved", function(){
		var $this = $(this),
			$form = $this.parents('form');
		checkBoxRefresh( $form );	
		detailBoxRefresh($this, $form);
		return false;
	});
	
	$(document).one('click', '.qs-result-box', function(){
		$(this).find('ul.hidden').removeClass('hidden');
		$(this).find('a').remove();
		return false;
	});
	
	$(document).on('change', '.submit-on-change input', function(){
		$(this).parents('form').submit();	
	});		
	
	function detailBoxRefresh($checkbox, $form){
		if($checkbox.is(':checked')){
			showDetailBox($form);
		}else{
			hideDetailBox($form);
		}
	}
	
	function showDetailBox($form){
		$form.find('.reason-detail').show();
	}
	function hideDetailBox($form){
		$form.find('.reason-detail').hide();
	}
	
	
	function checkBoxRefresh($form){
		$form.find('input[type=checkbox]').each(function(){
			refresh($(this));
		});
		function refresh($this){
			$form.find( $this.hasClass("ch-approved") ? ".ch-refused" : ".ch-approved").prop("disabled", $this.is(':checked') );
		}
	}
	
	 $("input.query").autocomplete({
		 source: function(request, response){  
		 	 $.getJSON( getBasePath() +"admin/quasar/auditors", request, function(data) {  
             	 response( $.map( data, function( item ) {
						return {label: item[1], value: item[1]};
					}));
        	});  
		 },
		minLength: 1,
		select: function( event, ui ) {
			ui.item.value;
		}
	 });
	
	 function loadFileManager(){
		 var $wrapp = $('.fileManagment');
		 if($wrapp.length === 0){
			 return false;
		 }
		 $('<iframe/>', {
			 width : "100%",
			 height : "450px",
			 src : getBasePath() + 'admin/file-manager.htm?uploadType=3&id=' + $wrapp.attr('data-id')
		 }).appendTo($wrapp.find('div'));
		 $wrapp.removeClass('hidden');
		 $(this).remove();
	 }
	
});