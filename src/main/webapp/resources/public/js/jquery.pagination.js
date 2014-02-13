(function($) {

$.fn.scrollPagination = function(options) {
		
	var settings = { 
		page : 1, 
		loadingMessage : 'Loading items...',
		noMoreItemsMessage : 'No More Posts!',
		delay : 100,
		scroll : true,
		url : ''
	};
	
	// Extend the options so they work with the plugin
	if(options) {
		$.extend(settings, options);
	}
	
	// Some variables 
	$this = $(this);
	$settings = settings;
	var page = $settings.page,
		busy = false;  

	
	function appendLoader(){
		$this.after('<div id="pagi-loader"><p>' + $settings.loadingMessage + '</p></div>');
	}
	function appendFinished(){
		// $this.after('<p class="pagi-finished">' + $settings.noMoreItemsMessage + '</p>');
	}
	function removeLoader(){
		$('#pagi-loader').remove();
	}
	
	function getRequestParams(){
		var params  = $.trim($('#strParams').text());
		if(params.length === 0){
			return "?page=" + (page + 1) ;
		}
		return params + "&page=" + (page + 1);
	}
	
	function getData() {
		var requestUrl = $settings.url + getRequestParams() ;
		$.get(requestUrl, function(data) {
			if($.trim(data) == "") { 
				appendFinished();	
			}else {
				page++; 
			   	$this.find('.pagi-content').append(data);
				busy = false;
			}	
			removeLoader();	
		});
			
	}	
			
	$(window).on( "scroll", function() {
		if($(window).scrollTop() + $(window).height() > $this.height() && !busy) {
			if($this.find('.pagi-content').length === 0){
				return false;
			}
			busy = true;
			appendLoader();
			setTimeout(function() {
				getData();
			}, $settings.delay);	
		}	
	});
}; // end fn

})(jQuery);