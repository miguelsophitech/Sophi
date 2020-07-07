package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.Actividad;

public interface IActividadService {

	public List<Actividad> findAll();
	
	public void save(Actividad actividad);
	
	public void saveAll(Iterable<Actividad> actividades);
	
	public Actividad findOne(Long codActividad);
	
	public void delete(Actividad actividad);
}
