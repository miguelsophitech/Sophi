package com.sophi.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.CapHora;
import com.sophi.app.models.entity.CapHoraId;

public interface ICapHoraDao extends CrudRepository<CapHora, CapHoraId>{
	
	@Query("FROM CapHora C WHERE C.fecInicioActividad = ?1 AND C.Id.codRecurso = ?2")
	List<CapHora> findListCapHoraByFechaRecurso(Date fecha, Long codRecurso);

}
