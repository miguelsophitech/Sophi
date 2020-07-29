package com.sophi.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sophi.app.models.entity.Recurso;

public interface IRecursoDao extends CrudRepository<Recurso, Long>{

	Recurso findByDescCorreoElectronico(String correoElectronico);

	@Query("FROM Recurso R WHERE LOWER(R.descRecurso) LIKE LOWER(concat(?1, '%')) AND LOWER(R.descApellidoPaterno) LIKE LOWER(concat(?2, '%'))")
	List<Recurso> findByNombreApellido(String descRecurso, String descApellidoPaterno);
	
}
