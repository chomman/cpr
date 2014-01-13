$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
       return "";
    }
    else{
       return results[1] || "";
    }
};


function generateOption(selectedId, items){
	var html = getOption('', 'Nezáleží', false);
	for(var i in items){
		html += getOption(items[i].id, items[i].name, (selectedId+"" == items[i].id+""));
	}
	return html;
}

function loadFilterData(){
	 $.getJSON( $("#base").text() +"ajax/standard-filter", function(data) {  
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