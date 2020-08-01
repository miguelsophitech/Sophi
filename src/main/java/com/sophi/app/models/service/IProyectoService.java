package com.sophi.app.models.service;

import java.util.Date;
import java.util.List;

import com.sophi.app.models.entity.Proyecto;

public interface IProyectoService {
	
	public List<Proyecto> findAll();
	
	public void save(Proyecto proyecto);
	
	public Proyecto findOne(Long codProyecto);
	
	public Proyecto findByDescProyectoAndCodClienteAndCodEstatusProyectoAndFecRegistro(String descProyecto, Long codCliente, Long codEstatusProyecto, Date fecRegistro);
	
	public List<Proyecto> findByCodCliente(Long codCliente);
	
	public Proyecto findByCodProyecto(Long codProyecto);
	
	public Proyecto findByCodProyectoAndCodEstatusProyecto(Long codProyecto, Long codEstatusProyecto);
	
	public Proyecto findByCodProyectoAndCodEstatusProyectoAndCodCliente(Long codProyecto, Long codEstatusProyecto, Long codCliente);

}
