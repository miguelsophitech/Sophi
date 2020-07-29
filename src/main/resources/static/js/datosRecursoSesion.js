$(document).ready(function() {
	console.log("entra en layout");
	var url="/datosRecursoLogin/"+$("#authGetName").val();
	console.log(url);
	$("#encabezado").load(url);
});