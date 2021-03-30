package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CAT_COMPETENCIAS_PERFILES")
public class CompetenciasPerfiles implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CompetenciasPerfilesId competenciasperfilesId;
	
	@Column(name = "desc_definicion")
	private String descDefinicion;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecRegistro;

	public CompetenciasPerfilesId getCompetenciasperfilesId() {
		return competenciasperfilesId;
	}

	public void setCompetenciasperfilesId(CompetenciasPerfilesId competenciasperfilesId) {
		this.competenciasperfilesId = competenciasperfilesId;
	}

	public String getDescDefinicion() {
		return descDefinicion;
	}

	public void setDescDefinicion(String descDefinicion) {
		this.descDefinicion = descDefinicion;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}
}
