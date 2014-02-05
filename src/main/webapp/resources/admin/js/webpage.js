$(function() { 					
	//initWISIWIG("610", "270"); 
	$("#description").limiter(255, $("#chars"));
	 var $loader = $('#loader'),
 	 $form = $('form.valid');
	 
	 tinyMCE.init({
		 	selector: "textarea.wisiwig",
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
	
	
	$(document).on('switchlang', function(){
		var $focusedEl = $('.disabled'),
		$clickedEl = $('.lang.processSave'),
		locale = $clickedEl.attr('data-lang');
		$focusedEl.removeClass("disabled").addClass("lang processSave " + $focusedEl.attr('data-lang'));
		$clickedEl.removeClass().addClass("disabled");
		$('input[name=locale]').val(locale);
		console.log('locale: ' + locale);
	});
	$(document).on("click", ".processSave", function(e){
		e.preventDefault();
		processSave({changeLang : true, target : $(this).attr('data-lang')});
		return false;
	});
	$(document).on("click", ".button", function(e){
		e.preventDefault();
		processSave({changeLang : false});
		return false;
	});
	
	function showWebpageLoader(){
		$form.fadeTo(200, 0.6);
		$loader.center().show();	
	}
	
	function hideWebpageLoader(){
		$form.fadeTo(200, 1);
		$loader.hide();
	}
	
	function processSave(context){		
		if( $('input[name=locale]').val() == 'cs' && !validate($form) ){
			return false;
		}
		var data = toArray($form.serializeArray());
		data.topText = tinyMCE.editors[0].getContent();
		data.bottomText = tinyMCE.editors[1].getContent();
		send(data, context);
		return false;
	}
	
	function getText(v){
		if(v == null || typeof v === 'undefined'){
			return "";
		}
		return v;
	}
	
	function updateForm(data){
		$form.find('#name').val(getText(data.name));
		$form.find('#title').val(getText(data.title));
		$form.find('#description').val(getText(data.description));
		tinyMCE.editors[0].setContent(getText(data.topText));
		tinyMCE.editors[1].setContent(getText(data.bottomText));
	}

	function send(data, context){
		showWebpageLoader();
		 $.ajax({
	         url : getBasePath() + 'admin/webpages/async-edit' + (context.changeLang ? '?changeLang=' + context.target : ''),
	         type : "POST",
	         contentType: "application/json",
	         dataType : "json",
	         data : JSON.stringify(data),
	         success : function (response) {
	         	if(response.status == "SUCCESS"){
	                if(!context.changeLang){ 
	                	showStatus({err: 0, msg: "Úspěšně aktualizováno"});
	                }else{
	                	updateForm(response.data);
	                	$(document).trigger("switchlang");
	                }
	         	}else{
	         		var i = 0, errorInfo = "";
	         		for(i; i < response.result.length ; i++){
			                  errorInfo += response.result[i] +(i != 0 ? "<br />" : '');  
			             }
	         		$("#ajax-result").html('<p class="msg error">' + errorInfo + '</p>');		             
	         		 showStatus({err: 1, msg: errorInfo});
	         	}
	         },
	         error : function(xhr, status, err) {
	         	showStatus({err: 1, msg: "Nastala neočekávaná chyba, operaci zkuste zopakovat."});
	         },
	         complete : function(jqXHR , textStatus){
	        	 hideWebpageLoader();
	         }
	     });
		 
		
	}
	
});



function toArray(a){
	var d = {};	
	for (i in a) {
		if(a[i].name != "_enabled"){
			d[a[i].name] = a[i].value;
		}
	}
	return d;
}