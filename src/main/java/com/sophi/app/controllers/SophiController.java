package com.sophi.app.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SophiController {
	
	@GetMapping({"/index","/","","/home"})
	public String index(Map<String, Object> map) {
		map.put("titulo","Sophi");
		return "index";
	}

}
