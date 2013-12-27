  var $input = $("#standardPicker"), 
$statusSelect = $('#standardStatus'),
$inputLabel = $('#standard-replaced-label');
  
$(document).ready(function() {    
  
    refreshInputLabel();
    
    $input.autocomplete({
			 source: function(request, response){  
			 	 $.getJSON( $("#base").text() +"admin/cpr/standard/autocomplete", request, function(data) {  
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
    
    $(document).on("click", ".standard-link-cancel", cancelSelection);
    
    // aktualizuje popisok inputu pre vyhladavanie normy
    $('#standardStatus').change( refreshInputLabel );
    
    function refreshInputLabel(){
    	var selectedVal = $('#standardStatus').find("option:selected").val().toLowerCase();
    	if(selectedVal === "canceled"){
    		$inputLabel.text('norma je nahrazena: ');
    	}else{
    		$inputLabel.text('norma nahrazuje: ');
    	}
    	return false;
    }    	
});

function cancelSelection(){
	$('.picker-item').remove();
	$input.val('');
	$input.show();
	$('#pickerVal').val('');
}


function linkUrl(id){
	var url = document.location.href;
	return url.substr(0, url.lastIndexOf("/") + 1) + id;
}

function selectStandard(code, id){
	console.log(code,id);
	var html = '<a class="resetmargin picker-item standard-link" target="_blank" href="'+linkUrl(id)+'">'+code+'</a>';
	html += '<span class="resetmargin picker-item standard-link-cancel">zrušit provázání</span>';
	$("#standardPicker").after(html).hide();
	$('#pickerVal').val(id);
}

