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
		$(document).trigger('updateprice');
		return false;
	}
	
	showProductInfo();
	
	$(document).on("click", ".pub-btn.add", selectPublication);
	$(document).on("click", ".pub-btn.remove", deselectPublication);
	$(document).on("updateprice", updateTotalPrice);
	
	$(document).on("submit", "form#user", function(){
		var $form = $(this);
		if(!validate($form)){
			return false;
		}
		var data = toArray($form.serializeArray());
		data.portalProductItems = getPortalProductItems();
		sendRequest("POST", data, $form.attr('action'), function(json){
			if(json.status === "SUCCESS"){
				//$form.after('<div><p class="status ok"><span class="status-ico"></span>Objednávka bola úspešne odoslaná.</p><div>');
				//$form.remove();
			}else{
				showErrors(json);
			}
		});
		return false;
	});
	
	
	function selectPublication(){
		var $this = $(this);
		$this.parent().parent().addClass('selected');
		$(document).trigger('updateprice');
		return false;
	}
	
	function deselectPublication(){
		var $this = $(this);
		$this.parent().parent().removeClass('selected');
		$(document).trigger('updateprice');
		return false;
	}
	
	$(document).trigger('updateprice');
});

function getPortalProductItems(){
	var items = [];
	items.push(toInt($('#portalProduct').val()));
	$('.selected').each(function(){
		items.push(toInt($(this).attr('data-id')));
	});
	return items;
}

function updateTotalPrice(){
	var curr = getCurrency();
	$('#price').html( rounded(getTotalPrice()) + ' '+ curr);
	$('#price-vat').html( getVat() + ' '+ curr);
	$('#price-with-dph').html( rounded(getTotalPriceWithVat()) + ' '+ curr);
	return false;
}

function getTotalPrice(){
	var price = toInt($("#portalProduct option:selected").attr('data-price'));
	$('.selected').each(function(){
		price += toInt($(this).attr('data-price'));
	});
	return price;
}

function getVat(){
	return rounded(getTotalPriceWithVat() - getTotalPrice()) ;
}

function rounded(v){
	return v.toFixed(2);
}

function getTotalPriceWithVat(){
	var vat = parseFloat($('#vat').text()),
		price = toInt($("#portalProduct option:selected").attr('data-price')) * vat;
	$('.selected').each(function(){
		price += toInt($(this).attr('data-price')) * vat;
	});
	return price;
}

function toInt(v){
	return parseFloat(v);
}

function getCurrency(){
	return $('#portalCurrency').val() === 'CZK' ? 'Kč' : '&euro;';
}