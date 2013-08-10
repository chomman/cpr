$(function() {
	
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
		
		console.log('Image name: ' + imageName);
		if(imageName.length > 0){
			var imageSRC =  $('#base').text()  + 'image/n/' + imageName,
				selector = $('#selector');
			if(selector.text().length > 0){
				console.log('SELECTOR: ' + "#"+selector.text() + 'IMG SRC='  + imageSRC);
				$(window.opener.document).find("#"+selector.text()+"-inp").val(imageSRC);
				window.close();
				window.opener.focus();
			}
		}
		
	});
	
	
	
});