package com.sophi.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sophi.app.models.service.IRecursoService;

@Controller
public class SophiController {
	
	@Autowired
	private IRecursoService recursoService;
	
	@GetMapping({"/index","/","","/home"})
	public String index(Map<String, Object> map) {
		map.put("titulo","Sophi");
		return "index";
	}
	
	
	@GetMapping(value="/datosRecursoLogin/{login}")
	public String cargarActividadPrimaria(@PathVariable String login, Model model){
		recursoService.findByDescCorreoElectronico(login);
		model.addAttribute("recursoSesion", recursoService.findByDescCorreoElectronico(login));
		return "layout/layout :: dataSesion";
	}
	
	

}
