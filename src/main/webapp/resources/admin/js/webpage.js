$(function() { 					
	 
	initDate('#publishedSince');
	tinyMCE.init({
		selector: "textarea.wisiwig",
		language : "cs",
		height : 400,
		width : '100%',
		forced_root_block : "",
		force_br_newlines : true,
		force_p_newlines : false,
		content_css : getBasePath() + 'resources/admin/css/tinymce.css',
		plugins: "image,link,table,autoresize,fullscreen",
		convert_urls: false,
		autoresize_min_height: 400,
		autoresize_max_height: 700
	});
	
	$(document).on('submit', 'form[name=webpageContent]', saveContent);
	$(document).on('submit', 'form[name=webpageSettings]', saveSettings);
	$(document).on('submit', 'form[name=avatar]', uploadAvatar);
	$(document).on('click', 'a.lang:not(".disabled")', switchLangs);
	$(document).on('click', 'a.delete', deleteAvatar);
	$(document).on('webpagetypechanged', refreshFieldsVisibility);
	
	refreshFieldsVisibility();
});

function switchLangs(){
	var $this = $(this),
		lang = $this.attr('data-lang'),
		id = $('input[name=id]').val();
	return sendRequest("GET", null, "lang/"+id+ "?localeCode=" + lang , function(json){
		if(json.status == "SUCCESS"){
			setContent(json.result);
			$('.lang').removeClass('disabled');
			$this.addClass('disabled');
			$('#locale').val(lang);
		}else{
			showErrors(json);
		}
	});
}

function setContent(obj){
	tinyMCE.editors[0].setContent(getText(obj.content));
	$.each( ["title", "description", "name"] , function( i, v ){
		$('#pj-' + v).val(getText(obj[v]));
	});
}

function saveSettings(){
		return sendRequest("POST", getWebpageSettings() , "async-update-settings", function(json){
			if(json.status == "SUCCESS"){
				showStatus({err: 0, msg: "Úspěšně aktualizováno"});
				$(document).trigger('webpagetypechanged');
			}else{
				showErrors(json);
			}
		});
}



function saveContent(){
	return sendRequest("POST", getContent() , "async-update", function(json){
		if(json.status == "SUCCESS"){
			showStatus({err: 0, msg: "Úspěšně aktualizováno"});
		}else{
			showErrors(json);
		}
	});
}

function sendRequest(type, data, action, callBack){
	try{
		showWebpageLoader(); 
		$.ajax({
			url :  getBasePath() + 'admin/webpage/' + action,
			contentType: "application/json",
			type : type,
			dataType : "json",
			data : JSON.stringify(data)
		 })
		 .done( callBack )
		 .fail( showErrors )
		 .always( hideWebpageLoader );
	}catch(e){
		console.warn(e);
	}
	 return false;
}


function refreshFieldsVisibility(){
	$('.pj-type').removeClass('hidden');
	switch( getWebpageType() ){
	case 'ARTICLE':
	case 'CATEGORY':
		renderContentType();
		break;
	case 'REDIRECT':
		renderRedirectType();
		break;
	case 'NEWS':
		renderNewsType();
		break;
	}
	return false;
	
	
	function renderContentType(){
		$('.pj-redirect-type, .pj-article-type').addClass('hidden');
	}
	function renderRedirectType(){
		$('.pj-content-type, .pj-article-type').addClass('hidden');
	}
	function renderNewsType(){
		$('.pj-redirect-type').addClass('hidden');
	}
}

function getWebpageType(){
	return $('[name=webpageType]').val();
}

function getWebpageSettings(){
	var data = {
		id : $('#id').val(),
		publishedSince : getDateTime('#publishedSince'),
		enabled : getCheckVal('#enabled'),
		webpageType : getWebpageType(),
		showThumbnail : getCheckVal('#showThumbnail') 
	};
	if($('#lockedRemove').length){
		data.lockedRemove = getCheckVal('#lockedRemove'); 
	}
	if($('#lockedCode').length){
		data.lockedCode = getCheckVal('#lockedCode'); 
	}
	return data;
}


function showErrors(json){
	var i = 0, errorInfo = "Došlo k neočekávané chybě, operaci opakujte.";
	if(typeof json.result !== 'undefined'){
		for(i; i < json.result.length ; i++){
			errorInfo += json.result[i] +(i != 0 ? "<br />" : '');  
		}
		$(".ajax-result").html('<p class="msg error">' + errorInfo + '</p>');
	}
	showStatus({err: 1, msg: errorInfo});
	console.warn(arguments);
	return false;
}

function getContent(){
	var data = toArray($('form[name=webpageContent]').serializeArray());
	webpageContent.content = tinyMCE.editors[0].getContent();
	return data;
} 

function getCheckVal(s){
	return $(s).is(':checked');
}

function toArray(a){
	var d = {};	
	for (i in a) {
		var n = a[i].name.split(".");
		if(n.length === 2){
			if(typeof d[n[0]] === 'undefined'){
				d[n[0]] = {};
			}
			d[n[0]][n[1]] = a[i].value;
		}else{
			d[a[i].name] = a[i].value;
		}
	}
	return d;
}

function initDate(selector){
	var datetime =  $(selector).val().trim();
	if(datetime.length > 0){
		datetime = datetime.split(" ");
		$(selector + '-date').val(datetime[0]);
		$(selector + '-time').val(datetime[1]);
	}
} 
function checkdateformat(v){;
	return /^(\d{2}).(\d{2}).(\d{4})(\s{1})(\d{2}):(\d{2})$/.test(v);
}

function getDateTime(element){
	var datetime, 
		date = $(element + "-date").val().trim(),
		time = $(element + "-time").val().trim();
	if(date.length == 0) return "";
	datetime = (time.length == 0 ? date + " 00:00" : date + " " + time);
	if(checkdateformat(datetime)){
		return datetime;
	}
	return "";
}

function showWebpageLoader(){
	$('form').fadeTo(200, 0.6);
	$('#loader').center().show();	
}

function hideWebpageLoader(){
	$('form').fadeTo(200, 1);
	$('#loader').hide();
}
function getText(v){
	if(v == null || typeof v === 'undefined'){
		return "";
	}
	return v;
}

function deleteAvatar(){
	return sendRequest("DELETE", false , $('#id').val() + '/avatar', function(json){
		if(json.status == "SUCCESS"){
			$('.pj-fotobox').addClass('hidden');
			$('form[name=avatar]').removeClass('hidden');
		}else{
			showErrors(json);
		}
	});
}

function uploadAvatar(){
	showWebpageLoader();   
	var formData = new FormData();
	  formData.append("file", $("#file").get(0).files[0]);
	  $.ajax({
	    url: $(this).attr('action'),
		data: formData,
		dataType: 'text',
		processData: false,
		contentType: false,
		type: 'POST'
	})
	 .done( function( json ){
		 console.log('done');
		 json = $.parseJSON(json);
		 if(json.status == "SUCCESS"){
			 var $wrapp = $('.pj-fotobox span');
			 $wrapp.html(
					 '<a href="'+getImgSrc("n", json.result) +'" class="lightbox" >'+
					 	'<img src="' + getImgSrc("s/150", json.result) + '" alt="Avatar" />' +
					 '</a>'
			 );
			 $('.pj-fotobox').removeClass('hidden');
			 $('form[name=avatar]').addClass('hidden');
		 }else{
			 showStatus({err: 1, msg: json.result });
		 }
	 })
	 .fail( showErrors )
	 .always( hideWebpageLoader );
	  return false;
	  
	  function getImgSrc(type, name){
		  return getBasePath() + 'image/'+type+'/avatars/' + name;
	  }
}
