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
	$(document).on('click', 'a.lang:not(".disabled")', switchLangs);
	

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
	$.each( ["title", "url", "description", "name"] , function( i, v ){
		$('#pj-' + v).val(getText(obj[v]));
	});
}

function saveContent(){
	try{
		return sendRequest("POST", getContent() , "async-update", function(json){
			if(json.status == "SUCCESS"){
				showStatus({err: 0, msg: "Úspěšně aktualizováno"});
			}else{
				showErrors(json);
			}
		});
	}catch(e){
		console.log(e);
	}
	return false;
}

function sendRequest(type, data, action, callBack){
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
	 return false;
}




function showErrors(json){
	var i = 0, errorInfo = "Došlo k neočekávané chybě, operaci opakujte.";
	if(typeof json.result !== 'undefined'){
		for(i; i < json.result.length ; i++){
			errorInfo += json.result[i] +(i != 0 ? "<br />" : '');  
		}
		$("#ajax-result").html('<p class="msg error">' + errorInfo + '</p>');
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

function getDateTIme(element){
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
