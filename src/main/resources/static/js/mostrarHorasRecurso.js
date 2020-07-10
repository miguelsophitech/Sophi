$(document).ready(function() {

	$( "#filterRecurso" ).change(function() {
            $.ajax({
                url: "/recursos",
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