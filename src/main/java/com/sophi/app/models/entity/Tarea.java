package com.sophi.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CAT_TAREAS")
public class Tarea implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tarea")
	private Long codTarea;
	
	@Column(name = "desc_tarea")
	private String descTarea;

	public Long getCodTarea() {
		return codTarea;
	}

	public void setCodTarea(Long codTarea) {
		this.codTarea = codTarea;
	}

	public String getDescTarea() {
		return descTarea;
	}

	public void setDescTarea(String descTarea) {
		this.descTarea = descTarea;
	}
	
	

	
}
