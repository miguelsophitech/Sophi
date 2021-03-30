package com.sophi.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CAT_EQUIPO")
public class Equipo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_equipo")
	private Long codEquipo;
	
	@Column(name = "desc_equipo")
	private String descEquipo;
	
	@Column(name = "desc_modelo")
	private String descModelo;
	
	@Column(name = "desc_num_serie")
	private String descNumSerie;
	
	@Column(name = "val_estado")
	private Long valEstado;
	
	@Column(name = "cod_tipo_herramienta")
	private Long codTipoHerramienta;

	public Long getCodEquipo() {
		return codEquipo;
	}

	public void setCodEquipo(Long codEquipo) {
		this.codEquipo = codEquipo;
	}

	public String getDescEquipo() {
		return descEquipo;
	}

	public void setDescEquipo(String descEquipo) {
		this.descEquipo = descEquipo;
	}

	public String getDescModelo() {
		return descModelo;
	}

	public void setDescModelo(String descModelo) {
		this.descModelo = descModelo;
	}

	public String getDescNumSerie() {
		return descNumSerie;
	}

	public void setDescNumSerie(String descNumSerie) {
		this.descNumSerie = descNumSerie;
	}

	public Long getValEstado() {
		return valEstado;
	}

	public void setValEstado(Long valEstado) {
		this.valEstado = valEstado;
	}

	public Long getCodTipoHerramienta() {
		return codTipoHerramienta;
	}

	public void setCodTipoHerramienta(Long codTipoHerramienta) {
		this.codTipoHerramienta = codTipoHerramienta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
