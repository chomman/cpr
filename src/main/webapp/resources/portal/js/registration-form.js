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
		console.log(data);
		sendRequest("POST", data, $form.attr('action'), function(json){
			if(json.status === "SUCCESS"){
				$form.after('<div><p class="status ok"><span class="status-ico"></span>Objednávka bola úspešne odoslaná.</p><div>');
				$form.remove();
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

function updateTotalPrice(){
	var curr = getCurrency();
	$('#price').text( getTotalPrice() + ' '+ curr);
	$('#price-vat').text( getVat() + ' '+ curr);
	$('#price-with-dph').text( getTotalPriceWithVat() + ' '+ curr);
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
	return getTotalPriceWithVat() - getTotalPrice();
}

function getTotalPriceWithVat(){
	var vat = parseFloat($('#vat').text(), 10),
		price = toInt($("#portalProduct option:selected").attr('data-price')) * vat;
	console.log('vat: ' + vat);
	$('.selected').each(function(){
		price += toInt($(this).attr('data-price')) * vat;
	});
	return price;
}

function toInt(v){
	return parseInt(v, 10);
}

function getCurrency(){
	return $('#portalCurrency').val() === 'CZK' ? 'Kč' : '&euro;';
}