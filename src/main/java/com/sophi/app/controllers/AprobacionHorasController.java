package com.sophi.app.controllers;

import java.util.ArrayList;
import java.util.List;

//import javax.validation.Valid;

import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.AprobacionHoras;
import com.sophi.app.models.entity.AprobacionHorasDto;
import com.sophi.app.models.entity.Proyecto;
import com.sophi.app.models.service.IAprobacionHorasService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("aprobacionhoraslista")
public class AprobacionHorasController {

    @Autowired
    private IAprobacionHorasService aprobacionHorasService;

    @Autowired
    private IRecursoService recursoService;
    
    @Autowired
    private IProyectoService proyectoService;

    @RequestMapping(value = "/aprobacionhoras", method = RequestMethod.GET)
    public String AprobacionHoras(Model model){
    	List<AprobacionHoras> aprobacioneshoras = new ArrayList<>();
        List<Recurso> listaRecursos = new ArrayList<Recurso>();
        List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
        aprobacionHorasService.findAll().iterator().forEachRemaining(aprobacioneshoras::add);
        listaRecursos = recursoService.findAll();
        listaProyectos = proyectoService.findAll();
        AprobacionHorasDto aphdto = new AprobacionHorasDto();
        aphdto.setAprobacionhoras(aprobacioneshoras);
        model.addAttribute("titulo", "Listado de horas capturadas");
        //model.addAttribute("aprobacionhoras", aprobacionHorasService.findAll());
        model.addAttribute("aprobacionhoraslista", aphdto);
        model.addAttribute("recursos", listaRecursos);
        model.addAttribute("proyectos", listaProyectos);
        return "aprobacionhoras";
    }
    
    @RequestMapping(value="/guardar", method = RequestMethod.POST)
	public String validarHoras(@ModelAttribute("aprobacionhoraslista") AprobacionHorasDto aprobacionhoraslista, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {

		if(result.hasErrors()) {
			model.addAttribute("titulo", "Listado Horas Capturadas");
			List<Recurso> listaRecursos = new ArrayList<Recurso>();
	        listaRecursos = recursoService.findAll();
	        model.addAttribute("recursos", listaRecursos);
			return "aprobacionhoras";
		}
		aprobacionHorasService.saveAll(aprobacionhoraslista.getAprobacionhoras());
		status.setComplete();
		flash.addFlashAttribute("success", "Horas Validadas");
		return "redirect:/aprobacionhoras";
	}

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
//		"<th></th>"+
//        "<th>Fecha</th>"+
//        "<th>Colaborador</th>"+
//        "<th>Comentario</th>"+
//        "<th>Horas Planeadas</th>"+
//		"<th>Horas Capturadas</th>"+
//		"<th>Horas Validadas</th>"+
//        "</tr>"+
//        "</thead>"+
//        "<tfoot>"+
//		"<tr>"+
//		"<th></th>"+
//        "<th>Fecha</th>"+
//        "<th>Colaborador</th>"+
//        "<th>Comentario</th>"+
//        "<th>Horas Planeadas</th>"+
//		"<th>Horas Capturadas</th>"+
//		"<th>Horas Validadas</th>"+
//        "</tr>"+
//        "</tfoot>"+
//		"<tbody>";
//		
//		for(AprobacionHoras aprobacionhoras : aprobacionhorasList){
//            String tablaAprobacionHoras = "";
//            tablaAprobacionHoras = "<td><input type=\"checkbox\" name=\"check\" onClick=\"checkbox();\"/></td>";
//            tablaAprobacionHoras += "<td>"+aprobacionhoras.getRecurso().getDescRecurso()+"</td>";
//            tablaAprobacionHoras += "<td>"+aprobacionhoras.getDescComentarioDetalle()+"</td>";
//            tablaAprobacionHoras += "<td>"+aprobacionhoras.getActividad().getValDuracionActividad()+"</td>";
//            tablaAprobacionHoras += "<td>"+aprobacionhoras.getValDuracionReportada()+"</td>";
//            tablaAprobacionHoras += "<td>"+aprobacionhoras.getValDuracionValidada()+"</td>";
//            contenido += tablaAprobacionHoras;
//        }
//		
//		contenido = contenido + "</tbody>"+
//        "</table>"+
//        "</div>";
//		return contenido;
//	}

}
