package com.sophi.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.Proyecto;

public interface IProyectoDao extends CrudRepository<Proyecto, Long> {

	Proyecto findByDescProyectoAndCodClienteAndCodEstatusProyectoAndFecRegistro(String descProyecto, Long codCliente, Long codEstatusProyecto, Date fecRegistro);
	
	List<Proyecto> findByCodCliente(Long codCliente);
	
	Proyecto findByCodProyecto(Long codProyecto);
	
	Proyecto findByCodProyectoAndCodEstatusProyecto(Long codProyecto, Long codEstatusProyecto);
	
	Proyecto findByCodProyectoAndCodEstatusProyectoAndCodCliente(Long codProyecto, Long codEstatusProyecto, Long codCliente);

}
