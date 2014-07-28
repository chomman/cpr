$(function(){
	
	$(".widget-bx").each(function(i,obj){
		var $this = $(this);
		loadItems($this.attr('data-url'), '#' + $this.attr('id'));
	});
});



function loadItems(url, selector){
	var $wrapp = $(selector);
	getItems({
			url : getBasePath() + "/admin/quasar/async/widget/" + url
		}, 
		function(html){
			var $items = $wrapp.find('div').eq(0);
			if(html.length > 1){
				 $items.html('<table class="data widget">'+html+'</table>');
				 if(url === "1") initStars();
			}else{
				 $items.html('<p>'+$.getMessage("noItemFound")+'</p>');
			}
			$wrapp.removeClass('loading');
		}
	);
}



function getItems(opts, callBack){
	try{
		//showWebpageLoader(); 
		$.ajax(opts)
		 .done( callBack );
		// .fail( showErrors )
		// .always( hideWebpageLoader );
	}catch(e){
		console.warn(e);
	}
	 return false;
}


function initStars(){

	var $readOnly = $('.rating'),
		opts = {
			  targetKeep : true,
			  targetText : "Not rated",
			  cancelHint : "Unacceptable",
			  score: function() {
				  	var val = $(this).attr('data-rating');
				  	if(typeof val === 'undefined'){
				  		return val;
				  	}
				    return val.length === 0 ? undefined : val;
				  }
		};
	if($readOnly.length > 0){
		$readOnly.raty($.extend({ 
			readOnly : true, 
			targetFormat : '({score})', 
			target : '.rating-descr'
		}, opts));
	}
}