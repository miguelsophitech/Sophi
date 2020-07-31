$(document).ready(function() {
	var tabla = $('#dataTable');
	var codCliente = $('.codCliente');
	var fila = $('tr');
	var filtro = $('#filterCliente');
	
	for(var i=1 ; i<fila.length ; i++){
		fila.eq(i).attr('id', codCliente.eq(i-1).val());
	}
	
	$("#filterCliente").change(function() {
		var valor = $(this).val();
		if(valor != -1){
			$('tr:not(#' + valor + ')', tabla).hide();
			$('tr', tabla).eq(0).show();
			$('tr#' + valor, tabla).show();
		}
			
		else{
			$('tr', tabla).show();
		}
	});
});