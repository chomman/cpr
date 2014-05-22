$(function() {
	
	$(document).on('click', '.delete', onRemoveFile );
	
	var image = $(".imgBox div");
	
	image.hover(
		function () {
			$(this).removeClass("h"); 
		},
		function () {
			$(this).addClass("h");
		}
	);
	
	image.on("click", function(){
		var imageName = $(this).find('img').attr("alt");
		if(imageName.length > 0){
			var imageSRC =  getBasePath()  + 'image/n/' + imageName,
				selector = $('#selector');
			if(selector.text().length > 0){
				$(window.opener.document).find("#"+selector.text()+"-inp").val(imageSRC);
				window.close();
				window.opener.focus();
			}
		}
	});
	
	$(document).on('click', '.document', function(){
		var docUrl = $(this).attr('data-url'),
			$selector = $('#selector'),
			fileDownloadUrl = getBasePath() + 'f/' + docUrl,
			$hrefInputWrapp = $(window.opener.document).find("#"+$selector.text());
			$hrefInputWrapp.find("#"+$selector.text()+"-inp").val(fileDownloadUrl);
			$hrefInputWrapp.parent().parent().next().find("input").val(getFileName(docUrl));
			window.close();
			window.opener.focus();
	});
	
	$("table").stupidtable();
	$("[data-sort]").eq(0).click();
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