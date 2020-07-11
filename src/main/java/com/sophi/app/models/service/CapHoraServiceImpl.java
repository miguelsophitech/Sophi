package com.sophi.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophi.app.models.dao.ICapHoraDao;
import com.sophi.app.models.entity.CapHora;
import com.sophi.app.models.entity.CapHoraId;

@Service
public class CapHoraServiceImpl implements ICapHoraService {
	
	@Autowired
	private ICapHoraDao capHoraDao;

	@Override
	@Transactional(readOnly = true)
	public List<CapHora> findAll() {
		return (List<CapHora>) capHoraDao.findAll();
	}

	@Override
	@Transactional
	public void save(CapHora capHora) {
		capHoraDao.save(capHora);
	}

	@Override
	@Transactional(readOnly = true)
	public CapHora findOne(CapHoraId capHoraId) {
		return capHoraDao.findById(capHoraId).orElse(null);
	}

	@Override
	@Transactional
	public void delete(CapHora capHora) {
		capHoraDao.delete(capHora);
	}
	
	@Override
	public List<CapHora> findListCapHoraByFechaRecurso(Date fecha, Long codRecurso) {
		return capHoraDao.findListCapHoraByFechaRecurso(fecha, codRecurso);
	}


}
