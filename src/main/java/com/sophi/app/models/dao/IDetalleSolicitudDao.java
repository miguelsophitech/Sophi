package com.sophi.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.DetalleSolicitud;

public interface IDetalleSolicitudDao extends CrudRepository<DetalleSolicitud, Long>{
	
	public List<DetalleSolicitud> findByCodSolicitud(Long codSolicitud);

}
