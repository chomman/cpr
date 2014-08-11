$(function() {
	if(typeof tinyMCE !== 'undefined'){
		tinyMCE.init({
			selector: "textarea.wisiwig",
			language : "cs",
			height : 400,
			width : '100%',
			forced_root_block : "",
			force_br_newlines : true,
			force_p_newlines : false,
			content_css : getBasePath() + 'resources/admin/css/tinymce.css',
			plugins: "textcolor,image,link,table,autoresize,fullscreen",
			convert_urls: false,
			entity_encoding : 'raw',
			toolbar: "undo redo | styleselect | bold italic | forecolor backcolor | alignleft aligncenter alignright | bullist numlist | link image",
			autoresize_min_height: 400,
			autoresize_max_height: 700,
			statusbar : false
		});
	}
});