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
		
	loadCertificationBodies();
	loadCompanies();
	loadEacCodes(eacCodeList);
	loadNandoCodes(nandoCodeList);	
		
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
	$(document).on('click', '#change-status input[type=submit]', onChangeStatusSubmit);
	
	function onChangeStatusSubmit(){
		$(this).val($.getMessage("submiting"));
		showLoader();
	}
	
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
	
	$cBodySelect.on('change', refreshOrderNoField);
	$cBodySelect.on('chosen:updated', refreshOrderNoField);
	
	$("textarea.limit").limiter(255, $("#chars"));
	
	
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
		}else if(!validateOrderNo()){
			return false;
		}
	});
								  
	$(document).on('submit', 'form.dossierReport', function() {
		saveCodes();
		$('#certificationNo').val($('#certificationNo').val().replace(' ',''));
		log('subbmitting..');
		if (!validate($(this)) ) {
			showStatus({err : 1, msg: $.getMessage("errForm")});
			return false;
		}else if(!isAnyNandoCodeSet()){
			return false;
		}else if(!validateOrderNo()){
			return false;
		}
	});
	
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
	
	function loadNandoCodes(list){
		if($("#nandoCodes").length > 0){
			sendRequest("GET", false, getNandoCodeUrl(), function(json) {
				list = toArray(json, false);
				initTags("#nandoCodes", list, false);
			});
		}
	}


	function loadEacCodes(list){
		if($("#eacCodes").length > 0){
			sendRequest("GET", false, "ajax/eac-codes", function(json) {
				list = toArray(json, true);
				initTags("#eacCodes", list, true);
			});
		}
	}

	function loadCompanies(){
		var $select = $('#companySelect');
			if($select.length > 0){
			sendRequest("GET", false, "ajax/companies", function(json) {
				log('Companies loaded. Size: ' + json.length);
				$select.html(generateOption($select.attr('data-id'), json, 2)).trigger("chosen:updated");
			});
		}
	}

	function loadCertificationBodies(){
		var $select = $('#certificationBody');
		if($select.length > 0){
			sendRequest("GET", false, "ajax/certification-bodies", function(json) {
				log('Certification Bodies loaded. Size: ' + json.length);
				$select.html(generateOption($select.attr('data-id'), json, 2)).trigger("chosen:updated");
			});
		}
	}

});

function validateOrderNo(){
	if($('#orderNo.required').length > 0){
		if(!isOrderNoValid($('#orderNo.required').val())){
			showStatus({
				err : 1,
				msg : $.getMessage("errOrderNo")
			});
			return false;
		}
	}
	return true;
}

function isOrderNoValid(val){
	return /^((8036|8136|8236)\d{5})$/.test(val);
}



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
function isAnyNandoCodeSet() {
	if (!areTagSet('#nandoCodes')) {
		showStatus({
			err : 1,
			msg : $.getMessage("errNandoCodes")
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
		var o = {
				label : json[i].code + " - "+ (isEacCode ? json[i].name : json[i].specification),
				value : json[i].code
			};
		if( json[i].hasOwnProperty("forProductSpecialist")){
			o.forProductSpecialist =  json[i].forProductSpecialist;
		}
		list.push(o);
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
	log('Refreshing order no ..');
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

function log(msg){
	if(console){
		console.log(msg);
	}
}

function getLogType(){
	 return $('body[data-type]').attr('data-type');
}

function isAuditLog(){
	 return getLogType() === "audit-log";
}

function isDossierReport(){
	 return getLogType() === "dossier-report";
}

function getNandoCodeUrl(){
	return "ajax/nando-codes?type=" + ( isAuditLog() ? "1" : "2");
}