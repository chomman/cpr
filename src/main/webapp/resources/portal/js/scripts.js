$(init);

function init(){
	$('[placeholder]').focus(function() {
	  var input = $(this);
	  if (input.val() == input.attr('placeholder')) {
	    input.val('');
	    input.removeClass('placeholder');
	  }
	}).blur(function() {
	  var input = $(this);
	  if (input.val() == "" || input.val() == input.attr('placeholder')) {
	    input.addClass('placeholder');
	    input.val(input.attr('placeholder'));
	  }
	}).blur();
	
	
	$(document).on("mouseenter", "li.pj-parent", showSubnav );
	$(document).on("mouseleave", "li.pj-parent", hideSubNav );
	
	$(document).on("click", ".show-loginbox", showLoginBox );
	$(document).on("click", ".hide-loginbox", hideLoginBox );
	$(document).on("submit", "form#user", function(){
		var $form = $(this);
		if(!validate($form)){
			//return false;
		}
		var data = toArray($form.serializeArray());
		console.log(data);
		sendRequest("POST", data, $form.attr('action'), function(json){
			if(json.status === "SUCCESS"){
				
			}else{
				showErrors(json);
			}
		});
		return false;
	});
	
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


function sendRequest(type, data, url, callBack){
	try{
		showWebpageLoader(); 
		$.ajax({
			url : url,
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
	if(typeof json.result !== 'undefined'){
		for(i; i < json.result.length ; i++){
			errorInfo += '<span class="msg">' + json.result[i] + '</span>';  
		}
		$("#ajax-result").html('<p class="status error"><span class="status-ico"></span>' + errorInfo + '</p>');
	}
	showStatus({err: 1, msg: getFormErrorMessage() });
	console.warn(arguments);
	return false;
}

function getFormErrorMessage(){
	return isCzech() ? 'Chybně vyplněný formulář' : 'Form contains errors';
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