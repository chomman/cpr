function createClasses(){
	$('tr:odd').addClass('odd');
} 

$(function() {
	createClasses();
	$('.tt').tooltip({ show: {duration: "fast" }});
});

