package com.sophi.app.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

//import javax.validation.Valid;

import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.RecursoVacaciones;
import com.sophi.app.models.entity.Rol;
import com.sophi.app.models.entity.SolicitudVacaciones;
import com.sophi.app.Utiles;
import com.sophi.app.models.entity.AprobacionHoras;
import com.sophi.app.models.entity.AprobacionHorasDto;
import com.sophi.app.models.entity.AuxActividadHorasRecurso;
import com.sophi.app.models.entity.CapHora;
import com.sophi.app.models.entity.DetalleRecursoHoras;
import com.sophi.app.models.entity.Proyecto;
import com.sophi.app.models.entity.ProyectoRecurso;
import com.sophi.app.models.service.IActividadService;
import com.sophi.app.models.service.IAprobacionHorasService;
import com.sophi.app.models.service.ICapHoraService;
import com.sophi.app.models.service.IProyectoRecursoService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoService;
import com.sophi.app.models.service.IRecursoVacacionesService;
import com.sophi.app.models.service.IRolService;
import com.sophi.app.models.service.ISolicitudVacacionesService;
import com.sophi.app.models.service.ISubtareaService;
import com.sophi.app.models.service.RecursoVacacionesServiceImpl;
import com.sophi.app.models.service.RolServiceImpl;

import javassist.bytecode.analysis.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AprobacionVacacionesController {
	
	@Autowired
	private IRecursoVacacionesService recursoVacacionesService;
	
	@Autowired
	private ISolicitudVacacionesService solicitudVacacionesService;

    @Autowired
    private IAprobacionHorasService aprobacionHorasService;

    @Autowired
    private IRecursoService recursoService;
    
    @Autowired
    private IProyectoService proyectoService;
    
    @Autowired
    private IProyectoRecursoService proyectoRecursoService;
    
    @Autowired
    private ISubtareaService subtareaService;
    
    @Autowired
    private IActividadService actividadService;
    
    @Autowired
    private ICapHoraService capHoraService;
    
    @Autowired
    private IRolService rolService;
    
    @RequestMapping(value = "/aprobacionVacaciones/{email}", method = RequestMethod.GET)
    public String aprobacionVacaciones(Model model, @PathVariable(value = "email") String email){
    	//Obtiene el codigo del recurso solicitante
    	Long codRecurso = null;
    	codRecurso = recursoService.findByDescCorreoElectronico(email).getCodRecurso();
    	
    	List<Rol> roles = new ArrayList<Rol>();
    	List<String> cadenaRol = new ArrayList<String>();
    	
    	//creacion e incializacion de lista temporales
		List<Proyecto> listaProyecto = new ArrayList<Proyecto>();
		
    	
    	//si existe el recurso solicitante...
    	if (codRecurso != null) {
    		
    		roles = rolService.findByCodRecurso(codRecurso);
    		for (Rol rol : roles) {
				cadenaRol.add(rol.getDescRol());
				System.out.println(rol.getDescRol());
			}
    		
    		//Obtiene listado de proyectos donde el recurso es lider.
        	List<Proyecto> listaProyectosLider = new ArrayList<Proyecto>();
        	String r = "ROLE_ADMIN";
        	if (cadenaRol.contains(r)) {
        		listaProyectosLider = proyectoService.findAll();
        	} else {
        		listaProyectosLider = proyectoService.findListaProyectosRecursoAprobadorTodos(codRecurso);
        	}
        	
        	//si el recurso es lider y tiene proyectos a cargo
        	if (listaProyectosLider.size() > 0) {
        		
        		//recorre la lista de proyectos de recurso lider solicitante
        		for (Proyecto proyecto : listaProyectosLider) {
        			//si el proyecto se encuentra en estatus 1 o 2  se incluye como proyecto activo
        			//y se obtienen las horas a aprobar 
        			if(proyecto.getCodEstatusProyecto().equals(1L) || proyecto.getCodEstatusProyecto().equals(2L)) {
        				listaProyecto.add(proyecto);
        			}
        		}
        		
        	}
    	
    	}
    	
//    	Agregar proyecto admin
//    	listaProyecto.add(proyectoService.findOne(1L));

        
        model.addAttribute("proyectos", listaProyecto);
        return "aprobacionVacaciones";
    }
    
    
    @RequestMapping(value = "/listadoRecursos/{codProyecto}", method = RequestMethod.GET)
    public String listadoRecursosCaptura(Model model, @PathVariable(value = "codProyecto") String codProyecto){
    	String todos = "no";
    	//Listas temporales para almacenar info
    	List<Long> proyectos = new ArrayList<Long>();
    	List<ProyectoRecurso> listProyectoRecurso = new ArrayList<ProyectoRecurso>();
    	List<RecursoVacaciones> listaDetalle = new ArrayList<RecursoVacaciones>();
    
    	//Se obtienen los codigos de proyectos del recurso solicitante (-1 es todos)
    	for(String codPro : codProyecto.split(",")) {
    		if (codPro.equals("-1")) {
    			todos="si";
    			continue;
    		} else {
    			proyectos.add(Long.parseLong(codPro));
    		}
    	}
    	
    	//Se obtiene los recurso de los proyectos solicitados
    	for (Long clvPro : proyectos) {
    			listProyectoRecurso.addAll(proyectoRecursoService.findByProyectoRecursoIdCodProyecto(clvPro));
		}
    	
    	//si existen recursos en los proyectos
		if (listProyectoRecurso.size() > 0){
			if (todos.equals("si")) {
				HashSet<Long> recursosUnicos = new HashSet<Long>();
				for (ProyectoRecurso proyectoRecurso : listProyectoRecurso) {
					recursosUnicos.add(proyectoRecurso.getProyectoRecursoId().getCodRecurso());
				}
				if(recursosUnicos.size()>0) {
					for (Long codR : recursosUnicos) {
					RecursoVacaciones recursoVacaciones = recursoVacacionesService.findById(codR);
					Recurso recurso = recursoService.findOne(recursoVacaciones.getCodRecurso());
					recursoVacaciones.setNombreRecurso(recurso.getDescRecurso() + " " + recurso.getDescApellidoPaterno());
					
					List<SolicitudVacaciones> listaSolicitudes = new ArrayList<>();
					listaSolicitudes = solicitudVacacionesService.findByCodRecurso(recursoVacaciones.getCodRecurso());
					Long diasPendientes = 0L;
					if (listaSolicitudes.size()>0) {
						for (SolicitudVacaciones solicitud : listaSolicitudes) {
							if(solicitud.getFecAprobacion() == null && solicitud.getFecCancelacion() == null && solicitud.getFecRechazo() == null) {
								diasPendientes += solicitud.getValDiasSolicitados();
							}
						}
					}
					recursoVacaciones.setValPendientes(diasPendientes);
					
					listaDetalle.add(recursoVacaciones);
					}
				}
			} else {
				for (ProyectoRecurso proyectoRecurso : listProyectoRecurso) {
					RecursoVacaciones recursoVacaciones = recursoVacacionesService.findById(proyectoRecurso.getProyectoRecursoId().getCodRecurso());
					Recurso recurso = recursoService.findOne(recursoVacaciones.getCodRecurso());
					recursoVacaciones.setNombreRecurso(recurso.getDescRecurso() + " " + recurso.getDescApellidoPaterno());
					
					List<SolicitudVacaciones> listaSolicitudes = new ArrayList<>();
					listaSolicitudes = solicitudVacacionesService.findByCodRecurso(recursoVacaciones.getCodRecurso());
					Long diasPendientes = 0L;
					if (listaSolicitudes.size()>0) {
						for (SolicitudVacaciones solicitud : listaSolicitudes) {
							if(solicitud.getFecAprobacion() == null && solicitud.getFecCancelacion() == null && solicitud.getFecRechazo() == null) {
								diasPendientes += solicitud.getValDiasSolicitados();
							}
						}
					}
					recursoVacaciones.setValPendientes(diasPendientes);
					listaDetalle.add(recursoVacaciones);
				}
			}
			
			
		}
    	
		model.addAttribute("listRecursos", listaDetalle);
    	
    	return "layout/plantilla-caphora :: recursos-vacaciones";
    }
    
    
    @RequestMapping(value = "/aprobacionVacaciones/detalle", method = RequestMethod.GET)
    public String detalleAprobacionHorasProyecto(@RequestParam(value = "r") Long codRecurso,
    											Model model){
    	
    	List<SolicitudVacaciones> listaSolicitudes = new ArrayList<>();
		listaSolicitudes = solicitudVacacionesService.findByCodRecurso(codRecurso);
    	
    	Recurso recurso = recursoService.findOne(codRecurso);
    	model.addAttribute("nombre", recurso.getDescRecurso() + " " + recurso.getDescApellidoPaterno());
    	model.addAttribute("listaSolicitudes",listaSolicitudes);
    	
    	return "layout/plantilla-caphora :: detalle-recursos-vacaciones";
    }
    
    
	public List<CapHora> actividadesCapturadas(Long codRecurso,Date fecCaptura, String proyectos){
		
		String[] listaProyectos = proyectos.split(",");
		List<Long> codProyectos = new ArrayList<Long>();
		codProyectos.add(1L);
		for (String proy : listaProyectos) {
			codProyectos.add(Long.parseLong(proy));
		}
		
		List<CapHora> listActHoraCapturadas = new ArrayList<CapHora>();
		List<CapHora> listActHoraCapturadasF = new ArrayList<CapHora>();
		listActHoraCapturadas = capHoraService.findListCapHoraByFechaRecurso(fecCaptura, codRecurso);
		
		for (CapHora capHora : listActHoraCapturadas) {
			if (codProyectos.contains(capHora.getCodProyecto())) {
				if(capHora.getValNuevaActividad().equals(1L) || capHora.getValNuevaActividad().equals(2L)) {
					capHora.setDescProyecto(proyectoService.findByCodProyecto(capHora.getCodProyecto()).getDescProyecto());
					capHora.setDescActividadSecundaria(subtareaService.findOne(capHora.getCodActividad()).getDescSubtarea());
					capHora.setHorasPlaneadas((float) 0);
					listActHoraCapturadasF.add(capHora);
				} else {
					capHora.setDescProyecto(proyectoService.findByCodProyecto(capHora.getCodProyecto()).getDescProyecto());
					capHora.setDescActividadSecundaria(actividadService.findOne(capHora.getCodActividad()).getDescActividadSecundaria());
					capHora.setHorasPlaneadas(Float.parseFloat(actividadService.findOne(capHora.getCodActividad()).getValDuracionActividad()));
					listActHoraCapturadasF.add(capHora);
				}
			} 
		}
		 return listActHoraCapturadasF;
	}
    
    
    public Date sumarDia(Date fecha,int dia) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(fecha);
    	calendar.add(Calendar.DAY_OF_YEAR, dia);
    	return calendar.getTime();
    }
    
    @RequestMapping(value = "/updAprobacionSolicitud", method = RequestMethod.GET)
    @ResponseBody
    public String updAprobacionSolicitud(@RequestParam(value = "codSolicitud") Long codSolicitud, 
    	@RequestParam(value = "aprobador") String mail ) {
    	Long codRecurso = recursoService.findByDescCorreoElectronico(mail).getCodRecurso();
    	SolicitudVacaciones solicitud = null;
    	solicitud = solicitudVacacionesService.findById(codSolicitud);
    	if (solicitud != null) {
    		solicitud.setFecRechazo(null);
    		solicitud.setFecCancelacion(null);
    		solicitud.setFecAprobacion(new Utiles().getFechaActual());
    		solicitud.setCodRecursoAprobador(codRecurso);
    		
    		RecursoVacaciones recursoVacaciones = null;
    		recursoVacaciones = recursoVacacionesService.findById(solicitud.getCodRecurso());
    		if(recursoVacaciones != null) {
    			recursoVacaciones.setValAprobado(recursoVacaciones.getValAprobado() + solicitud.getValDiasSolicitados());
//    			recursoVacaciones.setValDisponibles(recursoVacaciones.getValDisponibles() - solicitud.getValDiasSolicitados());
    			recursoVacacionesService.save(recursoVacaciones);
    		}
    		
    		solicitudVacacionesService.save(solicitud);
    		return "ok";
    	} else {
    		return "noOK";
    	}
    	
    }
    
    @RequestMapping(value = "/updRechazoSolicitud", method = RequestMethod.GET)
    @ResponseBody
    public String updRechazoSolicitud(@RequestParam(value = "codSolicitud") Long codSolicitud, 
    	@RequestParam(value = "aprobador") String mail ) {
    	Long codRecurso = recursoService.findByDescCorreoElectronico(mail).getCodRecurso();
    	SolicitudVacaciones solicitud = null;
    	solicitud = solicitudVacacionesService.findById(codSolicitud);
    	if (solicitud != null) {
    		solicitud.setFecRechazo(new Utiles().getFechaActual());
    		solicitud.setFecCancelacion(null);
    		solicitud.setFecAprobacion(null);
    		solicitud.setCodRecursoAprobador(codRecurso);
    		
    		RecursoVacaciones recursoVacaciones = null;
    		recursoVacaciones = recursoVacacionesService.findById(solicitud.getCodRecurso());
    		if(recursoVacaciones != null) {
//    			recursoVacaciones.setValAprobado(recursoVacaciones.getValAprobado() + solicitud.getValDiasSolicitados());
    			recursoVacaciones.setValDisponibles(recursoVacaciones.getValDisponibles() + solicitud.getValDiasSolicitados());
    			recursoVacacionesService.save(recursoVacaciones);
    		}
    		
    		solicitudVacacionesService.save(solicitud);
    		return "ok";
    	} else {
    		return "noOK";
    	}
    	
    }
    
    @RequestMapping(value = "/cancelarSolicitud", method = RequestMethod.GET)
    @ResponseBody
    public String cancelarSolicitud(@RequestParam(value = "codSolicitud") Long codSolicitud) {
    	SolicitudVacaciones solicitud = null;
    	solicitud = solicitudVacacionesService.findById(codSolicitud);
    	if (solicitud != null) {
    		RecursoVacaciones recursoVacaciones = null;
    		recursoVacaciones = recursoVacacionesService.findById(solicitud.getCodRecurso());
    		if(recursoVacaciones != null) {
    			recursoVacaciones.setValDisponibles(recursoVacaciones.getValDisponibles() + solicitud.getValDiasSolicitados());
    			recursoVacacionesService.save(recursoVacaciones);
    		}
    		solicitudVacacionesService.delete(solicitud);
    		return "ok";
    	} else {
    		return "noOK";
    	}
    	
    }
    
    
    

}
