package com.sophi.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sophi.app.models.entity.PreguntaClima;
import com.sophi.app.models.entity.Recurso;
import com.sophi.app.models.entity.RespuestaRecursoClima;
import com.sophi.app.models.service.IPreguntaClimaService;
import com.sophi.app.models.service.IPreguntaRespuestaClimaService;
import com.sophi.app.models.service.IRecursoService;
import com.sophi.app.models.service.IRespuestaRecursoClimaService;

@Controller
public class FlashController {
	
	@Autowired
	private IRecursoService recursoService;
	
	@Autowired
	private IPreguntaClimaService preguntaClimaService;
	
	@Autowired
	private IPreguntaRespuestaClimaService preguntaRespuestaClimaService;
	
	@Autowired
	private IRespuestaRecursoClimaService respuestaRecursoClimaService;
	
	@RequestMapping(value = "/guardaRespuestaFlash")
	@ResponseBody
	public String guardaRespuestaFlash(@RequestParam String recurso, @RequestParam Long respuesta,Model model) {
		Recurso recursoRes = recursoService.findByDescCorreoElectronico(recurso);
		RespuestaRecursoClima respuestaRecurso = new RespuestaRecursoClima();
		
		respuestaRecurso.setPreguntaRespuestaClima(preguntaRespuestaClimaService.findOne(respuesta));
		respuestaRecurso.setRecurso(recursoRes);
		respuestaRecursoClimaService.save(respuestaRecurso);
		return "Gracias";
	} 
	
	@RequestMapping(value = "/guardaPreguntaFlash")
	@ResponseBody
	public String guardaPreguntaFlash(@RequestParam String pregunta, Model model) {
		PreguntaClima preguntaClima = preguntaClimaService.findOne(1L);
		preguntaClima.setDescPreguntaClima(pregunta);
		preguntaClima.setValTipo(1L);
		preguntaClimaService.save(preguntaClima);
		return "1";
	} 
	
	@GetMapping(value="/datosRecursoLoginFlash/{login}")
	public String cargarDatosRecursoLoginFlash(@PathVariable String login, Model model){
		recursoService.findByDescCorreoElectronico(login);
		model.addAttribute("recursoFlash", recursoService.findByDescCorreoElectronico(login));
		model.addAttribute("preguntaFlash", preguntaClimaService.findOne(1L));
		model.addAttribute("respuestasFlash",preguntaRespuestaClimaService.findAll());
		return "layout/layout :: modalFlash";
	}
	
	@RequestMapping(value = "/validaRespuestaFlash")
	@ResponseBody
	public String validaRespuestaFlash(@RequestParam String recurso,Model model) {
		Recurso recursoRes = recursoService.findByDescCorreoElectronico(recurso);
		List<RespuestaRecursoClima> listadoRespuesta = respuestaRecursoClimaService.findByRecurso(recursoRes);
		if (listadoRespuesta.size() > 0) {
			return "0";
		} else {
			return "1";
		}
	} 
	
	@RequestMapping(value = "/flashClima")
	public String flashClima(Model model) {
		model.addAttribute("preguntaFlash", preguntaClimaService.findOne(1L));
		return "clima";
	} 
	
	

}
