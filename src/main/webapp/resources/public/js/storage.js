$(function() {
	
	
	if(localStorage !== undefined){
	
		if(typeof(newDop) !== 'undefined'){
			 saveNewDoP(newDop);
		}
		var wrapper = $('#localStorage');
		
		if(wrapper !== undefined){
			wrapper.fadeOut(0);
			if( isSotred() ){
				wrapper.html(printDops());
				wrapper.fadeIn(800);
			}
		}
		
		$('body').on("click", ".delete", function(){
			if (confirm("Skutečně chcete odstranit prohlášení?")) { 
				deleteDop($(this).attr("title"));
			}else{
				return false;
			}
		});
	}
	
	
});


function saveNewDoP(newDop){
	
	var dop = loadFromStorage();
	if(dop === undefined || dop == null){
		dop = {};
		dop[newDop.token] = newDop;
	}
	dop[newDop.token] = newDop;
	saveDop(dop);
	
}

function loadFromStorage(){
	var string = localStorage.getItem("dop");
	if(string !== undefined){
		return $.parseJSON(string);
	}
}

function deleteDop(token){
	var dop = loadFromStorage();
	console.log(dop);
	if(dop !== undefined){
		delete dop[token];
	}
	console.log(dop);
	saveDop(dop);
	console.log(loadFromStorage());
}

function printDops(){
	var data = loadFromStorage(),
	base = $("#base").text(),
	rows = '';
	$.each( data, function( key, value ) {
		rows += '<tr><td>'+ value.ehn +'</td><td>'+ value.system +'</td><td>'+ printDate(value.created) +'</td>' + 
				'<td><a class="view" href="'+ base +'dop/'+ value.token +'">Zobrazit</td><td><a class="edit" href="'+ base +'dop/edit/'+ value.token +'">Upravit</td>'+
				'<td><a class="delete" title="'+ value.token +'" href="'+ base +'dop/delete/'+ value.token +'">Smazat</td></tr>';
	});
	return '<strong>Vaše vygenerovaná prohlášení (uložená v tomto prohlížeči)</strong><table>'+gethead()+'<tbody>'+rows+'</tbody></table>';
}



function gethead(){
	return '<thead><tr><th>Norma</th><th>Systém</th><th>Datum vytvoření</th><th>Zobrazit</th><th>Upravit</th><th>Smazat</th></tr></thead>';
}

function saveDop(dop){
	localStorage.setItem("dop", JSON.stringify(dop));
}


function isSotred(){
	return (localStorage.getItem("dop") !== undefined && localStorage.getItem("dop") !== "{}" ? true :false);
}



function printDate(d){
	var dateTime = d.split('T'),
	date = dateTime[0].split("-"),
	time = dateTime[1].split(":");
	return date[2]+ '.' + date[1] + '.'+date[0] + ' / ' + time[0] + ':' + time[1];
}


