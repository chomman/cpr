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
			console.log('result: ' + html);
			console.log('length: ' + html.length);
			var $items = $wrapp.find('div').eq(0);
			if($.trim(html).length > 1){
				 $items.html('<table class="data widget">'+html+'</table>');
				 if(url === "1") initStars();
			}else{
				 $items.html('<p class="msg alert">'+$.getMessage("noItemFound")+'</p>');
			}
			$items.removeClass('loading');
		}
	);
}



function getItems(opts, callBack){
	try{
		$.ajax(opts)
		 .done( callBack )
		 .fail( onFail );
	}catch(e){
		console.warn(e);
	}
	 return false;
}

function onFail(){
	showStatus({ err : 1, msg : $.getMessage("errUnknown") });
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