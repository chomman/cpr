function createClasses(){
	$('tr:odd').addClass('odd');
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
});

