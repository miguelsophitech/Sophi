package com.sophi.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.RecursoTrayectoriaProyecto;

public interface IRecursoTrayectoriaProyectoDao  extends CrudRepository<RecursoTrayectoriaProyecto, Long>{
	
	List<RecursoTrayectoriaProyecto> findByCodRecurso(Long codRecurso);

}
