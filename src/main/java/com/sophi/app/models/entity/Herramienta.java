package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	@Column(name = "cod_equipo")
	private Long codEquipo;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_observaciones")
	private String descObservaciones;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fecRegistro;
	
	@PrePersist
	public void prePersist() {
		fecRegistro = new Date();
	}
	
	@Column(name = "cod_recurso")
	private Long codRecurso;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_recurso")
	private Recurso recurso;
	
	@Column(name = "responsiva")
	private byte[] responsiva;
	
	@Column(name = "fec_prestamo")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecPrestamo;
	
	@Column(name = "fec_devolucion")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecDevolucion;

	public Long getCodEquipo() {
		return codEquipo;
	}

	public void setCodEquipo(Long codEquipo) {
		this.codEquipo = codEquipo;
	}

	public String getDescObservaciones() {
		return descObservaciones;
	}

	public void setDescObservaciones(String descObservaciones) {
		this.descObservaciones = descObservaciones;
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

	public Long getCodRecurso() {
		return codRecurso;
	}

	public void setCodRecurso(Long codRecurso) {
		this.codRecurso = codRecurso;
	}

	public byte[] getResponsiva() {
		return responsiva;
	}

	public void setResponsiva(byte[] responsiva) {
		this.responsiva = responsiva;
	}

	public Date getFecPrestamo() {
		return fecPrestamo;
	}

	public void setFecPrestamo(Date fecPrestamo) {
		this.fecPrestamo = fecPrestamo;
	}

	public Date getFecDevolucion() {
		return fecDevolucion;
	}

	public void setFecDevolucion(Date fecDevolucion) {
		this.fecDevolucion = fecDevolucion;
	}
	
}
