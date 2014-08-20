(function ( $ ) {
    $.getMessage = function( ) {
        var key = arguments[0];
        try{
            if(typeof Messages[key] !== "undefined"){
                var str =  Messages[key];
                if(arguments.length > 1){
                    for(var i = 1;i < arguments.length; i++){
                        str = $.replaceArgs(str, i, arguments[i]);
                    }
                }
                return str;
            }else{
                if((document.location.hostname == "localhost")){
                    console.warn('MESSAGE NOT FOUND: ['+key+']');
                }
            }
        }catch(e){
           console.warn(e);
        }
        return '';
    };
    $.replaceArgs = function(str, i, val){
            var regex = new RegExp('\\{'+(i-1)+'\\}', "g");
            return str.replace( regex , val);
    };
    
}( jQuery ));