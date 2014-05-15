$(function(){ 
	$(".chosen").chosen({ width : "320px", disable_search : true });
	
	$(document).on('click', '.is-company',function(){
		$('.pj-company').hide().removeClass('hidden').show(500);
		$(this).remove();
		return false;
	});
	
	$(document).on("click", ".pub-btn.add", selectPublication);
	$(document).on("click", ".pub-btn.remove", deselectPublication);
	$(document).on("change", "#portalCountry", updateTotalPrice);
	$(document).on("updateprice", updateTotalPrice);
	$(document).on("currencychanged", onCurrencyChanged);
	$(document).on("click","a.currency", onChangeCurrency);
	
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
	
	function onChangeCurrency(){
		var $this = $(this),
			activeCls = 'active',
			CURR = $.trim($this.text());
		if($this.hasClass(activeCls)){
			return false;
		}
		
		$('.currency.' + activeCls).removeClass(activeCls);
		$this.addClass(activeCls);
		setCurrency(CURR);
		return false;
	}
	
	
	function onCurrencyChanged(){
		updateProductPriceLabels();
		updateTotalPrice();
		return false;
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
	
	$(document).trigger('currencychanged');
});

function getRequestOrederUrl(token){
	return getBasePath() + (
			isCzech() ? "async/order/" : "en/async/order/" ) + token;
}

function getPortalProductItems(){
	var items = [];
	$('.selected').each(function(){
		items.push(toInt($(this).attr('data-id')));
	});
	return items;
}

function updateTotalPrice(){
	var curr = getCurrencySymobol();
	$('#vat-val').text(getVatFormattedValue());
	$('#price').html( rounded(getTotalPrice(false)) + ' '+ curr);
	$('#price-vat').html( getVat() + ' '+ curr);
	$('#price-with-dph').html( rounded(getTotalPrice(true)) + ' '+ curr);
	return false;
}

function getTotalPrice(withWat){
	var price = 0.0,
		vat = getVatValue(),
		key = getPriceKey();
	$('.selected').each(function(){
		price += getPrice(toInt($(this).attr(key)), withWat, vat);
	});
	return price;
}

function updateProductPriceLabels(){
	var curr = getCurrencySymobol(),
		key = getPriceKey();
	$('tr[data-id]').each(function(){
		var $this = $(this);
		$this.find('.price-wrapp').html($this.attr(key) + " " + curr);
	});
}


function getVatFormattedValue(){
	var vat = getVatValue();
	return  vat == 0 ? '0 %' : (vat * 100 - 100)+ ' %';
}

function getVatValue(){
	return isSelectedCR() ? toInt($('#vat').text()) : 0.0;
}

function isSelectedCR(){
	return $('#portalCountry').val() === 'CR'; 
}

function getPriceKey(){
	return useCzkCurrency() ? 'data-czkprice' : 'data-eurprice';
}

function getPrice(price, withWat, vat){
	return !withWat || vat === 0.0 ? price : (price * vat);
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
function setCurrency(code){
	$('#portalCurrency').val(code);
	$(document).trigger('currencychanged');
}
function useCzkCurrency(){
	return $('#portalCurrency').val() === 'CZK' ? true : false;
}

function getCurrencySymobol(){
	return useCzkCurrency() ? 'Kč' : '&euro;';
}

