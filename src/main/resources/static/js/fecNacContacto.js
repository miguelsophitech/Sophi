function fecNacContacto(fecNac){
	var date = new Date();

	var day = date.getDate();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	
	var fecha = new Object();
	
	if(month < 10){
		fecha = year.toString().concat("-0", month.toString(), "-", day.toString());
//		console.log(fecha);
//		console.log(`${year}-0${month}-${day}`);
	}else{
		fecha = year.toString().concat("-", month.toString(), "-", day.toString());
//		console.log(fecha);
//		console.log(`${year}-${month}-${day}`);
	}
	
	var edad = year - fecNac.substring(0,4);
	
	document.getElementById("input_fec_nac").max = fecha;
	document.getElementById("input_fec_nac").min = fecNac;
	document.getElementById("input_fec_nac").value = fecNac;
	
	console.log(edad);
	
	if(edad < 18){
		document.getElementById("fec_nac").innerHTML = "";
		document.getElementById("fec_nac").innerHTML = "Fecha de nacimiento menor a 18 años";
	}
	
	if(document.getElementById("input_fec_nac").value === ""){
		edad = "";
		document.getElementById("fec_nac").innerHTML = "";
		document.getElementById("input_fec_nac").max = "";
		document.getElementById("input_fec_nac").min = "";
	}
	
	if(edad >= 90) {
		document.getElementById("fec_nac").innerHTML = "";
		document.getElementById("fec_nac").innerHTML = "Fecha de nacimiento mayor a 90 años";
	}
}