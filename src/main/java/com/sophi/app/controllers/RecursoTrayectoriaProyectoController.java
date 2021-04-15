package com.sophi.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sophi.app.models.entity.RecursoTrayectoriaProyecto;
import com.sophi.app.models.service.IRecursoTrayectoriaProyectoService;

@Controller
public class RecursoTrayectoriaProyectoController {
	
	@Autowired
	private IRecursoTrayectoriaProyectoService recursoTrayectoriaProyectoService;
	
	@RequestMapping(value = "/verTrayectoriaProyecto")
	public String verTrayectoriaProyecto(@RequestParam Long codRecurso, Map<String, Object> model) {
		model.put("listaTrayectoriaProyectos", recursoTrayectoriaProyectoService.findByCodRecurso(codRecurso));
		return "layout/trayectoria:: fragmentTrayectoriaProyecto";
	}
	
	@RequestMapping(value="/formTrayectoriaProyecto", method = RequestMethod.POST)
	@ResponseBody
	public String guardarTrayectoriaProyecto(@Valid RecursoTrayectoriaProyecto recursoTrayectoriaProyecto, Model model) {
		recursoTrayectoriaProyectoService.save(recursoTrayectoriaProyecto);
		return "ok";
	}
	
	@RequestMapping(value = "/formTrayectoriaProyecto")
	public String crearTrayectoriaProyecto(@RequestParam Long codRecurso, Map<String, Object> model) {
		RecursoTrayectoriaProyecto recursoTrayectoriaProyecto = new RecursoTrayectoriaProyecto();
		recursoTrayectoriaProyecto.setCodRecurso(codRecurso);
		model.put("trayectoriaProyecto", recursoTrayectoriaProyecto);
		return "layout/trayectoria:: fragmentModalTrayectoria";
	}
	
	@RequestMapping(value = "/editTrayectoriaProyecto")
	public String editarTrayectoriaProyecto(@RequestParam Long rtp, Map<String, Object> model) {
		RecursoTrayectoriaProyecto recursoTrayectoriaProyecto = recursoTrayectoriaProyectoService.findById(rtp);
		model.put("trayectoriaProyecto", recursoTrayectoriaProyecto);
		return "layout/trayectoria:: fragmentModalTrayectoria";
	}
	
	@RequestMapping(value = "/borrarTrayectoriaProyecto")
	@ResponseBody
	public String borrarTrayectoriaProyecto(@RequestParam Long rtp, Map<String, Object> model) {
		recursoTrayectoriaProyectoService.delete(rtp);
		return "ok";
	}

	
}
