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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECURSOS_HERRAMIENTAS")
public class Herramienta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_herramienta")
	private Long codHerramienta;
	
	//@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_herramienta")
	private String descHerramienta;
	
	//@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_observaciones")
	private String descObservaciones;
	
	//@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_num_serie")
	private String descNumSerie;
	
	//@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_modelo")
	private String descModelo;
	
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
	@JoinColumn(name = "cod_recurso", insertable=false, updatable=false)
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
	
	@Column(name = "cod_tipo_herramienta")
	private Long codTipoHerramienta;
	
	@Column(name = "cod_estado_herramienta")
	private Long codEstadoHerramienta;
	
	@OneToOne
	@JoinColumn(name = "cod_tipo_herramienta", insertable=false, updatable=false)
	private TipoHerramienta tipoHerramienta;
	
	@OneToOne
	@JoinColumn(name = "cod_estado_herramienta", insertable=false, updatable=false)
	private EstadoHerramienta estadoHerramienta;

	public Long getCodHerramienta() {
		return codHerramienta;
	}

	public void setCodHerramienta(Long codHerramienta) {
		this.codHerramienta = codHerramienta;
	}

	public String getDescObservaciones() {
		return descObservaciones;
	}

	public void setDescObservaciones(String descObservaciones) {
		this.descObservaciones = descObservaciones;
	}

	public String getDescNumSerie() {
		return descNumSerie;
	}

	public void setDescNumSerie(String descNumSerie) {
		this.descNumSerie = descNumSerie;
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

	public String getDescHerramienta() {
		return descHerramienta;
	}

	public void setDescHerramienta(String descHerramienta) {
		this.descHerramienta = descHerramienta;
	}

	public String getDescModelo() {
		return descModelo;
	}

	public void setDescModelo(String descModelo) {
		this.descModelo = descModelo;
	}

	public TipoHerramienta getTipoHerramienta() {
		return tipoHerramienta;
	}

	public void setTipoHerramienta(TipoHerramienta tipoHerramienta) {
		this.tipoHerramienta = tipoHerramienta;
	}

	public EstadoHerramienta getEstadoHerramienta() {
		return estadoHerramienta;
	}

	public void setEstadoHerramienta(EstadoHerramienta estadoHerramienta) {
		this.estadoHerramienta = estadoHerramienta;
	}

	public Long getCodTipoHerramienta() {
		return codTipoHerramienta;
	}

	public void setCodTipoHerramienta(Long codTipoHerramienta) {
		this.codTipoHerramienta = codTipoHerramienta;
	}

	public Long getCodEstadoHerramienta() {
		return codEstadoHerramienta;
	}

	public void setCodEstadoHerramienta(Long codEstadoHerramienta) {
		this.codEstadoHerramienta = codEstadoHerramienta;
	}

	public Long getCodRecurso() {
		return codRecurso;
	}

	public void setCodRecurso(Long codRecurso) {
		this.codRecurso = codRecurso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}