package com.sophi.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.Equipo;

public interface IEquipoDao extends CrudRepository<Equipo, Long>{
	
	public Equipo findByCodEstadoHerramienta(Long codEstadoHerramienta);
	
	public List<Equipo> findByCodTipoHerramienta(Long codTipoHerramienta);
	
	public Equipo findByCodHerramienta(Long codHerramienta);
	
}
