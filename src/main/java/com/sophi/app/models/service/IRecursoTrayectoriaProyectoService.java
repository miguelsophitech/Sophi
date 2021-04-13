package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.RecursoTrayectoriaProyecto;

public interface IRecursoTrayectoriaProyectoService {
	
	public List<RecursoTrayectoriaProyecto> findByCodRecurso(Long codRecurso);
	
	public RecursoTrayectoriaProyecto findById(Long codRecursoTrayectoriaProyecto);
	
	public void save(RecursoTrayectoriaProyecto recursoTrayectoriaProyecto);
	
	public void delete(Long codRecursoTrayectoriaProyecto);

}
