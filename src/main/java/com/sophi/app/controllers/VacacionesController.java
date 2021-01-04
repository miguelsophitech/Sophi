package com.sophi.app.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sophi.app.Utiles;
import com.sophi.app.mail.dto.MailRequest;
import com.sophi.app.mail.dto.MailResponse;
import com.sophi.app.mail.service.EmailService;
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
	
	@Autowired
	private EmailService service;

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
		System.out.println(codRecurso);
		List<ProyectoRecurso> listaPR = new ArrayList<>();
		StringBuilder strb = new StringBuilder();
		strb.append("");
		
		RecursoVacaciones recursoVacaciones = null;
		recursoVacaciones = recursoVacacionesService.findById(codRecurso);
		
		String pattern = "yyyyMMdd";
		DateFormat df = new SimpleDateFormat(pattern);
		String hoyVal = df.format(new Date(new Utiles().getFechaActual().getTime() + (1000 * 60 * 60 * 24 * 7) ));
		
		List<String> aprobadores = new ArrayList<String>();
		
		listaPR = proyectoRecursoService.findProyectoRecursoActivo(codRecurso);
		for (ProyectoRecurso proyectoRecurso : listaPR) {
			Proyecto proyecto = proyectoService.findByCodProyectoAndCodEstatusProyecto(proyectoRecurso.getProyectoRecursoId().getCodProyecto(), 2L);
			//Proyecto proyecto = proyectoService.findByCodProyecto(proyectoRecurso.getProyectoRecursoId().getCodProyecto());
			if(proyecto != null && proyecto.getCodProyecto() != 1 && proyecto.getCodEstatusProyecto() == 2) {
				strb.append(proyecto.getDescProyecto());
				strb.append(" del ");
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				strb.append(format.format(proyecto.getFecIncioProyecto()));
				strb.append(" al ");
				//System.out.println("Proyecto: " +proyecto.getDescProyecto());
				//System.out.println("Inicio: " +proyecto.getFecIncioProyecto());
				//System.out.println("Fin: " +proyecto.getFecFinProyecto());
				if(proyecto.getFecFinProyecto() != null) {
					strb.append(format.format(proyecto.getFecFinProyecto()));
				}
				else {
					strb.append("N/A");
				}
				strb.append(". ");
				aprobadores.add(recursoService.findOne(proyecto.getCodRecursoAprobador()).getDescCorreoElectronico());
			}
			
		}
		
		 Set<String> hashSet = new HashSet<String>(aprobadores);
		 aprobadores.clear();
		 aprobadores.addAll(hashSet);
		 
		 StringBuilder aprob = new StringBuilder();
		 
		 for (String string : hashSet) {
			 aprob.append(string + ",");
		}
		 

		model.addAttribute("recursoVacaciones", recursoVacaciones);
		model.addAttribute("hoyVal",hoyVal);
		model.addAttribute("informacion",strb.toString());
		model.addAttribute("aprobadores",aprob.toString());
		return "formSolicitudVacaciones";		
	}
	
	@GetMapping({"/confirmarSolicitud"})
	@ResponseBody
	public String confirmarSolicitud(@RequestParam Long codRecurso,
										@RequestParam List<String> diasSelec,
										@RequestParam String usr,
										@RequestParam Long totalSolicitud,
										@RequestParam String aprobadores,
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
		
		System.out.println(aprobadores);
		
		for (String mailAprobador : aprobadores.split(",")) {
		//Mail Notificacion INICIO 
		Recurso recurso = recursoService.findOne(codRecurso);
		Recurso recursoAprobador = recursoService.findByDescCorreoElectronico(mailAprobador);
		MailRequest request = new MailRequest();
		request.setName(recursoAprobador.getDescRecurso());
		request.setSubject("Nueva solicitud de vacaciones");
		request.setTo(recursoAprobador.getDescCorreoElectronico());
		
		Map<String, Object> modelM = new HashMap<String, Object>();
		modelM.put("nombreRecurso", request.getName());
		modelM.put("mensaje", "<h3>Nueva solicitud de vacaciones por responder de \""+ recurso.getDescRecurso() + " " + recurso.getDescApellidoPaterno() + "\"</h3>.");
		modelM.put("imagen","<img data-cfsrc=\"images/status.png\" alt=\"\" data-cfstyle=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" style=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" src=\"https://sophitech.herokuapp.com/img/img-banca.png\">");
		modelM.put("btnLink", "<a href=\"https://sophitech.herokuapp.com/aprobacionVacaciones/" +mailAprobador+" \" style=\"text-align: center; border-radius: 5px; font-weight: bold; background-color: #C02C57; color: white; padding: 14px 25px; text-decoration: none; display: inline-block; \">Ver detalle</a>");
		modelM.put("pie", "");
		
		MailResponse response = service.sendEmailEvaluador(request, modelM);
		System.out.println(response.getMessage());
		//Mail Notificacion FIN 
		}
		
		return "/misVacaciones/"+usr;
	}
	
	
	
	
	
	
	
	
}