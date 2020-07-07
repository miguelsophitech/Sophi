package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.Herramienta;

public interface IHerramientaService {
	
	public List<Herramienta> findAll();
	
	public void save(Herramienta herramienta);
	
	public Herramienta findOne(Long codHerramienta);
	
	public void delete(Herramienta herramienta);
	
}
