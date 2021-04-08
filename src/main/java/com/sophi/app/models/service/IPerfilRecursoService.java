package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.PerfilRecurso;

public interface IPerfilRecursoService {
	
	public PerfilRecurso findByCodPerfil(Long codPerfil);
	
	public List<PerfilRecurso> findAll();

}
