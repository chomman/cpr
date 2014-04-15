(function($) {

$.fn.standardPicker = function(opts) {
		
	var options = { 
		url : '',
		inputName : 'replaceStandard',
		item : null
	};
	
	if(opts) {
		options = $.extend(opts, options);
	}
	
	$this = $(this);
	
	if($this.length == 1){
		
		
		$this.autocomplete({
			 source: function(request, response){  
			 	 $.getJSON( getBasePath + url, request, function(data) {  
                 	 response( $.map( data, function( item ) {
                 		return {label: item[1], value: item[0]};
					}));
            	});  
			 },
			minLength: 1,
			select: function( event, ui ) {
				selectStandard( ui.item.label, ui.item.value);
				return false;
			}
		});
	}else{
		console.warn('Multiple instances of standardPicker are not allowed');
	}
	
	$(document).on("click", ".standard-link-cancel", cancelSelection);
	
	function createElements(){
		var html  = '<input id="standardPicker" type="'+(options.item != null ? "text" : "hidden")+'" >';
		$this.parent().append()
	}
	
	function refreshInputLabel(){
    	if($standardStatus.length === 0){
    		return false;
    	}
    	var selectedVal = $standardStatus.find("option:selected").val(),
    		$inputLabel = $('#standard-replaced-label');
    	if(selectedVal === "CONCURRENT"){
    		$inputLabel.text('platí souběžně s: ');
    	}else if(selectedVal.indexOf("CANCELED") > -1){
    		$inputLabel.text('norma je nahrazena: ');
    	}else{
    		$inputLabel.text('norma nahrazuje: ');
    	}
    	return false;
    }    
	
	function cancelSelection(){
		$('.picker-item').remove();
		$this.val('').show();
		$('#pickerVal').val('');
	}
	
	
	
	function selectStandard(code, id){
		var html = '<a class="resetmargin picker-item standard-link" target="_blank" href="#">'+code+'</a>';
		html += '<span class="resetmargin picker-item standard-link-cancel">zrušit provázání</span>';
		$("#standardPicker").after(html).hide();
		$('#pickerVal').val(id);
		var o = $('#statusDateWrapp');
		o.removeClass('hidden');
		o.find('input').datepicker(datepickerOpts);
	}

}; 

})(jQuery);