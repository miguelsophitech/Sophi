package com.sophi.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.CapHora;

public interface ICapHoraDao extends CrudRepository<CapHora, Long>{
	
	@Query("FROM CapHora C WHERE C.fecInicioActividad = ?1 AND C.Id.codRecurso = ?2")
	List<CapHora> findListCapHoraByFechaRecurso(Date fecha, Long codRecurso);
	
	
	@Query("SELECT  SUM(valDuracionReportada) FROM CapHora C WHERE C.Id.codRecurso = ?1 AND C.fecInicioActividad  BETWEEN ?2 AND ?3")
	Float findSumHorasReportadasSemana(Long codRecurso, Date fechaInicio, Date fechaFin);

}
