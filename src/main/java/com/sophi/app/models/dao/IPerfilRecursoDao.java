package com.sophi.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.PerfilRecurso;

public interface IPerfilRecursoDao extends CrudRepository<PerfilRecurso, Long>{

	PerfilRecurso findByCodPerfil(Long codPerfil);

}
