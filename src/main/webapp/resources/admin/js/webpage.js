$(function() { 					
	//initWISIWIG("610", "270"); 
	$("#description").limiter(255, $("#chars"));
	 var $loader = $('#loader'),
 	 $form = $('form.valid');


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
	$(document).on('switchlang', function(){
		var $focusedEl = $('.disabled'),
		$clickedEl = $('.lang.processSave'),
		locale = $clickedEl.attr('data-lang');
		$focusedEl.removeClass("disabled").addClass("lang processSave " + $focusedEl.attr('data-lang'));
		$clickedEl.removeClass().addClass("disabled");
		$('input[name=locale]').val(locale);
		
	});
	$(document).on("click", ".processSave", function(e){
		e.preventDefault();
		processSave(true);
		return false;
	});
	$(document).on("click", ".button", function(e){
		e.preventDefault();
		processSave(false);
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
	
	function processSave(changeLang){		
		if(validate($form)){
			var data = toArray($form.serializeArray());
			send(data, changeLang);
		}else{
			console.log('err..');
		}
		return false;
	}

	function send(data, changeLang){
		console.log(data);
		showWebpageLoader();
		 $.ajax({
	         url : getBasePath() + 'admin/webpages/async-edit' + (changeLang ? '?changeLang=true' : ''),
	         type : "POST",
	         contentType: "application/json",
	         dataType : "json",
	         data : JSON.stringify(data),
	         success : function (response) {
	         	if(response.status == "SUCCESS"){
	                if(!changeLang){ 
	                	showStatus({err: 0, msg: "Úspěšně aktualizováno"});
	                }else{
	                	$(document).trigger("switchlang");
	                }
	         	}else{
	         		var i = 0, errorInfo = "";
	         		for(i; i < response.result.length ; i++){
			                  errorInfo += response.result[i] +(i != 0 ? "<br />" : '');  
			             }
	         		$("#ajax-result").html('<p class="msg error">' + errorInfo + '</p>');		             
	         		 showStatus({err: 1, msg: errMsg});
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