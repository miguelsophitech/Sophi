package com.sophi.app.controllers;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sophi.app.models.entity.Actividad;
import com.sophi.app.models.entity.ActividadDto;
import com.sophi.app.models.entity.ActividadPlan;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.service.IActividadService;
import com.sophi.app.models.service.IRecursoService;

@Controller
public class PlaneacionController {
	
	@Autowired
	private IActividadService actividadService;
	
	@Autowired
	private IRecursoService recursoService;
	
	@GetMapping("/planeacionProyecto")
	public String planeacionProyecto() {
		return "formActividadesPlan";
	}
	
	@PostMapping("/guardarPlan")
	public String guardarPlanCSV(@ModelAttribute ActividadDto actividades, Model model) {
		if(!(actividades.getActividades().isEmpty() && (actividades.getActividades() == null))) {
		actividadService.saveAll(actividades.getActividades());
		} else {
			System.out.println("No cargo nada");
		}
		return "redirect:/";
	}
	
	@PostMapping("/subirPlan")
	public String subirPlanCSV(@RequestParam("archivoCsvPlan") MultipartFile archivoCsvPlan, Model model) {
		if (archivoCsvPlan.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {
        	
            try (Reader reader = new BufferedReader(new InputStreamReader(archivoCsvPlan.getInputStream()))) {

				CsvToBean<ActividadPlan> csvToBean = new CsvToBeanBuilder<ActividadPlan>(reader)
						.withType(ActividadPlan.class)
						.withIgnoreLeadingWhiteSpace(true)
						.build();

                
                List<ActividadPlan> actividadesPlanStg = csvToBean.parse();
                List<ActividadPlan> actividadesPlan = new ArrayList<ActividadPlan>();
                
                List<Actividad> actividadesCargar = new ArrayList<Actividad>();
                
                String actividadPrimaria ="";
                String actividadPrimariaAux = "";
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                
                List<Long> listCodRecursos = new ArrayList<Long>(); 
                
                String fechaInicioPlan = "";
                String fechaFinPlan = "";
                
                for (int i = 0; i < actividadesPlanStg.size(); i++) {
                	
                	String actividadSecundaria ="";
                	
                	if (i == 1) {
						fechaInicioPlan = actividadesPlanStg.get(i).getInicio().substring(0, 10);
						fechaFinPlan = actividadesPlanStg.get(i).getFin().substring(0, 10);
					}
                	
                	
                	if (i > 1) {
	                	ActividadPlan ap = new ActividadPlan();
	                	ap =actividadesPlanStg.get(i);
	                	ap.setSerie(i);
	                	actividadesPlan.add(ap);
	                	
	                	if(actividadesPlanStg.get(i).getRecursos().isEmpty()) {
	                		actividadPrimariaAux = (actividadPrimariaAux + " >> " + actividadesPlanStg.get(i).getNombre()).trim();
	                		actividadPrimaria = actividadPrimariaAux;
	                		continue;
	                	} else {
	                		actividadPrimariaAux="";
	                		actividadSecundaria = actividadesPlanStg.get(i).getNombre();
	                	                	
	                	
	                	
	                	String[] listaRecursos = actividadesPlanStg.get(i).getRecursos().split(",");
	                	if (listaRecursos.length>0) {
	                		for (int j = 0; j < listaRecursos.length; j++) {
	                			String descRecurso = listaRecursos[j].split("\\s+")[0];
	    	                	String descApellidoPaterno = listaRecursos[j].split("\\s+")[1];
	    	                	
	    	                	Actividad actividad = new Actividad();
	    	                	actividad.setDescActividadPrimaria(actividadPrimaria);
	    	                	actividad.setDescActividadSecundaria(actividadSecundaria);
	    	                	actividad.setValNumActividad((long) i);
	    	                	actividad.setCodCliente(1L);
	    	                	actividad.setCodEstatusProyecto(1L);
	    	                	actividad.setCodProyecto(11L);
	    	                	actividad.setValDuracionActividad(Float.parseFloat(actividadesPlanStg.get(i).getEsfuerzo().replaceAll("hora","").replaceAll("s","")));
	    	                	actividad.setValNuevaActividad(0L);
	    	                	actividad.setFecInicioActividad(format.parse(actividadesPlanStg.get(i).getInicio().substring(0, 10)));
	    	                	actividad.setFecFinActividad(format.parse(actividadesPlanStg.get(i).getFin().substring(0, 10)));
	    	                	actividad.setCodActividadDependiente(actividadesPlanStg.get(i).getPredecesora());
	    	                	
	    	                	System.out.println(descRecurso + " >>> " + descApellidoPaterno);
	    	                	List<Recurso> listRecursoActividad = new ArrayList<Recurso>(); 
	    	                	listRecursoActividad=recursoService.findByNombreApellido(descRecurso, descApellidoPaterno);
	    	                	
	    	                	
	    	                	
	    	                	if (!listRecursoActividad.isEmpty()) {
	    	                		System.out.println(listRecursoActividad.get(0).getCodRecurso());
	    	                		actividad.setCodRecurso(listRecursoActividad.get(0).getCodRecurso());
	    	                		listCodRecursos.add(listRecursoActividad.get(0).getCodRecurso());
	    	                		actividad.setNombreRecurso(descRecurso + " " + descApellidoPaterno );
		    	                	actividadesCargar.add(actividad);
		    	                	
	    	                	} else {
	    	                		System.out.println("No existe Recurso");
	    	                	}
	    	                	
	    	                	
	    	                	System.out.println(actividadPrimaria);
	    	                	System.out.println(actividadSecundaria);
	    	                	
	    	                	System.out.println("------------------------------------------RECURSO");
	    	                	
							}
	                	}
	                	
	                	
	                	}
	                	
	                	System.out.println("------------------------------------------ACTIVIDAD");
	                	
                	}
                	
                	
				}
                

                // TODO: guardar en base.. 
//                actividadService.saveAll(actividadesCargar);
                
                List<Long> listCodRecursosUnicos = new ArrayList<>(new HashSet<>(listCodRecursos));
                

                model.addAttribute("actividadesPlan", actividadesPlan);
                ActividadDto actividadesCargarDto = new ActividadDto();
                actividadesCargarDto.setActividades(actividadesCargar);
                model.addAttribute("totalRecursosIdentificados", listCodRecursosUnicos.size());
                model.addAttribute("fechaInicioPlan",fechaInicioPlan);
                model.addAttribute("fechaFinPlan",fechaFinPlan);
                model.addAttribute("actividadesCargarDto", actividadesCargarDto);
                model.addAttribute("status", true);

            } catch (Exception ex) {
                model.addAttribute("message", "Ocurrio un problema mientras se procesa el archivo.");
                model.addAttribute("status", false);
            }
        	
        }
		
		return "listaActividadesPlan";
	}
	

}
