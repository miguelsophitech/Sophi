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

import com.sophi.app.models.entity.DetalleClienteAreaComercial;
import com.sophi.app.models.entity.DetalleClienteAreaComercialId;
import com.sophi.app.models.entity.Proyecto;
import com.sophi.app.models.entity.ProyectoRecurso;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.RecursoGasto;
import com.sophi.app.models.entity.RecursoGastoId;
import com.sophi.app.models.entity.TipoGasto;
import com.sophi.app.models.service.IProyectoRecursoService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoGastoService;
import com.sophi.app.models.service.ITipoGastoService;
import com.sophi.app.models.service.RecursoGastoServiceImpl;

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
		Proyecto p = proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyecto(recursoGasto.getRecursoGastoId().getCodProyecto(), 2L);
		Date fechaHoy = new Date();
		List<ProyectoRecurso> listaProRec = proyectoRecursoService.findByProyectoRecursoIdCodRecurso(recursoGasto.getRecursoGastoId().getCodRecurso());
    	List<Proyecto> listaProyecto = new ArrayList<Proyecto>();
    	
		
		
		recursoGasto.getRecursoGastoId().setCodCliente(p.getProyectoId().getCodCliente());
		recursoGasto.getRecursoGastoId().setCodEstatusProyecto(p.getProyectoId().getCodEstatusProyecto());
		recursoGasto.getRecursoGastoId().setCodProyecto(p.getProyectoId().getCodProyecto());
		
		recursoGasto.setFecRegistro(fechaHoy);
    	
    	recursoGastoService.save(recursoGasto);
    	
    	for(ProyectoRecurso proRec:listaProRec) {
    		proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente());
    		listaProyecto.add(proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente()));
    	}
    	
    	model.addAttribute("tiposGastos", tipoGastoService.findAll());
    	model.addAttribute("proyectosAsignados", listaProyecto);
    	model.addAttribute("recursoGasto", recursoGasto);
    	model.addAttribute("r", recursoGasto.getRecursoGastoId().getCodRecurso());
    	
    	List<RecursoGasto> listaRG = recursoGastoService.findByRecursoGastoIdCodRecurso(recursoGasto.getRecursoGastoId().getCodRecurso());
    	
    	model.addAttribute("listaGastos", listaRG);
    	
    	return "listarRecursoGastos";
		
	}
    
    //lista de gastos recurso
    @RequestMapping(value = "/listarRecursoGastos/{codRecurso}", method = RequestMethod.GET)
	public String listaRecursoGastos(Model model,@PathVariable(value = "codRecurso") long codRecurso) {
    	
    	List<RecursoGasto> listaRG = recursoGastoService.findByRecursoGastoIdCodRecurso(codRecurso);
    	
    	model.addAttribute("listaGastos", listaRG);
    	model.addAttribute("r", codRecurso);
    	
    	return "listaRecursoGastos";
		
	}
    
    //Carga de pagina
    @RequestMapping(value = "/editarRecursoGastos/{codTipoGasto}/{codProyecto}/{codRecurso}/{codCliente}/{codEstatusProyecto}", method = RequestMethod.GET)
	public String edicionRecursoGasto(Model model,@PathVariable(value = "codTipoGasto") long codTipoGasto,@PathVariable(value = "codProyecto") long codProyecto,@PathVariable(value = "codRecurso") long codRecurso,@PathVariable(value = "codCliente") long codCliente,@PathVariable(value = "codEstatusProyecto") long codEstatusProyecto) {
    	
    	List<ProyectoRecurso> listaProRec = proyectoRecursoService.findByProyectoRecursoIdCodRecurso(codRecurso);
    	List<Proyecto> listaProyecto = new ArrayList<Proyecto>();
    	RecursoGastoId rgId = new RecursoGastoId();
    	
    	TipoGasto tg= tipoGastoService.findOne(codTipoGasto);
    	rgId.setCodCliente(codCliente);
    	rgId.setCodEstatusProyecto(codEstatusProyecto);
    	rgId.setCodProyecto(codProyecto);
    	rgId.setCodRecurso(codRecurso);
    	rgId.setTipoGasto(tg);
    	RecursoGasto recursoGasto = recursoGastoService.findOne(rgId);
		
    			
    	for(ProyectoRecurso proRec:listaProRec) {
    		proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente());
    		listaProyecto.add(proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(proRec.getProyectoRecursoId().getCodProyecto(), proRec.getProyectoRecursoId().getCodEstatusProyecto(), proRec.getProyectoRecursoId().getCodCliente()));
    	}
    	
    	System.out.println("CodRecurso "+codRecurso);
    	model.addAttribute("tiposGastos", tipoGastoService.findAll());
    	model.addAttribute("proyectosAsignados", listaProyecto);
    	model.addAttribute("recursoGasto", recursoGasto);
    	model.addAttribute("r", codRecurso);
    	
    	List<RecursoGasto> listaRG = recursoGastoService.findByRecursoGastoIdCodRecurso(recursoGasto.getRecursoGastoId().getCodRecurso());
    	
    	model.addAttribute("listaGastos", listaRG);
    	
		return "listaRecursoGastos";
		
	}
    
    //Ver de pagina
    @RequestMapping(value = "/verRecursoGastos/{codTipoGasto}/{codProyecto}/{codRecurso}/{codCliente}/{codEstatusProyecto}", method = RequestMethod.GET)
	public String verRecursoGasto(Model model,@PathVariable(value = "codTipoGasto") long codTipoGasto,@PathVariable(value = "codProyecto") long codProyecto,@PathVariable(value = "codRecurso") long codRecurso,@PathVariable(value = "codCliente") long codCliente,@PathVariable(value = "codEstatusProyecto") long codEstatusProyecto) {
    	
    	List<ProyectoRecurso> listaProRec = proyectoRecursoService.findByProyectoRecursoIdCodRecurso(codRecurso);
    	List<Proyecto> listaProyecto = new ArrayList<Proyecto>();
    	RecursoGastoId rgId = new RecursoGastoId();
    	
    	TipoGasto tg= tipoGastoService.findOne(codTipoGasto);
    	rgId.setCodCliente(codCliente);
    	rgId.setCodEstatusProyecto(codEstatusProyecto);
    	rgId.setCodProyecto(codProyecto);
    	rgId.setCodRecurso(codRecurso);
    	rgId.setTipoGasto(tg);
    	RecursoGasto recursoGasto = recursoGastoService.findOne(rgId);
		
    			
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
    @GetMapping(value = "/imagenGasto/{codTipoGasto}/{codProyecto}/{codRecurso}/{codCliente}/{codEstatusProyecto}")
	public void verImagenGasto(@PathVariable(value = "codTipoGasto") long codTipoGasto,@PathVariable(value = "codProyecto") long codProyecto,@PathVariable(value = "codRecurso") long codRecurso,@PathVariable(value = "codCliente") long codCliente,@PathVariable(value = "codEstatusProyecto") long codEstatusProyecto,  HttpServletResponse response) throws IOException{
		response.setContentType("image/jpeg");
		RecursoGastoId rgId = new RecursoGastoId();
		TipoGasto tg= tipoGastoService.findOne(codTipoGasto);
    	rgId.setCodCliente(codCliente);
    	rgId.setCodEstatusProyecto(codEstatusProyecto);
    	rgId.setCodProyecto(codProyecto);
    	rgId.setCodRecurso(codRecurso);
    	rgId.setTipoGasto(tg);
    	RecursoGasto recursoGasto = recursoGastoService.findOne(rgId);
    	System.out.println("Recurso "+recursoGasto.getRecursoGastoId().getTipoGasto().getDescTipoGasto());
		InputStream is = new ByteArrayInputStream(recursoGasto.getComprobante());
		IOUtils.copy(is, response.getOutputStream());
	}
    
    
    
}