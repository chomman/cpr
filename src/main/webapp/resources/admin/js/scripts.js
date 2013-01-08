function createClasses(){
	$('tr:odd').addClass('odd');
} 
function resize() {
    $("#right").css('min-height', $(document).height()  - 138);
}
function searchTable(val){
	var table = $('.data');
	table.find('tr').each(function(index, row)
	{
		var allCells = $(row).find('td');
		if(allCells.length > 0)
		{
			var found = false;
			allCells.each(function(index, td)
			{
				var regExp = new RegExp(val, 'i');
				if(regExp.test($(td).text()))
				{
					found = true;
					return false;
				}
			});
			if(found === true)$(row).show();else $(row).hide();
		}
	});
}

 $(function() {
	createClasses();
	resize();
	$('#quick-search').keyup(function(){
		searchTable($(this).val());
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
        height : "165",
        width : "600",
        editor_selector : "mceEditor",
        plugins : "lists,style,table",
        content_css : "resources/admin/css/tinymce.css",
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
