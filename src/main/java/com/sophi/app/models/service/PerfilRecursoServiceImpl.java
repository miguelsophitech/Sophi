package com.sophi.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophi.app.models.dao.IPerfilRecursoDao;
import com.sophi.app.models.entity.PerfilRecurso;

@Service
public class PerfilRecursoServiceImpl implements IPerfilRecursoService {
	
	@Autowired
	private IPerfilRecursoDao perfilRecursoDao;

	@Override
	@Transactional(readOnly = true)
	public List<PerfilRecurso> findAll() {
		return (List<PerfilRecurso>) perfilRecursoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public PerfilRecurso findByCodPerfil(Long codPerfil) {
		return perfilRecursoDao.findByCodPerfil(codPerfil);
	}

}
