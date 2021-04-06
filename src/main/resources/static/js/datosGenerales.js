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