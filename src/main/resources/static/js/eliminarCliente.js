var borrar_cliente;

function eliminar(id) {
	borrar_cliente = id;
	$("#eliminarRegistro").modal("show");
}

function eliminarRegistro(){
	$.ajax({
		type: "GET",
		url:"/listaClientes/"+borrar_cliente,
		success: function(){
			location.reload();
			$("#eliminarRegistro").modal("hide");
		}
	})
}