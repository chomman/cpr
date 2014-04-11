$(init);


function init(){
	$('[placeholder]').focus(function() {
	  var input = $(this);
	  if (input.val() == input.attr('placeholder')) {
	    input.val('');
	    input.removeClass('placeholder');
	  }
	}).blur(function() {
	  var input = $(this);
	  if (input.val() == "" || input.val() == input.attr('placeholder')) {
	    input.addClass('placeholder');
	    input.val(input.attr('placeholder'));
	  }
	}).blur();




	$(document).on("mouseenter", "ul.first-child > li", function(){
		showSubnav($(this));
	});
	$(document).on("mouseleave", "ul.first-child", function(){
		var $curr = $("ul.first-child a.pj-current").parent();
		if($curr.length > 0){
			showSubnav($curr);
		}
	});
	
	showSubnav($("ul.first-child li:first-child"));
}

function showSubnav($li){
	var $ul = $li.parent();
	$ul.find('.curr').removeClass('curr');
	$ul.find('ul').hide();
	$li.addClass('curr').find('ul').show();
	return false;
}