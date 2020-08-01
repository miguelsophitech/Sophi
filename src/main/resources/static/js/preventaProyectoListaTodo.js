$(document).ready(function() {

	//cargaTodos();

	$( "#filterCliente" ).change(function() {
		var cliente=$("#filterCliente").val();
		//alert("cliente "+cliente);
		$.ajax({ 
					url: "/cargaProyectosTodo/"+cliente,
        			success: function(res){
        				//alert(res);
        				
        				document.getElementById("contenidoTabla").innerHTML = "";
        				document.getElementById("contenidoTabla").innerHTML = res;
        				 $('#dataTable').DataTable();
        				 //alert("llega hasta aca");
        			}
    	});
	});
});

function cargaTodos() {
  $.ajax({ 
					url: "/cargaProyectosTodo/-1",
        			success: function(res){
        				//alert(res);
        				
        				document.getElementById("contenidoTabla").innerHTML = "";
        				document.getElementById("contenidoTabla").innerHTML = res;
        				$('#dataTable').DataTable();
        				//alert("llega hasta aca");
        			}
    	});
}