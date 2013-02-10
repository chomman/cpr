$(document).ready(function() {    
      $("input.query").autocomplete({
			 source: function(request, response){  
			 	 $.getJSON( $("#base").text() +"tag/autocomplete", request, function(data) {  
                 	 response( $.map( data, function( item ) {
                 		 	console.log(item);
							return {label: item[1], value: item[1]};
						}));
            	});  
			 },
			minLength: 2,
			select: function( event, ui ) {
				ui.item.value;
			}
	});
});