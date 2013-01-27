$(document).ready(function() {    
      $("input.query").autocomplete({
			 source: function(request, response){  
			 	 $.getJSON( $("#base").text() +"admin/user/autocomplete", request, function(data) {  
                 	 response( $.map( data, function( item ) {
                 			 var shortText = jQuery.trim(item[1] + " " +  item[2]);
							return {label: shortText, value: item[1]};
						}));
            	});  
			 },
			minLength: 2,
			select: function( event, ui ) {
				ui.item.value;
			}
	});
});