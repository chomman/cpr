(function($) {
$.fn.bundledCheckbox = function() {
	
	var $elements = $(this);
	initChBox();
	
	function initChBox(){
		$elements.each(function(){
			var $this = $(this);
			refresh($this, $this.prop('checked') );
		});
		return false;
	}
	
	$elements.on('change', initChBox );
	
	function refresh($input, show){
		var name = $input.attr('name'),
			$box = $('.' + name);
		if(show){
			$box.show(200);
		}else{
			$box.hide();
		}
		return false;
	} 
	
};
})(jQuery);