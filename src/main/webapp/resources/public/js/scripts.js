function createClasses(){
	$('tr:odd').addClass('odd');
} 
function showStatus(data){
	var html = '<p class="'+ (data.err === 0 ? "ok" : "err") +'">'+ data.msg +'</p>',
	o = $("#status");
	o.html(html).center().fadeIn();
	setTimeout(function() {o.fadeOut(100);}, 4000);
}
function validate(f){
	var inputs = f.find('input.required, textarea.required, .email, .more7'),
	valid = true,

	vldt = {
		required : function(v,i) {return {r : !!v ,  msg : ''};},
		email	 : function(v,i) {return {r : v.match( /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/ ), msg : ''};},
		more7 : function(v,i) {return {r : v.length === 0 || v.length >= 7, msg : ''} ;},
		numeric  : function(v,i) {return {r : !isNaN(v), msg : ''} ;},
	};
	inputs.removeClass('formerr');
	inputs.each(function(){
		var input = $(this),
			val = input.val(),
			cls = input.attr("class").split(' ');

		for(i in cls){
			if(vldt.hasOwnProperty(cls[i])){
				var res = vldt[cls[i]](val,input);
				if(!res.r){
					input.addClass('formerr');
					showStatus({err : 1, msg : res.msg});
					valid = false;
				}
			}
		}
	});
	var selects = $('select.required');
	if(selects.length  != 0){ 
		selects.each(function(){
			var o = $(this);
			if(o.find("option:selected").val() === ""){
				valid = false;	
				o.addClass('formerr');
			}else{
				o.removeClass('formerr');
			}
		});
	}
	
	return valid;	
}
$(function() {

	$('.tt').tooltip({ show: {duration: "fast" }});
	
	$('.tt-form').tooltip({
		position: {
			my: "center bottom-20",
			at: "center top",
			tooltipClass: "forom-tooltip"
		}
	});
	
	$('.print').click(function(){
		window.print();
		return false;
	});
	
	$('.valid').submit(function(){
		if(!validate($(this))){
			showStatus({err : 1, msg : 'Chybně vyplněný formulář'});
			return false;
		}
	});
	var locale =  getLocale(),
		$langSwitcher = $('#lswitcher');
	$langSwitcher.polyglotLanguageSwitcher({
			effect: 'fade',
          testMode: true,
          onChange: function(e){
        	  var selectedLocale = e.selectedItem.replace("l", "");
        	  if(locale === selectedLocale){
        		  return false;
        	  }
        	  document.location.replace(makeUrl(locale, selectedLocale));
        	  return false;
          }
		});
	
	jQuery.fn.center = function () {
	    this.css("position","absolute");
	    this.css("top", (($(window).height() - this.outerHeight()) / 2) + $(window).scrollTop() + "px");
	    this.css("left", (($(window).width() - this.outerWidth()) / 2) + $(window).scrollLeft() + "px");
	    return this;
	};
	
	resizeBorder(); 
	$('.tooltip').tooltipster({maxWidth : 300 });
});


function makeUrl(current, selected){
	var url = document.location.pathname + document.location.search,
		basePath = $('#base').text();
	url = url.substring(basePath.length, url.length);
	if(selected !== "cs" && current === "cs"){
		url = basePath + selected + "/" +url;
	}else{
		url = basePath + url.substring(3, url.length);
	}
	return document.location.protocol + "//" + document.location.host + url;
	
}

function statsWith(val, str){
    return val.slice(0, str.length) == str;
}



function getSystemById(id){
	for(var i in systems){
		if(systems[i].id === id){
			return systems[i];
		}
	}
	return null;
} 

function resizeBorder(){
	var max = 0;
	if($("#terminologyNav").length > 0){
		max = $("#terminologyNav").height();
		if(max < $("#terminologyContent").height()){
			max = $("#terminologyContent").height();
		}
		$("#main-content .border").height(max);
	}
}


