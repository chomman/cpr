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
		if(!validate($form) || getTotalPrice() === 0 ){
			return false;
		}
		var data = toArray($form.serializeArray());
		data.portalProductItems = getPortalProductItems();
		sendRequest("POST", data, $form.attr('action'), function(json){
			if(json.status === "SUCCESS"){
				$form.after('<div><p class="status ok"><span class="status-ico"></span>'+getSuccessMessage(data)+
							'</p><div><div id="orderWrapp" class="loading"><p>'+ getLoadingMsg()+'</p></div>');
				$form.remove();
				sendHtmlReqest("GET", null, getRequestOrederUrl(json.data), function(html){
					var $wrapp = $('#orderWrapp');
					 $wrapp.removeClass('loading');
					 $wrapp.html(html);
				});
			}else{
				showErrors(json);
			}
		});
		return false;
	});
	
	function getSuccessMessage(data){
		if(isCzech()){
			return "Objednávka byla úspěšně odeslána. Informace o objednávce byly odeslány na email: <b>"+data.email+"</b>.";
		}
		return "Order was successfully sent. Order information was sent to given e-mail: <b>"+data.email+"</b>.";
	}
	
	function getLoadingMsg(){
		if(isCzech()){
			return "Načítají se informace o objednávce...";
		}
		return "Loading order information...";
	}
	
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

function getRequestOrederUrl(token){
	return getBasePath() + (
			isCzech() ? "async/order/"  :  "en/async/order/" ) + token;
}

function getPortalProductItems(){
	var items = [],
		$select = $('#portalProduct');
	if($select.length === 1){
		items.push(parseInt( $select.val(), 10) );
	}
	$('.selected').each(function(){
		items.push(toInt($(this).attr('data-id')));
	});
	return items;
}

function updateTotalPrice(){
	var curr = getCurrency();
	$('#price').html( rounded(getTotalPrice(false)) + ' '+ curr);
	$('#price-vat').html( getVat() + ' '+ curr);
	$('#price-with-dph').html( rounded(getTotalPrice(true)) + ' '+ curr);
	return false;
}

function getTotalPrice(withWat){
	var price = 0.0,
		vat = toInt($('#vat').text()),
		$option = $("#portalProduct option:selected");
		
	if($option.length === 1){
		price += getPrice(toInt($option.attr('data-price')), withWat, vat);
	}
	$('.selected').each(function(){
		price += getPrice(toInt($(this).attr('data-price')), withWat, vat);
	});
	return price;
}

function getPrice(price, withWat, vat){
	return withWat ? (price * vat) : price;
}

function getVat(){
	return rounded(getTotalPrice(true) - getTotalPrice(false)) ;
}

function rounded(v){
	return v.toFixed(2);
}

function toInt(v){
	return parseFloat(v);
}

function getCurrency(){
	return $('#portalCurrency').val() === 'CZK' ? 'Kč' : '&euro;';
}