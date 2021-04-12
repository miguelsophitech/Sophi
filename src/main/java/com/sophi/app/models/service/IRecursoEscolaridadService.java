package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.RecursoEscolaridad;

public interface IRecursoEscolaridadService {
	
	public List<RecursoEscolaridad> findByCodRecurso(Long codRecurso);
	
	public void save(RecursoEscolaridad recursoEscolaridad);
	
	public void delete(Long codRecursoEscolaridad);
	
	public RecursoEscolaridad findById(Long codRecursoEscolaridad);
<<<<<<< HEAD

=======
	
>>>>>>> 226810d4818dc7b69624a09fafa12975435b3022
}
