package com.sophi.app.models.dao;

import com.sophi.app.models.entity.AprobacionGastos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IAprobacionGastosDao extends CrudRepository<AprobacionGastos, Long> {
    
//	@Query("FROM AprobacionGastos Ap WHERE Ap.codProyecto = ?1")
//    List<AprobacionGastos> findAprobacionGastosBycodProyecto(Long codProyecto);
	
}