$(document).ready(function() {
	resetFormHerramienta();
	
	$("#iNuevaHerramienta").click(function(){
	 	$("#nuevaHerrmientaModal").show();
	 });
});

function resetFormHerramienta(){
	$("#codHerramienta").val("");
	$("#observaciones").val();
	$("#responsiva").val();
	$("#fecPrestamo").val();
	$("#fecDevolucion").val();
	$("#validObservaciones").hide();
	$("#validResponsiva").hide();
}

function registraHerramienta(){
	  var codRecurso = $("#codRecurso").val();
	  var codHerramienta = $("#codHerramienta").val();
	  var observaciones = $("#observaciones").val();
	  var responsiva = $("#responsiva").val();
	  var fecPrestamo =  $("#fecPrestamo").val(); 
	  var fecDevolucion = $("#fecDevolucion").val();
	  
	  if(observaciones.length === 0){
		  $("#validObservaciones").show();
	  } else if (responsiva.length === 0){
		  $("#validResponsiva").show();
	  } else {
		  
		  $.ajax({
			    type: "GET",
			    url: "/guardaHerramienta",
			    data: {codHerramienta: codHerramienta,
			    	observaciones: observaciones, 
			    	responsiva: responsiva,
			    	fecPrestamo: fecPrestamo,
			    	fecDevolucion: fecDevolucion,
			    	codRecurso: codRecurso },
				success: function(result){
					var url = "/obtieneHerramienta/?codRecurso="+codRecurso;
					$("#divHerramienta").load(url);
					$("#nuevaHerramientaModal").modal('hide');
					resetFormHerramienta();
			    }
			});	
	  }
			
  }
  
  function editarHerramienta(codHerramienta){ 
	  $.ajax({
		    type: "GET",
		    url: "/obtieneHerramientaUnico",
		    data: {h: codHerramienta},
			success: function(herramienta){
				var codHerramienta = encodeURIComponent(herramienta[0]); //codHerramienta
				var observaciones = herramienta[1]; //Observaciones
				var responsiva = encodeURIComponent(herramienta[2]); //responsiva
				var fecPrestamo = herramienta[3]; //fecPrestamo
				var fecDevolucion = herramienta[4]; //fecDevolucion
				
				
				$("#codHerramienta").val(codHerramienta);
				$("#observaciones").val(observaciones);
				$("#responsiva").val(responsiva); 
				$("#fecPrestamo").val(fecPrestamo);
				$("#fecDevolucion").val(fecDevolucion);
				$("#nuevaHerramientaModal").modal('show');
		    }
		});
  }