package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECURSOS_CAP_HORAS")
public class AprobacionHoras implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CapHoraId Id;

	@OneToOne
	@JoinColumn(name = "cod_actividad", insertable = false, updatable = false)
	private Actividad actividad;

	@OneToOne
	@JoinColumn(name = "cod_recurso", insertable = false, updatable = false)
	private Recurso recurso;
	
	@Column(name = "cod_proyecto", insertable = false, updatable = false)
	private Long codProyecto;

	@Column(name = "desc_comentario_detalle")
	private String descComentarioDetalle;
	
	@Column(name = "fec_inicio_actividad")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecInicioActividad;
	
	@Column(name = "fec_fin_actividad")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecFinActividad;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecRegistro;

	@Column(name = "val_duracion_reportada")
	private Float valDuracionReportada;
	
	@Column(name = "val_duracion_validada")
	private Float valDuracionValidada;
	
	@Column(name = "cod_recurso_validador")
	private Long codRecursoValidador;
	
	@Column(name = "fec_validacion")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date fecValidacion;
	
	@Column(name = "val_nueva_actividad")
	private Long valNuevaActividad;
	
	public CapHoraId getId() {
		return Id;
	}

	public void setId(CapHoraId id) {
		this.Id = id;
	}
	
	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	
	public String getDescComentarioDetalle() {
		return descComentarioDetalle;
	}

	public void setDescComentarioDetalle(String descComentarioDetalle) {
		this.descComentarioDetalle = descComentarioDetalle;
	}

	public Date getFecInicioActividad() {
		return fecInicioActividad;
	}

	public void setFecInicioActividad(Date fecInicioActividad) {
		this.fecInicioActividad = fecInicioActividad;
	}

	public Date getFecFinActividad() {
		return fecFinActividad;
	}

	public void setFecFinActividad(Date fecFinActividad) {
		this.fecFinActividad = fecFinActividad;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public Float getValDuracionReportada() {
		return valDuracionReportada;
	}

	public void setValDuracionReportada(Float valDuracionReportada) {
		this.valDuracionReportada = valDuracionReportada;
	}

	public Float getValDuracionValidada() {
		return valDuracionValidada;
	}

	public void setValDuracionValidada(Float valDuracionValidada) {
		this.valDuracionValidada = valDuracionValidada;
	}

	public Long getCodRecursoValidador() {
		return codRecursoValidador;
	}

	public void setCodRecursoValidador(Long codRecursoValidador) {
		this.codRecursoValidador = codRecursoValidador;
	}

	public Date getFecValidacion() {
		return fecValidacion;
	}

	public void setFecValidacion(Date fecValidacion) {
		this.fecValidacion = fecValidacion;
	}

	

	public Long getValNuevaActividad() {
		return valNuevaActividad;
	}

	public void setValNuevaActividad(Long valNuevaActividad) {
		this.valNuevaActividad = valNuevaActividad;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public AprobacionHoras(CapHoraId id, @NotEmpty(message = "Este dato no debe estar vacío") String descComentarioDetalle,
			Date fecInicioActividad, Date fecFinActividad, float valDuracionReportada, float valDuracionValidada,
			Long codRecursoValidador, Long valNuevaActividad, Date fecValidacion, Date fecRegistro) {
		super();
		Id = id;
		this.descComentarioDetalle = descComentarioDetalle;
		this.fecInicioActividad = fecInicioActividad;
		this.fecFinActividad = fecFinActividad;
		this.valDuracionReportada = valDuracionReportada;
		this.valDuracionValidada = valDuracionValidada;
		this.codRecursoValidador = codRecursoValidador;
		this.valNuevaActividad = valNuevaActividad;
		this.fecValidacion = fecValidacion;
		this.fecRegistro = fecRegistro;
	}

	public AprobacionHoras() {
		
	}

}
