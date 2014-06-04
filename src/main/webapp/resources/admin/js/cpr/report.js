$(function() { 	
	 
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
 
 $(document).on('click', '.preview', function(){
	 var locale = $('.disabled').attr('data-lang'),
	 	 $this = $(this);
	 if(locale  === 'cs'){
		 $this.attr('href', getBasePath() + $this.attr('data-url'));
	 }else{
		 $this.attr('href', getBasePath() + locale +"/"+ $this.attr('data-url'));
	 }
	 processSave(true);
 });
  
 $(document).on("submit", "form", function(e){
		e.preventDefault();
		processSave(false);
		return false;
 });
 
var $loader = $('#loader'),
	$form = $('form.valid');
 
function showWebpageLoader(){
	$form.fadeTo(200, 0.6);
	$loader.center().show();	
}
	
function hideWebpageLoader(){
	$form.fadeTo(200, 1);
	$loader.hide();
}

	function processSave(isPreview){		
		if(!validate( $form) ){
			return false;
		}
		var data = toArray($form.serializeArray());
		data.contentCzech = tinyMCE.get("contentCzech").getContent();
		data.contentEnglish = tinyMCE.get("contentEnglish").getContent();
		send(data, isPreview);
		return false;
	}

	function send(data, isPreview){
		showWebpageLoader();
		 $.ajax({
	         url : $form.attr("action"),
	         type : "POST",
	         contentType: "application/json",
	         dataType : "json",
	         data : JSON.stringify(data),
	         success : function (response) {
	         	if(response.status == "SUCCESS"){
	                if(!isPreview){
	                	showStatus({err: 0, msg: "Úspěšně aktualizováno"});
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
		if(a[i].name[0] === '_') continue;
		d[a[i].name] = a[i].value;
	}
	if(typeof d.enabled === 'undefined'){
		d.enabled = false;
	}else{
		d.enabled = true;
	}
	return d;
}



