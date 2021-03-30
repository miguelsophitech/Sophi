package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="CAT_COMPETENCIAS")
public class Competencias implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cod_competencias")
	private Long codCompetencias;
	
	@Column(name = "desc_competencias")
	private String descCompetencias;
	
	@Column(name = "desc_definicion")
	private String descDefinicion;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecRegistro;

	public Long getCodCompetencias() {
		return codCompetencias;
	}

	public void setCodCompetencias(Long codCompetencias) {
		this.codCompetencias = codCompetencias;
	}

	public String getDescCompetencias() {
		return descCompetencias;
	}

	public void setDescCompetencias(String descCompetencias) {
		this.descCompetencias = descCompetencias;
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
