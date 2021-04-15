$(document).ready(function() {
	resetFormHerramienta();
	
	$("#iNuevaHerramienta").click(function(){
	 	$("#nuevaHerramientaModal").show();
	 	resetFormHerramienta();
	 });
	 
	 if($("#valActivo").val() == 1){
	 		$("#fecSalida").hide();
	 		$("#motivo").hide();
	 	} else {
	 		$("#fecSalida").show();
	 		$("#motivo").show();
	 	}
	 
	 $("#valActivo").click(function(){
	 	if($(this).val() == 1){
	 		$("#fecSalida").hide();
	 		$("#motivo").hide();
	 	} else {
	 		$("#fecSalida").show();
	 		$("#motivo").show();
	 	}
	 });
	 
	 $("#codTipoHerramienta").change(function(){
	 	console.log("cambiando de tipo");
	 	$("#validCodTipoHerramienta").hide();
	 	var codTipoHerramienta = $(this).val();
	 	var url = "/cargaHerramientas/?cth=" + codTipoHerramienta;
	 	$("#divSelectHerramientas").load(url);
	 });
	 
	 $("#codHerramienta").change(function(){
	 	$("#validCodHerramienta").hide();
	 });
});

function resetFormHerramienta(){
	$("#codHerramientaRecurso").val("");
	$("#observaciones").val("");
	//$("#responsiva").val("");
	$("#fecPrestamo").val("");
	$("#fecDevolucion").val("");
	$("#validObservaciones").hide();
	$("#validResponsiva").hide();
	$("#validCodHerramienta").hide();
	$("#validCodTipoHerramienta").hide();
}

function registraHerramienta(){
	  var codHerramientaRecurso = $("#codHerramientaRecurso").val();
	  var codRecurso = $("#codRecurso").val();
	  var codHerramienta = $("#codHerramienta").val();
	  var observaciones = $("#observaciones").val();
	  //var responsiva = document.getElementById('responsiva').files;
	  var fecPrestamo =  $("#fecPrestamo").val();
	  var fecDevolucion = $("#fecDevolucion").val();
	  
	  if($("#codTipoHerramienta").val() <= 0){
	  	$("#validCodTipoHerramienta").show();
	  }
	  
	  if($("#codHerramienta").val() <= 0){
	  	$("#validCodHerramienta").show();
	  }
	  
	  else {
	  	$("#validCodHerramienta").hide();
	  	$.ajax({
		    type: "GET",
		    url: "/guardaHerramienta",
		    data: {codHerramientaRecurso: codHerramientaRecurso,
		        codHerramienta: codHerramienta,
		    	observaciones: observaciones, 
		    	//responsiva: responsiva,
		    	fecPrestamoString: fecPrestamo,
		    	fecDevolucionString: fecDevolucion,
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
  
  function editarHerramienta(codHerramientaRecurso){
	  $.ajax({
		    type: "GET",
		    url: "/obtieneHerramientaUnico",
		    data: {chr: codHerramientaRecurso},
			success: function(herramienta){
				var codHerramientaRecurso = encodeURIComponent(herramienta[0]); //codHerramientaRecurso
				var codHerramienta = herramienta[1]; //codHerramienta
				var observaciones = herramienta[2]; //Observaciones
				//var responsiva = herramienta[3]; //responsiva
				var fecPrestamo = herramienta[3]; //fecPrestamo
				var fecDevolucion = herramienta[4]; //fecDevolucion
				
				console.log(codHerramienta);
				
				$("#codHerramienta").val(codHerramienta);
				$("#codHerramientaRecurso").val(codHerramientaRecurso);
				$("#codHerramienta").val(codHerramienta);
				$("#observaciones").val(observaciones);
				//$("#responsiva").val(responsiva); 
				$("#fecPrestamo").val(fecPrestamo);
				$("#fecDevolucion").val(fecDevolucion);
				$("#nuevaHerramientaModal").modal('show');
				
		    },
	         error: function (herramienta) {
	            console.log("Algún dato viene nulo");
	        }
		});
  }
  
  /*function filtroHerramientaTodo(){
  	var url = "/filtroHerramienta/"+$("#codTipoHerramienta").val();
  	$("#listaTodo").load(url);
  }
  
  function filtroHerramientaLaptops(){
  	var url = "/filtroHerramienta/"+$("#codTipoHerramienta").val();
  	$("#listaLaptops").load(url);
  }
  
  function filtroHerramientaTablets(){
  	var url = "/filtroHerramienta/"+$("#codTipoHerramienta").val();
  	$("#listaLaptops").load(url);
  }*/
  
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