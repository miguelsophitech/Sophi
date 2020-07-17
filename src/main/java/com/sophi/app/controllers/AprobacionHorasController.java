package com.sophi.app.controllers;

import java.util.ArrayList;
import java.util.List;

import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.AprobacionHoras;
import com.sophi.app.models.entity.AprobacionHorasDto;
import com.sophi.app.models.service.IAprobacionHorasService;
import com.sophi.app.models.service.IRecursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("aprobacionhoras")
public class AprobacionHorasController {

    @Autowired
    private IAprobacionHorasService aprobacionHorasService;

    @Autowired
    private IRecursoService recursoService;

    @RequestMapping(value = "/aprobacionhoras", method = RequestMethod.GET)
    public String AprobacionHoras(Model model){
    	List<AprobacionHoras> aprobacionhoras = new ArrayList<>();
    	aprobacionHorasService.findAll().iterator().forEachRemaining(aprobacionhoras::add);
        List<Recurso> listaRecursos = new ArrayList<Recurso>();
        listaRecursos = recursoService.findAll();
        model.addAttribute("titulo", "Listado de horas capturadas");
        model.addAttribute("aprobacionhoras", new AprobacionHorasDto(aprobacionhoras));
//        model.addAttribute("aprobacionhoras", aprobacionHorasService.findAll());
        model.addAttribute("recursos", listaRecursos);
        return "aprobacionhoras";
    }
    
    @RequestMapping(value="/guardar", method = RequestMethod.POST)
	public String validarHoras(@ModelAttribute AprobacionHorasDto aprobacionhoras, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
    	System.out.println("Hola Mundo!");
    	
    	for(AprobacionHoras ah : aprobacionhoras.getAprobacionhoras()) {
    		System.out.println(ah.getValDuracionValidada());
    	}

		if(result.hasErrors()) {
			model.addAttribute("titulo", "Listado Horas Capturadas");
			List<Recurso> listaRecursos = new ArrayList<Recurso>();
	        listaRecursos = recursoService.findAll();
	        model.addAttribute("recursos", listaRecursos);
			return "formContacto";
		}
		aprobacionHorasService.saveAll(aprobacionhoras.getAprobacionhoras());
		model.addAttribute("aprobacionhoras", aprobacionHorasService.findAll());
		status.setComplete();
		flash.addFlashAttribute("success", "Horas Validadas");
		return "redirect:/aprobacionhoras";
	}
    
//    @RequestMapping(value = "/aprobacionhoras2", method = RequestMethod.GET)
//    public String AprobacionHoras2(Model model){
//    	List<AprobacionHoras> aprobacionhoras = new ArrayList<>();
//        aprobacionHorasService.findAll().iterator().forEachRemaining(aprobacionhoras::add);
//        List<Recurso> listaRecursos = new ArrayList<Recurso>();
//        listaRecursos = recursoService.findAll();
//        model.addAttribute("titulo", "Listado de horas capturadas");
//        model.addAttribute("aprobacionhoras", new AprobacionHorasDto(aprobacionhoras)); 
//        model.addAttribute("recursos", listaRecursos);
//        return "aprobacionhoras";
//    }

//    @RequestMapping(value = "/cargaHoras", method = RequestMethod.GET)
//	@ResponseBody
//	public String cargaContactos(@RequestParam("id") Long codProyecto, Model model) {
//        List<AprobacionHoras> aprobacionhorasList = null;
//        
//        if(codProyecto == -1) {
//        	aprobacionHorasService.findAll();
//        }
//        
//        else {
//        	aprobacionHorasService.findAprobacionHorasBycodProyecto(codProyecto);
//        }
//        
//		String contenido = "";
//		contenido = contenido + "<div class=\"table-responsive\">"+
//		"<table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">"+
//		"<thead>"+
//		"<tr>"+
//		"<th>Check</th>"+
//        "<th>Fecha</th>"+
//        "<th>Proyecto</th>"+
//        "<th>Colaborador</th>"+
//        "<th>Tarea/Descripcion</th>"+
//		"<th>Horas Capturadas</th>"+
//		"<th>Horas Validadas</th>"+
//        "</tr>"+
//        "</thead>"+
//        "<tfoot>"+
//		"<tr>"+
//		"<th>Check</th>"+
//        "<th>Fecha</th>"+
//        "<th>Proyecto</th>"+
//        "<th>Colaborador</th>"+
//        "<th>Tarea/Descripcion</th>"+
//		"<th>Horas Capturadas</th>"+
//		"<th>Horas Validadas</th>"+
//        "</tr>"+
//        "</tfoot>"+
//		"<tbody>";
//		
//		for(AprobacionHoras aprobacionhoras:aprobacionhorasList){
//            String tablaAprobacionHoras = "";
//            tablaAprobacionHoras = "<tr><td><input type=\"checkbox\" name=\"check\" onClick=\"checkbox();\"/></td>";
//            tablaAprobacionHoras += "<td>"+aprobacionhoras.getFecRegistro()+"</td>";
//            //tablaAprobacionHoras += "<td>"+aprobacionhoras.getCodCliente()+"</td>";
//            tablaAprobacionHoras += "<td>"+aprobacionhoras.getNombreRecurso()+"</td>";
//            tablaAprobacionHoras += "<td>"+aprobacionhoras.getDescComentarioDetalle()+"</td>";
//            tablaAprobacionHoras += "<td><input type=\"number\" name=\"validar\" th:value=\"${aprobacionhoras.getValDuracionValidada()}\" readonly=\"readonly\"/></td></tr>";
//            contenido += tablaAprobacionHoras;
//        }
//		
//		contenido = contenido + "</tbody>"+
//        "</table>"+
//        "</div>";
//		return contenido;
//	}

}
