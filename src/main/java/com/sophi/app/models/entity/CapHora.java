package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECURSOS_CAP_HORAS")
public class CapHora implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	CapHoraId Id;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_comentario_detalle")
	private String descComentarioDetalle;
	
	@Transient
	private String descActividadSecundaria;
	
	@Transient
	private String descProyecto;
	
	@Column(name = "fec_inicio_actividad")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecInicioActividad;
	
	@Column(name = "fec_fin_actividad")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecFinActividad;
	
	
	@Column(name = "val_duracion_reportada")
	private float valDuracionReportada;

	@Column(name = "val_duracion_validada")
	private float valDuracionValidad;
	
	@Column(name = "cod_recurso_validador")
	private Long codRecursoValidador;
	
	@Column(name = "val_nueva_actividad")
	private Long valNuevaActividad;
	
	@Column(name = "fec_validacion")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecValidacion;

	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fecRegistro;
	
	@PrePersist
	public void prePersist() {
		fecRegistro = new Date();
	}

	public CapHoraId getId() {
		return Id;
	}

	public void setId(CapHoraId id) {
		Id = id;
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

	public float getValDuracionReportada() {
		return valDuracionReportada;
	}

	public void setValDuracionReportada(float valDuracionReportada) {
		this.valDuracionReportada = valDuracionReportada;
	}

	public float getValDuracionValidad() {
		return valDuracionValidad;
	}

	public void setValDuracionValidad(float valDuracionValidad) {
		this.valDuracionValidad = valDuracionValidad;
	}

	public Long getCodRecursoValidador() {
		return codRecursoValidador;
	}

	public void setCodRecursoValidador(Long codRecursoValidador) {
		this.codRecursoValidador = codRecursoValidador;
	}

	public Long getValNuevaActividad() {
		return valNuevaActividad;
	}

	public void setValNuevaActividad(Long valNuevaActividad) {
		this.valNuevaActividad = valNuevaActividad;
	}

	public Date getFecValidacion() {
		return fecValidacion;
	}

	public void setFecValidacion(Date fecValidacion) {
		this.fecValidacion = fecValidacion;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getDescActividadSecundaria() {
		return descActividadSecundaria;
	}

	public void setDescActividadSecundaria(String descActividadSecundaria) {
		this.descActividadSecundaria = descActividadSecundaria;
	}
	
	public String getDescProyecto() {
		return descProyecto;
	}

	public void setDescProyecto(String descProyecto) {
		this.descProyecto = descProyecto;
	}

	public CapHora(CapHoraId id, @NotEmpty(message = "Este dato no debe estar vacío") String descComentarioDetalle,
			Date fecInicioActividad, Date fecFinActividad, float valDuracionReportada, float valDuracionValidad,
			Long codRecursoValidador, Long valNuevaActividad, Date fecValidacion, Date fecRegistro) {
		super();
		Id = id;
		this.descComentarioDetalle = descComentarioDetalle;
		this.fecInicioActividad = fecInicioActividad;
		this.fecFinActividad = fecFinActividad;
		this.valDuracionReportada = valDuracionReportada;
		this.valDuracionValidad = valDuracionValidad;
		this.codRecursoValidador = codRecursoValidador;
		this.valNuevaActividad = valNuevaActividad;
		this.fecValidacion = fecValidacion;
		this.fecRegistro = fecRegistro;
	}

	public CapHora() {
		
	}

}
