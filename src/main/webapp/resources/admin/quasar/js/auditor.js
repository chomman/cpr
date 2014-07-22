$(function(){
	var $rating = $('#rating-wrapp'),
		opts = {
				  half     : true,
				  starType : 'i',
				  targetKeep : true,
				  targetText : "Unacceptable",
				  cancelHint : "Set unacceptable",
				  score: function() {
					    return $(this).attr('data-rating');
					  }
				};
	
	if($rating.length > 0){
		$rating.raty($.extend({cancel : true, target : '#rating-text'}, opts));
	}else{
		 $rating = $('div.rating');
		 if($rating.length > 0){
			 $rating.raty($.extend({ readOnly : true}, opts));
		 }
	}
});