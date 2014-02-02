$(function() { 					
	//initWISIWIG("610", "270"); 
	$("#description").limiter(255, $("#chars"));
	
	tinymce.init({
		selector:'.mceEditor', 
		language : "cs",
		height : 270,
		width : 630,
		forced_root_block : "",
		force_br_newlines : true,
		force_p_newlines : false,
		 //content_css : $("#base").text() + 'resources/admin/css/tinymce.css',
		plugins: "image,link,table",
		convert_urls: false
	});
	
	$(document).on("click", ".processSave", processSave);
	
	
});

function processSave(){
	var $form = $('form.valid');
	
	if(validate($form)){
		console.log('form ', $form);
		console.log($form.serializeArray());
		var data = renameArr($form.serializeArray());
		console.log(data);
	}else{
		console.log('err..');
	}
	
	
	return false;
}

function renameArr(a){
	var d = {};	
	for (i in a) {
		d[a[i].name] = a[i].value;
	}
	return d;
}