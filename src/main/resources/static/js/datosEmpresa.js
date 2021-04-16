$(document).ready(function() {

	resetFormHerramienta();
	
	$("#listaTodo").show();
	$("#listaLaptops").hide();
	$("#listaTablets").hide();
	
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
	 
	 
	 	
/*
	 	if($(this).val() === "1"){
	 		$("#listaTodo").hide();
			$("#listaLaptops").show();
			$("#listaTablets").hide();
	 	} 
	 	
	 	else if($(this).val() === "2") {
	 		$("#listaTodo").hide();
			$("#listaLaptops").hide();
			$("#listaTablets").show();
	 	} 
	 	
	 	else {
	 		$("#listaTodo").show();
			$("#listaLaptops").hide();
			$("#listaTablets").hide();
	 	}
	 });*/
});

function resetFormHerramienta(){
	$("#codHerramientaRecurso").val("");
	$("#observaciones").val("");
	//$("#responsiva").val("");
	$("#fecPrestamo").val("");
	$("#fecDevolucion").val("");
	$("#validObservaciones").hide();
	$("#validResponsiva").hide();
}

function registraHerramienta(){
//	  var codHerramientaRecurso = $("#codHerramientaRecurso").val();
//	  var codRecurso = $("#codRecurso").val();
//	  var codHerramienta = $("#codHerramienta").val();
//	  var observaciones = $("#observaciones").val();
//	  var responsiva = new FormData($("#upload-file-form")[0]);
//	  var fecPrestamo =  $("#fecPrestamo").val();
//	  var fecDevolucion = $("#fecDevolucion").val();
	  
	  $.ajax({
		    type: "POST",
		    url: "/guardaHerramienta",
//		    data: {codHerramientaRecurso: codHerramientaRecurso,
//		        codHerramienta: codHerramienta,
//		    	observaciones: observaciones, 
//		    	responsiva : responsiva,
//		    	fecPrestamoString: fecPrestamo,
//		    	fecDevolucionString: fecDevolucion,
//		    	codRecurso: codRecurso },
		    data: new FormData($("#upload-file-form")[0]),
		    enctype: 'multipart/form-data',
		    processData: false,
		    contentType: false,
		    cache: false,
			success: function(result){
				var url = "/obtieneHerramienta/?codRecurso="+codRecurso;
				$("#divHerramientas").load(url);
				$("#nuevaHerramientaModal").modal('hide');
				resetFormHerramienta();
		    }
		});
			
  }


function submitFormHerramientas(){
	var $form_herramientas = $('#formularioHerramientas')[0];
	var data = new FormData($form_herramientas);
//	$("#btnAceptarCapacitacion").prop("disabled", true);
	
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: '/formCrearEditarHerramienta',
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function(data) {
//            $("#btnAceptarCapacitacion").prop("disabled", false);
        	var url = "/obtieneHerramienta/?codRecurso="+$("#codRecurso").val();
			$("#divHerramientas").load(url);
            $("#registroHerramientaModal").modal('hide');
//            verTrayectoriaCapacitacion($("#codRecurso").val());
        }
    });
}

function abrirFormHerramienta(){
	var url = "/formCrearEditarHerramienta/?codRecurso=" + $("#codRecurso").val();
	$("#recursoHerramientaModal").load(url,function(){
		$("#registroHerramientaModal").modal('show');
	});
}
  
function editarHerramienta(codHerramientaRecurso){
	var url = "/formEditarHerramienta/?chr=" + codHerramientaRecurso;
	$("#recursoHerramientaModal").load(url,function(){
		$("#registroHerramientaModal").modal('show');
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
