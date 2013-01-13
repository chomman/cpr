 $(function() { 
        $('form').submit(function(e) {
        	e.preventDefault();
            var form = $( this ),
                url = form.attr('action'),
                data = renameArr(form.serializeArray());
            var mce =  tinyMCE.get('text');
            	mce.setProgressState(1); // Show progress
				data.text = mce.getContent();
            console.log(data);
            $.ajax({
                url : url,
                type : "POST",
                traditional : true,
                contentType : "application/json",
                dataType : "json",
                data : JSON.stringify(data),
                success : function (response) {
                	if(response.status == "SUCCESS"){
	                    showStatus({err: 0, msg: "Úspěšně aktualizováno"});
                	}else{
                		 showStatus({err: 1, msg: "Nastala neočekávaná chyba, operaci zkuste zopakovat."});
                	}
                	mce.setProgressState(0);
                },
                error : function(xhr, status, err) {
                	howStatus({err: 1, msg: ERROR_MESSAGE});
                    mce.setProgressState(0);
                }
            });

            return false;
        });
});