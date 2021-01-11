$(document).ready(function() {
  	$("#vactivo").click(function() {
  		if($(this).is(":checked")) {
  			$("#result").val(1);
  			console.log($("#result").val());
  		}
        else if($(this).is(":not(:checked)")) {
        	$("#result").val(0);
        	console.log($("#result").val());
        }
     });
     
     if($("#result").val() == 1){
     	 $("#vactivo").attr("checked", true);
     }
     else{
     	$("#vactivo").attr("checked", false);
     }
});