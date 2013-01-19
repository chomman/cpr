function splitDates(){
	var datetime =  $('#publishedSince').val().trim();
	if(datetime.length > 0){
		datetime = datetime.split(" ");
		$('#publishedSince-date').val(datetime[0]);
		$('#publishedSince-time').val(datetime[1]);
	}
	datetime =  $('#publishedUntil').val().trim();
		if(datetime.length > 0){
			datetime = datetime.split(" ");
			$('#publishedUntil-date').val(datetime[0]);
			$('#publishedUntil-time').val(datetime[1]);
		}
} 
function checkdateformat(v){;
 	console.log(/^(\d{2}).(\d{2}).(\d{4})(\s{1})(\d{2}):(\d{2})$/.test(v));
	return /^(\d{2}).(\d{2}).(\d{4})(\s{1})(\d{2}):(\d{2})$/.test(v);
}
function getDateTIme(element){
	var datetime, 
		date = $(element + "-date").val().trim(),
		time = $(element + "-time").val().trim();
	
	if(date.length == 0) return "";
	datetime = (time.length == 0 ? date + " 00:00" : date + " " + time);
	console.log(datetime);
	if(checkdateformat(datetime)){
		return datetime;
	}
	return "";
}

function serializeData(mce){
	  var data = {};
	  data.articleContent = mce.getContent();
	  data.title = $('#title').val();
	  data.header = $('#header').val();
	  data.enabled = $("input[name=enabled]").is(':checked');
	  data.timestamp = $('#timestamp').val();
	  data.publishedSince = getDateTIme('#publishedSince');
	  data.publishedUntil = getDateTIme('#publishedUntil');
	  data.id = $('#id').val();
 	  return data;
  }
 
 
$(function() { 					
	initWISIWIG("610", "450"); 
	var elem = $("#chars");
	splitDates();
	$("#header").limiter(255, elem);
	$('form').submit(function(e) {
        var form = $( this ),
            url = form.attr('action'),
            errMsg = "Formulář je chybně vyplněn, zkontrolujte zadané data.";
        if(! validate(form)){
        	showStatus({err : 1, msg : errMsg});
        	return false;
        }
        mce =  tinyMCE.get('articleContent'),
        data = serializeData(mce);
        console.log(data);
        data = JSON.stringify(data);
        console.log(data);
        mce.setProgressState(1); // Show progress
        $.ajax({
            url : url,
            type : "POST",
            contentType: "application/json",
            dataType : "json",
            data : data,
            success : function (response) {
            	console.log(response);
            	if(response.status == "SUCCESS"){
                    showStatus({err: 0, msg: "Úspěšně aktualizováno"});
                    $('#timestamp').val(response.result);
                    $("#ajax-result").html('');
            	}else{
            		var i = 0, errorInfo = "";
            		for(i; i < response.result.length ; i++){
		                  errorInfo += response.result[i] +(i != 0 ? "<br />" : '');  
		             }
            		$("#ajax-result").html('<p class="msg error">' + errorInfo + '</p>');		             
            		 showStatus({err: 1, msg: errMsg});
            	}
            	mce.setProgressState(0);
            },
            error : function(xhr, status, err) {
            	showStatus({err: 1, msg: "Nastala neočekávaná chyba, operaci zkuste zopakovat."});
                mce.setProgressState(0);
            }
        });
        return false;
    });
});