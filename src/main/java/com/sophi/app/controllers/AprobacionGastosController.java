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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/aprobaciongastos", method = RequestMethod.GET)
    public String AprobacionGastos(Model model) {
    	List<AprobacionGastos> aprobacionesgastos = new ArrayList<>();
    	List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
        List<Recurso> listaRecursos = new ArrayList<Recurso>();
        aprobaciongastosService.findAll().iterator().forEachRemaining(aprobacionesgastos::add);
        listaProyectos = proyectoService.findAll();
        listaRecursos = recursoService.findAll();
        AprobacionGastosDto apgdto = new AprobacionGastosDto();
        apgdto.setAprobaciongastos(aprobacionesgastos);
        model.addAttribute("titulo", "Listado de gastos capturados");
        //model.addAttribute("aprobaciongastos", aprobaciongastosService.findAll());
        model.addAttribute("aprobaciongastoslista",apgdto);
        model.addAttribute("proyectos", listaProyectos);
        model.addAttribute("recursos", listaRecursos);
        return "aprobaciongastos";
    }
    
//    @RequestMapping(value="/guardar", method = RequestMethod.POST)
//    public String validarGastos(@ModelAttribute("aprobaciongastoslista") AprobacionGastosDto aprobaciongastoslista, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
//    	if(result.hasErrors()) {
//			model.addAttribute("titulo", "Listado Gastos Capturados");
//			List<Recurso> listaRecursos = new ArrayList<Recurso>();
//	        listaRecursos = recursoService.findAll();
//	        model.addAttribute("recursos", listaRecursos);
//			return "aprobaciongastos";
//		}
//		aprobaciongastosService.saveAll(aprobaciongastoslista.getAprobaciongastos());
//		status.setComplete();
//		flash.addFlashAttribute("success", "Gastos Validados");
//		return "redirect:/aprobaciongastos";
//    }
    
}