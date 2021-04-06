$(document).ready(function() {
	  
	 $("#descRecurso").keypress(function() {
		 myFunction(this);
		 }
	 ); 
	 
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
	 
  });

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
  
  //Envia fommulario para guardar/actualizar informacion
  function functionGuardar(){
	  
	  var $form = $('#formDatosGenerales');
	  $.ajax({
        method: "POST",
        url: $form.attr('action'),
        data: $form.serialize(),
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
	  console.log(valor);
	  if(valor==='Si'){
		  $("#divAlergias").show();
	  }else{
		  $("#divAlergias").hide();
	  }
  }
  
  function hideByEnfermedades(valor){
	  console.log(valor);
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
	
function functionEditar() {
	$("#divBtnEditar").hide();
	$("#divBtnGuardar").show();
	$('input').prop("disabled", false);
	$('select').prop("disabled", false);
}

function functionGuardar() {
	$("#divBtnGuardar").hide();
	$("#divBtnEditar").show();
	$('input').prop("disabled", true);
	$('select').prop("disabled", true);
	$('#iEditar').prop("disabled", false);
	$('#idatosEmpresa').submit();
}