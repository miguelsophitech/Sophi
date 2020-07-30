function fecNacContacto(fecNac){
	var date = new Date();

	var day = date.getDate();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	
	var fecha = new Object();
	
	console.log(fecNac);	
	
	if(month < 10){
		fecha = year.toString().concat("-0", month.toString(), "-", day.toString());
		console.log(fecha);
		console.log(`${year}-0${month}-${day}`);
	}else{
		fecha = year.toString().concat("-", month.toString(), "-", day.toString());
		console.log(fecha);
		console.log(`${year}-${month}-${day}`);
	}
	
	var edad = year - fecNac.substring(0,4);
	
	console.log(edad);
	
	if(edad < 18 || edad > 90){
		console.log("Ingresa una fecha de nacimiento válida");
		document.getElementById("fec_nac").innerHTML = "Fecha de nacimiento invalida";
	}
}