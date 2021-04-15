package com.sophi.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sophi.app.models.entity.Herramienta;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.service.IEquipoService;
import com.sophi.app.models.service.IHerramientaService;
import com.sophi.app.models.service.IRecursoService;

@Controller
@SessionAttributes("herramienta")
public class HerramientaController {
	
	@Autowired
	private IRecursoService recursoService;
	
	@Autowired
	private IHerramientaService herramientaService;
	
	@Autowired
	private IEquipoService equipoService;
	
	@RequestMapping(value = "/listarHerramientas/{id}")
	public String listarHerramientas(@PathVariable(value = "id") Long codRecurso, Map<String, Object> model, RedirectAttributes flash) {
		Recurso recurso = null;
		if (codRecurso > 0) {
			recurso = recursoService.findOne(codRecurso);
			if(recurso == null) {
				flash.addFlashAttribute("error", "El codigo del recurso no existe en base de datos!");
				return "redirect:/listarRecursos";
			}
		} else {
			flash.addFlashAttribute("error", "El codigo del recurso no debe ser cero!");
			return "redirect:/listarRecursos";
		}
		model.put("recurso", recurso);
		model.put("titulo", "Herramientas de " + recurso.getDescRecurso());
		return "listaHerramientas";
	}
	
	@RequestMapping(value = "/formHerramienta/{id}")
	public String editarHerramienta(@PathVariable(value = "id") Long codHerramienta, Map<String, Object> model, RedirectAttributes flash) {
		Herramienta herramienta = null;
		if (codHerramienta > 0) {
			herramienta = herramientaService.findOne(codHerramienta);
			if(herramienta == null) {
				flash.addFlashAttribute("error", "El codigo de la herramienta no existe en base de datos!");
				return "redirect:/listarRecursos";
			}
		} else {
			flash.addFlashAttribute("error", "El codigo de la herramienta no debe ser cero!");
			return "redirect:/listarRecursos";
		}
		model.put("herramienta", herramienta);
		model.put("titulo", "Formulario de herramientas");
		
		return "formHerramienta";
	}
	
	@RequestMapping(value = "/asignarHerramienta/{id}")
	public String crearHerramienta(@PathVariable(value = "id") Long codRecurso, Map<String, Object> model, RedirectAttributes flash) {
		Recurso recurso = null;
		if (codRecurso > 0) {
			recurso = recursoService.findOne(codRecurso);
			if(recurso == null) {
				flash.addFlashAttribute("error", "El codigo del recurso no existe en base de datos!");
				return "redirect:/listarRecursos";
			}
		} else {
			flash.addFlashAttribute("error", "El codigo del recurso no debe ser cero!");
			return "redirect:/listarRecursos";
		}
		Herramienta herramienta = new Herramienta();
		herramienta.setRecurso(recurso);
		model.put("herramienta", herramienta);
		model.put("titulo", "Formulario de herramientas");
		
		return "formHerramienta";
	}
	
	
	@RequestMapping(value="/formHerramienta", method = RequestMethod.POST)
	public String guardarHerramienta(@Valid Herramienta herramienta, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de herramientas");
			return "formHerramienta";
		}
		herramientaService.save(herramienta);
		status.setComplete();
		flash.addFlashAttribute("success", "Herramienta guardada con éxito");
		return "redirect:listarHerramientas/" + herramienta.getRecurso().getCodRecurso().toString();
	}
	
	@RequestMapping(value = "/borrarHerramienta/{id}")
	public String borrarHerramienta(@PathVariable(value = "id") Long codHerramienta, Map<String, Object> model, RedirectAttributes flash) {
		Herramienta herramienta= null;
		if (codHerramienta > 0) {
			herramienta = herramientaService.findOne(codHerramienta);
			if(herramienta == null) {
				flash.addFlashAttribute("error", "El codigo de la herramienta no existe en base de datos!");
				return "redirect:/listarRecursos";
			}
		} else {
			flash.addFlashAttribute("error", "El codigo de la herramienta no debe ser cero!");
			return "redirect:/listarRecursos";
		}
		
		herramientaService.delete(herramienta);
		flash.addFlashAttribute("success", "Herramienta eliminada con éxito");
		return "redirect:../listarHerramientas/" + herramienta.getRecurso().getCodRecurso().toString();
	}
	
	
	@RequestMapping(value = "/cargaHerramientas")
	public String cargaHerramienta(@RequestParam Long cth, Map<String, Object> model) {
		model.put("listaEquipos", equipoService.findListEquiposDisponiblesPorTipoHerramienta(cth));
		return "verRecurso :: fragmentHerramientasPorTipo";
	}
	

}
