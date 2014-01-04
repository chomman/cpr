$(document).ready(function() {  
	/*
	function startsWith(str, term){
		str = str.toLowerCase();
		term = term.toLowerCase();
	    return str.indexOf(term.toLowerCase()) == 0;
	}
	
	$("input.query-aono").autocomplete({
		 source: function(request, response){  
			 if(typeof $(".filter").eq(0).attr("data-enabled") !== "undefined"){
				 request.enabled = true;
			 }
		 	 $.getJSON( $("#base").text() +"ajax/autocomplete/aono", request, function(data) {  
	         	 response( $.map( data, function( item ) {	         		 	
	         		 	if(item[0].length > 60){
	         		 		var shortText = item[0].substring(0, 65).split(" ").slice(0, -1).join(" ") + " ...";
	         		 	}else{
	         		 		shortText = item[0];
	         		 	}
	         		 	
	         		 	if(typeof item[1] == "string" && startsWith(item[1], request.term)){
	         		 		return {label: item[1] + ' - ' +shortText, value: item[0]};
	         		 	}else{
	         		 		return {label: item[2] + ' - ' +shortText, value: item[0]};
	             		} 
	             		 
					}));
	        	});  
			 },
			minLength: 1,
			select: function( event, ui ) {
				ui.item.value;
			}
	});
	*/
});