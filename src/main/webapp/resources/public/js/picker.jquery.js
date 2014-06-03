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
        excludeId :  null,
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
   			 if(options.excludeId !== null){
   				 request.id = options.excludeId;
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
   	         		 	if(typeof item[2] == "string" && startsWith(item[2], request.term)){
   	         		 		return {label: item[2] + ' - ' +shortText, value: item[0]};
   	         		 	}else if(typeof item[3] == "string" && startsWith(item[3], request.term)){
   	         		 		return {label: item[3] + ' - ' +shortText, value: item[0]};
   	         	 		}else if(typeof item[3] === 'string' && typeof item[2] == 'string' ){
   	         		 		return {label: item[3] + ' (' + item[2] + ') - '+ shortText, value: item[0]};
   	             		}else if(typeof item[3] === 'string' ){
   	         		 		return {label: item[3] + ' - ' + shortText, value: item[0]};
   	             		}else if(typeof item[2] === 'string' ){
   	         		 		return {label: item[2] + ' - ' + shortText, value: item[0]};
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
        	//$this[0].type = "hidden";
        	$this.val(id);
        	$this.hide();
        	if(options.inputNames.hidden !== null ){
        		$this.attr("name", options.inputNames.hidden );
        	}
        	changeType($this[0], "hidden");
        }
        
        function cancelSelection(){
        	$this.val('');
        	$('.'+options.selectedClass).remove();
        	if(options.inputNames.text !== null ){
        		$this.attr("name", options.inputNames.text );
        	}
        	$this.show();
        	changeType($this[0], "text");
        }

        $(document).on("click", '.'+options.cancelBtnClass, cancelSelection );
	};
    	
}( jQuery ));

function changeType(input,type)
{
    try
    {
        var input2 = input.cloneNode(false);
        switch(type)
        {
            default:
            case 'text': {
                input2.type = 'text';
                break;
            }
            case 'hidden': {
                input2.type = 'hidden';
                break;
            }
        }
        input2.id = limput;
        input.parentNode.replaceChild(input2,input);
    }
    catch(e) {
    	if(console){console.log(e);}
    }
}