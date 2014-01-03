$(function() {
	var urlPrefix = $('#base').text();
	createClasses();
	resize();
	$('form.valid').submit(function(){
		if(! validate($(this))){
			showStatus({err : 1, msg : "Formulář je chybně vyplněn, zkontrolujte zadané data."});
			return false;
		}
	});
	
	$('#quick-search').quicksearch('.data tbody tr');	
	$('#req-nav select').change(function () {
		var id = $(this).attr("id").replace("id", "");
		window.location.href = urlPrefix + "admin/cpr/standard/edit/"+id+"/requirements?country=" + $(this).val();
	});
	
	$(document).on("submit","form.csnFileUpload", showLoader);
	
	$(window).resize(updateNav);
	
	if($('form.csnFileUpload').length > 0){
		$('body').append('<div id="overlay"></div><div id="loader"><div>Čekejte prosím ...</div></div>');
	}
	
    $('.tt').tooltip({
        position: {
            my: "center top",
            at: "center bottom+10",
        },
        show: {
            duration: "fast"
        }});

     $('input.csnOnlineReplace').on("paste keyup", function(){
    	var val = $(this).val();
    	if(val.length > 0 ){
    		var regex = new RegExp(/.*\k=\d/gi);
    		if (val.match(regex) ){
    			$(this).val(val.substring(val.indexOf('?k=')+3));
    		} 
    	}
     });
     
     $('.confirm').on('click', function () {
         return confirm('Opravdu chcete odstranit tuto položku?');
     });
     
     $('.confirmUnassignment').on('click', function () {
         return confirm('Opravdu chcete zrušit přiřazení?');
     });
     
     $('.confirmMessage').on('click', function () {
         return confirm($(this).attr("data-message"));
     });
     
     
     
     
     $('.date').datepicker({
			dayNamesMin: ['Ne', 'Po', 'Út', 'St', 'Čt', 'Pa', 'So'], 
			monthNames: ['Leden','Únor','Březen','Duben','Květen','Červen','Červenec','Srpen','Září','Říjen','Listopad','Prosinec'], 
			autoSize: false,
			dateFormat: 'dd.mm.yy',
			firstDay: 1});
     
     updateNav();
     $(".chosen").chosen({
    	 width : "510px"
     });
     
     $("select").not(".chosen, .chosenSmall, .chosenMini").chosen({
    	 width : "510px"
     });
     
     $(".chosenSmall").chosen({
    	 width : "200px"
     });
     $(".chosenMini").chosen({
    	 width : "110px"
     });
     createSelects();
});
function createSelects(){
	$(".chosen").chosen({
   	 width : "510px"
    });
    
    $("select").not(".chosen, .chosenSmall").chosen({
   	 width : "510px"
    });
    
    $(".chosenSmall").chosen({
   	 width : "200px"
    });
}
function refreshSelect(){
	$("select.async").trigger("chosen:updated");
}
function updateNav(){
	var o = $('nav');
	if($(document).width() < 1070){
		o.addClass("smallNav");
	}else{
		o.removeClass("smallNav");
	}
}
function createClasses(){
	$('tr:odd').addClass('odd');
} 
function resize() {
    $("#right").css('min-height', $(document).height()  - 138);
}
function showStatus(data){
	var html = '<p class="'+ (data.err === 0 ? "ok" : "err") +'">'+ data.msg +'</p>',
	o = $("#status");
	o.html(html).center().fadeIn();
	setTimeout(function() {o.fadeOut(100);}, 4000);
}

function renameArr(a){
	var d = {};	
	for (i in a) {
		d[a[i].name] = a[i].value;
	}
	return d;
}

function initWISIWIG(width, height){
	tinyMCE.init({
    	language : "cs",
        mode : "specific_textareas",
        height : height, //300
        width : width, 		//610
        editor_selector : "mceEditor",
        plugins : "lists,style,table",
        content_css :  $('#base').text() + "resources/admin/css/tinymce.css",
            theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,|,bullist,numlist,|,table,|,link,unlink,image,|,undo,redo,formatselect",
            theme_advanced_buttons2 : "",
            theme_advanced_buttons3 : "",
            theme_advanced_toolbar_location : "top",
            theme_advanced_toolbar_align : "left",
            theme_advanced_statusbar_location : "bottom",
            theme_advanced_resizing : true,
    });
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

jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", (($(window).height() - this.outerHeight()) / 2) + $(window).scrollTop() + "px");
    this.css("left", (($(window).width() - this.outerWidth()) / 2) + $(window).scrollLeft() + "px");
    return this;
};


function showLoader(){
	$loader = $('#loader');
	$("#overlay").show();
	$loader.center().show();
}
function removeLoader(){
	$('#loader').remove();
}

(function($) {
    $.fn.extend( {
        limiter: function(limit, elem) {
            $(this).on("keyup focus", function() {
                setCount(this, elem);
            });
            function setCount(src, elem) {
                var chars = src.value.length;
                if (chars > limit) {
                    src.value = src.value.substr(0, limit);
                    chars = limit;
                }
                elem.html( (limit - chars) + "/" + limit);
            }
            setCount($(this)[0], elem);
        }
    });
})(jQuery);


jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
    return this;
};