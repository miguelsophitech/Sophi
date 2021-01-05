$(document).ready(function() {
	
	var rangeIsFrom = "";
	var rangeIsTo = "";
	var semanaActual = "";
	var WeekNumber = new Date().getWeekNumber();
	
	console.log(WeekNumber);
	
	if(WeekNumber < 10){
		semanaActual = new Date().getFullYear() + '-W0' +WeekNumber;
	}
	
	else{
		semanaActual = new Date().getFullYear() + '-W' +WeekNumber;
	}
	
	document.getElementById("semana").defaultValue = semanaActual;
	
	$("#proyecto").change(function(){
		$("#recursosHoras").html('<div class="spinner-grow text-muted"></div>');
		var sem = document.getElementById("semana").value.substring(6); 
		var url = getDateRangeOfWeek(sem)+"/"+proyectoSeleccion(this.value);
		$("#recursosHoras").load(url);
	});
	
	$("#semana").change(function(){
		$("#recursosHoras").html('<div class="spinner-grow text-muted"></div>');
		var pro = document.getElementById("proyecto").value; 
		var url = getDateRangeOfWeek(this.value.substring(6))+"/"+proyectoSeleccion(pro);
		$("#recursosHoras").load(url);
	});
	
	datosInicial();
	
	$("span.captura").dblclick(function(){
		alert($(this).innerHTML);
	});
	
});

function totales(){
	var total_lun = 0;
	var total_mar = 0;
	var total_mie = 0;
	var total_jue = 0;
	var total_vie = 0;
	var total_sab = 0;
	var total_dom = 0;
	var total_lunR = 0;
	var total_marR = 0;
	var total_mieR = 0;
	var total_jueR = 0;
	var total_vieR = 0;
	var total_sabR = 0;
	var total_domR = 0;
	var total_lunP = 0;
	var total_marP = 0;
	var total_mieP = 0;
	var total_jueP = 0;
	var total_vieP = 0;
	var total_sabP = 0;
	var total_domP = 0;
	
	var totalSemana = 0;
	var totalSemanaR = 0;
	var totalSemanaP = 0;
	
	$('#dataTableDetalle tbody').find('tr').each(function (i, el) {
		est = $(this).find('td').eq(0).find('div').html();
		if (esRechazada(est)){
			total_lunR += parseFloat($(this).find('td').eq(1).text());
			total_marR += parseFloat($(this).find('td').eq(2).text());
			total_mieR += parseFloat($(this).find('td').eq(3).text());
			total_jueR += parseFloat($(this).find('td').eq(4).text());
			total_vieR += parseFloat($(this).find('td').eq(5).text());
			total_sabR += parseFloat($(this).find('td').eq(6).text());
			total_domR += parseFloat($(this).find('td').eq(7).text());
		} else if (esPendiente(est)) {
			total_lunP += parseFloat($(this).find('td').eq(1).text());
			total_marP += parseFloat($(this).find('td').eq(2).text());
			total_mieP += parseFloat($(this).find('td').eq(3).text());
			total_jueP += parseFloat($(this).find('td').eq(4).text());
			total_vieP += parseFloat($(this).find('td').eq(5).text());
			total_sabP += parseFloat($(this).find('td').eq(6).text());
			total_domP += parseFloat($(this).find('td').eq(7).text());
			} else {
				total_lun += parseFloat($(this).find('td').eq(1).text());
				total_mar += parseFloat($(this).find('td').eq(2).text());
				total_mie += parseFloat($(this).find('td').eq(3).text());
				total_jue += parseFloat($(this).find('td').eq(4).text());
				total_vie += parseFloat($(this).find('td').eq(5).text());
				total_sab += parseFloat($(this).find('td').eq(6).text());
				total_dom += parseFloat($(this).find('td').eq(7).text());
			}
	});
	
	$("#dia1").text((isNaN(total_lun) ? '0': total_lun) + ' hrs')
	$("#dia2").text((isNaN(total_mar) ? '0': total_mar) + ' hrs')
	$("#dia3").text((isNaN(total_mie) ? '0': total_mie) + ' hrs')
	$("#dia4").text((isNaN(total_jue) ? '0': total_jue) + ' hrs')
	$("#dia5").text((isNaN(total_vie) ? '0': total_vie) + ' hrs')
	$("#dia6").text((isNaN(total_sab) ? '0': total_sab) + ' hrs')
	$("#dia7").text((isNaN(total_dom) ? '0': total_dom) + ' hrs')
	
	totalSemana = total_lun + total_mar + total_mie + total_jue + total_vie + total_sab + total_dom;
	totalSemanaR = total_lunR + total_marR + total_mieR + total_jueR + total_vieR + total_sabR + total_domR;
	totalSemanaP = total_lunP + total_marP + total_mieP + total_jueP + total_vieP + total_sabP + total_domP;
	
	$("#totalSemana").text('('+ (isNaN(totalSemana) ? '0': totalSemana) + ' hrs aprobadas, ' + (isNaN(totalSemanaR) ? '0': totalSemanaR)  + ' hrs rechazadas, ' + (isNaN(totalSemanaP) ? '0': totalSemanaP)  + ' hrs pendientes)');
	
}


function esRechazada(est) {
    var personRegExp = new RegExp('danger');
    if (personRegExp.test(est)) {
        return 1;
    } else {
        return 0;
    }
}

function esPendiente(est) {
    var personRegExp = new RegExp('warning');
    if (personRegExp.test(est)) {
        return 1;
    } else {
        return 0;
    }
}

function datosInicial(){
	document.getElementById("semana").disabled = false;
	document.getElementById("proyecto").disabled = false;
	$("#recursosHoras").html('<div class="spinner-grow text-muted"></div>');
	var pro = document.getElementById("proyecto").value; 
	var sem = document.getElementById("semana").value.substring(6); 
	var url = getDateRangeOfWeek(sem) +"/"+proyectoSeleccion(pro);
	$("#recursosHoras").load(url);
}

function aprobar(cod){
	$("#aprA"+cod).html('<i class="spinner-border spinner-border-sm"></i>')
	$("#aprR"+cod).hide();
	var hrAprobada = $("#apr"+cod).text();
	var aprobador = $("#authGetName").val();
	$.ajax({
	    type: "GET",
	    url: "/updAprobacion",
	    data: {codCapHora: cod, 
	    		hrAprobada:hrAprobada,
	    		aprobador: aprobador},
		success: function(result){
	        if(result == 'ok'){
	        	$("#aprA"+cod).html('Aprobada <i class="far fa-check-circle"></i>');
	        	$("#est"+cod).html('<span class="badge badge-success"><i class="far fa-check-circle"></i></span>');
	        	$("#aprR"+cod).show();
	        	totales();
	        } else {
	        	alert("Hubo un problema al actualizar el registro, intente nuevamente.");
	        }
			
	    }
	});
	
}

function rechazar(cod){
	var coment = prompt("Detalle del rechazo:");
	if (coment != null && coment != "") {
		$("#aprR"+cod).html('<i class="spinner-border spinner-border-sm"></i>')
		$("#aprA"+cod).hide();
		var aprobador = $("#authGetName").val();
		$.ajax({
		    type: "GET",
		    url: "/updRechazar",
		    data: {codCapHora: cod, 
		    		aprobador: aprobador,
		    		comentario: coment},
			success: function(result){
		        if(result == 'ok'){
		        	$("#aprR"+cod).html('Rechazado <i class="far fa-times-circle"></i>');
		        	$("#aprA"+cod).show();
		        	$("#est"+cod).html('<span class="badge badge-danger"><i class="far fa-times-circle"></i></span>');
		        	totales();
		        } else {
		        	alert("Hubo un problema al actualizar el registro, intente nuevamente.");
		        }
				
		    }
		});	
		 
	} else {
		alert("Sin detalle, no se puede rechazar.");
	}
	
	
	
}


function detalleCaptura(obj){
	document.getElementById("semana").disabled = true;
	document.getElementById("proyecto").disabled = true;
	
	$("#recursosHoras").html('<div class="spinner-grow text-muted"></div>');
	var codRecurso = $(obj).attr('id');
	var codPr = document.getElementById("proyecto").value; 
	var semana = document.getElementById("semana").value;
	var proyectos="";
	
	if (codPr == '-1'){
		proyectos = codProyecto();
	} else {
		proyectos = codPr;
	}

	var url = "/aprobacionHorasProyecto/detalle/?r="+codRecurso+"&p="+proyectos+"&s="+semana+"&f="+rangeIsFrom+"&t="+rangeIsTo;
	$("#recursosHoras").load(url, function(){
		totales();
    });
	
}

function proyectoSeleccion(val){
	if (val == '-1'){
		return codProyecto();
	} else {
		return val;
	}
}

//obtiene listado de todos los proyectos;
function codProyecto(){
	var codProyectos="";
	$("#proyecto option").each(function(){
		codProyectos = codProyectos + $(this).val() + ",";
	});
	return codProyectos;
}

//Obtiene el numero de la semana actual de inicio
/*function getWeekNr() {
	var now = new Date(), i = 0, f, sem = (new Date(now.getFullYear(), 0, 1) .getDay() > 0) ? 1 : 0;
	while ((f = new Date(now.getFullYear(), 0, ++i)) < now) {
		if (!f.getDay()) {
			sem++;
		}
	}
	return sem;
}*/

Date.prototype.getWeekNumber = function () {
  var d = new Date(+this);  //Creamos un nuevo Date con la fecha de "this".
  d.setHours(0, 0, 0, 0);   //Nos aseguramos de limpiar la hora.
  d.setDate(d.getDate() + 4 - (d.getDay() || 7)); // Recorremos los días para asegurarnos de estar "dentro de la semana"
  //Finalmente, calculamos redondeando y ajustando por la naturaleza de los números en JS:
  return Math.ceil((((d - new Date(d.getFullYear(), 0, 1)) / 8.64e7) + 1) / 7);
};

//Obtiene el numero de la semana seleccionado en la vista
function semanaSeleccion(obj){//2020-W38
	var WeekNo = obj.value.substring(6); 
	getDateRangeOfWeek(WeekNo);
}

//Obtiene rango de fechas de la semana del parametro
function getDateRangeOfWeek(weekNo){
    var d1 = new Date();
    numOfdaysPastSinceLastMonday = eval(d1.getDay()- 1);
    d1.setDate(d1.getDate() - numOfdaysPastSinceLastMonday);
    var weekNoToday = new Date().getWeekNumber();
    var weeksInTheFuture = eval( weekNo - weekNoToday );
    d1.setDate(d1.getDate() + eval( 7 * weeksInTheFuture ));
    rangeIsFrom = eval(d1.getMonth()+1) +"-" + d1.getDate() + "-" + d1.getFullYear();
//    var from = rangeIsFrom.split("-")[2] + "-" + (rangeIsFrom.split("-")[0])-1 + "-" + rangeIsFrom.split("-")[1];
    d1.setDate(d1.getDate() + 6);
    rangeIsTo = eval(d1.getMonth()+1) +"-" + d1.getDate() + "-" + d1.getFullYear() ;
//    var to = rangeIsTo.split("-")[2] + "-" + (rangeIsTo.split("-")[0])-1 + "-" + rangeIsTo.split("-")[1];
 
//    return rangeIsFrom + " to "+rangeIsTo;
    
    var url="/capturaHorasPeriodo/"+rangeIsFrom +"/"+rangeIsTo;
    return url;
//    console.log(url);
//	$("#recursosHoras").load(url);
};


	