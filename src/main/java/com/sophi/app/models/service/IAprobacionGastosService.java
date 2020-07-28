package com.sophi.app.models.service;

import java.util.List;

import com.sophi.app.models.entity.AprobacionGastos;

public interface IAprobacionGastosService {

    public List<AprobacionGastos> findAll();

	public void saveAll(List<AprobacionGastos> aprobaciongastos);

	public List<AprobacionGastos> findAprobacionGastosBycodProyecto(Long codProyecto);
    
}