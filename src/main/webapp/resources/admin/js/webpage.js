var jsTree = null;
$(function() { 					
	 
	initDate('#publishedSince');
	$(document).on('submit', 'form[name=webpageContent]', saveContent);
	$(document).on('submit', 'form[name=webpageSettings]', saveSettings);
	$(document).on('submit', 'form[name=avatar]', uploadAvatar);
	$(document).on('click', 'a.lang:not(".disabled")', switchLangs);
	$(document).on('click', 'a.delete', deleteAvatar);
	$(document).on('webpagetypechanged', refreshFieldsVisibility);
	$(document).on('change', "#webpageType", refreshFieldsVisibility);
	
	refreshFieldsVisibility();
	
	// Webpage list
	$(document).on('click', '.pj-webpage-nav a:not(.preview)', onWebpageMenuClick);
	$(document).on('dnd_stop.vakata', onDragAndDropStop);
	$(document).on('dnd_start.vakata', onDragAndDropStart);
	
	$jsTree = $("#jstree");
	if($jsTree.length !== 0){
		init($jsTree);
	}
	
	$('#tags').tagit({
		allowSpaces: true,
		placeholderText : "Vpište slovo",
		beforeTagAdded: function(event, ui) {
				var s = $.trim(ui.tag.text());			
				if(s.length > 25){
					showStatus({err : 1, msg : 'Max. délka je 25 znakú'});
					return false;
				}
		    },
		    autocomplete: {     
		     	source: function(request, response){  
		    				$.getJSON( getBasePath() +"ajax/tag/autocomplete", request, function(data) {  
		    						response( data );
		    	            });  
		    			},
		    			minLength: 2,
		    			select: function( event, ui ) {
		    				ui.item.value;
		    			}
		    }
	});
	
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
	return executeRequest(
			{
				url :  getBasePath() + 'admin/webpage/' + action,
				contentType: "application/json",
				type : type,
				dataType : "json",
				data : JSON.stringify(data)
			 }
			,
			callBack
	);
}
function init($jsTree){
	$jsTree.jstree({
	    "core" : {
	      "check_callback" : true,
	      'data': {
	    	  'url' :  getBasePath() + "async/webpages",
	    	  'data' : function(node){
	    		  return { 'id' : (node.id === '#' ? -1 :  node.id )};
	    	  },
	      }
	    },
	    "plugins" : [ "dnd", "state"]
	});
			
}

function executeRequest(opts, callBack){
	try{
		showWebpageLoader(); 
		$.ajax( opts )
		 .done( callBack )
		 .fail( showErrors )
		 .always( hideWebpageLoader );
	}catch(e){
		console.warn(e);
	}
	return false;
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

function refreshFieldsVisibility(){
	$('.pj-type').removeClass('hidden');
	if($('.pj-type').length === 0){
		return false;
	}
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
		showThumbnail : getCheckVal('#showThumbnail'),
		fullWidth : getCheckVal('#fullWidth'),
		isOnlyForRegistrated : getCheckVal('#isOnlyForRegistrated')
	},
	$moduleInput = $('#webpageModule');
	if($moduleInput.val() != ""){
		data.webpageModule = $moduleInput.val();
	}
	if($('#lockedRemove').length){
		data.lockedRemove = getCheckVal('#lockedRemove'); 
	}
	if($('#lockedCode').length){
		data.lockedCode = getCheckVal('#lockedCode'); 
	}
	return data;
}




function getContent(){
	var data = toArray($('form[name=webpageContent]').serializeArray());
	webpageContent.content = tinyMCE.editors[0].getContent();
	data.tags = getTags();
	return data;
} 

function getTags(){
	return $('#tags').tagit("assignedTags");
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
	var $input = $(selector);
	if($input.length === 0){
		return false;
	}
	var datetime =  $input.val().trim();
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
			if(isIE() > 9){
				$('form[name=avatar]').removeClass('hidden');
			}
		}else{
			showErrors(json);
		}
	});
}

function uploadAvatar(){
	if(isIE() && isIE() < 10){
		showUnsupportedBrowserAlert();
		return false;
	}
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

// WEBPAGE LIST ---------------------------



function onDragAndDropStop(e, dnd){
	 if(isIE()){
		 showUnsupportedBrowserAlert();
		 return false;
	 }
	 var oldPos = dnd.data.pos;
	 if($("#"+ oldPos.id).length === 0){
		 getInstance().open_node(getInstance().get_parent(oldPos.id));
	 }
	 var newPos = getPosition(oldPos.id);
	 if(newPos === null){
		 console.warn('new position is NULL');
		 return;
	 }
	 if(newPos.order != oldPos.order || newPos.parent != oldPos.parent){
		  changeOrder(newPos);
	 } 

}


function onDragAndDropStart(e, dnd){
	 dnd.data.pos = getPosition(dnd.data.nodes[0]);
}


function onWebpageMenuClick(e){
	 var $this = $(this);
	 e.stopImmediatePropagation();
	 if($this.hasClass('confirmMessage') && !confirm($this.attr("data-message"))){
		 return false;
	 }
	document.location.href = $this.attr('href');
	 return false;
}


function changeOrder(data){
	var url = data.id +"/order?order=" + data.order + "&parentId=" + data.parent;
	return sendRequest("POST", false , url, function(json){
		if(!json.status == "SUCCESS"){
			showErrors(json);
		}
	});
}

function getPosition(id){
	var $li = $('#' + id);
    if($li.length == 1){
        return {
           id : id,
           order : getOredFor($li),
           parent : getParentFor($li)
        };
    }
    return null;
}


function getOredFor($li){
    return $li.index();
}


function getParentFor($li){
    var parent = getInstance().get_parent($li);
    if(parent !== "#"){
        return parent;
    }
    return null;
}

function getInstance(){
    if(jsTree == null){
    	jsTree = $.jstree.reference('#jstree');
    }
	return jsTree;
}

