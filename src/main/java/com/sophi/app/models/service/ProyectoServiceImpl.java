package com.sophi.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophi.app.models.dao.IProyectoDao;
import com.sophi.app.models.entity.Proyecto;

@Service
public class ProyectoServiceImpl implements IProyectoService {

	@Autowired
	private IProyectoDao proyectoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> findAll() {
		return (List<Proyecto>) proyectoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Proyecto proyecto) {
		proyectoDao.save(proyecto);
	}

	@Override
	@Transactional(readOnly = true)
	public Proyecto findOne(Long codProyecto) {
		return proyectoDao.findById(codProyecto).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proyecto findByDescProyectoAndCodClienteAndCodEstatusProyectoAndFecRegistro(String descProyecto, Long codCliente, Long codEstatusProyecto, Date fecRegistro) {
		return (Proyecto) proyectoDao.findByDescProyectoAndCodClienteAndCodEstatusProyectoAndFecRegistro(descProyecto, codCliente, codEstatusProyecto, fecRegistro);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> findByCodCliente(Long codCliente) {
		return (List<Proyecto>) proyectoDao.findByCodCliente(codCliente);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proyecto findByCodProyecto(Long codProyecto) {
		return proyectoDao.findByCodProyecto(codProyecto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proyecto findByCodProyectoAndCodEstatusProyecto(Long codProyecto, Long codEstatusProyecto) {
		return proyectoDao.findByCodProyectoAndCodEstatusProyecto(codProyecto, codEstatusProyecto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proyecto findByCodProyectoAndCodEstatusProyectoAndCodCliente(Long codProyecto, Long codEstatusProyecto, Long codCliente) {
		return proyectoDao.findByCodProyectoAndCodEstatusProyectoAndCodCliente(codProyecto, codEstatusProyecto, codCliente);
	}

}
