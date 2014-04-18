(function ( $ ) {   
    var options = { 
    	item : null,
        sourceUrl : "",
        cancelText : "zrušit výběr",
        selectedClass : 'pj-picker-item',
        cancelBtnClass : 'pj-cancel',
        autocompleteCallBack : null,
        enabledOnly : false,
        useDefaultCallBack : false,
        debug: false,
        inputNames : {
        	hidden : null, text : null
        }
    };
    
    $.fn.remotePicker = function(newOpts) {
    	options = $.extend( {}, options, newOpts );
        var $this = $(this);
        if(options.debug){
        	console.log('creating plugin..');
        	console.log($this);
        	console.log(options);
        }        
        
        if(options.debug){
        	console.info(options.autocompleteCallBack);
        }
        
        $this.autocomplete(  {
        	source: options.autocompleteCallBack !== null ? options.autocompleteCallBack : function(request, response){  
   			 if(options.enabledOnly){
   				 request.enabled = true;
   			 }
   		 	 $.getJSON( options.sourceUrl , request, function(data) {  
   	         	 response( $.map( data,  function( item) {	         		 	
   	         		 	
   	         		 	if(options.useDefaultCallBack){
   	         		 	return {label: item[1], value: item[0]};
   	         		 	}
   	         		 	
			    		if(item[1].length > 60){
   	         		 		var shortText = item[1].substring(0, 65).split(" ").slice(0, -1).join(" ") + " ...";
   	         		 	}else{
   	         		 		shortText = item[1];
   	         		 	}
   	         		 	
   	         		 	if(typeof item[1] == "string" && startsWith(item[2], request.term)){
   	         		 		return {label: item[2] + ' - ' +shortText, value: item[0]};
   	         		 	}else{
   	         		 		return {label: item[2] + ' - ' +shortText, value: item[0]};
   	             		} 
   	             		 
   					} ));
   	        	});  
   			 },
   			minLength: 1,
   			select: function( event, ui ) {
   				processSelection(ui.item.label, ui.item.value);
   			}
		});
        
        if(options.item !== null){
        	processSelection(options.item.value, options.item.id);
        }
        
                 
        function processSelection(code, id){
        	if(options.debug){
        		console.log('processSelection');
        		console.log(code, id);
        		console.log($this);
            }
        	
        	var html = '<div class="pj-wrapp"><span class="'+options.selectedClass+'">'+code+'</span>';
        	html += '<span class="'+options.selectedClass+' '+ options.cancelBtnClass +'">'+options.cancelText+'</span></div>';
        	$this.after(html);
        	$this.attr("type", "hidden");
        	$this.val(id);
        	if(options.inputNames.hidden !== null ){
        		$this.attr("name", options.inputNames.hidden );
        	}
        }
        
        function cancelSelection(){
        	$this.attr("type", "text");
        	$this.val('');
        	$('.'+options.selectedClass).remove();
        	if(options.inputNames.text !== null ){
        		$this.attr("name", options.inputNames.text );
        	}
        }

        $(document).on("click", '.'+options.cancelBtnClass, cancelSelection );
	};
    	
}( jQuery ));