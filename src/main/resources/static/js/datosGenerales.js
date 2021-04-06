$(document).ready(function() {
	  
	 $("#descRecurso").keypress(function() {
		 myFunction(this);
		 }
	 ); 
	 
	 $("#divBtnGuardar").hide();
	 
  });
  
  function hideByGenero(genero){
	  if(genero=="F"){
		  $("#divEmbarazos").show();
	  }else{
		  $("#divEmbarazos").hide();
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