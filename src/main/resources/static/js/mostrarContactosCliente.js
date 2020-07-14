$(document).ready(function() {

	$( "#filterCliente" ).change(function() {
            $.ajax({
                type: "GET",
                url: "/cargaContactos",
                data: {
                    id: $( "#filterCliente" ).val()
                },
                success: function(res){
                    //alert(res);
                    document.getElementById("contenidoTabla").innerHTML = "";
                    document.getElementById("contenidoTabla").innerHTML = res;
                    $('#dataTable').DataTable();
                    //alert("llega hasta acá");
            }
        });
	});
});