package com.sophi.app.controllers;

import java.util.ArrayList;
import java.util.List;

import com.sophi.app.models.entity.Proyecto;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.service.IAprobacionGastosService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("aprobaciongastos")
public class AprobacionGastosController {

    @Autowired
    private IAprobacionGastosService aprobaciongastosService;

    @Autowired
    private IRecursoService recursoService;
    
    @Autowired
    private IProyectoService proyectoService;

    @RequestMapping(value = "aprobaciongastos", method = RequestMethod.GET)
    public String AprobacionGastos(Model model) {
    	List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
        List<Recurso> listaRecursos = new ArrayList<Recurso>();
        listaProyectos = proyectoService.findAll();
        listaRecursos = recursoService.findAll();
        model.addAttribute("titulo", "Listado de gastos capturados");
        model.addAttribute("aprobaciongastos", aprobaciongastosService.findAll());
        model.addAttribute("recursos", listaRecursos);
        model.addAttribute("proyectos", listaProyectos);
        return "aprobaciongastos";
    }
    
//  @RequestMapping(value = "/cargaGastos", method = RequestMethod.GET)
//	@ResponseBody
//	public String cargaContactos(@RequestParam("id") Long codProyecto, Model model) {
//      List<AprobacionGastos> aprobaciongastosList = null;
//      
//      if(codProyecto == -1) {
//      	aprobacionGastosService.findAll();
//      }
//      
//      else {
//      	aprobacionGastosService.findAprobacionGastosBycodProyecto(codProyecto);
//      }
//      
//		String contenido = "";
//		contenido = contenido + "<div class=\"table-responsive\">"+
//		"<table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">"+
//		"<thead>"+
//		"<tr>"+
//		"<th></th>"+
//      "<th>Tipo Gastos</th>"+
//      "<th>Fecha</th>"+
//      "<th>Colaborador</th>"+
//      "<th>Descripci&oacute;n</th>"+
//		"<th>Importe</th>"+
//      "</tr>"+
//      "</thead>"+
//      "<tfoot>"+
//		"<tr>"+
//		"<th></th>"+
//  	"<th>Tipo Gastos</th>"+
//  	"<th>Fecha</th>"+
//  	"<th>Colaborador</th>"+
//  	"<th>Descripci&oacute;n</th>"+
//		"<th>Importe</th>"+
//      "</tr>"+
//      "</tfoot>"+
//		"<tbody>";
//		
//		for(AprobacionGastos aprobaciongastos : aprobaciongastosList){
//          String tablaAprobacionGastos = "";
//          tablaAprobacionGastos = "<td><input type=\"checkbox\" name=\"check\" onClick=\"checkbox();\"/></td>";
//          tablaAprobacionGastos += "<td>"+aprobaciongastos.tipogasto.getDescTipoGasto()+"</td>";
//          tablaAprobacionGastos += "<td>"+aprobaciongastos.fecRegistro+"</td>";
//          tablaAprobacionGastos += "<td>"+aprobaciongastos.recurso.getDescRecurso()+"</td>";
//          tablaAprobacionGastos += "<td>"+aprobaciongastos.descComentario+"</td>";
//          tablaAprobacionGastos += "<td>"+aprobaciongastos.impGasto+"</td>";
//          contenido += tablaAprobacionGastos;
//      }
//		
//		contenido = contenido + "</tbody>"+
//      "</table>"+
//      "</div>";
//		return contenido;
//	}
    
}