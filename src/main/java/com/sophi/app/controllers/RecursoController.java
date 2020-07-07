package com.sophi.app.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sophi.app.Utiles;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.service.IJornadaService;
import com.sophi.app.models.service.IProveedorService;
import com.sophi.app.models.service.IPuestoService;
import com.sophi.app.models.service.IRecursoService;
import com.sophi.app.models.service.ITipoRecursoService;


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
	private ITipoRecursoService tipoREcursoService;
	
	@Autowired
	private IJornadaService jornadaService;
	
	@GetMapping(value = "/verRecurso/{id}")
	public String verRecurso(@PathVariable(value="id") Long codRecurso, Map<String, Object> model, RedirectAttributes flash, HttpServletResponse response) {
		response.setContentType("image/jpeg");
		Recurso recurso = recursoService.findOne(codRecurso);
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
		
		model.put("recurso",recurso);
		model.put("titulo", "Información de " + recurso.getDescRecurso());
		
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
		model.put("tipoRecursoList", tipoREcursoService.findAll());
		model.put("proveedorList",proveedorService.findAll());
		return "formRecurso";
	}
	
	@RequestMapping(value="/formRecurso", method = RequestMethod.POST)
	public String guardarRecurso(@Valid Recurso recurso, BindingResult result, Model model, @RequestParam("fotoPerfil") MultipartFile fotoPerfil, RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario recurso");
			Utiles util = new Utiles();
			model.addAttribute("etapaList", util.recursosEtapaList());
			model.addAttribute("puestoList", puestoService.findAll());
			model.addAttribute("jornadaList", jornadaService.findAll());
			model.addAttribute("tipoRecursoList", tipoREcursoService.findAll());
			model.addAttribute("proveedorList",proveedorService.findAll());
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
		String mensajeFlash = (recurso.getCodRecurso() != null)?"Recurso editado con éxito!": "Recurso guardado con exito";
		
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
		model.put("tipoRecursoList", tipoREcursoService.findAll());
		model.put("proveedorList",proveedorService.findAll());
		return "formRecurso";
	}

}
