package com.sophi.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sophi.app.models.entity.RegistroWebinar;
import com.sophi.app.models.service.IRegistroWebinarService;

@Controller
public class WebinarController {
	
	@Autowired
	private IRegistroWebinarService registroWebinarService;
	
	@GetMapping(value="/registroWebinar")
	public String registroWebinar(Model model){
		RegistroWebinar registroWebinar = new RegistroWebinar();
		model.addAttribute("registroWebinar", registroWebinar);
		return "registerWebinar";
	}
	
	@PostMapping(value="/registroWebinar")
	public String guardarRegistroWebinar(@ModelAttribute RegistroWebinar registroWebinar, Model model){
		registroWebinarService.guardar(registroWebinar);
		model.addAttribute("mensaje", "Gracias por registrarte");
		return "registerWebinar";
	}

}
