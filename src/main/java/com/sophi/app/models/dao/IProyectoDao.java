package com.sophi.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.AprobacionHoras;
import com.sophi.app.models.entity.Proyecto;

public interface IProyectoDao extends CrudRepository<Proyecto, Long> {

//	Proyecto findByDescProyectoAndCodClienteAndCodEstatusProyectoAndFecRegistro(String descProyecto, Long codCliente, Long codEstatusProyecto, Date fecRegistro);
	Proyecto findByDescProyectoAndCodClienteAndCodEstatusProyecto(String descProyecto, Long codCliente, Long codEstatusProyecto);
	
	List<Proyecto> findByCodCliente(Long codCliente);
	
	Proyecto findByCodProyecto(Long codProyecto);
	
	Proyecto findByCodProyectoAndCodEstatusProyecto(Long codProyecto, Long codEstatusProyecto);
	
	Proyecto findByCodProyectoAndCodEstatusProyectoAndCodCliente(Long codProyecto, Long codEstatusProyecto, Long codCliente);
	
	List<Proyecto> findByCodRecursoLider(Long codRecursoLider);
	
	@Query("FROM Proyecto Pr WHERE Pr.codEstatusProyecto = 2 ")
	List<Proyecto> findProyectosActivos();

}
