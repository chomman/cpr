$(document).ready(function() {    
      $("input.query").autocomplete({
			 source: function(request, response){  
			 	 $.getJSON( $("#base").text() +"ajax/terminology/autocomplete", request, function(data) {  
                 	 response( $.map( data, function( item ) {
							return {label: item[1], value: item[1]};
						}));
            	});  
			 },
			minLength: 2,
			select: function( event, ui ) {
				ui.item.value;
			}
	});
      
      
     $('input[name=c]').change(function(){
    	 var val = $(this).val();
    	 if(val.length == 0) return;
    	 $.getJSON( $("#base").text() +"ajax/csn/category/" + val, function(data) {  
         	 console.log(data);
		  });
     }); 
});