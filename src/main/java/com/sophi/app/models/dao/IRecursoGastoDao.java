package com.sophi.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.RecursoGasto;
import com.sophi.app.models.entity.RecursoGastoId;

public interface IRecursoGastoDao extends CrudRepository<RecursoGasto, RecursoGastoId>{
	
	List<RecursoGasto> findByRecursoGastoIdCodRecurso(Long codRecurso);
	
}
