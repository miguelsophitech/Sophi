package com.sophi.app.controllers;

import java.util.ArrayList;
import java.util.List;

import com.sophi.app.models.entity.AprobacionGastos;
import com.sophi.app.models.entity.AprobacionGastosDto;
import com.sophi.app.models.entity.Proyecto;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.service.IAprobacionGastosService;
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
@SessionAttributes("aprobaciongastoslista")
public class AprobacionGastosController {

    @Autowired
    private IAprobacionGastosService aprobaciongastosService;

    @Autowired
    private IRecursoService recursoService;
    
    @Autowired
    private IProyectoService proyectoService;
    
    public Long CodRecurso;
    

    @RequestMapping(value = "/aprobaciongastos/{email}", method = RequestMethod.GET)
    public String AprobacionGastos(Model model, @PathVariable(value = "email") String email) {
    	 Long codRecurso = recursoService.findByDescCorreoElectronico(email).getCodRecurso();
    	List<AprobacionGastos> aprobacionesgastos = new ArrayList<>();
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
        
        aprobaciongastosService.findAll().iterator().forEachRemaining(aprobacionesgastos::add);
        listaRecursos = recursoService.findAll();
        AprobacionGastosDto apgdto = new AprobacionGastosDto();
        apgdto.setAprobaciongastos(aprobacionesgastos);
        model.addAttribute("titulo", "Listado de gastos capturados");
        //model.addAttribute("aprobaciongastos", aprobaciongastosService.findAll());
        model.addAttribute("aprobaciongastoslista",apgdto);
        model.addAttribute("proyectos", listaProyectoTodo);
        model.addAttribute("recursos", listaRecursos);
        model.addAttribute("r", codRecurso);
        CodRecurso = codRecurso;
        return "aprobaciongastos";
    }
    
    @RequestMapping(value="/validar", method = RequestMethod.POST)
    public String validarGastos(@ModelAttribute("aprobaciongastoslista") AprobacionGastosDto aprobaciongastoslista, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
    	if(result.hasErrors()) {
			model.addAttribute("titulo", "Listado Gastos Capturados");
			List<Recurso> listaRecursos = new ArrayList<Recurso>();
	        listaRecursos = recursoService.findAll();
	        model.addAttribute("recursos", listaRecursos);	        
	        model.addAttribute("r", CodRecurso);
			return "aprobaciongastos";
		}
		aprobaciongastosService.saveAll(aprobaciongastoslista.getAprobaciongastos());
		status.setComplete();
		flash.addFlashAttribute("success", "Gastos Validados");
		return "redirect:/aprobaciongastos/"+recursoService.findOne(CodRecurso).getDescCorreoElectronico();
    }
    
    @RequestMapping(value = "/cargaGastos", method = RequestMethod.GET)
	@ResponseBody
	public String cargaHoras(@RequestParam("id") Long codProyecto, Model model) {
        List<AprobacionGastos> aprobaciongastosList;
        
        if(codProyecto == -1) {
        	aprobaciongastosList = aprobaciongastosService.findAll();
        }
        
        else {
        	aprobaciongastosList = aprobaciongastosService.findAprobacionGastosBycodProyecto(codProyecto);
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
		String tablaAprobacionGastos = "";
		
		for(AprobacionGastos aprobaciongastos : aprobaciongastosList){
            if(aprobaciongastos.getFecValidacion() == null && aprobaciongastos.getCodRecursoValidador() == null) {
            	tablaAprobacionGastos += "<tr>\r\n";
                tablaAprobacionGastos += "<td><input id=\"check\" type=\"checkbox\" name=\"aprobaciongastos["+cont+"].fecValidacion\" value="+aprobaciongastos.getFecValidacion()+" onclick=\"validacion();\"></td>";
                tablaAprobacionGastos += "<td><span>"+aprobaciongastos.getTipogasto().getDescTipoGasto()+"</span></td>";
                tablaAprobacionGastos += "<td><span>"+aprobaciongastos.getFecRegistro()+"</span></td>";
                tablaAprobacionGastos += "<td><span>"+aprobaciongastos.getRecurso().getDescRecurso()+"</span></td>";
                tablaAprobacionGastos += "<td><span>"+aprobaciongastos.getDescComentario()+"</span></td>";
                tablaAprobacionGastos += "<td>$<span>"+aprobaciongastos.getImpGasto()+"</span></td>";
                tablaAprobacionGastos += "<td><input id=\"RecVal\" type=\"hidden\" name=\"aprobaciongastos["+cont+"].codRecursoValidador\" value="+aprobaciongastos.getCodRecursoValidador()+"></td>";
                tablaAprobacionGastos += "</tr>";
            }
            cont++;
        }
		contenido += tablaAprobacionGastos;
		contenido = contenido + "</tbody>"+
        "</table>"+
        "</div>";
		return contenido;
	}
    
}