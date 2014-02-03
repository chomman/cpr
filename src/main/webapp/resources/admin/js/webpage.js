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
		var data = toArray($form.serializeArray());
		console.log(data);
		send(data);
	}else{
		console.log('err..');
	}
	return false;
}

function send(data){
	 $.ajax({
         url : getBasePath() + 'admin/webpages/async-edit',
         type : "POST",
         contentType: "application/json",
         dataType : "json",
         data : JSON.stringify(data),
         success : function (response) {
         	if(response.status == "SUCCESS"){
                 showStatus({err: 0, msg: "Úspěšně aktualizováno"});
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
             mce.setProgressState(0);
         }
     });

}

function toArray(a){
	var d = {};	
	for (i in a) {
		if(a[i].name != "_enabled"){
			d[a[i].name] = a[i].value;
		}
	}
	return d;
}