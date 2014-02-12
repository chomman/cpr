(function($) {

$.fn.scrollPagination = function(options) {
		
	var settings = { 
		page  :   1, 
		loadingMessage : 'Loading items...',
		noMoreItemsMessage   : 'No More Posts!', // When the user reaches the end this is the message that is
		                            // displayed. You can change this if you want.
		delay   : 500, // When you scroll down the posts will load after a delayed amount of time.
		               // This is mainly for usability concerns. You can alter this as you see fit
		scroll  : true, // The main bit, if set to false posts will not load as the user scrolls. 
		               // but will still load if the user clicks.
		url : ''
	};
	
	// Extend the options so they work with the plugin
	if(options) {
		$.extend(settings, options);
	}
	
	// Some variables 
	$this = $(this);
	console.log($this);
	$settings = settings;
	var page = $settings.page,
		busy = false;  

	
	function appendLoader(){
		$this.after('<div id="pagi-loader"><p>' + $settings.loadingMessage + '</p></div>');
	}
	function appendFinished(){
		$this.after('<p>' + $settings.noMoreItemsMessage + '</p>');
	}
	function removeLoader(){
		$('#pagi-loader').remove();
	}
	
	function getData() {
		var requestUrl = $settings.url +'?'+ $('#strParams').text() + "page=" + (page + 1);
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
			busy = true;
			appendLoader();
			setTimeout(function() {
				getData();
			}, $settings.delay);	
		}	
	});
}; // end fn

})(jQuery);