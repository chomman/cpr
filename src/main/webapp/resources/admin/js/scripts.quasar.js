$(function(){
	$(document).on("click",".qs-btn", function(){
		$(this).parent().prev().find('form').submit();
		return false;
	});
	
	$(document).on("click", '.qs-inline-edit', showForm);
	
	function showForm(){
		$('.qs-editting').removeClass('qs-editting');
		var $this = $(this), 
			$form = $('#f' + $this.attr('data-id')),
			$input = $form.find('input[name=notifiedBody]').eq(0),
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
		console.log($form.find('input[name=notifiedBody]'));
		$input.remotePicker(opts);
		return false;
	}
	
	$(document).on('click','.qs-item .qs-field a', function(){
		$(this).parents('form').submit();
		console.log($(this).parents('form'));
		return false;
	});
	
	$(document).on("change", ".ch-refused, .ch-approved", function(){
		var $this = $(this),
			$form = $this.parents('form');
		checkBoxRefresh( $form );	
		detailBoxRefresh($this, $form);
		return false;
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
	
	
});