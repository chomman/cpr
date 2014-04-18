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
	$(document).on("mouseenter", "li.pj-parent", showSubnav );
	$(document).on("mouseleave", "li.pj-parent", hideSubNav );
}

function hideSubNav(e){
	e.preventDefault();
	$(this).find('ul').hide(100);
	return false;
}

function showSubnav(e){
	e.preventDefault();
	$(this).find('ul').stop(true).show(300);
	return false;
}