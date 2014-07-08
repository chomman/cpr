$(function(){
	sendRequest( "GET", false, "auth/companies",
	function(json){
		$('#companySelect')
			.html(generateOption(0, json, 2))
			.trigger("chosen:updated");;
	}
	);
});

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

function sendRequest(type, data, action, callBack){
	return executeRequest(
			{
				url :  getBasePath() + action,
				contentType: "application/json",
				type : type,
				dataType : "json",
				data : data ? JSON.stringify(data) : false
			 }
			,
			callBack
	);
}

function showLoader(){
	$('#wrapper').css({'opacity' : '.7'});
	$('#loader').show();
}
function hideLoader(){
	$('#wrapper').css({'opacity' : '1'});
	$('#loader').hide();
}

function showErrors(json){
	var i = 0, errorInfo = "Some error occurred, try it againg, please.";
	if(typeof json.result !== 'undefined'){
		for(i; i < json.result.length ; i++){
			errorInfo += json.result[i] +(i != 0 ? "<br />" : '');  
		}
		$(".ajax-result").html('<p class="msg error">' + errorInfo + '</p>');
	}
	showStatus({err: 1, msg: errorInfo});
	console.warn(arguments);
	return false;
}