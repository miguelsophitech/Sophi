package com.sophi.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophi.app.models.dao.IActividadDao;
import com.sophi.app.models.entity.Actividad;

@Service
public class ActividadServiceImpl implements IActividadService {
	
	@Autowired
	private IActividadDao actividadDao;

	@Override
	@Transactional(readOnly = true)
	public List<Actividad> findAll() {
		return (List<Actividad>) actividadDao.findAll();
	}

	@Override
	@Transactional
	public void save(Actividad actividad) {
		actividadDao.save(actividad);
	}

	@Override
	@Transactional(readOnly = true)
	public Actividad findOne(Long codActividad) {
		return actividadDao.findById(codActividad).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Actividad actividad) {
		actividadDao.delete(actividad);
	}

	@Override
	public void saveAll(Iterable<Actividad> actividades) {
		actividadDao.saveAll(actividades);
	}

	@Override
	public List<Long> findListaProyectoByRecurso(Long codRecurso) {
		return actividadDao.findListaProyectoByRecurso(codRecurso);
	}

	@Override
	public List<Actividad> findListaActividadesByRecursoProyecto(Long codRecurso, Long codProyecto) {
		return actividadDao.findListaActividadesByRecursoProyecto(codRecurso, codProyecto);
	}

	@Override
	public List<Actividad> findListaActividadesByRecursoProyectoPrimaria(Long codRecurso, Long codProyecto, String descPrimaria) {
		return actividadDao.findListaActividadesByRecursoProyectoPrimaria(codRecurso, codProyecto, descPrimaria);
	}

	@Override
	public List<String> findListaActividadesPrimariasByRecursoProyecto(Long codRecurso, Long codProyecto) {
		return actividadDao.findListaActividadesPrimariasByRecursoProyecto(codRecurso, codProyecto);
	}

	@Override
	public List<Actividad> findByCodRecurso(Long codRecurso) {
		return actividadDao.findByCodRecurso(codRecurso);
	}


}
