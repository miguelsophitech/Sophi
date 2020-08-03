package com.sophi.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sophi.app.models.dao.ISubtareaDao;
import com.sophi.app.models.entity.Subtarea;

public class SubtareaServiceImpl implements ISubtareaService {
	
	@Autowired
	private ISubtareaDao subtareaDao;

	@Override
	public List<Subtarea> findAll() {
		return (List<Subtarea>) subtareaDao.findAll();
	}

	@Override
	public Subtarea findOne(Long codSubtarea) {
		return subtareaDao.findById(codSubtarea).orElse(null);
	}

}
