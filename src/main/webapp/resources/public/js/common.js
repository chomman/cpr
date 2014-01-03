$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    console.log(results);
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


function getOption(val, label, isSelected){
	return '<option '+ (isSelected ? ' selected="selected" ' : '') +
			' value="'+val+'">'+label+'</option>';
}