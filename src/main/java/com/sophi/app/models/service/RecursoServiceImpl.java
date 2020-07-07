package com.sophi.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophi.app.models.dao.IRecursoDao;
import com.sophi.app.models.entity.Recurso;

@Service
public class RecursoServiceImpl implements IRecursoService {

	@Autowired
	private IRecursoDao recursoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Recurso> findAll() {
		return (List<Recurso>) recursoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Recurso recurso) {
		recursoDao.save(recurso);
	}

	@Override
	@Transactional(readOnly = true)
	public Recurso findOne(Long codRecurso) {
		return recursoDao.findById(codRecurso).orElse(null);
	}

	@Override
	public List<Recurso> findByNombreApellido(String descRecurso, String descApellidoPaterno) {
		return recursoDao.findByNombreApellido(descRecurso, descApellidoPaterno);
	}

	
}
