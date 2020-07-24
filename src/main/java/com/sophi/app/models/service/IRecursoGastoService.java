package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.RecursoGasto;
import com.sophi.app.models.entity.RecursoGastoId;

public interface IRecursoGastoService {
	
	public List<RecursoGasto> findAll();
	
	public void save(RecursoGasto recursoGasto);
	
	public RecursoGasto findOne(RecursoGastoId recursoGasto);
	
	public void delete(RecursoGasto recursoGasto);
	
	public List<RecursoGasto> findByRecursoGastoIdCodRecurso(Long codRecurso);
	
}
