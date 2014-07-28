$(function(){
	var $rating = $('#rating-wrapp'),
		$readOnly = $('.rating'),
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
	
if($rating.length > 0){
	$rating.raty($.extend({cancel : true, target : '#rating-text'}, opts));
}

if($readOnly.length > 0){
	$readOnly.raty($.extend({ 
		readOnly : true, 
		targetFormat : '({score})', 
		target : '.rating-descr'
	}, opts));
}
	
});