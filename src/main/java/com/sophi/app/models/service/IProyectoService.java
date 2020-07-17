package com.sophi.app.models.service;

import java.util.Date;
import java.util.List;

import com.sophi.app.models.entity.Proyecto;

public interface IProyectoService {
	
	public List<Proyecto> findAll();
	
	public void save(Proyecto proyecto);
	
	public Proyecto findOne(Long codProyecto);
	
	public Proyecto findByDescProyectoAndProyectoIdCodClienteAndProyectoIdCodEstatusProyectoAndFecRegistro(String descProyecto, Long codCliente, Long codEstatusProyecto, Date fecRegistro);
	
	public List<Proyecto> findByProyectoIdCodCliente(Long codCliente);
	
	public Proyecto findByProyectoIdCodProyecto(Long codProyecto);
	
	public Proyecto findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyecto(Long codProyecto, Long codEstatusProyecto);
	
	public Proyecto findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(Long codProyecto, Long codEstatusProyecto, Long codCliente);

}
