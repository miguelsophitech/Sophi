$(document).ready(function() {
	console.log("entra en layout");
	var url="/datosRecursoLogin/"+$("#authGetName").val();
	$("#encabezado").load(url);
	
	var url="/datosOpcionesRecursoLogin/"+$("#authGetName").val();
	$("#opciones").load(url);
});