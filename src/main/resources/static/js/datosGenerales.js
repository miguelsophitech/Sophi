$(document).ready(function() {
	  
	 $("#descRecurso").keypress(function() {
		 myFunction(this);
		 }
	 ); 
	 
	 resetFormEscolaridad();
	 resetFormContactoEmergencia();
	 
	//funcion que oculta dtalle de enfermedades/alergias/Embarazos a partir de opciones
	 validaEnfermedadAlergias();
	 
	 //Si cambia el valor del select hace validacion para mostrar detalle de alergias
	 $("#selectAlergias").change(function() {
		 hideByAlergias(this.value);
		});
	 
	//Si cambia el valor del select hace validacion para mostrar detalle de enfermedades
	 $("#selectEnfermedades").change(function() {
		 hideByEnfermedades(this.value);
		});
	 
	 //Oculta boton de guardar inicialmente
	 $("#divBtnGuardar").hide();
	 
	 //Deshabilita todos los elementos para editar
	 $(".dg-info").attr('disabled','disabled');
	 
	 $("#iNuevaHerramienta").click(function(){
	 	$("#nuevaHerrmientaModal").show();
	 });
	 
  });


  //funcion que resetea el formulario de escolaridad
  function resetFormEscolaridad(){
	$("#institucionAcademica").val("");
	$("#validInstitucionAcademica").hide();
  }
  
  function resetFormContactoEmergencia(){
	$("#nombreContactoEmergencia").val("");
	$("#telefonoContactoEmergencia").val("");
	$("#validNombreContactoEmergencia").hide();
	$("#validTelefonoContactoEmergencia").hide();
	$("#esDependiente").prop( "checked", false );
  }
  
  
  function registraContactoEmergencia(){
	  var codRecurso = $("#codRecurso").val();
	  var nombreContacto = $("#nombreContactoEmergencia").val();
	  var telefonoContacto =  $("#telefonoContactoEmergencia").val(); 
	  var parentescoContacto = $("#parentescoContactoEmergencia").val();
	  var esDependiente = 0;
	  
	  if( $('#esDependiente').prop('checked') ) {
		  esDependiente = 1;
		} else {
			esDependiente = 0;
		}
	  
	  if(nombreContacto.length === 0){
		  $("#validNombreContactoEmergencia").show();
	  } else if (telefonoContacto.length === 0){
		  $("#validTelefonoContactoEmergencia").show();
	  } else {
		  
		  $.ajax({
			    type: "GET",
			    url: "/guardaContactoEmergencia",
			    data: {nc: nombreContacto, 
			    	tc: telefonoContacto,
			    	pc: parentescoContacto,
			    	ed: esDependiente,
			    	cr: codRecurso },
				success: function(result){
					var url = "/obtieneContactosEmergencia/?codRecurso="+codRecurso;
					$("#divContactoEmergencia").load(url);
					$("#nuevaContactoEmergenciaModal").modal('hide');
					resetFormContactoEmergencia();
			    }
			});	
	  }
			
  }
  

  //funcion que registra la escolaridad
  function registraEscolaridad(){
	  var instAcademica = $("#institucionAcademica").val();
	  var gradoEscolar = $("#gradoEscolar").val();
	  var etapaEscolar = $("#etapaEscolar").val();
	  var codRecurso = $("#codRecurso").val();
	  var cedProf = $("#cedulaProfesional").val();
	  
	  
	  if(instAcademica.length === 0){
		  $("#validInstitucionAcademica").show();
	  } else {
		  
		  $.ajax({
			    type: "GET",
			    url: "/guardaEscolaridad",
			    data: {cr: codRecurso, 
			    	ia: instAcademica,
			    	ge: gradoEscolar,
			    	ee: etapaEscolar,
			    	cp: cedProf },
				success: function(result){
					var url = "/obtieneEscolaridad/?codRecurso="+codRecurso;
					$("#divEscolaridad").load(url);
					$("#nuevaEscolaridadModal").modal('hide');
					resetFormEscolaridad();
			    }
			});	
	  }
	  
  }

  //funcion que oculta dtalle de enfermedades/alergias/Embarazos a partir de opciones
  function validaEnfermedadAlergias(){
	  hideByAlergias($("#selectAlergias").val());
	  hideByEnfermedades($("#selectEnfermedades").val());
	  hideByGenero($("#selectGenero").val());
  }

  //funcion que habilita los elementos para editar informacion
  function functionEditar(){
	  $("#divBtnEditar").hide();
	  $("#divBtnGuardar").show();
	  $(".dg-edit").removeAttr('disabled');
	  $(".dg-edit").removeClass("dg-info").addClass("dg-info-edit");
  }
  
  
  //funcion que borra un registro de escolaridad
  function borrarEscolaridad(codRegEscolaridad){
	  var codRecurso = $("#codRecurso").val();
	  $.ajax({
		    type: "GET",
		    url: "/borrarEscolaridad",
		    data: {cre: codRegEscolaridad },
			success: function(result){
				var url = "/obtieneEscolaridad/?codRecurso="+codRecurso;
				$("#divEscolaridad").load(url);
		    }
		});	
  }
  
  //Envia fommulario para guardar/actualizar informacion
  function functionGuardar(){
	  
	  var $form_datos_generales = $('#formDatosGenerales');
	  $.ajax({
        method: "POST",
        url: $form_datos_generales.attr('action'),
        data: $form_datos_generales.serialize(),
        success: function(status){
            console.log(status);
        }
	  });
	  
	  var $form_datos_empresa = $('#formDatosEmpresa');
	  $.ajax({
        method: "POST",
        url: $form_datos_empresa.attr('action'),
        data: $form_datos_empresa.serialize(),
        success: function(status){
            console.log(status);
        }
	  });

	  $("#divBtnEditar").show();
	  $("#divBtnGuardar").hide();
	  $(".dg-edit").attr('disabled','disabled');
	  $(".dg-edit").removeClass("dg-info-edit").addClass("dg-info");
  }
  
  function hideByGenero(genero){
	  if(genero=="F"){
		  $("#divEmbarazos").show();
	  }else{
		  $("#divEmbarazos").hide();
	  }
  }
  
  function hideByAlergias(valor){
	  if(valor==='Si'){
		  $("#divAlergias").show();
	  }else{
		  $("#divAlergias").hide();
	  }
  }
  
  function hideByEnfermedades(valor){
	  if(valor==='Si'){
		  $("#divEnfermedades").show();
	  }else{
		  $("#divEnfermedades").hide();
	  }
  }
  
  function myFunction(campo) {
	  console.log("escribe");
	  campo.style.backgroundColor = "red";
	}