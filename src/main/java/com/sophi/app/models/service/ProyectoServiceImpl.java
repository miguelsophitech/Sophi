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
	public Proyecto findByDescProyectoAndProyectoIdCodClienteAndProyectoIdCodEstatusProyectoAndFecRegistro(String descProyecto, Long codCliente, Long codEstatusProyecto, Date fecRegistro) {
		return (Proyecto) proyectoDao.findByDescProyectoAndProyectoIdCodClienteAndProyectoIdCodEstatusProyectoAndFecRegistro(descProyecto, codCliente, codEstatusProyecto, fecRegistro);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> findByProyectoIdCodCliente(Long codCliente) {
		return (List<Proyecto>) proyectoDao.findByProyectoIdCodCliente(codCliente);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proyecto findByProyectoIdCodProyecto(Long codProyecto) {
		return proyectoDao.findByProyectoIdCodProyecto(codProyecto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proyecto findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyecto(Long codProyecto, Long codEstatusProyecto) {
		return proyectoDao.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyecto(codProyecto, codEstatusProyecto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proyecto findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(Long codProyecto, Long codEstatusProyecto, Long codCliente) {
		return proyectoDao.findByProyectoIdCodProyectoAndProyectoIdCodEstatusProyectoAndProyectoIdCodCliente(codProyecto, codEstatusProyecto, codCliente);
	}

}
