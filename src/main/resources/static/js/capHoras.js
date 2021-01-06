$(document).ready(function() {
	var fh = new Date();
	formatoFechaLarga(fh);
	completaSemana(fh);
	semanaInicioFin(fh);

	$("#sigDia").click(function() {
		limpiaActive();
		formatoFechaLarga(sumarDias(fh, 1));
		completaSemana(fh);
		cargaActividadDia();
		semanaInicioFin(fh);
	});

	$("#antDia").click(function() {
		limpiaActive();
		formatoFechaLarga(sumarDias(fh, -1));
		completaSemana(fh);
		cargaActividadDia();
		semanaInicioFin(fh);
	});
	
	$("#selectProyecto").click(function() {
		cargarActividadesPrimariasProyecto();
	});
	
	$("a[id^='da']").click(function(){
		limpiaActive();
		$(this).addClass("active");
		
		cargaActividadDia();
		var fech = $("#semanaDias .active span").text().split("-");
		fh = new Date(+fech[2], fech[1]-1, +fech[0]);
		formatoFechaLarga(fh);
		semanaInicioFin(fh);
	});
	
	$(document).on('click', '.borrar', function (event) {
	    event.preventDefault();
	    $(this).closest('tr').remove();
	});
	
	cargaActividadDia();
	
//	cargarActividadesPrimariasProyecto();
//	filtraActPorFase();
//	altaCapHoraActividad();

});

function semanaInicioFin(fecha){

var curr = new Date(fecha);

var first = curr.getDate() - curr.getDay(); // First day is the day of the month - the day of the week 
var last = first + 6; // last day is the first day + 6 



var firstday = new Date(curr.setDate(first)).toUTCString();
var firstday2 = new Date(firstday);
//var lastday = new Date(curr.setDate(last)).toUTCString(); 
//var lastday2 = new Date(firstday2.setDate(firstday2.getDate() + 6)); 
var lastday =  new Date(firstday2.setDate(firstday2.getDate() + 6)).toUTCString();; 


$.ajax({
    type: "GET",
    url: "/horasTotalSemana",
    data: {codRecurso: $("#valCodRecurso").val() , fechaInicioSemana: firstday, fechaFinSemana: lastday },
	success: function(result){
        $("#dt").html(result);
    }
});

}

function cargaActividadDia(){
	var fech = $("#semanaDias .active span").text();
	var url="/cargarActividadCapturadas/"+$("#valCodRecurso").val()+"/"+fech;
	$("#detalleHorasCapturadas").load(url);
}

function cargarActividadesPrimariasProyecto(){
	var url="/cargarActividadPrimaria/"+$("#valCodRecurso").val()+"/"+$("#selectProyecto").val();
	$("#resultListActividadesPrimarias").load(url);
}

function filtraActPorFase(){
	cargarActividadesSecundariasProyecto();
}

function cargarActividadesSecundariasProyecto(){
	var url="/cargarActividadSecundaria/"+$("#valCodRecurso").val()+"/"+$("#selectProyecto").val()+"/"+encodeURIComponent($("#selectActividadesPrimarias").val());
	$("#resultListActividadesSecundarias").load(url);
}


function altaCapHoraActividad(){
	var fech = $("#semanaDias .active span").text();
	var url="/cargarDetActividad/"+$("#selectActividadesSecundarias").val()+"/"+fech;
	$("#resultDetActividades").load(url);
}

function altaCapHoraActividadNoPlan(){
	var fech = $("#semanaDias .active span").text();
	var url="/cargarDetActividadNoPlan/"+$("#selectActividadesSecundarias").val()+"/"+fech+"/"+$("#valCodRecurso").val();
	$("#resultDetActividades").load(url);
}

function altaCapHoraActividadFuera(){
	var fech = $("#semanaDias .active span").text();
	var url="/cargarDetActividadFuera/"+$("#selectActividadesSecundarias").val()+"/"+fech+"/"+$("#valCodRecurso").val()+"/"+$("#selectProyecto").val();
	$("#resultDetActividades").load(url);
}

function limpiaActive() {
	for (var i = 0; i <= 6; i++) {
		$("#da" + i).removeClass("active");
	}
}

function handleChange(input) {
    if (input.value < 0) input.value = 0;
    if (input.value > 24) input.value = 24;
}

function validaForm(){
	if(!$("#descDetalleHora").val()){
		$("#descDetalleHora").addClass("alert-danger");
		$("#divDescDetalleHora").html("<small class='form-text text-danger'>Este dato es requerido</small>");
	} else if ($("#valHoraCap").val() > 0 && $("#valHoraCap").val() <= 24 && $("#valHoraCap").val().match(/(^\d*\.{0,1}\d{0,1})$/)){
	    $('#capHorasForm').submit();
	    $('#capHoraModal').modal('hide');
	    $('#selectProyecto').val("");
		$('#selectActividadesPrimarias').val("");
		$('#selectActividadesSecundarias').val("");
		$('#descDetalleHora').val("");
		$('#valHoraCap').val("");
	} else {
		$("#descDetalleHora").removeClass("alert-danger");
		$("#divDescDetalleHora").html("");
		
		$("#valHoraCap").addClass("alert-danger");
		$("#divValHoraCap").html("<small class='form-text text-danger'>1-24 hrs</small>");
	}
}

function validaFormEdit(){
	if(!$("#descDetalleHoraEdit").val()){
		$("#descDetalleHoraEdit").addClass("alert-danger");
		$("#divDescDetalleHoraEdit").html("<small class='form-text text-danger'>Este dato es requerido</small>");
	} else if ($("#valHoraCapEdit").val() > 0 && $("#valHoraCapEdit").val() <= 24 && $("#valHoraCapEdit").val().match(/(^\d*\.{0,1}\d{0,1})$/)){
		$('#formEditCapHoraActividad').submit();
		$('#capHoraModalEdit').modal('hide');
		$('#selectProyectoEdit').val("");
		$('#selectActividadSecundariaEdit').val("");
		$('#descDetalleHoraEdit').val("");
		$('#valHoraCapEdit').val("");
	} else {
		$("#descDetalleHoraEdit").removeClass("alert-danger");
		$("#divDescDetalleHoraEdit").html("");
		
		$("#valHoraCapEdit").addClass("alert-danger");
		$("#divValHoraCapEdit").html("<small class='form-text text-danger'>1-24 hrs</small>");
	}
}

function formatoFechaLarga(fecha) {
	var meses = new Array("Enero", "Febrero", "Marzo", "Abril", "Mayo",
			"Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
			"Diciembre");
	var diasSemana = new Array("Domingo", "Lunes", "Martes", "Miércoles",
			"Jueves", "Viernes", "Sábado");
	var nombreFecha = diasSemana[fecha
		.getDay()]
		+ ", "
		+ fecha.getDate()
		+ " de "
		+ meses[fecha.getMonth()]
		+ " de " + fecha.getFullYear();
	document.getElementById("fechaModal").innerHTML = nombreFecha;
	document.getElementById("fechaDiaActual").innerHTML = nombreFecha;
	$("#da" + fecha.getDay()).addClass("active");
}

function sumarDias(fecha, dias) {
	fecha.setDate(fecha.getDate() + dias);
	return fecha;
}

function completaSemana(fechaActual) {
	diasArriba(fechaActual);
	diasAbajo(fechaActual);
}

function diasAbajo(fab) {
	var fecha = new Date(fab);
	for (var j = fab.getDay(); j >= 0; j--) {
		document.getElementById("d" + j).innerHTML = fecha.getDate() + "-"
				+ (fecha.getMonth() + 1) + "-" + fecha.getFullYear();
		fecha = sumarDias(fecha, -1)
	}
}

function diasArriba(far) {
	var fecha = new Date(far);
	for (var i = far.getDay(); i <= 6; i++) {
		document.getElementById("d" + i).innerHTML = fecha.getDate() + "-"
				+ (fecha.getMonth() + 1) + "-" + fecha.getFullYear();
		fecha = sumarDias(fecha, 1)
	}
}


function delCaptura(codCaptura){
	$.ajax({
	    type: "GET",
	    url: "/borrarCapHora",
	    data: {codCaptura: codCaptura},
		success: function(result){
	        console.log(result);
	        semanaInicioFin(fh);
	    }
	});
}


function editCaptura(codCaptura){
	var url="/editCaptura/"+codCaptura;
	$("#formEditCaptura").load(url);
	$('#capHoraModalEdit').modal('show');
	$('#selectProyectoEdit').prop('selected', false);
	$('#selectActividadSecundariaEdit').prop('selected', false);
}

function no_refresh(capHora){	
	console.log(capHora);
	$.ajax({
		type: 'POST',
		url: '/formCapHoraActividad',
		data: capHora,
		success: function(result){
			console.log(result);
			$('.table').html(result);
		}
	});
	return false;
}