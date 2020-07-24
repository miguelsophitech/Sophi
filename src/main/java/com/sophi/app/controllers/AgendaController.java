package com.sophi.app.controllers;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sophi.app.models.entity.Agenda;
import com.sophi.app.models.entity.Cargo;
import com.sophi.app.models.entity.Cliente;
import com.sophi.app.models.service.IAgendaService;
import com.sophi.app.models.service.ICargoService;
import com.sophi.app.models.service.IClienteService;

@Controller
@SessionAttributes("agenda")
public class AgendaController {
	
	@Autowired
	private IAgendaService agendaService;

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private ICargoService cargoService;

	
	@RequestMapping(value = "/agenda", method = RequestMethod.GET)
	public String Agenda(Model model) {
		List<Cliente> listaClientes = clienteService.findAll();
		model.addAttribute("titulo", "Lista de Contactos");
		model.addAttribute("contactos", agendaService.findAll());
		model.addAttribute("clientes", listaClientes);
		return "agenda";
	}

	@RequestMapping(value = "/cargaContactos", method = RequestMethod.GET)
	@ResponseBody
	public String cargaContactos(@RequestParam("id") Long codCliente, Model model) {
		List<Agenda> agendaList;

		if(codCliente == -1){
			agendaList = agendaService.findAll();
		} 
		
		else{
			agendaList = agendaService.findContactosBycodCliente(codCliente);
		}

		String contenido = "";
		contenido = contenido + "<div class=\"table-responsive\">"+
		"<table class=\"table table-hover\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">"+
		"<thead>"+
		"<tr>"+
		"<th>Nombre</th>"+
        "<th>Puesto</th>"+
        "<th>E-mail</th>"+
        "<th>Tel&eacute;fono Celular</th>"+
		"<th>Tel&eacute;fono Empresa</th>"+
		"<th>Extensi&oacute;n</th>"+
		"<th>Acciones</th>"+
        "</tr>"+
		"</thead>"+
		"<tbody>";
		
		for(Agenda agenda:agendaList) {
			String tablaContactos = "";
			tablaContactos = "<tr><td>"+agenda.getDescContacto()+"</td>";
			tablaContactos += "<td>"+agenda.getDescPuesto()+"</td>";
			tablaContactos += "<td>"+agenda.getDescCorreoElectronico()+"</td>";
			tablaContactos += "<td>"+agenda.getDescTelCelular()+"</td>";
			tablaContactos += "<td>"+agenda.getDescTelEmpresa()+"</td>";
			tablaContactos += "<td>"+agenda.getDescTelExt()+"</td>";
			tablaContactos += "<td><div class=\"btn-group btn-group-sm\" role=\"group\">";
			tablaContactos += "<a type=\"button\" class=\"btn btn-info\" href=\"/dataContacto/"+agenda.getCodContacto()+"\" data-toggle=\"tooltip\" title=\"Ver\"><i class=\"far fa-address-book\"></i></a>";
			tablaContactos += "<a type=\"button\" class=\"btn btn-info\" href=\"/formContacto/"+agenda.getCodContacto()+"\" data-toggle=\"tooltip\" title=\"Editar\"><i class=\"far fa-edit\"></i></a>";
			tablaContactos += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"eliminar("+agenda.getCodContacto()+");\" data-toggle=\"tooltip\" title=\"Borrar\"><i class=\"far fa-trash-alt\"></i></button></div></td></tr>";
			contenido += tablaContactos;
		}
		
		contenido = contenido + "</tbody>"+
        "</table>"+
        "</div>";
		return contenido;
	}
	
	@RequestMapping(value = "/dataContacto/{id}")
	public String verContacto(@PathVariable(value = "id") Long codContacto, Map<String, Object> model, RedirectAttributes flash) {
		Agenda agenda = null;
		if (codContacto > 0) {
			agenda = agendaService.findOne(codContacto);
			if(agenda == null) {
				flash.addFlashAttribute("error", "El codigo del contacto no existe en base de datos!");
				return "redirect:/agenda";
			}
		} else {
			flash.addFlashAttribute("error", "El codigo del contacto no debe ser cero!");
			return "redirect:/agenda";
		}
		agenda.setNombreCliente(clienteService.findOne(agenda.getCodCliente()).getDescCliente());
		agenda.setNombreCargo(cargoService.findOne(agenda.getCodCargo()).getDescCargo());
		model.put("agenda", agenda);
		model.put("textoTitulo","Información de tu contacto");
		model.put("textoDescriptivo","Encuentra aquí la información completa de tu contacto.");
		return "dataContacto";
	}
	
	@RequestMapping(value = "/formContacto")
	public String crearContacto(Map<String, Object> model) {
		Agenda agenda = new Agenda();
		model.put("agenda", agenda);
		List<Cargo> cargoList = new ArrayList<Cargo>();
		List<Cliente> clienteList = new ArrayList<Cliente>();
		cargoList = cargoService.findAll();
		clienteList = clienteService.findAll();
		model.put("cargoList",cargoList);
		model.put("clienteList",clienteList);
		model.put("titulo", "Formulario de contactos");
		return "formContacto";
	}
	
	@RequestMapping(value="/formContacto", method = RequestMethod.POST)
	public String guardarContacto(@Valid Agenda agenda, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de contactos");
			List<Cargo> cargoList = new ArrayList<Cargo>();
			List<Cliente> clienteList = new ArrayList<Cliente>();
			cargoList = cargoService.findAll();
			clienteList = clienteService.findAll();
			model.addAttribute("cargoList",cargoList);
			model.addAttribute("clienteList",clienteList);
			return "formContacto";
		}
		agendaService.save(agenda);
		status.setComplete();
		flash.addFlashAttribute("success", "Contacto guardado con exito");
		return "redirect:/agenda";
	}
	
	@RequestMapping(value = "/formContacto/{id}")
	public String editarContacto(@PathVariable(value = "id") Long codContacto, Map<String, Object> model, RedirectAttributes flash) {
		Agenda agenda = null;
		if (codContacto > 0) {
			agenda = agendaService.findOne(codContacto);
			if(agenda == null) {
				flash.addFlashAttribute("error", "El codigo del contacto no existe en base de datos!");
				return "redirect:/agenda";
			}
		} else {
			flash.addFlashAttribute("error", "El codigo del contacto no debe ser cero!");
			return "redirect:/agenda";
		}
		List<Cargo> cargoList = new ArrayList<Cargo>();
		List<Cliente> clienteList = new ArrayList<Cliente>();
		cargoList = cargoService.findAll();
		clienteList = clienteService.findAll();
		model.put("cargoList",cargoList);
		model.put("clienteList",clienteList);
		model.put("agenda", agenda);
		model.put("titulo", "Formulario contacto");
		return "formContacto";
	}
	
	@GetMapping(value = "/agenda/{id}")
	@ResponseBody
    public String eliminarContacto(@PathVariable(value = "id") Long codContacto, Model model) {
        System.out.println(codContacto);
		agendaService.delete(codContacto);
        return "1";
    }
	
}
