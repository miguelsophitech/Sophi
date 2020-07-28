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
var lastday = new Date(curr.setDate(last)).toUTCString(); 

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

function limpiaActive() {
	for (var i = 0; i <= 6; i++) {
		$("#da" + i).removeClass("active");
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



