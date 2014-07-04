$(function() {
	var FILE_MAPPING_PREFIX = 'f/',
		IMAGE_MAPPING_PREFIX = 'image/n/';
	
	$(document).on('click', '.delete', onRemoveFile );

	$(document).on('click', '.image', function(){
			procesSelect(IMAGE_MAPPING_PREFIX, $(this).attr('data-url'));
	});
	
	$(document).on('click', '.document', function(){
		var url = $(this).attr('data-url');
		if(!$('body').hasClass('quasar')){
			procesSelect(FILE_MAPPING_PREFIX, $(this).attr('data-url'));
		}else{
			$(this).attr('href', getBasePath() + FILE_MAPPING_PREFIX + url);
		}
	});
	
	$(document).on('click', 'ul li a', function(){
		$('form').addClass('hidden');
		$('ul li a').removeClass('active');
		var cls = $(this).attr('data-class');
		$('.'+cls).removeClass('hidden');
		$(this).addClass('active');
		return false;
	});
	
	function procesSelect(mappingPrefix, fileLocation){
		var $selector = $('#selector'),
		fileDownloadUrl = getBasePath() + mappingPrefix + fileLocation,
		$hrefInputWrapp = $(window.opener.document).find("#"+$selector.text());
		$hrefInputWrapp.find("#"+$selector.text()+"-inp").val(fileDownloadUrl);
		if(mappingPrefix === FILE_MAPPING_PREFIX){
			var $titleInput = $hrefInputWrapp.parent().parent().next().find("input");
			if($.trim($titleInput.val()) === ""){
				$hrefInputWrapp.parent().parent().next().find("input").val(getFileName(fileLocation));
			}
		}
		window.close();
		window.opener.focus();
	}
	
	$("table").stupidtable();
	$("[data-sort]").eq(0).click();
	$(".fancy").fancybox();
});



function onRemoveFile(){
	if(!confirm('Skutečně chcete odstranit soubor?')){
		return false;
	}
	var $this = $(this), 
		url = $(this).attr('data-url');
	prepareRequest("admin/file/remove?url="+url , false , function(json){
		if(json.status === 'SUCCESS'){
			$this.closest('.rm').fadeOut(500);
		}else{
			showErrors();
		}
	});
}

function prepareRequest(url, data, callBack){
	executeRequest({
		url :  getBasePath() + url,
		contentType: "application/json",
		dataType : "json",
		data :  data ? JSON.stringify(data) :  false
		},
		callBack
	);
}
function executeRequest(opts, callBack){
	try{
		showLoader(); 
		$.ajax( opts )
		 .done( callBack )
		 .fail( showErrors )
		 .always( hideLoader );
	}catch(e){
		console.warn(e);
	}
	return false;
}


function showErrors(){
	alert("Došlo k neočekávané chybě, operaci opakujte.");
	return false;
}

function getBasePath(){
	return $('#base').text();
}
function showLoader(){
	$('#loader').center().show();	
}
function hideLoader(){
	$('#loader').hide();
}

function getFileName(fileUrl){
	return fileUrl.replace(/^.*[\\\/]/, '');
}


jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
    return this;
};