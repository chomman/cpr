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
	
	$(document).on("click", ".show-loginbox", showLoginBox );
	$(document).on("click", ".hide-loginbox", hideLoginBox );
}

function hideSubNav(e){
	e.preventDefault();
	$(this).find('ul').hide(10);
	return false;
}

function showSubnav(e){
	e.preventDefault();
	$(this).find('ul').stop(true).show(200);
	return false;
}



function showLoginBox(){
	$('#pj-login-box').removeClass('hidden');
	$('.pj-login').addClass('hidden');
	return false;
}

function hideLoginBox(){
	$('#pj-login-box').addClass('hidden');
	$('.pj-login').removeClass('hidden');
	return false;
}