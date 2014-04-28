$(function(){ 
	$(".chosen").chosen({ width : "500px" });
	$(document).on('change', 'select', showProductInfo);
	$(document).on('click', '.is-company',function(){
		$('.pj-company').hide().removeClass('hidden').show(500);
		$(this).remove();
		return false;
	});
	function showProductInfo(){
		var id = $('#portalProduct').val();
		$('.product-info').addClass('hidden');
		$('#p' + id).removeClass('hidden');
		return false;
	}
	showProductInfo();
});