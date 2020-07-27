package com.sophi.app.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import com.sophi.app.models.entity.Proyecto;
import com.sophi.app.models.entity.ProyectoRecurso;
import com.sophi.app.models.entity.RecursoGasto;
import com.sophi.app.models.entity.TipoGasto;
import com.sophi.app.models.service.IProyectoRecursoService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoGastoService;
import com.sophi.app.models.service.ITipoGastoService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@SessionAttributes("recursoGasto")
public class RecursoGastoController {

    @Autowired
    private ITipoGastoService tipoGastoService;
    
    @Autowired
    private IProyectoRecursoService proyectoRecursoService;
    
    @Autowired
    private IProyectoService proyectoService;
    
    @Autowired
    private IRecursoGastoService recursoGastoService;


    //Carga de pagina
    @RequestMapping(value = "/recursoGastoAlta/{codRecurso}", method = RequestMethod.GET)
	public String recursosGastosA(Model model,@PathVariable(value = "codRecurso") long codRecurso) {
		
    	List<ProyectoRecurso> listaProRec = proyectoRecursoService.findByProyectoRecursoIdCodRecurso(codRecurso);
    	List<Proyecto> listaProyecto = new ArrayList<Proyecto>();
    	RecursoGasto recursoGasto = new RecursoGasto();
		
    			
    	for(ProyectoRecurso proRec:listaProRec) {
    		proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente());
    		listaProyecto.add(proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente()));
    	}
    	
    	System.out.println("CodRecurso "+codRecurso);
    	model.addAttribute("tiposGastos", tipoGastoService.findAll());
    	model.addAttribute("proyectosAsignados", listaProyecto);
    	model.addAttribute("recursoGasto", recursoGasto);
    	model.addAttribute("r", codRecurso);
    	
		return "recursoGastoAlta";
		
	}
    
    //Guarda gasto
    @RequestMapping(value="/recursoGastoAlta", method = RequestMethod.POST)
	public String recursoGastoAlta(Map<String, Object> modelM,@Valid RecursoGasto recursoGasto, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
		Proyecto p = proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyecto(recursoGasto.getCodProyecto(), 2L);
		Date fechaHoy = new Date();
		List<ProyectoRecurso> listaProRec = proyectoRecursoService.findByProyectoRecursoIdCodRecurso(recursoGasto.getCodRecurso());
    	List<Proyecto> listaProyecto = new ArrayList<Proyecto>();
    	TipoGasto tp = tipoGastoService.findOne(recursoGasto.getTipoGasto().getCodTipoGasto());
    	recursoGasto.setTipoGasto(tp);
		
		
		recursoGasto.setCodCliente(p.getProyectoId().getCodCliente());
		recursoGasto.setCodEstatusProyecto(p.getProyectoId().getCodEstatusProyecto());
		recursoGasto.setCodProyecto(p.getProyectoId().getCodProyecto());
		
		recursoGasto.setFecRegistro(fechaHoy);
		
		if(recursoGasto.getComprobante()==null || recursoGasto.getComprobante().length==0) {
			if(recursoGasto.getCodRecursoGasto()!=null) {
				RecursoGasto rg = recursoGastoService.findOne(recursoGasto.getCodRecursoGasto());
				if(rg!=null) {
					recursoGasto.setComprobante(rg.getComprobante());
				}
			}
		}
    	
    	recursoGastoService.save(recursoGasto);
    	
    	for(ProyectoRecurso proRec:listaProRec) {
    		proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente());
    		listaProyecto.add(proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente()));
    	}
    	
    	model.addAttribute("tiposGastos", tipoGastoService.findAll());
    	model.addAttribute("proyectosAsignados", listaProyecto);
    	model.addAttribute("recursoGasto", recursoGasto);
    	model.addAttribute("r", recursoGasto.getCodRecurso());
    	
    	List<RecursoGasto> listaRG = recursoGastoService.findByCodRecurso(recursoGasto.getCodRecurso());
    	
    	model.addAttribute("listaGastos", listaRG);
    	
    	return "redirect:/listarRecursoGastos/"+recursoGasto.getCodRecurso();
		
	}
    
    //lista de gastos recurso
    @RequestMapping(value = "/listarRecursoGastos/{codRecurso}", method = RequestMethod.GET)
	public String listaRecursoGastos(Model model,@PathVariable(value = "codRecurso") long codRecurso) {
    	
    	List<RecursoGasto> listaRG = recursoGastoService.findByCodRecurso(codRecurso);
    	
    	model.addAttribute("listaGastos", listaRG);
    	model.addAttribute("r", codRecurso);
    	
    	return "listaRecursoGastos";
		
	}
    
    //editar de pagina
    @RequestMapping(value = "/editarRecursoGastos/{codRecursoGasto}/{codTipoGasto}/{codProyecto}/{codRecurso}/{codCliente}/{codEstatusProyecto}", method = RequestMethod.GET)
	public String edicionRecursoGasto(Model model,@PathVariable(value = "codRecursoGasto") long codRecursoGasto, @PathVariable(value = "codTipoGasto") long codTipoGasto,@PathVariable(value = "codProyecto") long codProyecto,@PathVariable(value = "codRecurso") long codRecurso,@PathVariable(value = "codCliente") long codCliente,@PathVariable(value = "codEstatusProyecto") long codEstatusProyecto) {
    	
    	List<ProyectoRecurso> listaProRec = proyectoRecursoService.findByProyectoRecursoIdCodRecurso(codRecurso);
    	List<Proyecto> listaProyecto = new ArrayList<Proyecto>();
    	
    	RecursoGasto recursoGasto = recursoGastoService.findOne(codRecursoGasto);
		
    			
    	for(ProyectoRecurso proRec:listaProRec) {
    		proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente());
    		listaProyecto.add(proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente()));
    	}
    	
    	System.out.println("CodRecurso "+codRecurso);
    	model.addAttribute("tiposGastos", tipoGastoService.findAll());
    	model.addAttribute("proyectosAsignados", listaProyecto);
    	model.addAttribute("recursoGasto", recursoGasto);
    	model.addAttribute("r", codRecurso);
    	
    	List<RecursoGasto> listaRG = recursoGastoService.findByCodRecurso(recursoGasto.getCodRecurso());
    	
    	model.addAttribute("listaGastos", listaRG);
    	
		return "recursoGastoAlta";
		
	}
    
    //Ver de pagina
    @RequestMapping(value = "/verRecursoGastos/{codRecursoGasto}/{codTipoGasto}/{codProyecto}/{codRecurso}/{codCliente}/{codEstatusProyecto}", method = RequestMethod.GET)
	public String verRecursoGasto(Model model,@PathVariable(value = "codRecursoGasto") long codRecursoGasto, @PathVariable(value = "codTipoGasto") long codTipoGasto,@PathVariable(value = "codProyecto") long codProyecto,@PathVariable(value = "codRecurso") long codRecurso,@PathVariable(value = "codCliente") long codCliente,@PathVariable(value = "codEstatusProyecto") long codEstatusProyecto) {
    	
    	List<ProyectoRecurso> listaProRec = proyectoRecursoService.findByProyectoRecursoIdCodRecurso(codRecurso);
    	List<Proyecto> listaProyecto = new ArrayList<Proyecto>();

    	RecursoGasto recursoGasto = recursoGastoService.findOne(codRecursoGasto);
		
    			
    	for(ProyectoRecurso proRec:listaProRec) {
    		proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente());
    		listaProyecto.add(proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente()));
    	}
    	
    	System.out.println("CodRecurso "+codRecurso);
    	model.addAttribute("tiposGastos", tipoGastoService.findAll());
    	model.addAttribute("proyectosAsignados", listaProyecto);
    	model.addAttribute("recursoGasto", recursoGasto);
    	model.addAttribute("r", codRecurso);
    	
		return "recursoGastoVer";
		
	}
    
    //imagen de gasto
    @GetMapping(value = "/imagenGasto/{codRecursoGasto}/{codTipoGasto}/{codProyecto}/{codRecurso}/{codCliente}/{codEstatusProyecto}")
	public void verImagenGasto(@PathVariable(value = "codRecursoGasto") long codRecursoGasto,@PathVariable(value = "codTipoGasto") long codTipoGasto,@PathVariable(value = "codProyecto") long codProyecto,@PathVariable(value = "codRecurso") long codRecurso,@PathVariable(value = "codCliente") long codCliente,@PathVariable(value = "codEstatusProyecto") long codEstatusProyecto,  HttpServletResponse response) throws IOException{
		response.setContentType("image/jpeg");
    	RecursoGasto recursoGasto = recursoGastoService.findOne(codRecursoGasto);
    	System.out.println("Recurso "+recursoGasto.getTipoGasto().getDescTipoGasto());
		InputStream is = new ByteArrayInputStream(recursoGasto.getComprobante());
		IOUtils.copy(is, response.getOutputStream());
	}
    
    //eliminar recursoGasto
  //Carga de pagina
   @RequestMapping(value = "/eliminarRecursoGastos/{codRecursoGasto}/{codTipoGasto}/{codProyecto}/{codRecurso}/{codCliente}/{codEstatusProyecto}", method = RequestMethod.GET)
   public String eliminarRecursoGasto(Model model,@PathVariable(value = "codRecursoGasto") long codRecursoGasto, @PathVariable(value = "codTipoGasto") long codTipoGasto,@PathVariable(value = "codProyecto") long codProyecto,@PathVariable(value = "codRecurso") long codRecurso,@PathVariable(value = "codCliente") long codCliente,@PathVariable(value = "codEstatusProyecto") long codEstatusProyecto) {
	   RecursoGasto rg = recursoGastoService.findOne(codRecursoGasto);
	   recursoGastoService.delete(rg);
    	
	   return "redirect:/listarRecursoGastos/"+rg.getCodRecurso();
		
	}
    
    
}