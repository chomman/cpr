function createClasses(){
	$('tr:odd').addClass('odd');
} 

 $(function() {
	createClasses();
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
        editor_selector : "mceEditor",
        plugins : "lists,style,table",
        content_css : "css/tinymce.css",
            theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,|,bullist,numlist,|,table,|,link,unlink,image,|,undo,redo,formatselect",
            theme_advanced_buttons2 : "",
            theme_advanced_buttons3 : "",
            theme_advanced_toolbar_location : "top",
            theme_advanced_toolbar_align : "left",
            theme_advanced_statusbar_location : "bottom",
            theme_advanced_resizing : true,
    });
});
