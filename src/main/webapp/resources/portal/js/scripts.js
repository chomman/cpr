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
	$(document).on("submit", "form.valid", function(){
		console.log('isValid: ' + validate($(this)));
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
	var inputs = f.find('.required, .email, .more6, .numeric'),
	valid = true,
	vldt = {
		required : function(v,i) {return {r : !!v ,  msg : ''};},
		email	 : function(v,i) {return {r : v.match( /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/ ), msg : ''};},
		more6 : function(v,i) {return {r : v.length === 0 || v.length >= 6, msg : ''} ;},
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
					showStatus({err : 1, msg : (isCzech() ? 'Chybně vyplněný formulář' : 'Form contains errors')});
					valid = false;
				}
			}
		}
	});
	
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