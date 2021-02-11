package com.sophi.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.sophi.app.mail.dto.MailRequest;
import com.sophi.app.mail.dto.MailResponse;
import com.sophi.app.mail.service.EmailService;
import com.sophi.app.mail.service.InviteEmailService;
import com.sophi.app.models.entity.AprobacionGastos;
import com.sophi.app.models.entity.CapHora;
import com.sophi.app.models.entity.DetalleRecursoHoras;
import com.sophi.app.models.entity.InviteContacto;
import com.sophi.app.models.entity.Proyecto;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.RecursoGasto;
import com.sophi.app.models.service.IActividadService;
import com.sophi.app.models.service.ICapHoraService;
import com.sophi.app.models.service.IInviteContactoService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoService;
import com.sophi.app.models.service.ISubtareaService;

@Controller
public class EmailController {

	@Autowired
	private EmailService service;
	
	@Autowired
	private InviteEmailService inviteService;
	
	@Autowired
	private IRecursoService recursoService;
	
	@Autowired
	private ICapHoraService capHoraService;
	
	@Autowired
	private IProyectoService proyectoService;
	
	@Autowired
	private ISubtareaService subtareaService;
	
	@Autowired
	private IActividadService actividadService;
	
	@Autowired
	private IInviteContactoService inviteContactoService;
	
//	@Scheduled(cron="0 3 12 * * TUE", zone="America/Mexico_City")
	public void enviaInvitacionWebinar() {
		List<InviteContacto> listaContactosInvite = new ArrayList<InviteContacto>();
		listaContactosInvite = inviteContactoService.findAll();
		for (InviteContacto inviteContacto : listaContactosInvite) {
			MailRequest request = new MailRequest();
			request.setName(inviteContacto.getNombre());
			request.setSubject("Revolución Analítica para Seguros");
			request.setTo(inviteContacto.getEmail());
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("nombre_invite", request.getName());
			
			MailResponse response = inviteService.sendEmail(request, model);
			System.out.println(response.getMessage());
		}
	}
	
	
	
	//CRON Viernes 10:00 hora mexico
	@Scheduled(cron="0 0 10 * * FRI", zone="America/Mexico_City")
	public void enviaRecordatorios() {
		List<Recurso> listRecursos = new ArrayList<Recurso>();
		listRecursos = recursoService.findRecursosActivos();
		if (listRecursos.size()>0) {
			for (Recurso recurso : listRecursos) {
				
				MailRequest request = new MailRequest();
				request.setName(recurso.getDescRecurso());
				request.setSubject("Recordatorio de captura");
				request.setTo(recurso.getDescCorreoElectronico());
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("nombreRecurso", request.getName());
				model.put("mensaje", "<h3>Recuerda que hoy debes capturar tus horas en la plataforma</h3>");
				model.put("pie", "Evita que tu esfuerzo se vaya a la banca");
				model.put("imagen","<img data-cfsrc=\"images/time.png\" alt=\"\" data-cfstyle=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" style=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" src=\"https://sophitech.herokuapp.com/img/img-time.png\">");
				
				MailResponse response = service.sendEmail(request, model);
				System.out.println(response.getMessage());
			}
		}
	}
	
	//CRON Miercoles 13:15 hora mexico
	@Scheduled(cron="0 0 11 * * MON", zone="America/Mexico_City")
	public void enviaAvisoHrsRechazdas() {
		List<DetalleRecursoHoras> listDetalle = new ArrayList<DetalleRecursoHoras>();

		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_WEEK) - 1) * -1);
		Date fecFinal = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_YEAR, -6);
		Date fecInicial  = calendar.getTime();
		System.out.println(fecInicial);
		System.out.println(fecFinal);
		
		listDetalle = capHoraService.findRecursoHorasRechazo(fecInicial, fecFinal);
		if (listDetalle.size() > 0) {
			for (DetalleRecursoHoras detalleRecursoHoras : listDetalle) {
				Recurso recurso = recursoService.findOne(detalleRecursoHoras.getLink());
				MailRequest request = new MailRequest();
				System.out.println(recurso.getDescRecurso());
				request.setName(recurso.getDescRecurso());
				request.setSubject("Aviso - horas rechazadas");
				request.setTo(recurso.getDescCorreoElectronico());
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("nombreRecurso", request.getName());
				model.put("mensaje","<h3>" + detalleRecursoHoras.getRechazadas() + " horas fueron rechazadas la semana pasada, revisa tu captura en la plataforma</h3>");
				model.put("pie", "");
				model.put("imagen","<img data-cfsrc=\"images/rechazo.png\" alt=\"\" data-cfstyle=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" style=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" src=\"https://sophitech.herokuapp.com/img/img-rechazo.png\">");
				
				MailResponse response = service.sendEmail(request, model);
				System.out.println(response.getMessage());
			}
		}
	}
	
	
	
	
	//CRON Miercoles 13:15 hora mexico
	@Scheduled(cron="0 22 18 * * WED", zone="America/Mexico_City")
	public void enviaAvisoHrsRechazdasCustom() {
		List<CapHora> listDetalle = new ArrayList<CapHora>();

		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_WEEK) - 1) * -1);
		Date fecFinal = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_YEAR, -200);
		Date fecInicial  = calendar.getTime();
		System.out.println(fecInicial);
		System.out.println(fecFinal);
		
		listDetalle = capHoraService.findRecursoHorasRechazoCustom(fecInicial, fecFinal);
		if (listDetalle.size() > 0) {
			for (CapHora detalleRecursoHoras : listDetalle) {
				
				System.out.println(recursoService.findOne(detalleRecursoHoras.getCodRecurso()).getDescRecurso() + " - Fecha: " + detalleRecursoHoras.getFecInicioActividad() + " Comentario: " + detalleRecursoHoras.getDescRechazo() + " Hrs rechazadas: " + detalleRecursoHoras.getValDuracionRechazada());
						
						
						
						
				
//				Recurso recurso = recursoService.findOne(detalleRecursoHoras.getLink());
//				MailRequest request = new MailRequest();
//				System.out.println(recurso.getDescRecurso());
//				request.setName(recurso.getDescRecurso());
//				request.setSubject("Aviso - horas rechazadas");
//				request.setTo(recurso.getDescCorreoElectronico());
//				
//				Map<String, Object> model = new HashMap<String, Object>();
//				model.put("nombreRecurso", request.getName());
//				model.put("mensaje","<h3>" + detalleRecursoHoras.getRechazadas() + " horas fueron rechazadas la semana pasada, revisa tu captura en la plataforma</h3>");
//				model.put("pie", "");
//				model.put("imagen","<img data-cfsrc=\"images/rechazo.png\" alt=\"\" data-cfstyle=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" style=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" src=\"https://sophitech.herokuapp.com/img/img-rechazo.png\">");
//				
//				MailResponse response = service.sendEmail(request, model);
//				System.out.println(response.getMessage());
			}
		}
	}
	
	
	
	
	@Scheduled(cron="0 0 10 * * MON", zone="America/Mexico_City")
	public void enviaRecordatoriosParaAprobacion() {
		List<Long> proyectos = new ArrayList<Long>();
		List<Recurso> aprobadores = new ArrayList<Recurso>();
		
		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_WEEK) - 1) * -1);
		Date fecInicial = calendar.getTime();
		System.out.println(fecInicial);
		calendar.add(Calendar.DAY_OF_YEAR, 6);
		Date fecFinal = calendar.getTime();
		System.out.println(fecFinal);
		
		proyectos = capHoraService.findProyectosCapturadosSemana(fecInicial, fecFinal);
		if (proyectos.size() > 0) {
			
//			for (Long codProyecto : proyectos) {
//				Proyecto proyecto = proyectoService.findOne(codProyecto);
//				Recurso recurso = recursoService.findOne(proyecto.getCodRecursoAprobador());
			aprobadores = recursoService.findListRecursosAprobadores();	
				
			for (Recurso recurso: aprobadores) {
				MailRequest request = new MailRequest();
				request.setName(recurso.getDescRecurso());
				request.setSubject("Recordatorio de aprobación");
				request.setTo(recurso.getDescCorreoElectronico());
				
				System.out.println(recurso.getDescRecurso());
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("nombreRecurso", request.getName());
				model.put("mensaje", "<h3>Recuerda que hoy debes aprobar las horas de la semana</h3>");
				model.put("pie", "");
				model.put("imagen","<img data-cfsrc=\"images/aprob.png\" alt=\"\" data-cfstyle=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" style=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" src=\"https://sophitech.herokuapp.com/img/img-aprobacion.png\">");
				
				MailResponse response = service.sendEmail(request, model);
				System.out.println(response.getMessage());
				
//				}
			}
		}
	}
	
	
	@Scheduled(cron="0 0 13 * * MON", zone="America/Mexico_City")
	public void enviaEstatusDeAprobacion() {
		List<Long> recursos = new ArrayList<Long>();

		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_WEEK) - 1) * -1);
		Date fecFinal = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_YEAR, -6);
		Date fecInicial = calendar.getTime();
		
		System.out.println(fecInicial);
		System.out.println(fecFinal);
		
		String strDateFormat = "dd/MMM";
		SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
		System.out.println(objSDF.format(fecInicial)); 
		System.out.println(objSDF.format(fecFinal)); 
		
		recursos = capHoraService.findRecursoCapturadosSemana(fecInicial, fecFinal);
		
		if (recursos.size() > 0) {
			
			for (Long codRecurso : recursos) {
				
				List<CapHora> listCapHora = new ArrayList<CapHora>();
				listCapHora = capHoraService.findListCapHoraByPeriodoFechaRecurso(codRecurso, fecInicial, fecFinal);
				
				StringBuilder tablaDetalle = new StringBuilder();
				tablaDetalle.append("<div style=\"overflow-x:auto;\">");
//				tablaDetalle.append("<table align=\"left\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" bordercolor=\"#C3C3C3\" border=\"1\" width=\"100%\" style=\"margin: auto; font-size:9px; font-weight: 100;\">");
				tablaDetalle.append("<table style=\"margin: auto; font-size:9px; font-weight: 100; border-collapse: collapse; width: 100%;  border: 1px solid #ddd;\">");
				tablaDetalle.append("<thead>");
				tablaDetalle.append("<tr>");
				tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Fecha</th>");
				tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Proyecto</th>");
				tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Tarea</th>");
				tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Hrs.Total</th>");
				tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Hrs.Ok</th>");
				tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Hrs.NOk</th>");
				tablaDetalle.append("</tr>");
				tablaDetalle.append("</thead>");
				tablaDetalle.append("<tbody>");
				
				for (CapHora captura : listCapHora) {
					if(captura.getValNuevaActividad().equals(1L) || captura.getValNuevaActividad().equals(2L)) {
						captura.setDescProyecto(proyectoService.findByCodProyecto(captura.getCodProyecto()).getDescProyecto());
						captura.setDescActividadSecundaria(subtareaService.findOne(captura.getCodActividad()).getDescSubtarea());
					} else {
						captura.setDescProyecto(proyectoService.findByCodProyecto(captura.getCodProyecto()).getDescProyecto());
						captura.setDescActividadSecundaria(actividadService.findOne(captura.getCodActividad()).getDescActividadSecundaria());
					}
					
					tablaDetalle.append("<tr>");
					tablaDetalle.append("<td style=\"border-bottom: 1px solid #ddd; color: #000;\">");
					tablaDetalle.append(captura.getFecInicioActividad());
					tablaDetalle.append("</td>");
					tablaDetalle.append("<td style=\"border-bottom: 1px solid #ddd; color: #000;\">");
					tablaDetalle.append(captura.getDescProyecto());
					tablaDetalle.append("</td>");
					tablaDetalle.append("<td style=\"border-bottom: 1px solid #ddd; color: #000;\">");
					tablaDetalle.append(captura.getDescActividadSecundaria());
					tablaDetalle.append("</td>");
					tablaDetalle.append("<td style=\"border-bottom: 1px solid #ddd; color: #000;\">");
					tablaDetalle.append(captura.getValDuracionReportada());
					tablaDetalle.append("</td>");
					tablaDetalle.append(captura.getValDuracionValidad()> 0 ? "<td style=\"color : green; border-bottom: 1px solid #ddd;\">" + captura.getValDuracionValidad() :  "<td style=\"border-bottom: 1px solid #ddd; color: #000;\">" + captura.getValDuracionValidad());
					tablaDetalle.append("</td>");
					tablaDetalle.append(captura.getValDuracionRechazada() > 0 ? "<td style=\"color : red; border-bottom: 1px solid #ddd;\">" + captura.getValDuracionRechazada() :  "<td style=\"border-bottom: 1px solid #ddd; color: #000;\">" + captura.getValDuracionRechazada());
					tablaDetalle.append("</td>");
					tablaDetalle.append("</tr>");
				}
				
				tablaDetalle.append("</tbody>");
				tablaDetalle.append("</table>");
				tablaDetalle.append("</div><br>");
				
				Recurso recurso = recursoService.findOne(codRecurso);
				
				MailRequest request = new MailRequest();
				request.setName(recurso.getDescRecurso());
				request.setSubject("Resumen de captura de horas");
				request.setTo(recurso.getDescCorreoElectronico());
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("nombreRecurso", request.getName());
				model.put("mensaje", "<h3>Resumen de horas del " + objSDF.format(fecInicial) + " al " + objSDF.format(fecFinal) +"</h3>" + tablaDetalle.toString());
				model.put("imagen","<img data-cfsrc=\"images/status.png\" alt=\"\" data-cfstyle=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" style=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" src=\"https://sophitech.herokuapp.com/img/img-status.png\">");
				model.put("pie", "");
				
				MailResponse response = service.sendEmail(request, model);
				System.out.println(response.getMessage());
			}
		}
	}
	
	
	//Enviar resumen de horas aprobadas (por proyecto y enviado al aprobador)
	@Scheduled(cron="0 0 19 * * MON", zone="America/Mexico_City")
	public void enviaEstatusDeAprobacionPorProyecto() {
		List<Long> proyectos = new ArrayList<Long>();
		
		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_WEEK) - 1) * -1);
		Date fecFinal = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_YEAR, -6);
		Date fecInicial = calendar.getTime();
		
		System.out.println(fecInicial);
		System.out.println(fecFinal);
		
		String strDateFormat = "dd/MMM";
		SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
		System.out.println(objSDF.format(fecInicial)); 
		System.out.println(objSDF.format(fecFinal)); 
		
		proyectos = capHoraService.findProyectosCapturadosSemana(fecInicial, fecFinal);
		
		if (proyectos.size() > 0 ) {
			for (Long codProyecto : proyectos) {
				Proyecto proyecto = new Proyecto();
				proyecto = proyectoService.findOne(codProyecto);
				if(proyecto != null) {
					List<DetalleRecursoHoras> detalle = new ArrayList<DetalleRecursoHoras>();
					detalle = capHoraService.findProyectoRecursosResumenSemanal(proyecto.getCodProyecto(), fecInicial, fecFinal);
					if (detalle.size() > 0) {
						
						StringBuilder tablaDetalle = new StringBuilder();
						tablaDetalle.append("<div style=\"overflow-x:auto;\">");
						tablaDetalle.append("<table style=\"margin: 20px; font-size:9px; font-weight: 100; border-collapse: collapse; width: 100%;  border: 1px solid #ddd;\">");
						tablaDetalle.append("<thead>");
						tablaDetalle.append("<tr>");
						tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Recurso</th>");
						tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Hrs.Total</th>");
						tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Hrs.Ok</th>");
						tablaDetalle.append("<th style=\"background-color: #4CAF50; color: white;\">Hrs.NOk</th>");
						tablaDetalle.append("</tr>");
						tablaDetalle.append("</thead>");
						tablaDetalle.append("<tbody>");
						
						for (DetalleRecursoHoras det : detalle) {
							tablaDetalle.append("<tr>");
							tablaDetalle.append("<td style=\"border-bottom: 1px solid #ddd; color: #000;\">");
							tablaDetalle.append(det.getNombreRecurso());
							tablaDetalle.append("</td>");
							tablaDetalle.append("<td style=\"border-bottom: 1px solid #ddd; color: #000;\">");
							tablaDetalle.append(det.getCapturadas());
							tablaDetalle.append("</td>");
							tablaDetalle.append(det.getAprobadas() > 0 ? "<td style=\"color : green; border-bottom: 1px solid #ddd;\">" + det.getAprobadas() :  "<td style=\"border-bottom: 1px solid #ddd; color: #000;\">" + det.getAprobadas());
							tablaDetalle.append("</td>");
							tablaDetalle.append(det.getRechazadas() > 0 ? "<td style=\"color : red; border-bottom: 1px solid #ddd;\">" + det.getRechazadas() :  "<td style=\"border-bottom: 1px solid #ddd; color: #000;\">" + det.getRechazadas());
							tablaDetalle.append("</td>");
							tablaDetalle.append("</tr>");
						}
						
						tablaDetalle.append("</tbody>");
						tablaDetalle.append("</table>");
						tablaDetalle.append("</div><br>");
						
						Recurso recursoAprobador = recursoService.findOne(proyecto.getCodRecursoAprobador());
						
						MailRequest request = new MailRequest();
						request.setName(recursoAprobador.getDescRecurso());
						request.setSubject(proyecto.getDescProyecto() + " - Resumen de captura de horas ");
						request.setTo(recursoAprobador.getDescCorreoElectronico());
						
						Map<String, Object> model = new HashMap<String, Object>();
						model.put("nombreRecurso", request.getName());
						model.put("mensaje", "<h3>Resumen de horas del proyecto \""+ proyecto.getDescProyecto() + "\" del " + objSDF.format(fecInicial) + " al " + objSDF.format(fecFinal) +"</h3>" + tablaDetalle.toString());
						model.put("imagen","<img data-cfsrc=\"images/status.png\" alt=\"\" data-cfstyle=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" style=\"width: 200px; max-width: 400px; height: auto; margin: auto; display: block;\" src=\"https://sophitech.herokuapp.com/img/img-status.png\">");
						model.put("pie", "");
						
						MailResponse response = service.sendEmail(request, model);
						System.out.println(response.getMessage());
						
					}
				}
				
			}
			
		}
	}
	
	
	
}
