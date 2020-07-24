package com.sophi.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.ProyectoRecurso;
import com.sophi.app.models.entity.ProyectoRecursoId;

public interface IProyectoRecursoDao extends CrudRepository<ProyectoRecurso, ProyectoRecursoId>{
	
	List<ProyectoRecurso> findByProyectoRecursoIdCodRecurso(Long codRecurso);
	
}
