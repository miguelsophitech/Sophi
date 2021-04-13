$(document).ready(function() {
	
	verTrayectoriaProyecto($("#codRecurso").val());
	 
});

function openModalRegistrarTrayectoriaProyecto(){
	console.log("entra a registrar")
	var codRecurso = $("#codRecurso").val();
	var url = "/formTrayectoriaProyecto/?codRecurso=" + codRecurso;
	$("#recursoTrayectoriaProyectoModal").load(url, function() {
		$("#registroProyectoModal").modal('show');
	});
}


function editarTrayectoriaProyecto(cod){
	var url = "/editTrayectoriaProyecto/?rtp=" + cod;
	$("#recursoTrayectoriaProyectoModal").load(url, function() {
		$("#registroProyectoModal").modal('show');
	});
}

function verTrayectoriaProyecto(codRecurso){
	var url = "/verTrayectoriaProyecto/?codRecurso=" + codRecurso;
	$("#cardTrayectoriaProyecto").load(url);
}


function enviaDatos(){
	 var $form_trayectoria_proyectos = $('#formularioTrayectoriaProyecto');
	  $.ajax({
       method: "POST",
       url: $form_trayectoria_proyectos.attr('action'),
       data: $form_trayectoria_proyectos.serialize(),
       success: function(status){
           console.log(status);
           $("#registroProyectoModal").modal('hide');
           verTrayectoriaProyecto($("#codRecurso").val());
       }
	  });
}

function borrarTrayectoriaProyecto(cod){
	  $.ajax({
		    type: "GET",
		    url: "/borrarTrayectoriaProyecto",
		    data: {rtp: cod },
			success: function(status){
				console.log(status);
				verTrayectoriaProyecto($("#codRecurso").val());
		    }
		});	
}