$(function () {

    var dataTable = null;

    var tableHeight = function () {
        return $(window).height() - 170 + "px";
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
        "paging": false,
        "autoWidth": false,
        "ordering": false,
        "filter": false,
        "searching": false,
        "info": false,
        "scrollCollapse": true
    });

    new $.fn.dataTable.FixedColumns( dataTable );
    
    var $tBody = $('.dataTables_scrollBody tbody'),
    	$thead = $('.dataTables_scrollHead thead');
    
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
    	
    	$thead.find('th:eq('+tdIndex+')').addClass("qs-th-hover");
    	return false;
    });
    $tBody.on('mouseleave', clearTable);
    
    function clearTable(){
    	$tBody.find(".qs-hover").removeClass("qs-hover");
    	$thead.find('.qs-th-hover').removeClass("qs-th-hover");
    }
    
       
     $(window).resize(onResize);
});