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
	console.log("s: " + s + "  /  " + $.type(s) + " typeof s  !== 'undefined'" + typeof s  !== 'undefined');
	if(typeof s  !== 'undefined'){ 
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



$(function() {
	var urlPrefix = $('#base').text(), message = {};
	message.SUCCESS_CREATE = "Položka byla úspěšně vytvořena.",
	message.SUCCESS_UPDATE = "Úspěšně aktualizováno",
	message.ERROR_VALIDATE = "Formulář je chybně vyplněn, zkontrolujte zadané data.",
	message.ERROR = "Nastala neočekávaná chyba, operaci zkuste zopakovat.";
	createClasses();
	resize();
	$('form.valid').submit(function(){
		if(! validate($(this))){
			showStatus({err : 1, msg : ERROR_VALIDATE});
			return false;
		}
	});
	
	$('#quick-search').quicksearch('.data tbody tr');	
	$('#req-nav select').change(function () {
		window.location.href = urlPrefix + "admin/cpr/standard/edit/1/requirements?country=" + $(this).val();
	});
	
    $('.tt').tooltip({
        position: {
            my: "center top",
            at: "center bottom+10",
        },
        show: {
            duration: "fast"
        }});

     tinyMCE.init({
    	language : "cs",
        mode : "specific_textareas",
        height : "300",
        width : "610",
        editor_selector : "mceEditor",
        plugins : "lists,style,table",
        content_css :  urlPrefix + "resources/admin/css/tinymce.css",
            theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,|,bullist,numlist,|,table,|,link,unlink,image,|,undo,redo,formatselect",
            theme_advanced_buttons2 : "",
            theme_advanced_buttons3 : "",
            theme_advanced_toolbar_location : "top",
            theme_advanced_toolbar_align : "left",
            theme_advanced_statusbar_location : "bottom",
            theme_advanced_resizing : true,
    });
     
     $('.confirm').on('click', function () {
         return confirm('Opravdu chcete odstranit tuto položku?');
     });
     
     $('.date').datepicker({
			dayNamesMin: ['Ne', 'Po', 'Út', 'St', 'Čt', 'Pa', 'So'], 
			monthNames: ['Leden','Únor','Březen','Duben','Květen','Červen','Červenec','Srpen','Září','Říjen','Listopad','Prosinec'], 
			autoSize: false,
			dateFormat: 'dd.mm.yy',
			firstDay: 1});
});
