$(function(){
		$(".functions table").stickyTableHeaders();
		$('.functions tr:odd').addClass('odd');
		
		 var $tBody = $('.functions table tbody');
	    
	    $tBody.on('mouseenter', 'td', function(){
	    	clearTable();
	    	var $this = $(this),
	    		tdIndex = $this.index(),
	    		trIndex = $this.parent().index();
	    	for(var i = 0; i < trIndex; i++){
	    		$rows = $tBody.find('tr:eq('+i+') td:eq('+tdIndex+')').addClass("qs-hover");
	    	}
	    	$this.prevAll().addClass("qs-hover");
	    	$this.addClass("qs-hover");
	    	return false;
	    });
	    $tBody.on('mouseleave', clearTable);
	    
	    function clearTable(){
	    	$tBody.find(".qs-hover").removeClass("qs-hover");
	    }
});