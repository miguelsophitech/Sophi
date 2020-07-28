package com.sophi.app.controllers;

import java.util.ArrayList;
import java.util.List;

import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.service.IRecursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("aprobaciongastos")
public class AprobaciónGastosController {

    //@Autowired
    //private IAprobacionGastosService aprobaciongastosService;

    @Autowired
    private IRecursoService recursoService;

    @RequestMapping(value = "aprobaciongastos", method = RequestMethod.GET)
    public String AprobacionGastos(Model model) {
        List<Recurso> listaRecursos = new ArrayList<Recurso>();
        listaRecursos = recursoService.findAll();
        model.addAttribute("titulo", "Listado de gastos capturados");
       // model.addAttribute("aprobaciongastos", aprobaciongastosService.findAll());
        model.addAttribute("recursos", listaRecursos);
        return "aprobaciongastos";
    }
    
}