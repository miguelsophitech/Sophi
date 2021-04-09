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
	  var responsiva = document.getElementById('responsiva').files;
	  var fecPrestamo =  $("#fecPrestamo").val();
	  var fecDevolucion = $("#fecDevolucion").val();
	  
	  console.log(typeof(responsiva));
	  
	  if(observaciones.length === 0){
		  $("#validObservaciones").show();
	  } /*else if (responsiva !== null){
		  $("#validResponsiva").show();
	  }*/ else {
		  
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
					$("#divHerramientas").load(url);
					$("#nuevaHerramientaModal").modal('hide');
					resetFormHerramienta();
			    }
			});	
	  }
			
  }
  
  function editarHerramienta(codHerramienta){
  		console.log(codHerramienta);
	  $.ajax({
		    type: "GET",
		    url: "/obtieneHerramientaUnico",
		    data: {h: codHerramienta},
			success: function(herramienta){
				var codHerramienta = encodeURIComponent(herramienta[0]); //codHerramienta
				var observaciones = herramienta[1]; //Observaciones
				var responsiva = herramienta[2]; //responsiva
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
  
  function borrarHerramienta(codHerramienta){
	  var codRecurso = $("#codRecurso").val();
	  $.ajax({
		    type: "GET",
		    url: "/borrarHerramienta",
		    data: {ch: codHerramienta },
			success: function(result){
				var url = "/obtieneHerramienta/?codRecurso="+codRecurso;
				$("#divHerramientas").load(url);
		    }
		});	
  }