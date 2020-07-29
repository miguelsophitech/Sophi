package com.sophi.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sophi.app.models.entity.Actividad;
import com.sophi.app.models.service.IActividadService;
import com.sophi.app.models.service.ICapHoraService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoService;

@Controller
//@SessionAttributes("capHora")
public class ActividadesRecursoController {
	
	@Autowired
	private IActividadService actividadService;
	
	@Autowired
	private IRecursoService recursoService;
	
	@Autowired
	private IProyectoService proyectoService;
	
	@GetMapping("/misActividades/{email}")
	public String capHoras(@PathVariable(value="email") String email, Model model) {
		Long codRecurso = recursoService.findByDescCorreoElectronico(email).getCodRecurso();
		List<Actividad> listaActividades =  actividadService.findByCodRecurso(codRecurso);
		
		HashMap<Long, String> proyectoList = new HashMap<Long, String>(); 
		for (Long id : actividadService.findListaProyectoByRecurso(codRecurso)) {
			proyectoList.put(id, proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyecto(id, 2L).getDescProyecto());
		}
		
		for (Actividad actividad : listaActividades) {
			for (Map.Entry<Long, String> entry : proyectoList.entrySet()) {
			    if (actividad.getCodProyecto().equals(entry.getKey())) {
			    	actividad.setNombreProyecto(entry.getValue());
			    	break;
			    }
			}
		}
		
		model.addAttribute("listaActividades", listaActividades);
		model.addAttribute("codRecurso", codRecurso);
		return "listaActividadesRecurso";
	}
	
	
}
