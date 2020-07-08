package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.Recurso;

public interface IRecursoService {
	
	List<Recurso> listaRecursos();

	public List<Recurso> findAll();
	
	public void save(Recurso recurso);
	
	public Recurso findOne(Long codRecurso);
	
	//manual	
	public List<Recurso> findByNombreApellido(String descRecurso, String descApellidoPaterno);

}
