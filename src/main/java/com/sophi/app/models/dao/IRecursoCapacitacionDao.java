package com.sophi.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.RecursoCapacitacion;

public interface IRecursoCapacitacionDao extends CrudRepository<RecursoCapacitacion, Long>{
	
	public List<RecursoCapacitacion> findByCodRecurso(Long codRecurso);

}
