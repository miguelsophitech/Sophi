function fecregact(){
	var fec_registro = document.getElementById("fecreg").value;
	var fec_actualizacion = document.getElementById("fecact").value;
	
	console.log(fec_registro);
	console.log(fec_actualizacion);
	
	if(fec_registro == null && fec_actualizacion == null){
		document.getElementById("fecreg").style.display = "block";
		document.getElementById("fecact").style.display = "none";
		document.getElementById("Nombre_ant").value = "";
	}
	
	if(fec_registro != null && fec_actualizacion == null){
		document.getElementById("fecreg").style.display = "none";
		document.getElementById("fecact").style.display = "block";
		document.getElementById("Nombre_ant").value = document.getElementById("Nombre").value;
	}
	
	if(fec_registro != null && fec_actualizacion != null){
		document.getElementById("fecreg").style.display = "none";
		document.getElementById("fecact").style.display = "block";
		document.getElementById("Nombre_ant").value = document.getElementById("Nombre").value;
	}
}

function codigo_cliente(){
	var codigo_cliente = document.querySelectorAll("codigocliente");
	var nombre_cliente = document.getElementById("Nombre").value;
	codigo_cliente.value = new Date().getFullYear();
	
	resultado = nombre_cliente.concat(codigo_cliente.value);
	
	if(nombre_cliente != ""){
		document.getElementById("codigocliente").value = resultado;
	}
	
	else{
		document.getElementById("codigocliente").value = "";
	}
	
	console.log(resultado);
}