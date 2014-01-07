$(document).ready(function() {    
	var baseUrl = $("#base").text();
	$("input.query").autocomplete({
		 source: function(request, response){  
		 	$.getJSON( baseUrl +"ajax/terminology/autocomplete", request, function(data) {  
		 		 response(data );
      	});  
		 },
		minLength: 1,
		select: function( event, ui ) {
			ui.item.value;
		}
	});
	
	
	$("input.csnId").autocomplete({
		 source: function(request, response){  
		 	 $.getJSON( baseUrl +"ajax/csn/autocomplete", request, function(data) {
		 		 response( data );
     	});  
		 },
		minLength: 1,
		select: function( event, ui ) {
			ui.item.value;
		}
	});
	
	
	
	$("input.csnCategory").autocomplete({
		 source: function(request, response){  
			 var url =  (request.term.length > 4 ? '/cs' : '');
		 	 $.getJSON( baseUrl +"ajax/csn/category/autocomplete"+url, request, function(data) {  
		 		 response( $.map( data, function( item ) {
		 			 return {label: item[1] + " | " + item[0], value: item[1]};
				 }));
       	});  
		 },
		minLength: 1,
		select: function( event, ui ) {
			ui.item.value;
		}
});
      
      
     $('input[name=c]').change(function(){
    	 var val = $(this).val();
    	 if(val.length == 0) return;
    	 $.getJSON( baseUrl +"ajax/csn/category/" + val, function(data) {  
         	 console.log(data);
		  });
     }); 
});