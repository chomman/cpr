$(function() {
	initDatepicker();
	onChooseExistingCompany();
	onChooseExistingCertificationBodies();

	var eacCodeList = [], 
		nandoCodeList = [], 
		$companySelect = $('#companySelect'), 
		$cBodySelect = $('#certificationBody'),
		aSourceCallBack = function(
			request, response) {$.getJSON(getBasePath() + "/ajax/"+ $(this)[0].element.attr('data-url'), request, function(data) {
			response($.map(data, function(item) {
				return {
					label : item.name,
					value : item.id
				};
			}));
		});
	};
	
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

	if($companySelect.length > 0){
		sendRequest("GET", false, "ajax/companies", function(json) {
			$companySelect.html(generateOption($companySelect.attr('data-id'), json, 2)).trigger("chosen:updated");
		});
		sendRequest("GET", false, "ajax/certification-bodies", function(json) {
			$cBodySelect.html(generateOption($cBodySelect.attr('data-id'), json, 2)).trigger("chosen:updated");
		});
		sendRequest("GET", false, "ajax/eac-codes", function(json) {
			eacCodeList = toArray(json, true);
			initTags("#eacCodes", eacCodeList, true);
		});
		sendRequest("GET", false, "ajax/nando-codes", function(json) {
			nandoCodeList = toArray(json, false);
			initTags("#nandoCodes", nandoCodeList, false);
		});
	}

	$("input[name=companyName]").autocomplete({
		source : aSourceCallBack,
		minLength : 1,
		select : function(event, ui) {
			$companySelect.val(ui.item.value).trigger("chosen:updated");
			onChooseExistingCompany();
		}
	});

	$("input[name=certificationBodyName]").autocomplete({
		source : aSourceCallBack,
		minLength : 1,
		select : function(event, ui) {
			$cBodySelect.val(ui.item.value).trigger("chosen:updated");
			onChooseExistingCertificationBodies();
		}
	});

	$(document).on('click', '.qs-new-company a.toggle', onChooseExistingCompany);
	$(document).on('click', '.qs-existing-company a.toggle', onCreateNewCompany);
	$(document).on('click', '.qs-new-certification-bodies a.toggle',onChooseExistingCertificationBodies);
	$(document).on('click', '.qs-existing-certification-bodies a.toggle', onCreateNewCertificationBodies);
	$(document).on('click', '.qs-change-status-btn', onChangeStatus);
	$(document).on('click', '#change-status a', onCancelChangeStatus);
	
	function onCancelChangeStatus(){
		$("#change-status").addClass('hidden');
		$('.qs-log-nav').show(500);
		$('.qs-log-items').stop().animate({"opacity": 1});
		return false;
	}
	
	function onChangeStatus(){
		$this = $(this),
		note = $this.attr("data-note"),
		$form = $('#change-status');
		$form.find('input[name=status]').val($this.attr('data-status'));
		if(typeof note !== 'undefined' && note.length > 0){
			$form.find('p').remove();
			$form.append('<p>' + note + '</p>');
		}
		$form.find('h4').text($.trim($this.text().replace("»","")));
		$form.removeClass('hidden');
		$form.find('[type=submit]').removeClass().addClass('qs-btn').addClass($this.attr("data-cls"));
		$this.parent().hide(500);
		$('.qs-log-items').stop().animate({"opacity": 0.6});
	}
	
	$cBodySelect.on('change, chosen:updated', refreshOrderNoField);
	$("textarea.limit").limiter(255, $("#chars"));
	
	
	function initEacCodes(){
		$('#eacCodes').tagit({
			allowSpaces : true,
			placeholderText : $.getMessage("phEacCode"),
			beforeTagAdded : function(event, ui) {
				return listContains(eacCodeList, ui.tagLabel);
			},
			autocomplete : {
				source : function(req, res) {
					res(searchInList(req.term, eacCodeList, true));
				},
				select : function(event, ui) {
					ui.item.value;
				}
			}
		});
	}
	function initNandoCodes(){
		$('#nandoCodes').tagit({
			allowSpaces : true,
			placeholderText : $.getMessage("phNandoCode"),
			beforeTagAdded : function(event, ui) {
				return listContains(nandoCodeList, ui.tagLabel);
			},
			autocomplete : {
				source : function(req, res) {
					res(searchInList(req.term, nandoCodeList, false));
				},
				select : function(event, ui) {
					ui.item.value;
				}
			}
		});
	}
	$(document).on('click', '.qs-show-comments', function(){
		$(this).hide().next().removeClass('hidden');
		return false;
	});
	
	$(document).on('submit', 'form.auditLog', function() {
		saveCodes();
		if (!validate($(this)) ) {
			showStatus({err : 1, msg: $.getMessage("errForm")});
			return false;
		}else if(!areTagsValid()){
			return false;
		}
	});
});

function initTags(sel, list, isEac){
	$(sel).tagit({
		allowSpaces : true,
		placeholderText : $.getMessage("placeholderCodes"),
		beforeTagAdded : function(event, ui) {
			return listContains(list, ui.tagLabel);
		},
		autocomplete : {
			source : function(req, res) {
				res(searchInList(req.term, list, isEac));
			},
			select : function(event, ui) {
				ui.item.value;
			}
		}
	});
}

function initDatepicker(){
	var date = $('#minDate').text();
	if($.trim(date).length === 0){
		$('.qs-date').datepicker(getDatepickerOptions());
	}else{
		$('.qs-date').datepicker(getDatepickerOptions("minDate", date));
	}
}

function saveCodes() {
	$('#hEacCodes').val($('#eacCodes').tagit("assignedTags"));
	$('#hNandoCodes').val($('#nandoCodes').tagit("assignedTags"));
}

function areTagsValid() {
	if (!areTagSet('#eacCodes') && !areTagSet('#nandoCodes')) {
		showStatus({
			err : 1,
			msg : $.getMessage("errCodes")
		});
		return false;
	}
	return true;
}

function areTagSet(selector){
	return $(selector).tagit("assignedTags").length > 0;
}


function listContains(list, code) {
	for ( var i in list) {
		if (list[i].value === code) {
			return true;
		}
	}
	showStatus({
		err : 1,
		msg : $.getMessage("errCodes", code)
	});
	return false;
}

function searchInList(term, list, isEacCodeReq) {
	if (isEacCodeReq && /^\d+$/.test(term)) {
		term = "EAC " + term;
	}
	var rList = [];
	for ( var i in list) {
		if (startsWith(list[i].value, term)) {
			rList.push(list[i]);
			if (rList.length > 5) {
				return rList;
			}
		}
	}
	return rList;
}
function toArray(json, isEacCode) {
	var list = [];
	for ( var i in json) {
		list.push({
			label : json[i].code + " - "+ (isEacCode ? json[i].name : json[i].specification),
			value : json[i].code
		});
	}
	return list.sort(sortByName);
}

function onChooseExistingCompany() {
	onChooseExisting('company');
}
function onCreateNewCompany() {
	onCreateNew('company');
}
function onChooseExistingCertificationBodies() {
	onChooseExisting('certification-bodies');
	refreshOrderNoField();
}
function onCreateNewCertificationBodies() {
	onCreateNew('certification-bodies');
	refreshOrderNoField();
}

function onChooseExisting(postfix) {
	$('.qs-new-' + postfix).addClass('hidden').find('input').removeClass(
			'required');
	$('.qs-existing-' + postfix).removeClass('hidden').find('select').addClass(
			'required');
}
function onCreateNew(postfix) {
	$('.qs-new-' + postfix).removeClass('hidden').find('input').val('')
			.addClass('required');
	$('.qs-existing-' + postfix).addClass('hidden').find('select').val('')
			.removeClass('required').trigger("chosen:updated");
}

function refreshOrderNoField() {
	var $field = $('.order-no'),
		$select = $('#certificationBody');
	if($select.length === 0) return false;
	if ($select.val() === '1' && $select.hasClass('required')) {
		$field.show().find('input').addClass('required');
	} else {
		$field.hide().find('input').val('').removeClass('required');
	}
}

function executeRequest(opts, callBack) {
	try {
		showLoader();
		$.ajax(opts).done(callBack).fail(showErrors).always(hideLoader);
	} catch (e) {
		console.warn(e);
	}
	return false;
}

function sendRequest(type, data, action, callBack) {
	return executeRequest({
		url : getBasePath() + action,
		contentType : "application/json",
		type : type,
		dataType : "json",
		data : data ? JSON.stringify(data) : false
	}, callBack);
}

function showLoader() {
	$('#wrapper').css({
		'opacity' : '.7'
	});
	$('#loader').show();
}
function hideLoader() {
	$('#wrapper').css({
		'opacity' : '1'
	});
	$('#loader').hide();
}

function showErrors(json) {
	var i = 0, errorInfo =  $.getMessage("errUnknown");
	if (typeof json.result !== 'undefined') {
		for (i; i < json.result.length; i++) {
			errorInfo += json.result[i] + (i != 0 ? "<br />" : '');
		}
		$(".ajax-result").html('<p class="msg error">' + errorInfo + '</p>');
	}
	showStatus({
		err : 1,
		msg : errorInfo
	});
	console.warn(arguments);
	return false;
}

function sortByName(a, b) {
	var textA = a.value.toUpperCase();
	var textB = b.value.toUpperCase();
	return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
}
