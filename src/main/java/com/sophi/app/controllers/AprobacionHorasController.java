package com.sophi.app.controllers;

import com.sophi.app.models.service.IAprobacionHorasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("aprobacionhoras")
public class AprobacionHorasController {

    @Autowired
    private IAprobacionHorasService aprobacionHorasService;

    @RequestMapping(value = "aprobaciónhoras", method = RequestMethod.GET)
    public String AprobacionHoras(Model model){
        model.addAttribute("titulo", "Listado de horas capturadas");
        model.addAttribute("aprobaciónhoras", aprobacionHorasService.findAll());
        return "aprobaciónhoras";
    }

}
