$(function(){
	var $rating = $('#rating-wrapp'),
		opts = {
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
	
	setRatingLabel();
});

function setRatingLabel(){
	var $el = $('#auditLogRating');
	if($el.length > 0){
		$el.parent().append('<span class="rating-label">('+getRatingLabel(parseFloat($el.text()))+')</span>');
	}
}

function getRatingLabel(rating){
	var labels = $.getMessage("jqueryRatyHints"),
		rounded = Math.floor(rating);
	if(rounded >= 1){
		return labels[rounded];
	}
	if(rating > 0){
		return labels[0];
	}
	return $.getMessage("jqueryRatyUnacceptable");
}
