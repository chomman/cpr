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
			$input = $form.find('input[name=notifiedBody]'),
			opts = { sourceUrl : getBasePath() +"ajax/autocomplete/aono" };
		$form.addClass('qs-editting');
		if(typeof $input.attr('data-json') !== 'undefined'){
			opts.item = $.parseJSON($input.attr('data-json'));
		}
		
		$input.remotePicker(opts);
		return false;
	}
	
});