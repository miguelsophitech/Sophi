function checkbox() {

	var input = document.querySelectorAll("#validar");
	console.log(input.length);
	
	for(var i=0 ; i<document.getElementsByName('check').length ; i++){
		if(document.getElementsByName('check')[i].checked){
	        input[i].disabled = false;
	    }
	
	    else{
	        input[i].disabled = true;
	    }
	}
	
}