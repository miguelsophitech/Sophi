function eliminar(id) {
	$("#codigoCliente").val(id);
	$("#eliminarRegistro").modal("show");
}

function eliminarRegistro(){
	$.ajax({
		type: "GET",
		url:"/listaClientes/"+$("#codigoCliente").val(),
		success: function(res){
			console.log(res);
			if(res == "1"){
				location.reload();
			}
			$("#eliminarRegistro").modal("hide");
		}
	})
}