package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECURSOS_HERRAMIENTAS")
public class Herramienta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_herramienta")
	private Long codHerramienta;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_herramienta")
	private String descHerramienta;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_numero_serie")
	private String descNumeroSerie;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fecRegistro;
	
	@PrePersist
	public void prePersist() {
		fecRegistro = new Date();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_recurso")
	private Recurso recurso;

	public Long getCodHerramienta() {
		return codHerramienta;
	}

	public void setCodHerramienta(Long codHerramienta) {
		this.codHerramienta = codHerramienta;
	}

	public String getDescHerramienta() {
		return descHerramienta;
	}

	public void setDescHerramienta(String descHerramienta) {
		this.descHerramienta = descHerramienta;
	}

	public String getDescNumeroSerie() {
		return descNumeroSerie;
	}

	public void setDescNumeroSerie(String descNumeroSerie) {
		this.descNumeroSerie = descNumeroSerie;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
