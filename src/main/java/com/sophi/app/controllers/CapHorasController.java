package com.sophi.app.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sophi.app.models.entity.Actividad;
import com.sophi.app.models.entity.CapHora;
import com.sophi.app.models.entity.CapHoraId;
import com.sophi.app.models.service.IActividadService;
import com.sophi.app.models.service.ICapHoraService;

@Controller
@SessionAttributes("capHora")
public class CapHorasController {
	
	@Autowired
	private IActividadService actividadService;
	
	@Autowired
	private ICapHoraService capHoraService;
	
	@GetMapping("/capHoras/{id}")
	public String capHoras(@PathVariable(value="id") Long codRecurso, Model model) {
		model.addAttribute("titulo", "Captura de horas");
		List<Long> proyectoList = new ArrayList<Long>();
		proyectoList = actividadService.findListaProyectoByRecurso(codRecurso);
		model.addAttribute("proyectoList", proyectoList);
		return "formCapHoras";
	}
	
	@GetMapping(value="/cargarActividadPrimaria/{codRecurso}/{codProyecto}")
	public String cargarActividadPrimaria(@PathVariable Long codRecurso, @PathVariable Long codProyecto, Model model){
		model.addAttribute("actividadesPrimariasList", actividadService.findListaActividadesPrimariasByRecursoProyecto(codRecurso, codProyecto));
		return "layout/capHora :: listActividadesPrimarias";
	}
	
	@GetMapping(value="/cargarActividadSecundaria/{codRecurso}/{codProyecto}/{descPrimaria}")
	public String cargarActividadSecundaria(@PathVariable Long codRecurso, @PathVariable Long codProyecto,@PathVariable String descPrimaria,  Model model){
		model.addAttribute("actividadesPrimariasList", actividadService.findListaActividadesByRecursoProyectoPrimaria(codRecurso, codProyecto, descPrimaria));
		return "layout/capHora :: listActividadesSecundarias";
	}
	
	@GetMapping(value="/cargarDetActividad/{codActividad}/{fecCaptura}")
	public String cargarDetActividad(@PathVariable Long codActividad, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fecCaptura, Model model){
		
		Actividad actividad = new Actividad();
		actividad = actividadService.findOne(codActividad);
		CapHoraId capHoraId = new CapHoraId(actividad.getCodActividad(), actividad.getCodRecurso(), actividad.getCodProyecto(), actividad.getCodCliente(), actividad.getCodEstatusProyecto());
		CapHora capHora = new CapHora();
		capHora.setId(capHoraId);
		capHora.setFecInicioActividad(fecCaptura);
		model.addAttribute("capHora", capHora);
		return "layout/capHora :: detActividades";
	}
	
	
	@GetMapping(value="/cargarActividadCapturadas/{codActividad}/{fecCaptura}")
	public String cargarActividadesCapturadas(@PathVariable Long codRecurso, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fecCaptura, Model model){
		
		List<CapHora> listActHoraCapturadas = new ArrayList<CapHora>();
		listActHoraCapturadas = capHoraService.findListCapHoraByFechaRecurso(fecCaptura, codRecurso);
		model.addAttribute("listActHoraCapturadas", listActHoraCapturadas);
		return "layout/capHora :: detActividadesCapturadas";
	}
	
	

	@RequestMapping(value="/formCapHoraActividad", method = RequestMethod.POST)
	public String guardarCapHora(@Valid CapHora capHora, BindingResult result, Model model, RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {

			return "formCapHoras";
		}
		
		String mensajeFlash = "Registro guardado con exito";
		
		capHoraService.save(capHora);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:capHoras/"+capHora.getId().getCodRecurso();
	}

}
