$(function () {

    var dataTable = null;

    var tableHeight = function () {
        return $(window).height() - 0 + "px";
    };

    // Change height to match window
    var onResize = function () {
        var oSettings = dataTable.fnSettings();
        oSettings.oScroll.sY = tableHeight(); 
        dataTable.fnDraw();
    };

    console.log('height:' + tableHeight());
    dataTable = $('.container table').dataTable({
        "scrollY": tableHeight(),
        "scrollX": '100%',
        "autoWidth": true,
        "ordering": false,
        "paging": false,
        "filter": false,
        "searching": false,
        "info": false,
        "scrollCollapse": true
    });

    new $.fn.dataTable.FixedColumns( dataTable, {
    	heightMatch: 'none'
    } );
    
    var $tBody = $('.dataTables_scrollBody tbody');
    
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
    
       
     $(window).resize(onResize);
});