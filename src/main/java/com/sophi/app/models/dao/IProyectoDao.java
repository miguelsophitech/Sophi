package com.sophi.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.Proyecto;

public interface IProyectoDao extends CrudRepository<Proyecto, Long> {

	Proyecto findByDescProyectoAndProyectoIdCodClienteAndProyectoIdCodEstatusProyectoAndFecRegistro(String descProyecto, Long codCliente, Long codEstatusProyecto, Date fecRegistro);
	
	List<Proyecto> findByProyectoIdCodCliente(Long codCliente);
	
	Proyecto findByProyectoIdCodProyecto(Long codProyecto);
	
	Proyecto findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyecto(Long codProyecto, Long codEstatusProyecto);
	
	Proyecto findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(Long codProyecto, Long codEstatusProyecto, Long codCliente);

}
