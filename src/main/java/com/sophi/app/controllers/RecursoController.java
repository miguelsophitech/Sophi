package com.sophi.app.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sophi.app.Utiles;
import com.sophi.app.models.entity.ContactoEmergencia;
import com.sophi.app.models.entity.Herramienta;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.RecursoEscolaridad;
import com.sophi.app.models.service.IEstadoCivilService;
import com.sophi.app.models.service.IEtapaEscolarService;
import com.sophi.app.models.service.IGradoEscolarService;
import com.sophi.app.models.service.IAreaRecursoService;
import com.sophi.app.models.service.IContactoEmergenciaService;
import com.sophi.app.models.service.IEstadoHerramientaService;
import com.sophi.app.models.service.IHerramientaService;
import com.sophi.app.models.service.IJornadaService;
import com.sophi.app.models.service.IParentescoService;
import com.sophi.app.models.service.IProveedorService;
import com.sophi.app.models.service.IPuestoService;
import com.sophi.app.models.service.IRecursoEscolaridadService;
import com.sophi.app.models.service.IRecursoService;
import com.sophi.app.models.service.ITipoHerramientaService;
import com.sophi.app.models.service.ITipoRecursoService;
import com.sophi.app.models.service.ITipoSangreService;


@Controller
@SessionAttributes("recurso")
public class RecursoController {
	
	@Autowired
	private IRecursoService recursoService;
	
	@Autowired
	private IProveedorService proveedorService;
	
	@Autowired
	private IPuestoService puestoService;
	
	@Autowired
	private ITipoRecursoService tipoRecursoService;
	
	@Autowired
	private IJornadaService jornadaService;
	
	@Autowired
	private IEstadoCivilService estadoCivilService;
	
	@Autowired
	private ITipoSangreService tipoSangreService;
  
	@Autowired
	private IAreaRecursoService areaRecursoService;
	
	@Autowired
	private IHerramientaService herramientaService;
	
	@Autowired
	private IRecursoEscolaridadService recursoEscolaridadService;
	
	@Autowired
	private IGradoEscolarService gradoEscolarService;
	
	@Autowired
	private IEtapaEscolarService estapaEscolarService;
	
	@Autowired
	private IParentescoService parentescoService;
	
	@Autowired
	private IContactoEmergenciaService contactoEmergenciaService;
	
	@Autowired
	private ITipoHerramientaService tipoHerramientaService;
	
	@Autowired
	private IEstadoHerramientaService estadoHerramientaService;
	
	@GetMapping(value = "/verRecurso/{id}")
	public String verRecurso(@PathVariable(value="id") Long codRecurso, Map<String, Object> model, RedirectAttributes flash, HttpServletResponse response) {
		response.setContentType("image/jpeg");
		Recurso recurso = recursoService.findOne(codRecurso);
		Herramienta herramienta = new Herramienta();
		if (codRecurso > 0) {
			recurso = recursoService.findOne(codRecurso);
			if(recurso == null) {
				flash.addFlashAttribute("error", "El código del recurso no existe en base de datos!");
				return "redirect:/listarRecursos";
			}
		} else {
			flash.addFlashAttribute("error", "El código del recurso no debe ser cero!");
			return "redirect:/listarRecursos";
		}
		
		
		List<RecursoEscolaridad> listaEscolaridad = recursoEscolaridadService.findByCodRecurso(codRecurso);
		
		model.put("recurso",recurso);
		model.put("recursoEdit",recurso);
		model.put("areaRecursoList", areaRecursoService.findAll());
		model.put("puestoList", puestoService.findAll());
		model.put("jornadaList", jornadaService.findAll());
		model.put("tipoRecursoList", tipoRecursoService.findAll());
		model.put("proveedorList",proveedorService.findAll());
		model.put("estadoCivilList", estadoCivilService.findAll());
		model.put("titulo", "Información de " + recurso.getDescRecurso());
		model.put("listaEstadoCivil", estadoCivilService.findAll());
		model.put("listaTipoSangre", tipoSangreService.findAll());
		model.put("herramienta", herramienta);
		model.put("listaEscolaridad", listaEscolaridad);
		model.put("listaGradoEscolar", gradoEscolarService.findAll());
		model.put("listaEtapaEscolar", estapaEscolarService.findAll());
		model.put("listaContactoEmergencia", contactoEmergenciaService.findByCodRecurso(codRecurso));
		model.put("listaParentesco", parentescoService.findAll());
		model.put("listaHerramientas", herramientaService.findByCodRecurso(codRecurso));
		model.put("tipoHerramientaList", tipoHerramientaService.findAll());
		model.put("estadoHerramientaList", estadoHerramientaService.findAll());
		return "verRecurso";
	}
	
	
	@GetMapping(value = "/fotoRecursoPerfil/{id}")
	public void verFotoRecursoPerfil(@PathVariable(value="id") Long codRecurso,  HttpServletResponse response) throws IOException{
		response.setContentType("image/jpeg");
		Recurso recurso = recursoService.findOne(codRecurso);
		InputStream is = new ByteArrayInputStream(recurso.getFoto());
		IOUtils.copy(is, response.getOutputStream());
	}
	
	@GetMapping(value = "/fotoRecursoCv/{id}")
	public void verFotoRecursoCv(@PathVariable(value="id") Long codRecurso,  HttpServletResponse response) throws IOException{
		response.setContentType("image/jpeg");
		Recurso recurso = recursoService.findOne(codRecurso);
		InputStream is = new ByteArrayInputStream(recurso.getFotoCv());
		IOUtils.copy(is, response.getOutputStream());
	}
	
	@RequestMapping(value = "/listarRecursos", method = RequestMethod.GET)
	public String listarRecursos(Model model) {
		model.addAttribute("titulo", "Listado de recursos");
		model.addAttribute("recursos", recursoService.findAll());
		return "listaRecursos";
	}
	
	@RequestMapping(value = "/formRecurso")
	public String crearRecurso(Map<String, Object> model) {
		Recurso recurso = new Recurso();
		Utiles util = new Utiles();
		model.put("recurso", recurso);
		model.put("titulo", "Formulario recurso");
		model.put("etapaList", util.recursosEtapaList());
		model.put("puestoList", puestoService.findAll());
		model.put("jornadaList", jornadaService.findAll());
		model.put("tipoRecursoList", tipoRecursoService.findAll());
		model.put("proveedorList",proveedorService.findAll());
		model.put("estadoCivilList", estadoCivilService.findAll());
		return "formRecurso";
	}
	
	@RequestMapping(value="/guardaActualizaRecurso", method = RequestMethod.POST)
	@ResponseBody
	public String guardarActualizaRecurso(@Valid Recurso recurso, Model model) {
		System.out.println("entra a guardar");
		recursoService.save(recurso);
		return "OK";
	}
	
	@RequestMapping(value="/formRecurso", method = RequestMethod.POST)
	public String guardarRecurso(@Valid Recurso recurso, BindingResult result, Model model, @RequestParam("fotoPerfil") MultipartFile fotoPerfil, RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario recurso");
			Utiles util = new Utiles();
			model.addAttribute("etapaList", util.recursosEtapaList());
			model.addAttribute("puestoList", puestoService.findAll());
			model.addAttribute("jornadaList", jornadaService.findAll());
			model.addAttribute("tipoRecursoList", tipoRecursoService.findAll());
			model.addAttribute("proveedorList",proveedorService.findAll());
			model.addAttribute("estadoCivilList", estadoCivilService.findAll());
			return "formRecurso";
		}
		
		if(!fotoPerfil.isEmpty()) {
			try {
				byte[] bytesFotoPerfil = fotoPerfil.getBytes();
				flash.addFlashAttribute("info", "Ha subido correctamente "+ fotoPerfil.getOriginalFilename());
				recurso.setFoto(bytesFotoPerfil);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
//		if(!fotoCv.isEmpty()) {
//			try {
//				byte[] bytesFotoCv = fotoCv.getBytes();
//				flash.addFlashAttribute("info", "Ha subido correctamente "+ fotoCv.getOriginalFilename());
//				recurso.setFotoCv(bytesFotoCv);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		String mensajeFlash = (recurso.getCodRecurso() != null)?"Recurso editado con éxito!": "Recurso guardado con éxito";
		
		recursoService.save(recurso);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listarRecursos";
	}
	
	@RequestMapping(value = "/formRecurso/{id}")
	public String editarRecurso(@PathVariable(value = "id") Long codRecurso, Map<String, Object> model, RedirectAttributes flash) {
		Recurso recurso = null;
		if (codRecurso > 0) {
			recurso = recursoService.findOne(codRecurso);
			if(recurso == null) {
				flash.addFlashAttribute("error", "El codigo del recurso no existe en base de datos!");
				return "redirect:/listarRecursos";
			}
		} else {
			flash.addFlashAttribute("error", "El codigo del recurso no debe ser cero!");
			return "redirect:/listarRecursos";
		}
		model.put("recurso", recurso);
		model.put("titulo", "Formulario recurso");
		Utiles util = new Utiles();
		model.put("etapaList", util.recursosEtapaList());
		model.put("puestoList", puestoService.findAll());
		model.put("jornadaList", jornadaService.findAll());
		model.put("tipoRecursoList", tipoRecursoService.findAll());
		model.put("proveedorList",proveedorService.findAll());
		model.put("estadoCivilList", estadoCivilService.findAll());
		return "formRecurso";
	}
	
	
	@RequestMapping(value="/guardaEscolaridad",method = RequestMethod.GET)
	@ResponseBody
	public String guardaEscolaridad(@RequestParam Long cr, 
			@RequestParam String ia, 
			@RequestParam Long ge,
			@RequestParam Long ee,  
			@RequestParam String cp, Model model) {
		
		RecursoEscolaridad re = new RecursoEscolaridad();
		re.setCodEtapaEscolar(ee);
		re.setDescInstitucionAcademica(ia);
		re.setDescCedulaProfesional(cp);
		re.setCodGradoEscolar(ge);
		re.setCodRecurso(cr);
		
		recursoEscolaridadService.save(re);
		return "ok";

	}
	
	@RequestMapping(value="/obtieneEscolaridad",method = RequestMethod.GET)
	public String obtieneEscolaridad(@RequestParam Long codRecurso, Model model) {
		
		model.addAttribute("listaGradoEscolar", gradoEscolarService.findAll());
		model.addAttribute("listaEtapaEscolar", estapaEscolarService.findAll());
		
		List<RecursoEscolaridad> listaEscolaridad = recursoEscolaridadService.findByCodRecurso(codRecurso);
		model.addAttribute("listaEscolaridad", listaEscolaridad);
		return "verRecurso :: fragmentEscolaridad";
	}
	
	@RequestMapping(value="/borrarEscolaridad",method = RequestMethod.GET)
	@ResponseBody
	public String borrarEscolaridad(@RequestParam Long cre, Model model) {
		recursoEscolaridadService.delete(cre);
		return "ok";

	}
	
	@RequestMapping(value="/guardaContactoEmergencia",method = RequestMethod.GET)
	@ResponseBody
	public String guardaContactoEmergencia(@RequestParam String nc, 
			@RequestParam String tc, 
			@RequestParam Long pc,
			@RequestParam Long ed,  
			@RequestParam Long cr, Model model) {
		
		ContactoEmergencia ce = new ContactoEmergencia();
		ce.setDescNombreContacto(nc);
		ce.setCodParentesco(pc);
		ce.setValDependienteEconomico(ed);
		ce.setCodRecurso(cr);
		ce.setDescTelContactoEmergencia(tc);
		
		contactoEmergenciaService.save(ce);
		return "ok";

	}
	
	@RequestMapping(value="/obtieneContactosEmergencia",method = RequestMethod.GET)
	public String obtieneContactosEmergencia(@RequestParam Long codRecurso, Model model) {
		
		model.addAttribute("listaContactoEmergencia", contactoEmergenciaService.findByCodRecurso(codRecurso));
		model.addAttribute("listaParentesco", parentescoService.findAll());
		
		return "verRecurso :: fragmentContactoEmergencia";
	}
	
}
