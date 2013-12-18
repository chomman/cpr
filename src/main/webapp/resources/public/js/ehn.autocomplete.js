$(document).ready(function() {    
      $("input.query").autocomplete({
			 source: function(request, response){  
			 	 $.getJSON( $("#base").text() +"ehn/autocomplete", request, function(data) {  
			 		response( $.map( data, function( item ) {
			 			if(item[1].length < 65){
			 		 		return {label: item[1], value: item[1]};
			 		 	}
			 			var shortText = item[1].substring(0, 65).split(" ").slice(0, -1).join(" ") + " ...";
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

