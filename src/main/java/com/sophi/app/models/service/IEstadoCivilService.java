package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.EstadoCivil;

public interface IEstadoCivilService {

	public EstadoCivil findOne(Long codEstadoCivil);

	public List<EstadoCivil> findAll();

}
