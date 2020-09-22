package com.sophi.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.Rol;

public interface IRolDao extends CrudRepository<Rol, Long>{
	
	@Query("FROM Rol r WHERE r.descRol = 'ROLE_LIDER'")
    List<Rol> findListaRoles();

}
