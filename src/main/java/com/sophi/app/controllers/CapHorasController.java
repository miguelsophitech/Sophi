package com.sophi.app.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sophi.app.models.entity.Actividad;
import com.sophi.app.models.entity.CapHora;
import com.sophi.app.models.service.IActividadService;
import com.sophi.app.models.service.ICapHoraService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoService;

@Controller
@SessionAttributes("capHora")
public class CapHorasController {
	
	@Autowired
	private IActividadService actividadService;
	
	@Autowired
	private IRecursoService recursoService;
	
	@Autowired
	private ICapHoraService capHoraService;
	
	@Autowired
	private IProyectoService proyectoService;
	
	@GetMapping("/capHoras/{email}")
	public String capHoras(@PathVariable(value="email") String email, Model model) {
		Long codRecurso = recursoService.findByDescCorreoElectronico(email).getCodRecurso();
		model.addAttribute("titulo", "Captura de horas");
		List<Long> proyectoListId = new ArrayList<Long>();
		HashMap<Long, String> proyectoList = new HashMap<Long, String>(); 
		proyectoListId = actividadService.findListaProyectoByRecurso(codRecurso);
		for (Long id : proyectoListId) {
			proyectoList.put(id, proyectoService.findByCodProyectoAndCodEstatusProyecto(id, 2L).getDescProyecto());
		}
		proyectoList.put(1L,"Sophitech");
		model.addAttribute("proyectoList", proyectoList);
		model.addAttribute("codRecurso", codRecurso);
		return "formCapHoras";
	}
	
	@GetMapping(value="/cargarActividadPrimaria/{codRecurso}/{codProyecto}")
	public String cargarActividadPrimaria(@PathVariable Long codRecurso, @PathVariable Long codProyecto, Model model){
		if(codProyecto.equals(1L)) {
			HashMap<Long, String> actividadesPrimariasList = new HashMap<Long, String>(); 
			actividadesPrimariasList.put(-1L, "Administración interna");
			model.addAttribute("actividadesPrimariasNoPlan", actividadesPrimariasList);
			return "layout/capHora :: listActividadesPrimariasNoPlan";
		}else {
			model.addAttribute("actividadesPrimariasList", actividadService.findListaActividadesPrimariasByRecursoProyecto(codRecurso, codProyecto));
			return "layout/capHora :: listActividadesPrimarias";
		}
	}
	
	@GetMapping(value="/cargarActividadSecundaria/{codRecurso}/{codProyecto}/{descPrimaria}")
	public String cargarActividadSecundaria(@PathVariable Long codRecurso, @PathVariable Long codProyecto,@PathVariable String descPrimaria,  Model model){
		System.out.println(codRecurso +" "+codProyecto +" "+descPrimaria );
		if(codProyecto.equals(1L)) {
			HashMap<Long, String> actividadesSecundariasList = new HashMap<Long, String>(); 
			actividadesSecundariasList.put(-1L, "Administrativas");
			actividadesSecundariasList.put(-2L, "Actividades recreativas internas");
			actividadesSecundariasList.put(-3L, "Vacaciones");
			actividadesSecundariasList.put(-4L, "Capacitación");
			actividadesSecundariasList.put(-5L, "Couching");
			actividadesSecundariasList.put(-6L, "Enfermedad");
			actividadesSecundariasList.put(-7L, "Incapacidad");
			actividadesSecundariasList.put(-8L, "Día festivo");
			actividadesSecundariasList.put(-9L, "Permiso planeado");
			actividadesSecundariasList.put(-10L, "Permiso emergencia");
			actividadesSecundariasList.put(-10L, "Viaje / Desplazamiento");
			model.addAttribute("actividadesSecundariasNoPlan", actividadesSecundariasList);
			return "layout/capHora :: listActividadesSecundariasNoPlan";
		} else {
		model.addAttribute("actividadesSecundariasList", actividadService.findListaActividadesByRecursoProyectoPrimaria(codRecurso, codProyecto, descPrimaria));
		return "layout/capHora :: listActividadesSecundarias";
		}
	}
	
	@GetMapping(value="/cargarDetActividad/{codActividad}/{fecCaptura}")
	public String cargarDetActividad(@PathVariable Long codActividad, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fecCaptura, Model model){
		Actividad actividad = new Actividad();
		actividad = actividadService.findOne(codActividad);
//		CapHoraId capHoraId = new CapHoraId(actividad.getCodActividad(), actividad.getCodRecurso(), actividad.getCodProyecto(), actividad.getCodCliente(), actividad.getCodEstatusProyecto());
		CapHora capHora = new CapHora();
//		capHora.setId(capHoraId);
		capHora.setCodActividad(actividad.getCodActividad());
		capHora.setCodRecurso(actividad.getCodRecurso());
		capHora.setCodProyecto(actividad.getCodProyecto());
		capHora.setCodCliente(actividad.getCodCliente());
		capHora.setCodEstatusProyecto(actividad.getCodEstatusProyecto());
		capHora.setFecInicioActividad(fecCaptura);
		model.addAttribute("capHora", capHora);
		return "layout/capHora :: detActividades";
	}
	
	@GetMapping(value="/cargarDetActividadNoPlan/{codActividad}/{fecCaptura}/{codRecurso}")
	public String cargarDetActividadNoPlan(@PathVariable Long codActividad, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fecCaptura,@PathVariable Long codRecurso, Model model){
		System.out.println(codActividad +" "+codRecurso +" "+fecCaptura);
//		CapHoraId capHoraId = new CapHoraId(codActividad, codRecurso, 1L, 1L, 2L);
		CapHora capHora = new CapHora();
		capHora.setCodActividad(codActividad);
		capHora.setCodRecurso(codRecurso);
		capHora.setCodProyecto(1L);
		capHora.setCodCliente(1L);
		capHora.setCodEstatusProyecto(2L);
//		capHora.setId(capHoraId);
		capHora.setFecInicioActividad(fecCaptura);
		model.addAttribute("capHora", capHora);
		return "layout/capHora :: detActividades";
	}
	
	
	@GetMapping(value="/cargarActividadCapturadas/{codRecurso}/{fecCaptura}")
	public String cargarActividadesCapturadas(@PathVariable Long codRecurso, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fecCaptura, Model model){
		HashMap<Long, String> actividadesSophitech = new HashMap<Long, String>(); 
		actividadesSophitech.put(-1L, "Administrativas");
		actividadesSophitech.put(-2L, "Actividades recreativas internas");
		actividadesSophitech.put(-3L, "Vacaciones");
		actividadesSophitech.put(-4L, "Capacitación");
		actividadesSophitech.put(-5L, "Couching");
		actividadesSophitech.put(-6L, "Enfermedad");
		actividadesSophitech.put(-7L, "Incapacidad");
		actividadesSophitech.put(-8L, "Día festivo");
		actividadesSophitech.put(-9L, "Permiso planeado");
		actividadesSophitech.put(-10L, "Permiso emergencia");
		actividadesSophitech.put(-10L, "Viaje / Desplazamiento");
		List<CapHora> listActHoraCapturadas = new ArrayList<CapHora>();
		listActHoraCapturadas = capHoraService.findListCapHoraByFechaRecurso(fecCaptura, codRecurso);
		for (CapHora capHora : listActHoraCapturadas) {
			if(capHora.getCodProyecto().equals(1L)) {
				capHora.setDescProyecto("Sophitech");
			} else {
				capHora.setDescProyecto(proyectoService.findByCodProyectoAndCodEstatusProyecto(capHora.getCodProyecto(), 2L).getDescProyecto());
			}
			
			if(capHora.getCodActividad()<0) {
				for (Map.Entry<Long, String> entry : actividadesSophitech.entrySet()) {
				    if( capHora.getCodActividad().equals(entry.getKey())) {
				    	capHora.setDescActividadSecundaria(entry.getValue());
				    	break;
				    };
				}
			} else {
				capHora.setDescActividadSecundaria(actividadService.findOne(capHora.getCodActividad()).getDescActividadSecundaria());
			}
		}
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
		return "redirect:capHoras/"+recursoService.findOne(capHora.getCodRecurso()).getDescCorreoElectronico();
	}
	
	
	@RequestMapping(value="/horasTotalSemana",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Float horasTotalSemana(@RequestParam Long codRecurso, @RequestParam Date fechaInicioSemana, @RequestParam Date fechaFinSemana, Model model) {
		System.out.println(codRecurso + " " + fechaInicioSemana + " "+ fechaFinSemana);
		return capHoraService.findSumHorasReportadasSemana(codRecurso, fechaInicioSemana, fechaFinSemana);
	}

}
