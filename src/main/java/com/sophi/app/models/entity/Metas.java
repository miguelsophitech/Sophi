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
@Table(name = "CAT_METAS")
public class Metas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_meta")
	private Long codMeta;
	
	@Column(name = "desc_meta")
	private String descMeta;
	
	@Column(name = "porcentaje")
	private Long porcentaje;
	
	@Column(name = "desc_definicion")
	private String descDefinicion;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecRegistro;

	public Long getCodMeta() {
		return codMeta;
	}

	public void setCodMeta(Long codMeta) {
		this.codMeta = codMeta;
	}

	public String getDescMeta() {
		return descMeta;
	}

	public void setDescMeta(String descMeta) {
		this.descMeta = descMeta;
	}

	public Long getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Long porcentaje) {
		this.porcentaje = porcentaje;
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
