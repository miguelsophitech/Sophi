package com.sophi.app.models.service;

import java.util.Date;
import java.util.List;

import com.sophi.app.models.entity.CapHora;

public interface ICapHoraService {

	public List<CapHora> findAll();
	
	public void save(CapHora capHora);
	
	public CapHora findOne(Long codCapHora);
	
	public void delete(CapHora capHora);
	
	public List<CapHora> findListCapHoraByFechaRecurso(Date fecha, Long codRecurso);
	
	public Float findSumHorasReportadasSemana(Long codRecurso, Date fechaInicio, Date fechaFin);
	
}
