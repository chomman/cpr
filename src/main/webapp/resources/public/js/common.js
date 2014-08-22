var mLocale = null;

$.urlParam = function(name){
    var r = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (r==null){
       return "";
    }
    else{
       return r[1] || "";
    }
};

function isBlank(v){
	return typeof v === 'undefined' || $.trim(v) === '';
}

function generateOption(selectedId, items){
	var html = getOption('', getOptionInitLable(arguments[2]), false);
	for(var i in items){
		html += getOption(items[i].id, items[i].name, (selectedId+"" == items[i].id+""));
	}
	return html;
}

function getOptionInitLable(variant){
	switch(variant){
		case 2:
			return isCzechLocale() ? '-- Vyberte z možností --' : '-- Select item --';
		case 1 :
		default:
			return isCzechLocale() ? 'Nezáleží' : 'No matter';
			
	}
}

function loadFilterData(){
	 var prefix = getLocale() === 'en' ? 'en/' : '';
	 $.getJSON( getBasePath() + prefix +"ajax/standard-filter", function(data) {  
			$('.async').each(function(){
					var $this = $(this),
						paramName = $this.attr('name'),
						attrName = $this.attr("data-items");
					if(typeof attrName !== 'undefined'){
						$this.html(generateOption($.urlParam(paramName), data[attrName]));
					}
			});
			loadRegulations(data.regulations);
			refreshSelect();
			refresCprFilterVisibility();
	    }); 
}

function loadRegulations(data){
	var itemList = [],
		$select = $('select[name=rId]');
	if(data.length === 0 || $select.length === 0){
		return false;
	}
	for(var i in data){
		var item = { id : data[i].id };
		if(data[i].euRegulation){
			item.name = getRegulationName(data[i].euRegulationContent);
		}else if(data[i].csRegulation){
			item.name = getRegulationName(data[i].csRegulationContent);
		}else if(data[i].skRegulation){
			item.name = getRegulationName(data[i].skRegulationContent);
		}
		itemList.push(item);
	}
	$select.html(generateOption($.urlParam("rId"), itemList));
	return false;
}

function getRegulationName(obj){
	if(isCzechLocale()){
		return obj.nameCzech;
	}else{
		if(isBlank(obj.nameEnglish)){
			return obj.nameCzech;
		}
		return obj.nameEnglish;
	}
}

function isCzechLocale(){
	return getLocale() === 'cs';
}
function refreshSelect(){
	$("select.async").trigger("chosen:updated");
}
function isBlank(str){
	if(typeof str === 'undefined' || 
			str === null || 
			$.trim(str).length === 0){
		return true;
	}
	return false;
}
function isStandardAdvancedSarch(){
	var params = ['createdFrom', 'createdTo', 
	              'scId', 'sgId', 'cdId', 'rId',
	              'mId','asId', 's', 'notifiedBody'];
	return isAdvancedSarch(params);
}
function isAdvancedSarch(params){
	for(var i in params){
		if(!isBlank($.urlParam(params[i]))){
			return true;
		}
	}
	return false;
}

function startsWith(str, term){
	str = str.toLowerCase();
	term = term.toLowerCase();
    return str.indexOf(term.toLowerCase()) == 0;
}

function getOption(val, label, isSelected){
	return '<option '+ (isSelected ? ' selected="selected" ' : '') +
			' value="'+val+'">'+label+'</option>';
}

function createCookie(name, value, days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        var expires = "; expires=" + date.toGMTString();
    }
    else expires = "";
    document.cookie = name + "=" + value + expires + "; path=/";
}

function getBasePath(){
	return $('#base').text();
}
function getLocale(){
	if(mLocale === null){
		mLocale =  $('#locale').text();
	}
	return mLocale;
}

function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) {
                c_end = document.cookie.length;
            }
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}

function getDatepickerOptions(type){
	var locale = getLocale(),
		names = {
		closeText : 'Vybrat',
		currentText : "Dnes",
		dayNamesMin: ['Ne', 'Po', 'Út', 'St', 'Čt', 'Pa', 'So'], 
		monthNames: ['Leden','Únor','Březen','Duben','Květen','Červen','Červenec','Srpen','Září','Říjen','Listopad','Prosinec'],
		monthNamesShort : ['Leden','Únor','Březen','Duben','Květen','Červen','Červenec','Srpen','Září','Říjen','Listopad','Prosinec']
	},datepickerOpts = {
			autoSize: false,
			dateFormat: 'dd.mm.yy',
			firstDay: 1
	};
	if(isBlank(locale) || locale === 'cs'){
		datepickerOpts = $.extend({}, datepickerOpts, names );
	}
	
	if(typeof type === 'undefined' || type === 'default'){
		return datepickerOpts;
	}else if(type === 'minDate'){
		var d = arguments[1].split('.');
		datepickerOpts = $.extend({}, datepickerOpts, { 
			minDate : new Date( parseInt(d[2]), parseInt(d[1], 10) - 1,  parseInt(d[0])) 
		});
		return datepickerOpts;
	}
	var selectOnlyMonth = $.extend({}, datepickerOpts, {
		changeMonth: true,
        changeYear: true,
        yearRange : "c-20:c+1",
        showButtonPanel: true,
        dateFormat: 'mm.yy',
        onClose: function(dateText, inst) { 
            var $this = $(this),
        		month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(),
            	year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
            	$this.datepicker('setDate', new Date(year, month, 1));
        },
        beforeShow: function(input, inst) {
            $('#ui-datepicker-div').addClass("ui-month-only");
            var $this = $(this);
            if ((datestr = $this.val()).length > 0) {
                actDate = datestr.split('.');
                month = actDate[0] -1 ;
                year = actDate[1];
                $this.datepicker('option', 'defaultDate', new Date(year, month, 1));
                $this.datepicker('setDate', new Date(year, month, 1));
            }
        }
	});
	return selectOnlyMonth;
}


function refresCprFilterVisibility(){
	if($('select[name=scId]').val() === "84" ||
	   $('select[name=rId]').val() === "4"){
		$('.cpr-filter').removeClass("hidden");
	}else{
		$('.cpr-filter').addClass("hidden");
	}
}

function initManthPicker(){
	var $manthSelector = $('.date-month');
	$manthSelector.each(function(){
		var $this = $(this),
			val = $this.val();
		if(!isBlank(val) && val.length === 10){
			$this.val(val.substring(3,12));
		}
	});
    $manthSelector.datepicker(getDatepickerOptions("month"));
}

$(document).on('change', 'select[name=scId],select[name=rId]', refresCprFilterVisibility);
$(document).on('click', '.disabled', function(){return false;});