function setFocus(element) {
    //var element = document.getElementById(id);
    if (element && element.focus) {
        element.focus();
    }
}

function onlynumber(e){
	var key; 
	if( document.all ){
		key = e.keyCode;
	}else{
		key = e.which;
	}
	var keychar = String.fromCharCode(key);
	switch (keychar){
		case "0" : return true;
		case "1" : return true;
		case "2" : return true;
		case "3" : return true;
		case "4" : return true;
		case "5" : return true;
		case "6" : return true;
		case "7" : return true;
		case "8" : return true;
		case "9" : return true;
		case "\13" : return true;
		case "\008" : return true;
        case "\127" : return true;
		default	: return false;
	}
}