package com.sophi.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.Agenda;

public interface IAgendaDao extends CrudRepository<Agenda, Long>{


}