$(document).ready(function() {

	$( "#filterProyecto" ).change(function() {
            $.ajax({
                type: "GET",
                url: "/cargaHoras",
                data: {
                    id: $( "#filterProyecto" ).val()
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