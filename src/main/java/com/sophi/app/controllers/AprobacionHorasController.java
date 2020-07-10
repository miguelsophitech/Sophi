package com.sophi.app.controllers;

import java.util.ArrayList;
import java.util.List;

import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.AprobacionHoras;
import com.sophi.app.models.service.IAprobacionHorasService;
import com.sophi.app.models.service.IRecursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("aprobacionhoras")
public class AprobacionHorasController {

    @Autowired
    private IAprobacionHorasService aprobacionHorasService;

    @Autowired
    private IRecursoService recursoService;

    @RequestMapping(value = "aprobacionhoras", method = RequestMethod.GET)
    public String AprobacionHoras(Model model){
        List<Recurso> listaRecursos = new ArrayList<Recurso>();
        listaRecursos = recursoService.findAll();
        model.addAttribute("titulo", "Listado de horas capturadas");
        model.addAttribute("aprobacionhoras", aprobacionHorasService.findAll());
        model.addAttribute("recursos", listaRecursos);
        return "aprobacionhoras";
    }

    @RequestMapping(value = "/aprobacionHoras", method = RequestMethod.GET)
	@ResponseBody
	public String cargaContactos(@RequestParam("id") Long codProyecto, Model model) {
        List<AprobacionHoras> aprobacionhorasList = null;
        
        if(codProyecto == -1) {
        	aprobacionHorasService.findAll();
        }
        
        else {
        	aprobacionHorasService.findAprobacionHorasBycodProyecto(codProyecto);
        }
        
		String contenido = "";
		contenido = contenido + "<div class=\"table-responsive\">"+
		"<table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">"+
		"<thead>"+
		"<tr>"+
		"<th>Check</th>"+
        "<th>Fecha</th>"+
        "<th>Proyecto</th>"+
        "<th>Colaborador</th>"+
        "<th>Tarea/Descripcion</th>"+
		"<th>Horas Capturadas</th>"+
		"<th>Horas Validadas</th>"+
        "</tr>"+
        "</thead>"+
        "<tfoot>"+
		"<tr>"+
		"<th>Check</th>"+
        "<th>Fecha</th>"+
        "<th>Proyecto</th>"+
        "<th>Colaborador</th>"+
        "<th>Tarea/Descripcion</th>"+
		"<th>Horas Capturadas</th>"+
		"<th>Horas Validadas</th>"+
        "</tr>"+
        "</tfoot>"+
		"<tbody>";
		
		for(AprobacionHoras aprobacionhoras:aprobacionhorasList){
            String tablaAprobacionHoras = "";
            tablaAprobacionHoras = "<tr><td><input type=\"checkbox\" id=\"check\" onChange=\"checkbox(this);\"/></td>";
            tablaAprobacionHoras += "<td>"+aprobacionhoras.getFecRegistro()+"</td>";
            tablaAprobacionHoras += "<td>"+aprobacionhoras.getCodCliente()+"</td>";
            tablaAprobacionHoras += "<td>"+aprobacionhoras.getCodRecurso()+"</td>";
            tablaAprobacionHoras += "<td>"+aprobacionhoras.getDescComentarioDetalle()+"</td>";
            tablaAprobacionHoras += "<td><input type=\"number\" id=\"horasvalidadas\" readonly/></td></tr>";
            contenido += tablaAprobacionHoras;
        }
		
		contenido = contenido + "</tbody>"+
        "</table>"+
        "</div>";
		return contenido;
	}

}
