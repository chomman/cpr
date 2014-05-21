$(init);

function init(){
	$('[placeholder]').focus(function() {
	  var input = $(this),
	  	  $form = input.parent();
	  if (input.val() == input.attr('placeholder')) {
	    input.val('');
	    input.removeClass('placeholder');
	  }
	  $form.addClass('pj-active');
	}).blur(function() {
	  var input = $(this),
	  	  $form = input.parent();
	  if (input.val() == "" || input.val() == input.attr('placeholder')) {
	    input.addClass('placeholder');
	    input.val(input.attr('placeholder'));
	  } 
	  $form.removeClass('pj-active');
	}).blur();
	
	
	$(document).on("mouseenter", "li.pj-parent", showSubnav );
	$(document).on("mouseleave", "li.pj-parent", hideSubNav );
	
	$(document).on("click", ".show-loginbox", showLoginBox );
	$(document).on("click", ".hide-loginbox", hideLoginBox );
	
	$(document).on("click", ".pj-langbox a[data-lang]", onChangeLang );
	$(document).on("click", ".pj-search a", onSearchClicked );
	
	$("input.query").autocomplete({
		 source: function(request, response){  
		 	 $.getJSON( getBasePath() +"ajax/autocomplete/webpages?enabledOnly=true", request, function(data) {  
	        	 response( $.map( data, function( item ) {
	        		 	var text = item[1],
	        		 		val = item[1];
	        		 	if(text.length > 40){
	        		 		val = text.substring(0, 40);
	        		 		text = val + "...";
	        		 	}
						return {label: text, value: val };
					}));
	   	});  
		 },
		minLength: 2,
		select: function( event, ui ) {
			ui.item.value;
		}
	});
}

function onSearchClicked(){
	var $form = $(this).parent(),
		term = $form.find('[name=q]').val(); 
	if($.trim(term) != "" && (term !== "Vyhledat..." && term !== "Search...") ){
		$form.submit();
	}
	return false;
}


function onChangeLang(){
	var langAttr = "data-lang",
		newLocale = $(this).attr(langAttr),
		oldLocale = $('body').attr(langAttr);
	if(newLocale != oldLocale){
		 console.log(buildUrl(oldLocale, newLocale));
		 document.location.replace(buildUrl(oldLocale, newLocale));
	}
	
	return false;
	
	function buildUrl(oldLocale, newLocale){
		var url = document.location.pathname + document.location.search,
			basePath = getBasePath();
		url = url.substring(basePath.length, url.length);
		if(newLocale !== "cs" && oldLocale === "cs"){
			url = basePath + newLocale + "/" +url;
		}else{
			url = basePath + url.substring(3, url.length);
		}
		return document.location.protocol + "//" + document.location.host + url;
		
	}
}



function hideSubNav(e){
	e.preventDefault();
	$(this).find('ul').hide(10);
	return false;
}

function showSubnav(e){
	e.preventDefault();
	$(this).find('ul').stop(true).show(200);
	return false;
}



function showLoginBox(){
	$('#pj-login-box').removeClass('hidden');
	$('.pj-login').addClass('hidden');
	return false;
}

function hideLoginBox(){
	$('#pj-login-box').addClass('hidden');
	$('.pj-login').removeClass('hidden');
	return false;
}

function validate(f){
	var inputs = f.find('.required, .email, .more6, .numeric, .phone, .zip'),
	valid = true,
	vldt = {
		required : function(v,i) {return !!v;},
		email	 : function(v,i) {return v.match( /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/ );},
		more6 : function(v,i) {return isBlank(v) || v.length >= 6;},
		numeric  : function(v,i) {return v.match( /(\d?|)/ ); },
		zip : function(v,i) {return  isBlank(v) || v.match( /((\d{5})|(\d{3} ?\d{2}))/ );},
		phone : function(v,i) { return isBlank(v) || v.match( /^[+]?[()/0-9. -]{9,}$/ );}
	};
	inputs.removeClass('formerr');
	inputs.each(function(){
		var input = $(this),
			val = input.val(),
			cls = input.attr("class").split(' ');
		for(i in cls){
			if(vldt.hasOwnProperty(cls[i]) && !vldt[cls[i]](val,input)){
				input.addClass('formerr');
				showStatus({err : 1, msg : getFormErrorMessage() });
				valid = false;
			}
		}
	});
	
	function isBlank(v){
		return typeof v === 'undefined' || $.trim( v.length ) === 0;
	}
	
	return valid;	
}
jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
    return this;
};

function showStatus(data){
	var html = '<p class="'+ (data.err === 0 ? "ok" : "err") +'">'+ data.msg +'</p>',
	o = $("#status");
	o.html(html).center().fadeIn();
	setTimeout(function() {o.fadeOut(100);}, 4000);
}

function getLang(){
	return $('body').attr('data-lang');
}

function isCzech(){
	return getLang() === 'cs';
}

function sendHtmlReqest(type, data, url, callBack){
	return executeXHR({
		url : url,
		type : type,
		data : data === null ? false : JSON.stringify(data)
	 }, callBack);
}

function executeXHR(opts, callBack){
	try{
		showWebpageLoader(); 
		$.ajax(opts)
		 .done( callBack )
		 .fail( showErrors )
		 .always( hideWebpageLoader );
	}catch(e){
		console.warn(e);
	}
	 return false;
}

function sendRequest(type, data, url, callBack){
	return executeXHR({
		url : url,
		contentType: "application/json",
		type : type,
		dataType : "json",
		data : data === null ? false : JSON.stringify(data)
	 }, callBack);
}

function showWebpageLoader(){
	$('form').fadeTo(200, 0.6);
	$('#loader').center().show();	
}
function hideWebpageLoader(){
	$('form').fadeTo(200, 1);
	$('#loader').hide();
}

function showErrors(json){
	var i = 0, errorInfo = "";
	if(typeof json.result !== 'undefined' && json.result != null){
		for(i; i < json.result.length ; i++){
			errorInfo += '<span class="msg">' + json.result[i] + '</span>';  
		}
		$("#ajax-result").html('<p class="status error"><span class="status-ico"></span>' + ( errorInfo.lenght === 0 ? getUnexpectedError() : errorInfo )+ '</p>');
	}
	if(json.result == null){
		showStatus({err: 1, msg: getUnexpectedError() });
	}else{
		showStatus({err: 1, msg: getFormErrorMessage() });
	}
	if(typeof console !== 'undefined'){
		console.warn(arguments);
	}
	return false;
}

function getFormErrorMessage(){
	return isCzech() ? 'Chybně vyplněný formulář' : 'Form contains errors';
}

function getUnexpectedError(){
	return isCzech() ? 'Došlo k neočekávané chybě, zkuste operaci opakovat.' : 'An unexpected error occurred, please try it again later';
}

function getBasePath(){
	return $('#base').text();
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

