package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.AprobacionHoras;

public interface IAprobacionHorasService {
	
	public List<AprobacionHoras> findAll();

	public List<AprobacionHoras> findAprobacionHorasByProyecto(Long codProyecto);
	
	//public List<AprobacionHoras> findAprobacionHorasBycodActividad(Long codActividad);
	
	public AprobacionHoras findOne(Long codActividad);

	public void save(AprobacionHoras aprobacionhoras);

	public void saveAll(List<AprobacionHoras> aprobacionhoras);

}
