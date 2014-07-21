$(function(){
	var $rating = $('#rating-wrapp'),
		opts = {
				  half     : true,
				  starType : 'i',
				  targetFormat : '{score}',
				  targetKeep : true,
				  scoreName   : 'rating',   
				  hints: ['Poor', 'Fair', 'Average', 'Good', 'Excellent'],
				  score: function() {
					    return $(this).attr('data-rating');
					  }
				};
	
	if($rating.length > 0){
		$rating.raty($.extend({ target : '#rating-text'}, opts));
	}else{
		 $rating = $('div.rating');
		 if($rating.length > 0){
			 $rating.raty($.extend({ readOnly : true}, opts));
		 }
	}
});