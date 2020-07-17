package com.sophi.app.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.FormSubmitEvent.MethodType;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sophi.app.models.entity.Agenda;
import com.sophi.app.models.entity.AreaComercial;
import com.sophi.app.models.entity.Cliente;
import com.sophi.app.models.entity.DetalleClienteAreaComercialId;
import com.sophi.app.models.entity.DetalleClienteAreaComercial;
import com.sophi.app.models.entity.DetalleClienteInfraestructura;
import com.sophi.app.models.entity.DetalleClienteInfraestructuraId;
import com.sophi.app.models.entity.DetalleInfraestructura;
import com.sophi.app.models.entity.DetalleProyectoContacto;
import com.sophi.app.models.entity.DetalleProyectoContactoId;
import com.sophi.app.models.entity.DetalleProyectoInfraestructura;
import com.sophi.app.models.entity.DetalleProyectoInfraestructuraId;
import com.sophi.app.models.entity.Proyecto;
import com.sophi.app.models.service.IAgendaService;
import com.sophi.app.models.service.IAreaComercialService;
import com.sophi.app.models.service.IClasificacionProyectoService;
import com.sophi.app.models.service.IClienteService;
import com.sophi.app.models.service.IDetalleClienteAreaComercialService;
import com.sophi.app.models.service.IDetalleClienteInfraestructuraService;
import com.sophi.app.models.service.IDetalleInfraestructuraService;
import com.sophi.app.models.service.IDetalleProyectoContactoService;
import com.sophi.app.models.service.IDetalleProyectoInfraestructuraService;
import com.sophi.app.models.service.IProyectoService;
import com.sophi.app.models.service.IRecursoService;
import com.sophi.app.models.service.ITipoFacturacionService;
import com.sophi.app.models.service.ITipoProyectoService;

@Controller
@SessionAttributes("recurso")
public class PreventaProyectoController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IAreaComercialService areaComercialService;
	
	@Autowired
	private ITipoProyectoService tipoProyectoService;
	
	@Autowired
	private IProyectoService proyectoService;
	
	@Autowired
	private IClasificacionProyectoService clasificacionproyectoService;
	
	@Autowired
	private IAgendaService agendaService;
	
	@Autowired
	private IDetalleInfraestructuraService detalleInfraestructuraService;
	
	@Autowired
	private IDetalleProyectoInfraestructuraService detalleProyectoInfraestructuraService;
	
	@Autowired
	private IDetalleClienteInfraestructuraService detalleClienteInfraestructuraService;
	
	@Autowired
	private IDetalleProyectoContactoService detalleProyectoContactoService;
	
	@Autowired
	private IDetalleClienteAreaComercialService detalleClienteAreaComercialService;
	
	@Autowired
	private ITipoFacturacionService tipoFacturacionService;
	
	@Autowired
	private IRecursoService recursoService;
	
	@RequestMapping(value = "/preventaProyecto", method = RequestMethod.GET)
	public String listarRecursos(Map<String, Object> modelP, Model model) {
		Proyecto proyecto = new Proyecto();
		modelP.put("proyecto", proyecto);
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("areasComerciales", areaComercialService.findAll());
		model.addAttribute("tiposProyecto", tipoProyectoService.findAll());
		model.addAttribute("tiposFacturacion", tipoFacturacionService.findAll());
		model.addAttribute("clasificacionesProyecto", clasificacionproyectoService.findAll());
		model.addAttribute("recursos", recursoService.findAll());
		return "preventaProyecto";
	}
	
	@RequestMapping(value="/preventaProyectoComplemento", method = RequestMethod.POST)
	public String guardarRecurso(Map<String, Object> modelM,@Valid Proyecto proyecto, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("areasComerciales", areaComercialService.findAll());
		model.addAttribute("tiposProyecto", tipoProyectoService.findAll());
		model.addAttribute("tiposFacturacion", tipoFacturacionService.findAll());
		model.addAttribute("clasificacionesProyecto", clasificacionproyectoService.findAll());
		
		if(result.hasErrors()) {
			return "preventaProyecto";
		}
		Date fechaHoy = new Date(); 
		proyecto.setFecRegistro(fechaHoy);
		
		//agrego el arrea comercial al cliente
		DetalleClienteAreaComercialId dcacId = new DetalleClienteAreaComercialId();
		DetalleClienteAreaComercial dcac = new DetalleClienteAreaComercial();
		dcacId.setCodAreaComercia(proyecto.getCodAreaComercial());
		dcacId.setCodCliente(proyecto.getProyectoId().getCodCliente());
		
		dcac.setDetalleClienteAreaComercialId(dcacId);
		dcac.setFecRegistro(fechaHoy);
		
		detalleClienteAreaComercialService.save(dcac);
		proyectoService.save(proyecto);
		
		System.out.println("Proyeto buscado "+proyecto.getDescProyecto()+" "+proyecto.getProyectoId().getCodCliente()+" "+proyecto.getProyectoId().getCodEstatusProyecto()+" "+ proyecto.getFecRegistro());
		//obtengo el proyecto guardado
		Proyecto proyectoN = proyectoService.findByDescProyectoAndProyectoIdCodClienteAndProyectoIdCodEstatusProyectoAndFecRegistro(proyecto.getDescProyecto(), proyecto.getProyectoId().getCodCliente(), proyecto.getProyectoId().getCodEstatusProyecto(), proyecto.getFecRegistro());
		model.addAttribute("contactos", agendaService.findContactosBycodCliente(proyecto.getProyectoId().getCodCliente()));
		model.addAttribute("tecnologias", detalleInfraestructuraService.findAll());
//		DetalleInfraestructura di= detalleInfraestructuraService.findAll().get(0);
//		System.out.println("Primero "+);
		modelM.put("proyecto", proyectoN);
		flash.addFlashAttribute("success", "Recurso guardado con exito");
		return "preventaProyectoComplemento";
	}
	
	@RequestMapping(value = "/agregarInfra/{codCliente}/{codProyecto}/{codDetalleInfra}/{codEstatusProyecto}")
	@ResponseBody
	public String agregarInfraestructura(Map<String, Object> modelM, Model model, @PathVariable(value = "codCliente") long codCliente,@PathVariable(value = "codProyecto") long codProyecto,@PathVariable(value = "codDetalleInfra") long codDetalleInfra, @PathVariable(value = "codEstatusProyecto") long codEstatusProyecto) {
		DetalleClienteInfraestructura dci = new DetalleClienteInfraestructura();
		DetalleClienteInfraestructuraId dciid = new DetalleClienteInfraestructuraId();
		DetalleProyectoInfraestructura dpi = new DetalleProyectoInfraestructura();
		DetalleProyectoInfraestructuraId dpiid = new DetalleProyectoInfraestructuraId();
		Date fechaHoy = new Date();
		
		System.out.println("codCliente "+codCliente+" codDEtalle "+codDetalleInfra+" fecha "+fechaHoy+" codEstatusProyecto "+codEstatusProyecto+" codProyecto "+codProyecto);
		
		dciid.setCodCliente(codCliente);
		dciid.setCodDetalleInfraestructura(codDetalleInfra);
		
		//dci.setDetalleClienteInfraestructuraId(dciid);
		
		dci.setDescVersion("");
		dci.setFecRegistro(fechaHoy);
		detalleClienteInfraestructuraService.save(dci);
		
		dpiid.setCodDetalleInfraestructura(codDetalleInfra);
		dpiid.setCodEstatusProyecto(codEstatusProyecto);
		dpiid.setCodProyecto(codProyecto);
		dpiid.setCodCliente(codCliente);
		
//		detalleProyectoInfraestructuraService.borrarByCodProyecto(codProyecto);
		dpi.setDetalleProyectoInfraestructuraId(dpiid);
		dpi.setDescVersion("");
		dpi.setFecRegistro(fechaHoy);
		detalleProyectoInfraestructuraService.save(dpi);
		Proyecto proyecto = proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(codProyecto, codEstatusProyecto, codCliente);
		modelM.put("proyecto", proyecto);
		
		return "preventaProyectoContactoInfraestructura";
    }
	
	@RequestMapping(value = "/eliminarInfra/{codPRoyecto}/{codEstatusProyecto}/{codCliente}")
    @ResponseBody
	public String eliminarInfra(Map<String, Object> modelM, Model model, @PathVariable(value = "codPRoyecto") long codProyecto, @PathVariable(value = "codEstatusProyecto") long codEstatusProyecto, @PathVariable(value = "codEstatusProyecto") long codCliente) {
		System.out.println("codProyecto borrarInfra"+codProyecto);
		
		detalleProyectoInfraestructuraService.borrarByCodProyecto(codProyecto);
		
		System.out.println("codProyecto "+codProyecto);
		Proyecto proyecto = proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(codProyecto, codEstatusProyecto, codCliente);
		modelM.put("proyecto", proyecto);
		return "preventaProyectoContactoInfraestructura";
    }
	
	@RequestMapping(value = "/guardarComplemento/{codProyecto}/{codContacto}/{codClasificacionProyecto}/{codEstatusProyecto}/{codCliente}")
	@ResponseBody
	public String guardarComplemento(Map<String, Object> modelM, Model model, @PathVariable(value = "codProyecto") long codProyecto, @PathVariable(value = "codContacto") long cod_contacto, @PathVariable(value = "codClasificacionProyecto") long codClasificacionProyecto, @PathVariable(value = "codEstatusProyecto") long codEstatusProyecto, @PathVariable(value = "codCliente") long codCliente) {
		DetalleProyectoContactoId dpcid = new DetalleProyectoContactoId();
		DetalleProyectoContacto dpc = new DetalleProyectoContacto();
		Date fechaHoy = new Date();
		Proyecto proyecto = proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(codProyecto, codEstatusProyecto, codCliente);
		
		detalleProyectoContactoService.borrarByCodProyecto(codProyecto);
		//Guarda contacto
		dpcid.setCodCliente(proyecto.getProyectoId().getCodCliente());
		dpcid.setCodContacto(cod_contacto);
		dpcid.setCodEstatusProyecto(proyecto.getProyectoId().getCodEstatusProyecto());
		dpcid.setCodProyecto(proyecto.getProyectoId().getCodProyecto());
		
		dpc.setValResponsableCliente(cod_contacto);
		dpc.setDetalleProyectoContactoId(dpcid);
		dpc.setFecRegistro(fechaHoy);
		
		detalleProyectoContactoService.save(dpc);

		proyectoService.save(proyecto);
		modelM.put("proyecto", proyecto);
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("contactos", agendaService.findContactosBycodCliente(proyecto.getProyectoId().getCodCliente()));
		model.addAttribute("areasComerciales", areaComercialService.findAll());
		model.addAttribute("tiposProyecto", tipoProyectoService.findAll());
		model.addAttribute("tiposFacturacion", tipoFacturacionService.findAll());
		model.addAttribute("clasificacionesProyecto", clasificacionproyectoService.findAll());
		
		System.out.println("Enviar a consulta");		
		return "redirect:/preventaProyectoConsulta/"+codProyecto+"/"+proyecto.getProyectoId().getCodEstatusProyecto()+"/"+proyecto.getProyectoId().getCodCliente();
    }
	
	@RequestMapping(value = "/eliminarContacto/{codProyecto}/{codEstatusProyecto}/{codCliente}")
    public String eliminarContacto(Map<String, Object> modelM, Model model, @PathVariable(value = "codProyecto") long codProyecto, @PathVariable(value = "codEstatusProyecto") long codEstatusProyecto, @PathVariable(value = "codCliente") long codCliente) {
		detalleProyectoContactoService.borrarByCodProyecto(codProyecto);
//		System.out.println("Contacto codProyecto "+codProyecto);
		Proyecto proyecto = proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(codProyecto, codEstatusProyecto, codCliente);
		modelM.put("proyecto", proyecto);
//		System.out.println("Elimina contacto  ");
		return "preventaProyectoConsulta";
    }
	
	@RequestMapping(value = "/guardarContactoProyecto/{codProyecto}/{codContacto}/{codEstatusProyecto}/{codCliente}")
    public String guardarContactoProyecto(Map<String, Object> modelM, Model model, @PathVariable(value = "codProyecto") long codProyecto, @PathVariable(value = "codContacto") long cod_contacto, @PathVariable(value = "codEstatusProyecto") long codEstatusProyecto, @PathVariable(value = "codCliente") long codCliente) {
		DetalleProyectoContactoId dpcid = new DetalleProyectoContactoId();
		DetalleProyectoContacto dpc = new DetalleProyectoContacto();
		Date fechaHoy = new Date();
		Proyecto proyecto = proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(codProyecto, codEstatusProyecto, codCliente);
//		System.out.println("Agregar contacto");
		//Guarda contacto
		dpcid.setCodCliente(codCliente);
		dpcid.setCodContacto(cod_contacto);
		dpcid.setCodEstatusProyecto(proyecto.getProyectoId().getCodEstatusProyecto());
		dpcid.setCodProyecto(proyecto.getProyectoId().getCodProyecto());
//		System.out.println("Agregar contacto SET ");
		dpc.setValResponsableCliente(cod_contacto);
		dpc.setDetalleProyectoContactoId(dpcid);
		dpc.setFecRegistro(fechaHoy);
		
		modelM.put("proyecto", proyecto);
		detalleProyectoContactoService.save(dpc);	
		return "preventaProyectoConsulta";
    }
	
	@RequestMapping(value = "/listaProyectos", method = RequestMethod.GET)
	public String listarRecursos(Model model) {
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("proyectos", proyectoService.findAll());
		//proyectoService.
		return "preventaProyectoLista";
	}
	
	@RequestMapping(value = "/cargaProyectos/{codCliente}", method = RequestMethod.GET)
	@ResponseBody
	public String cargaProyectos(Model model, @PathVariable(value = "codCliente") long codCliente) {
		model.addAttribute("clientes", clienteService.findAll());
		String contenido="";
		List<Proyecto> listaProyecto;
		
		if(codCliente==-1) {
			listaProyecto=proyectoService.findAll();
		}else{
			listaProyecto=proyectoService.findByProyectoIdCodCliente(codCliente);
		}
		
		contenido=contenido+"<div class=\"table-responsive\">"+
        "<table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">"+
        "<thead>"+
        "<tr>"+
        "<th>Código de proyecto</th>"+
        "<th>Proyecto</th>"+
        "<th>Fecha de inicio</th>"+
        "<th>Preventa o proyecto</th>"+
        "<th>Presupuesto</th>"+
        "<th>Detalle</th>"+
        "</tr>"+
        "</thead>"+
        "<tbody>";
		String estatusP="";
		String fechaI="";
        
		for(Proyecto p : listaProyecto) {
			if(p.getProyectoId().getCodEstatusProyecto()==1) {
				estatusP="Preventa";
				fechaI="N/A";
			}else {
				estatusP="Proyecto";
				fechaI=p.getFecIncioProyecto().toString();
			}
			contenido=contenido+"<tr>"+
			        "<td>"+p.getDescCodigoProyecto()+"</td>"+
			        "<td>"+p.getDescProyecto()+"</td>"+
			        "<td>"+fechaI+"</td>"+
			        "<td>"+estatusP+"</td>"+
			        "<td>"+p.getImpPresupuesto()+"</td>"+
			        "<td><a href=\"/preventaProyectoConsulta/"+p.getProyectoId().getCodProyecto()+"/"+p.getProyectoId().getCodEstatusProyecto()+"/"+p.getProyectoId().getCodCliente()+"\">Detalle</a></td>"+
					"</tr>";
		}
		
		contenido=contenido+"</tbody>"+
        "</table>"+
        "</div>";
		return contenido;
	}
	
	@RequestMapping(value="/preventaProyectoUpdate", method = RequestMethod.POST)
	public String preventaProyectoUpdate(Map<String, Object> modelM,@Valid Proyecto proyecto, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("areasComerciales", areaComercialService.findAll());
		model.addAttribute("tiposProyecto", tipoProyectoService.findAll());
		model.addAttribute("tiposFacturacion", tipoFacturacionService.findAll());
		model.addAttribute("clasificacionesProyecto", clasificacionproyectoService.findAll());
		model.addAttribute("proyectos", proyectoService.findAll());
		if(result.hasErrors()) {
			return "preventaProyectoConsulta";
		}
		
		proyectoService.save(proyecto);
		flash.addFlashAttribute("success", "Recurso guardado con exito");
		return "preventaProyectoLista";
	}
	
	@RequestMapping(value = "/areaComercialCliente/{codCliente}", method = RequestMethod.GET)
	@ResponseBody
	public String areaComercialCliente(Model model, @PathVariable(value = "codCliente") long codCliente) {
		
		String contenido="";
		List<DetalleClienteAreaComercial> listaAreasComercialesCliente = detalleClienteAreaComercialService.findByDetalleClienteAreaComercialIdCodCliente(codCliente);

		for(DetalleClienteAreaComercial cac : listaAreasComercialesCliente) {
			AreaComercial ac = areaComercialService.findOne(cac.getDetalleClienteAreaComercialId().getCodAreaComercia());
			contenido=contenido+"<option value=\""+ac.getCodAreaComercial()+"\">"+ac.getDescAreaComercial()+"</option>";
		}
		
		return contenido;
	}
	
	@RequestMapping(value = "/contactoCliente/{codCliente}", method = RequestMethod.GET)
	@ResponseBody
	public String contactoCliente(Model model, @PathVariable(value = "codCliente") long codCliente) {
		List<Agenda> listaA=agendaService.findContactosBycodCliente(codCliente);
		String contenido="";

		for(Agenda a : listaA) {
			contenido=contenido+"<option value=\""+a.getCodContacto()+"\">"+a.getDescContacto()+"</option>";
		}
		
		return contenido;
	}
	
	//Comienza de nuevo
	@RequestMapping(value = "/preventaProyectoAlta", method = RequestMethod.GET)
	public String preventaProyectoAlta(Map<String, Object> modelP, Model model) {
		Proyecto proyecto = new Proyecto();
		modelP.put("proyecto", proyecto);
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("areasComerciales", areaComercialService.findAll());
		model.addAttribute("tiposProyecto", tipoProyectoService.findAll());
		model.addAttribute("tiposFacturacion", tipoFacturacionService.findAll());
		model.addAttribute("clasificacionesProyecto", clasificacionproyectoService.findAll());
		model.addAttribute("recursos", recursoService.findAll());
		return "preventaProyectoAlta";
	}
	
	@RequestMapping(value = "/listaProyectosTodo", method = RequestMethod.GET)
	public String listaProyectos(Model model) {
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("proyectos", proyectoService.findAll());
		//proyectoService.
		return "preventaProyectoListaTodo";
	}
	
	@RequestMapping(value = "/cargaProyectosTodo/{codCliente}", method = RequestMethod.GET)
	@ResponseBody
	public String cargaProyectosTodo(Model model, @PathVariable(value = "codCliente") long codCliente) {
		model.addAttribute("clientes", clienteService.findAll());
		String contenido="";
		List<Proyecto> listaProyecto;
		
		if(codCliente==-1) {
			listaProyecto=proyectoService.findAll();
		}else{
			listaProyecto=proyectoService.findByProyectoIdCodCliente(codCliente);
		}
		
		contenido=contenido+"<div class=\"table-responsive\">"+
        "<table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">"+
        "<thead>"+
        "<tr>"+
        "<th>Código de proyecto</th>"+
        "<th>Proyecto</th>"+
        "<th>Fecha de inicio</th>"+
        "<th>Preventa o proyecto</th>"+
        "<th>Presupuesto</th>"+
        "<th>Detalle</th>"+
        "</tr>"+
        "</thead>"+
        "<tbody>";
		String estatusP="";
        
		for(Proyecto p : listaProyecto) {
			if(p.getProyectoId().getCodEstatusProyecto()==1) {
				estatusP="Preventa";
			}else {
				estatusP="Proyecto";
			}
			contenido=contenido+"<tr>"+
			        "<td>"+p.getDescCodigoProyecto()+"</td>"+
			        "<td>"+p.getDescProyecto()+"</td>"+
			        "<td>"+p.getFecIncioProyecto()+"</td>"+
			        "<td>"+estatusP+"</td>"+
			        "<td>"+p.getImpPresupuesto()+"</td>"+
			        "<td><a href=\"/preventaProyectoConsulta/"+p.getProyectoId().getCodProyecto()+"/"+p.getProyectoId().getCodEstatusProyecto()+"/"+p.getProyectoId().getCodCliente()+"\">Detalle</a></td>"+
					"</tr>";
		}
		
		contenido=contenido+"</tbody>"+
        "</table>"+
        "</div>";
		return contenido;
	}
	
	@RequestMapping(value = "/preventaProyectoConsulta/{codProyecto}/{codEstatusProyecto}/{codCliente}", method = RequestMethod.GET)
    public String preventaProyectoConsulta (Map<String, Object> modelM, Model model,@PathVariable(value = "codProyecto") long codProyecto, @PathVariable(value = "codEstatusProyecto") long codEstatusProyecto, @PathVariable(value = "codCliente") long codCliente) {
		Proyecto proyecto = proyectoService.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(codProyecto, codEstatusProyecto, codCliente);
		List<DetalleInfraestructura> listaDI= detalleInfraestructuraService.findAll();
		List<DetalleProyectoInfraestructura> listaDPI=detalleProyectoInfraestructuraService.findByDetalleProyectoInfraestructuraIdCodProyectoAndDetalleProyectoInfraestructuraIdCodEstatusProyectoAndDetalleProyectoInfraestructuraIdCodCliente(codProyecto, codEstatusProyecto, codCliente);
		List<DetalleInfraestructura> listaDISel= new ArrayList<DetalleInfraestructura>();
		List<DetalleProyectoContacto> listaDPC=detalleProyectoContactoService.findByDetalleProyectoContactoIdCodProyectoAndDetalleProyectoContactoIdCodEstatusProyectoAndDetalleProyectoContactoIdCodCliente(codProyecto, codEstatusProyecto, codCliente);
		
		for(int indice = 0;indice<listaDI.size();indice++){
//		    System.out.println(listaDI.get(indice).getCodDetalleInfraestructura());
		    for(int indice2 = 0;indice2<listaDPI.size();indice2++){
//		    	System.out.println("L"+listaDPI.get(indice2).getDetalleProyectoInfraestructuraId().getCod_detalle_infraestructura()  );
		    	if(listaDI.get(indice).getCodDetalleInfraestructura()==listaDPI.get(indice2).getDetalleProyectoInfraestructuraId().getCodDetalleInfraestructura()) {
//		    		System.out.println("Coincide "+listaDI.get(indice).getCodDetalleInfraestructura());
		    		listaDISel.add(listaDI.get(indice));
		    		listaDI.remove(listaDI.get(indice));
		    	}
		    }
		}
		
		String texto="";
		for(int indice = 0;indice<listaDISel.size();indice++){
			texto=texto+listaDISel.get(indice).getDescDetalle()+", ";
		}
		
		
//		System.out.println("tam "+listaDPC.size());
		Long codCon=0L;
		for(int indice = 0;indice<listaDPC.size();indice++){
//			System.out.println("contacto "+listaDPC.get(indice).getDetalleProyectoContactoId().getCod_contacto());
			codCon=listaDPC.get(indice).getDetalleProyectoContactoId().getCodContacto();
		}
		
		//lista real de areas comerciales
		List<DetalleClienteAreaComercial> listaDCAC=detalleClienteAreaComercialService.findByDetalleClienteAreaComercialIdCodCliente(codCliente);
		List<AreaComercial> listaAC= areaComercialService.findAll();
		List<AreaComercial> listaAux= new ArrayList<AreaComercial>();
		for(int indice = 0;indice<listaAC.size();indice++){
			for(int indice2 = 0;indice2<listaDCAC.size();indice2++){
				if(listaAC.get(indice).getCodAreaComercial()==listaDCAC.get(indice2).getDetalleClienteAreaComercialId().getCodAreaComercia()) {
					listaAux.add(listaAC.get(indice));
					System.out.println("AC "+listaAC.get(indice).getDescAreaComercial());
				}
			}
		}
		
		List<Cliente> listaCliente= clienteService.findAll();
		List<Cliente> listaClienteAux= new ArrayList<Cliente>();
		
		for(int indice = 0;indice<listaCliente.size();indice++){
			if(listaCliente.get(indice).getCodCliente()==codCliente) {
				listaClienteAux.add(listaCliente.get(indice));
			}
		}
		modelM.put("proyecto", proyecto);
		model.addAttribute("clientes", listaClienteAux);
		model.addAttribute("contactos", agendaService.findContactosBycodCliente(proyecto.getProyectoId().getCodCliente()));
		
		model.addAttribute("areasComerciales", areaComercialService.findAll());
		model.addAttribute("tiposProyecto", tipoProyectoService.findAll());
		model.addAttribute("tiposFacturacion", tipoFacturacionService.findAll());
		model.addAttribute("tecnologias", listaDI);
		model.addAttribute("tecAcept", listaDISel);
		model.addAttribute("textoTec", texto);
		model.addAttribute("codCon", codCon);
		model.addAttribute("recursos", recursoService.findAll());
			
		model.addAttribute("clasificacionesProyecto", clasificacionproyectoService.findAll());
		return "preventaProyectoConsulta";
    }
	
	@RequestMapping(value="/preventaProyectoCI", method = RequestMethod.POST)
	public String preventaProyectoCI(Map<String, Object> modelM,@Valid Proyecto proyecto, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("areasComerciales", areaComercialService.findAll());
		model.addAttribute("tiposProyecto", tipoProyectoService.findAll());
		model.addAttribute("tiposFacturacion", tipoFacturacionService.findAll());
		model.addAttribute("clasificacionesProyecto", clasificacionproyectoService.findAll());
		
		if(result.hasErrors()) {
			return "preventaProyectoAlta";
		}
		
		Date fechaHoy = new Date(); 
		proyecto.setFecRegistro(fechaHoy);
		
		//agrego el arrea comercial al cliente
		DetalleClienteAreaComercialId dcacId = new DetalleClienteAreaComercialId();
		DetalleClienteAreaComercial dcac = new DetalleClienteAreaComercial();
		dcacId.setCodAreaComercia(proyecto.getCodAreaComercial());
		dcacId.setCodCliente(proyecto.getProyectoId().getCodCliente());
		
		dcac.setDetalleClienteAreaComercialId(dcacId);
		dcac.setFecRegistro(fechaHoy);
				
		detalleClienteAreaComercialService.save(dcac);
		proyectoService.save(proyecto);
		
		System.out.println("Proyeto buscado "+proyecto.getDescProyecto()+" "+proyecto.getProyectoId().getCodCliente()+" "+proyecto.getProyectoId().getCodEstatusProyecto()+" "+ proyecto.getFecRegistro());
		//obtengo el proyecto guardado
		Proyecto proyectoN = proyectoService.findByDescProyectoAndProyectoIdCodClienteAndProyectoIdCodEstatusProyectoAndFecRegistro(proyecto.getDescProyecto(), proyecto.getProyectoId().getCodCliente(), proyecto.getProyectoId().getCodEstatusProyecto(), proyecto.getFecRegistro());
		model.addAttribute("contactos", agendaService.findContactosBycodCliente(proyecto.getProyectoId().getCodCliente()));
		model.addAttribute("tecnologias", detalleInfraestructuraService.findAll());

		modelM.put("proyecto", proyectoN);
		flash.addFlashAttribute("success", "Preventa/Proyecto guardado con exito");
		
		if(proyectoN.getProyectoId().getCodEstatusProyecto()==1) {
			model.addAttribute("proyectos", proyectoService.findAll());
			return "preventaProyectoListaTodo";
		}else {
			return "preventaProyectoContactoInfraestructura";
		}
		
	}
	
	@RequestMapping(value="/preventaProyectoActualizar", method = RequestMethod.POST)
	public String preventaProyectoContactoInfraestructura(Map<String, Object> modelM,@Valid Proyecto proyecto, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("areasComerciales", areaComercialService.findAll());
		model.addAttribute("tiposProyecto", tipoProyectoService.findAll());
		model.addAttribute("tiposFacturacion", tipoFacturacionService.findAll());
		model.addAttribute("clasificacionesProyecto", clasificacionproyectoService.findAll());
		model.addAttribute("proyectos", proyectoService.findAll());
		if(result.hasErrors()) {
			return "preventaProyectoConsulta";
		}
		
		System.out.println("codAreaComercial "+proyecto.getCodAreaComercial()+" codCliente "+(proyecto.getProyectoId().getCodCliente()));
		
		//agrego el area comercial al cliente
		Date fechaHoy = new Date(); 
		//agrego el arrea comercial al cliente
		DetalleClienteAreaComercialId dcacId = new DetalleClienteAreaComercialId();
		DetalleClienteAreaComercial dcac = new DetalleClienteAreaComercial();
		dcacId.setCodAreaComercia(proyecto.getCodAreaComercial());
		dcacId.setCodCliente(proyecto.getProyectoId().getCodCliente());
		
		dcac.setDetalleClienteAreaComercialId(dcacId);
		dcac.setFecRegistro(fechaHoy);
				
		detalleClienteAreaComercialService.save(dcac);
		System.out.println("codAreaComercial "+proyecto.getCodAreaComercial()+" codCliente "+(proyecto.getProyectoId().getCodCliente()));
		
		
		//guardo proyecto
		proyectoService.save(proyecto);
		flash.addFlashAttribute("success", "Recurso guardado con exito");
		
		//redirijo dependiendo
		if(proyecto.getProyectoId().getCodEstatusProyecto()==1) {
			model.addAttribute("proyectos", proyectoService.findAll());
			return "preventaProyectoListaTodo";
		}else {
			model.addAttribute("contactos", agendaService.findContactosBycodCliente(proyecto.getProyectoId().getCodCliente()));
			List<DetalleInfraestructura> listaDI= detalleInfraestructuraService.findAll();
			List<DetalleProyectoInfraestructura> listaDPI=detalleProyectoInfraestructuraService.findByDetalleProyectoInfraestructuraIdCodProyectoAndDetalleProyectoInfraestructuraIdCodEstatusProyectoAndDetalleProyectoInfraestructuraIdCodCliente(proyecto.getProyectoId().getCodProyecto(), proyecto.getProyectoId().getCodEstatusProyecto(), proyecto.getProyectoId().getCodCliente());
			List<DetalleInfraestructura> listaDISel= new ArrayList<DetalleInfraestructura>();
			for(int indice = 0;indice<listaDI.size();indice++){

			    for(int indice2 = 0;indice2<listaDPI.size();indice2++){

			    	if(listaDI.get(indice).getCodDetalleInfraestructura()==listaDPI.get(indice2).getDetalleProyectoInfraestructuraId().getCodDetalleInfraestructura()) {

			    		listaDISel.add(listaDI.get(indice));
			    		listaDI.remove(listaDI.get(indice));
			    	}
			    }
			}
			
			String texto="";
			for(int indice = 0;indice<listaDISel.size();indice++){
				texto=texto+listaDISel.get(indice).getDescDetalle()+", ";
			}
			List<DetalleProyectoContacto> listaDPC=detalleProyectoContactoService.findByDetalleProyectoContactoIdCodProyectoAndDetalleProyectoContactoIdCodEstatusProyectoAndDetalleProyectoContactoIdCodCliente(proyecto.getProyectoId().getCodProyecto(), proyecto.getProyectoId().getCodEstatusProyecto(), proyecto.getProyectoId().getCodCliente());
			Long codCon=0L;
			for(int indice = 0;indice<listaDPC.size();indice++){
				codCon=listaDPC.get(indice).getDetalleProyectoContactoId().getCodContacto();
			}
			modelM.put("proyecto", proyecto);
			model.addAttribute("tecnologias", listaDI);
			model.addAttribute("tecAcept", listaDISel);
			model.addAttribute("textoTec", texto);
			model.addAttribute("codCon", codCon);
			return "preventaProyectoContactoInfraestructura";
		}
	}
	
	@RequestMapping(value="/preventaProyectoContactoInfraestructura", method = RequestMethod.POST)
	public String preventaProyectoContactoInfra(Map<String, Object> modelM,@Valid Proyecto proyecto, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("areasComerciales", areaComercialService.findAll());
		model.addAttribute("tiposProyecto", tipoProyectoService.findAll());
		model.addAttribute("tiposFacturacion", tipoFacturacionService.findAll());
		model.addAttribute("clasificacionesProyecto", clasificacionproyectoService.findAll());
		
		if(result.hasErrors()) {
			return "preventaProyecto";
		}
		Date fechaHoy = new Date(); 
		proyecto.setFecRegistro(fechaHoy);
		
		//agrego el arrea comercial al cliente
		DetalleClienteAreaComercialId dcacId = new DetalleClienteAreaComercialId();
		DetalleClienteAreaComercial dcac = new DetalleClienteAreaComercial();
		dcacId.setCodAreaComercia(proyecto.getCodAreaComercial());
		dcacId.setCodCliente(proyecto.getProyectoId().getCodCliente());
		
		dcac.setDetalleClienteAreaComercialId(dcacId);
		dcac.setFecRegistro(fechaHoy);
		
		detalleClienteAreaComercialService.save(dcac);
		proyectoService.save(proyecto);
		
		System.out.println("Proyeto buscado "+proyecto.getDescProyecto()+" "+proyecto.getProyectoId().getCodCliente()+" "+proyecto.getProyectoId().getCodEstatusProyecto()+" "+ proyecto.getFecRegistro());
		//obtengo el proyecto guardado
		Proyecto proyectoN = proyectoService.findByDescProyectoAndProyectoIdCodClienteAndProyectoIdCodEstatusProyectoAndFecRegistro(proyecto.getDescProyecto(), proyecto.getProyectoId().getCodCliente(), proyecto.getProyectoId().getCodEstatusProyecto(), proyecto.getFecRegistro());
		model.addAttribute("contactos", agendaService.findContactosBycodCliente(proyecto.getProyectoId().getCodCliente()));
		model.addAttribute("tecnologias", detalleInfraestructuraService.findAll());

		List<DetalleProyectoContacto> listaDPC=detalleProyectoContactoService.findByDetalleProyectoContactoIdCodProyectoAndDetalleProyectoContactoIdCodEstatusProyectoAndDetalleProyectoContactoIdCodCliente(proyecto.getProyectoId().getCodProyecto(), proyecto.getProyectoId().getCodEstatusProyecto(), proyecto.getProyectoId().getCodCliente());
		//		System.out.println("tam "+listaDPC.size());
		Long codCon=0L;
		for(int indice = 0;indice<listaDPC.size();indice++){
//			System.out.println("contacto "+listaDPC.get(indice).getDetalleProyectoContactoId().getCod_contacto());
			codCon=listaDPC.get(indice).getDetalleProyectoContactoId().getCodContacto();
		}
		
		modelM.put("proyecto", proyectoN);
		model.addAttribute("codCon", codCon);
		flash.addFlashAttribute("success", "Recurso guardado con exito");
		return "preventaProyectoContactoInfraestructura";
	}

}
