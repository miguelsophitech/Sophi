package com.sophi.app.models.service;

import java.util.Date;
import java.util.List;

import com.sophi.app.models.entity.CapHora;
import com.sophi.app.models.entity.CapHoraId;

public interface ICapHoraService {

	public List<CapHora> findAll();
	
	public void save(CapHora capHora);
	
	public CapHora findOne(CapHoraId capHoraId);
	
	public void delete(CapHora capHora);
	
	public List<CapHora> findListCapHoraByFechaRecurso(Date fecha, Long codRecurso);
	
}
