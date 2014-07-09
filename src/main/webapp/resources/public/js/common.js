$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
       return "";
    }
    else{
       return results[1] || "";
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
			return getLocale() === 'cs' ? '-- Vyberte z možností --' : '-- Select item --';
		case 1 :
		default:
			return getLocale() === 'cs' ? 'Nezáleží' : 'No matter';
			
	}
}

function loadFilterData(){
	 var prefix = getLocale() === 'en' ? 'en/' : '';
	 $.getJSON( getBasePath() + prefix +"ajax/standard-filter", function(data) {  
			$('.async').each(function(){
					var $this = $(this),
						paramName = $this.attr('name'),
						attrName = $this.attr("data-items");
					$this.html(generateOption($.urlParam(paramName), data[attrName]));
			});
			refreshSelect();
	    }); 
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
	var params = ['createdFrom', 'createdTo', 'groupId', 
	           'commissionDecisionId','mandateId','assessmentSystemId',
	           'standardStatus', 'notifiedBody'];
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
    else var expires = "";
    document.cookie = name + "=" + value + expires + "; path=/";
}

function getBasePath(){
	return $('#base').text();
}
function getLocale(){
	return $('#locale').text();
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

$(document).on('click', '.disabled', function(){return false;});


$(document).on('submit', 'form', function(){
	
});