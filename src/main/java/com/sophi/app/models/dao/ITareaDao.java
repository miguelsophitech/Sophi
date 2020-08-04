package com.sophi.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.Tarea;

public interface ITareaDao extends CrudRepository<Tarea, Long> {
	
	

}
