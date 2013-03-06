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
	
	var s = $('select.required :selected');
	if($('select.required').length  != 0){ 
		if(s.val() === ''){
			valid = false;
			s.parent().addClass('formerr');
		}else{
			$(this).removeClass('formerr');
		}
	}
	
	return valid;	
}
$(function() {
	createClasses();
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
		console.log('submited');
		if(!validate($(this))){
			showStatus({err : 1, msg : 'Chybně vyplněný formulář'});
			return false;
		}
	});
	
	jQuery.fn.center = function () {
	    this.css("position","absolute");
	    this.css("top", (($(window).height() - this.outerHeight()) / 2) + $(window).scrollTop() + "px");
	    this.css("left", (($(window).width() - this.outerWidth()) / 2) + $(window).scrollLeft() + "px");
	    return this;
	};

});

