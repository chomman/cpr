$(function() { 	
	 tinyMCE.init({
		 	selector: "textarea.wisiwig",
			language : "cs",
			height : 470,
			width : 630,
			forced_root_block : "",
			force_br_newlines : true,
			force_p_newlines : false,
			 //content_css : $("#base").text() + 'resources/admin/css/tinymce.css',
			plugins: "image,link,table",
			convert_urls: false
	});
	 
 $(document).on('click', 'a.lang', function(){
		var $selected = $('.disabled'),
			$this = $(this),
			locale = $this.attr('data-lang'),
			$boxes = $('.switchable');
			$boxes.removeClass('hidden');
			$boxes.not('.' + locale).addClass('hidden');
			$selected.removeClass('disabled').addClass('lang');
			$this.addClass('disabled').removeClass('lang');
			return false;
 	}); 
});