package com.sophi.app.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sophi.app.Utiles;
import com.sophi.app.models.entity.DetalleSolicitud;
import com.sophi.app.models.entity.Proyecto;
import com.sophi.app.models.entity.ProyectoRecurso;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.RecursoVacaciones;
import com.sophi.app.models.entity.SolicitudVacaciones;
import com.sophi.app.models.service.IProyectoRecursoService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoService;
import com.sophi.app.models.service.IRecursoVacacionesService;
import com.sophi.app.models.service.ISolicitudVacacionesService;

@Controller
public class VacacionesController {
	
	@Autowired
	private IRecursoService recursoService;
	
	@Autowired
	private IRecursoVacacionesService recursoVacacionesService;
	
	@Autowired
	private ISolicitudVacacionesService solicitudVacacionesService;
	
	@Autowired
	private IProyectoRecursoService proyectoRecursoService;
	
	@Autowired
	private IProyectoService proyectoService;

	@GetMapping({"/misVacaciones/{mail}"})
	public String listadoVacaciones(@PathVariable String mail, Model model) {
		
		Recurso recurso = null;
		recurso = recursoService.findByDescCorreoElectronico(mail);
		
		List<SolicitudVacaciones> listaSolicitudes = new ArrayList<SolicitudVacaciones>();
		
		Long contador = 0L;
		
		RecursoVacaciones recursoVacaciones = null;
		
		if (recurso != null) {
			recursoVacaciones = recursoVacacionesService.findById(recurso.getCodRecurso());
			listaSolicitudes = solicitudVacacionesService.findByCodRecurso(recurso.getCodRecurso());
			for (SolicitudVacaciones solicitudVacaciones : listaSolicitudes) {
				contador++;
				solicitudVacaciones.setContador(contador);
			}
		}
		
		model.addAttribute("codRecurso", recurso.getCodRecurso());
		model.addAttribute("recursoVacaciones", recursoVacaciones);
		model.addAttribute("listaSolicitudes", listaSolicitudes);
		return "listaVacaciones";
	}
	
	@GetMapping({"/verDetalleSolicitud"})
	@ResponseBody
	public String verDetalleSolicitud(@RequestParam Long idSolicitud, Model model) {
		
		SolicitudVacaciones solicitud = null;
		solicitud = solicitudVacacionesService.findById(idSolicitud);
		
		StringBuilder result = new StringBuilder();
		result.append("");
		
		if (solicitud != null) {
			result.append("<br><h5>días solicitados:</h5>");
			result.append("<p>");
			for (DetalleSolicitud detalleSolicitud : solicitud.getDetallesSolicitud()) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				result.append(format.format(detalleSolicitud.getFecDiaSolicitado()));
				result.append("<br>");
			}
		}
		
		return result.toString();
	}
	
	@GetMapping({"/solicitudVacaciones/{codRecurso}"})
	public String solicitudVacaciones(@PathVariable Long codRecurso, Model model) {
		
		List<ProyectoRecurso> listaPR = new ArrayList<>();
		StringBuilder strb = new StringBuilder();
		strb.append("");
		
		RecursoVacaciones recursoVacaciones = null;
		recursoVacaciones = recursoVacacionesService.findById(codRecurso);
		
		String pattern = "yyyyMMdd";
		DateFormat df = new SimpleDateFormat(pattern);
		String hoyVal = df.format(new Date(new Utiles().getFechaActual().getTime() + (1000 * 60 * 60 * 24 * 7) ));
		
		listaPR = proyectoRecursoService.findProyectoRecursoActivo(codRecurso);
		for (ProyectoRecurso proyectoRecurso : listaPR) {
			Proyecto proyecto = proyectoService.findByCodProyecto(proyectoRecurso.getProyectoRecursoId().getCodProyecto());
			if(proyecto != null && proyecto.getCodProyecto() != 1) {
				strb.append(proyecto.getDescProyecto());
				strb.append(" del ");
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				strb.append(format.format(proyecto.getFecIncioProyecto()));
				strb.append(" al ");
				strb.append(format.format(proyecto.getFecFinProyecto()));
				strb.append(". ");
			}
			
		}

		model.addAttribute("recursoVacaciones", recursoVacaciones);
		model.addAttribute("hoyVal",hoyVal);
		model.addAttribute("informacion",strb.toString());
		return "formSolicitudVacaciones";
		
		
		
	}
	
	@GetMapping({"/confirmarSolicitud"})
	@ResponseBody
	public String confirmarSolicitud(@RequestParam Long codRecurso,
										@RequestParam List<String> diasSelec,
										@RequestParam String usr,
										@RequestParam Long totalSolicitud,
										Model model) {
		
		List<DetalleSolicitud> detallesSolicitud = new ArrayList<>();
		
		if(diasSelec.size()>0){
			if(!diasSelec.get(0).equals("[]")) {
				for (String idDia : diasSelec) {
					String codDia = null;
//						codDia = Long.parseLong(codDia.replace("\"", "").replace("[", "").replace("]", ""));
					codDia = idDia.replace("\"", "").replace("[", "").replace("]", "");
					DetalleSolicitud detalle = new DetalleSolicitud();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					try {
						detalle.setFecDiaSolicitado(format.parse(codDia));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					detallesSolicitud.add(detalle);
				}
			}
		}
		
		RecursoVacaciones recursoVacaciones = recursoVacacionesService.findById(codRecurso);
		recursoVacaciones.setValDisponibles(recursoVacaciones.getValDisponibles() - totalSolicitud);
		
		SolicitudVacaciones solicitud = new SolicitudVacaciones();
		solicitud.setCodRecurso(codRecurso);
		solicitud.setValDiasSolicitados(totalSolicitud);
		solicitud.setFecSolicitud(new Utiles().getFechaActual());
		solicitud.setValPeriodo(recursoVacaciones.getValPeriodo());
		solicitud.setDetallesSolicitud(detallesSolicitud);
		
		solicitudVacacionesService.save(solicitud);
		
		recursoVacacionesService.save(recursoVacaciones);
		
		return "/misVacaciones/"+usr;
	}
	
	
	
	
	
	
}