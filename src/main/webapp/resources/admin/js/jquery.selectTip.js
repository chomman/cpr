(function($) {

$.fn.selectTip = function(opts) {
		
	var options = { 
		rootElement : 'span',
		cssClass : 'mini-info inline'
	};
	if(opts) {
		options = $.extend(opts, options);
	}
	options.selector = '.' + options.cssClass.replace(' ', ".");
	$(this).each(function() {
		var $this = $(this);
		if($this.prop("tagName") == 'SELECT'){
			refreshTip($this);
		}
	});
	
	$(this).on('change', function(){
		refreshTip($(this));
	});
	
	function refreshTip($select){
		var val = $.trim($select.val());
		if(val.length === 0){
			remove($select);
		}else{
			create($select);
		}
	};
	function create($select){
		var text = $select.find(' option:selected').attr('title'),
			$infoBox = $select.parent().find(options.selector);	
		if($infoBox.length > 0){
			$infoBox.html( text );
		}else{
			$select.parent().append('<' + options.rootElement + ' class="' + options.cssClass + '" >' + text + '</' + options.rootElement + ">");
		}
		
	};
	function remove($select){
		var $infoBox = $select.parent().find(options.selector);
		if($infoBox.length > 0){
			$infoBox.remove();
		}
	};
}; // end fn

})(jQuery);