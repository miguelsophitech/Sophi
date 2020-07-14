function checkbox() {
	
	for(var i=0 ; i<document.getElementsByName('check').length ; i++){
		if(document.getElementsByName('check')[i].checked){
	        document.getElementsByName('validar')[i].readOnly = false;
	    }
	
	    else{
	        document.getElementsByName('validar')[i].readOnly = true;
	    }
	}
	
}