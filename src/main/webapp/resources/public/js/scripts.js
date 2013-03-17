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
	$(document).on("click",'#isCumulative', function(){checkCumulative();});
	checkCumulative();
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
	
	var as = $('select.as'), 
		as2 = $('select.as2');
	if(typeof(systems) !== 'undefined'){
		renderText();
		as.change(function(){checkSelect($(this));});
		checkSelect(as);
	}
	
	if(typeof(as2) !== 'undefined' && as2.length !== 0){
		as2.change(function(){checkSelect($(this));});
		checkSelect(as2);
	}
	
	$('#dop select, #isCumulative').change(function(){
		renderText();
	});
	$('#dop .report input').keyup(function(){
		renderText();
	});
});

function checkCumulative(){
	var box = $('#cbox');
	if(box !== undefined ){
		if($('#isCumulative').is(':checked')){
			box.show();
		}else{
			box.hide();
		}
	}
}

function checkSelect(o){
	var code = o.find("option:selected").attr('title'),
		nbLine = o.parent().next(),
		reportLine = nbLine.next(),
		legend = o.parent().parent().find('.legend');
		nbSelect = nbLine.find('select');
	if(code === undefined || code === ''){
		o.next().hide();
		o.next().next().hide();
		nbLine.hide();
		reportLine.hide();
		legend.hide();
	}else{
		if(code === '4'){
			nbLine.hide();
			reportLine.hide();
			nbSelect.removeClass("required");
		}else{
			nbLine.show();
			nbSelect.addClass("required");
			reportLine.show();
		}
		o.next().show();
		o.next().next().show();
		legend.text(o.find("option:selected").eq(0).text());
		legend.show();
	}
}

function getNoAoById(id){
	for(var i in noao){
		if(noao[i].id === id){
			return noao[i];
		}
	}
	return null;
}

function getSystemById(id){
	for(var i in systems){
		if(systems[i].id === id){
			return systems[i];
		}
	}
	return null;
} 


function prepareText(noaoId, systemId, report){
	var noao, system = getSystemById(systemId), text;
	if(system === null) return;
	text = system.dopText;
	if(system.assessmentSystemCode !== '4'){
		if(noaoId !== ''){
			noao = getNoAoById(noaoId);
			if(noao !== null){
				text = text.replace(varId , '<strong>'+noao.code+'</strong>');
				text = text.replace(varAonoName , '<strong>'+noao.name+' - '+noao.street +', '+noao.zip +', '+noao.city +', '+noao.country +'</strong>');
				text = text.replace(varReport , '<strong>'+report+'</strong>');
				return '<div class="system-r"><h3>'+standard+', '+ system.name +':</h3>'+text+'</div>';
			}
		}
	}else{
		return '<div class="system-r"><h3>'+standard+', '+ system.name +':</h3>'+text+'</div>';
	}
	
}

function renderText(){
	var html, 
	    o = $('#fbox'),
		systemId = $('select.as').val(),
		noaoId = o.find('.w600').val(),
		report = o.find('.report input').val();

	if(systemId !== ''){
		html = prepareText(noaoId, systemId, report);
	}
	o = $('#cbox');
	if($('#isCumulative').is(':checked') && o !== undefined){
		systemId = $('select.as2').val(),
		noaoId = o.find('.w600').val(),
		report = o.find('.report input').val();
		if(systemId !== ''){
			html += prepareText(noaoId, systemId, report);
		}
	}
	$("#render").html(html);
}


