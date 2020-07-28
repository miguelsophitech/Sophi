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
import org.springframework.web.bind.annotation.PathVariable;
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
    
    public Long CodRecurso;

    @RequestMapping(value = "/aprobacionhoras/{codRecurso}", method = RequestMethod.GET)
    public String AprobacionHoras(Model model, @PathVariable(value = "codRecurso") long codRecurso){
    	List<AprobacionHoras> aprobacioneshoras = new ArrayList<>();
        List<Recurso> listaRecursos = new ArrayList<Recurso>();
        List<Proyecto> listaProyecto = proyectoService.findAll();
		List<Proyecto> listaProyectoTodo = proyectoService.findAll();
		Proyecto proyectoAux;
        
        for(Proyecto p : listaProyecto) {
			if(p.getProyectoId().getCodEstatusProyecto()==2) {
				proyectoAux=proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(p.getProyectoId().getCodProyecto(), 1L, p.getProyectoId().getCodCliente());
				if(proyectoAux!=null) {
					listaProyectoTodo.remove(proyectoAux);
				}
			}
		}
        
        aprobacionHorasService.findAll().iterator().forEachRemaining(aprobacioneshoras::add);
        listaRecursos = recursoService.findAll();
        AprobacionHorasDto aphdto = new AprobacionHorasDto();
        aphdto.setAprobacionhoras(aprobacioneshoras);
        model.addAttribute("titulo", "Listado de horas capturadas");
        model.addAttribute("aprobacionhoraslista", aphdto);
        model.addAttribute("recursos", listaRecursos);
        model.addAttribute("proyectos", listaProyectoTodo);
        model.addAttribute("r", codRecurso);
        CodRecurso = codRecurso;
        return "aprobacionhoras";
    }
    

    @RequestMapping(value="/guardar", method = RequestMethod.POST)
	public String validarHoras(@ModelAttribute("aprobacionhoraslista") AprobacionHorasDto aprobacionhoraslista, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {

		if(result.hasErrors()) {
			model.addAttribute("titulo", "Listado Horas Capturadas");
			List<Recurso> listaRecursos = new ArrayList<Recurso>();
	        listaRecursos = recursoService.findAll();
	        model.addAttribute("recursos", listaRecursos);
	        model.addAttribute("r", CodRecurso);
			return "aprobacionhoras";
		}

		aprobacionHorasService.saveAll(aprobacionhoraslista.getAprobacionhoras());
		status.setComplete();
		flash.addFlashAttribute("success", "Horas Validadas");
		return "redirect:/aprobacionhoras/"+CodRecurso;
	}

	@RequestMapping(value = "/cargaHoras", method = RequestMethod.GET)
	@ResponseBody
	public String cargaHoras(@RequestParam("id") Long codProyecto, Model model) {
        List<AprobacionHoras> aprobacionhorasList;
        
        if(codProyecto == -1) {
        	aprobacionhorasList = aprobacionHorasService.findAll();
        }
        
        else {
        	aprobacionhorasList = aprobacionHorasService.findAprobacionHorasBycodProyecto(codProyecto);
        }
        
		String contenido = "";
		contenido = contenido + "<div class=\"table-responsive\">"+
		"<table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">"+
		"<thead>"+
		"<tr>"+
		"<th></th>"+
        "<th>Fecha</th>"+
        "<th>Colaborador</th>"+
        "<th>Comentario</th>"+
        "<th>Horas Planeadas</th>"+
		"<th>Horas Capturadas</th>"+
		"<th>Horas Validadas</th>"+
		"<th></th>"+
        "</tr>"+
        "</thead>"+
        "<tfoot>"+
		"<tr>"+
		"<th></th>"+
        "<th>Fecha</th>"+
        "<th>Colaborador</th>"+
        "<th>Comentario</th>"+
        "<th>Horas Planeadas</th>"+
		"<th>Horas Capturadas</th>"+
		"<th>Horas Validadas</th>"+
		"<th></th>"+
        "</tr>"+
        "</tfoot>"+
		"<tbody>";
		
		int cont = 0;
		String tablaAprobacionHoras = "";
		
		for(AprobacionHoras aprobacionhoras : aprobacionhorasList){
            if(aprobacionhoras.getFecValidacion() == null && aprobacionhoras.getCodRecursoValidador() == null) {
            	tablaAprobacionHoras += "<tr>\r\n";
            	tablaAprobacionHoras += "<td><input id=\"check\" type=\"checkbox\" name=\"aprobacionhoras["+cont+"].fecValidacion\" value="+aprobacionhoras.getFecValidacion()+" onClick=\"checkbox();\"/></td>";
                tablaAprobacionHoras += "<td><span>"+aprobacionhoras.getFecRegistro()+"</span></td>";
                tablaAprobacionHoras += "<td><span>"+aprobacionhoras.getRecurso().getDescRecurso()+"</span></td>";
                tablaAprobacionHoras += "<td><span>"+aprobacionhoras.getDescComentarioDetalle()+"</span></td>";
                tablaAprobacionHoras += "<td><span>"+aprobacionhoras.getActividad().getValDuracionActividad()+"</span></td>";
                tablaAprobacionHoras += "<td><span>"+aprobacionhoras.getValDuracionReportada()+"</span></td>";
                tablaAprobacionHoras += "<td><input id=\"validar\" type=\"text\" style=\"height:30px; width:40px;\" name=\"aprobacionhoras["+cont+"].valDuracionValidada\" value="+aprobacionhoras.getValDuracionValidada()+" disabled/></td>";
                tablaAprobacionHoras += "<td><input id=\"RecVal\" type=\"hidden\" name=\"aprobacionhoras["+cont+"].codRecursoValidador\" value="+aprobacionhoras.getCodRecursoValidador()+"></td>";
                tablaAprobacionHoras += "</tr>";
            }
            cont++;
        }
		contenido += tablaAprobacionHoras;
		contenido = contenido + "</tbody>"+
        "</table>"+
        "</div>";
		return contenido;
	}

}
