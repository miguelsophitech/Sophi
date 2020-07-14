package com.sophi.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.AprobacionHoras;
import com.sophi.app.models.service.IAprobacionHorasService;
import com.sophi.app.models.service.IRecursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            tablaAprobacionHoras = "<tr><td><input type=\"checkbox\" name=\"check\" onClick=\"checkbox();\"/></td>";
            tablaAprobacionHoras += "<td>"+aprobacionhoras.getFecRegistro()+"</td>";
            tablaAprobacionHoras += "<td>"+aprobacionhoras.getCodCliente()+"</td>";
            tablaAprobacionHoras += "<td>"+aprobacionhoras.getNombreRecurso()+"</td>";
            tablaAprobacionHoras += "<td>"+aprobacionhoras.getDescComentarioDetalle()+"</td>";
            tablaAprobacionHoras += "<td><input type=\"number\" name=\"validar\" th:value=\"${aprobacionhoras.getValDuracionValidada()}\" readonly=\"readonly\"/></td></tr>";
            contenido += tablaAprobacionHoras;
        }
		
		contenido = contenido + "</tbody>"+
        "</table>"+
        "</div>";
		return contenido;
	}
    
//    @RequestMapping(value = "/aprobacionhoras/{id}")
//    public String ValidarHoras(@PathVariable(value = "id") Long codActividad, Map<String, Object> model, RedirectAttributes flash) {
//    	AprobacionHoras aprobacionhoras = null;
//    	if(codActividad > 0) {
//    		aprobacionhoras = aprobacionHorasService.findAprobacionHorasBycodActividad(codActividad);
//    		if(aprobacionhoras == null) {
//    			flash.addFlashAttribute("error", "El codigo de la actividad no existe en base de datos!");
//    			return "redirect:/aprobacionhoras";
//    		}
//    	}
//    }

}
