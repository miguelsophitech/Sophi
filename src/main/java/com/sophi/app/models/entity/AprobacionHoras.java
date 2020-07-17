package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECURSOS_CAP_HORAS")
public class AprobacionHoras implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cod_actividad")
	private Long codActividad;
	
	@OneToOne
	@JoinColumn(name = "cod_actividad")
	private Actividad HorasPlaneadas, ActividadPrimaria, ActividadSecundaria;
	
//	@Column(name = "cod_recurso")
//	private Long codRecurso;

	@OneToOne
	@JoinColumn(name = "cod_recurso")
	private Recurso recurso;

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
	
	@Column(name = "cod_proyecto")
	private Long codProyecto;
	
	@Column(name = "val_duracion_reportada")
	private Float valDuracionReportada;
	
	@Column(name = "val_duracion_validada")
	private Float valDuracionValidada;
	
	@Column(name = "cod_recurso_validador")
	private Long codRecursoValidador;
	
	@Column(name = "fec_validacion")
	private Date fecValidacion;
	
	@Column(name = "cod_cliente")
	private Long codCliente;
	
	@Column(name = "val_nueva_actividad")
	private Long valNuevaActividad;
	
	@Column(name = "cod_estatus_proyecto")
	private Long codEstatusProyecto;

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	
	public Long getCodActividad() {
		return codActividad;
	}

	public void setCodActividad(Long codActividad) {
		this.codActividad = codActividad;
	}
	
	public Float getHorasPlaneadas() {
		return HorasPlaneadas.getValDuracionActividad();
	}

	public void setHorasPlaneadas(Actividad horasPlaneadas) {
		HorasPlaneadas = horasPlaneadas;
	}
	
	public String getActividadPrimaria() {
		return ActividadPrimaria.getDescActividadPrimaria();
	}

	public void setActividadPrimaria(Actividad actividadPrimaria) {
		ActividadPrimaria = actividadPrimaria;
	}
	
	public String getActividadSecundaria() {
		return ActividadSecundaria.getDescActividadSecundaria();
	}

	public void setActividadSecundaria(Actividad actividadSecundaria) {
		ActividadSecundaria = actividadSecundaria;
	}

//	public Long getCodRecurso() {
//		return codRecurso;
//	}
//
//	public void setCodRecurso(Long codRecurso) {
//		this.codRecurso = codRecurso;
//	}
	
	
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

	public Long getCodProyecto() {
		return codProyecto;
	}

	public void setCodProyecto(Long codProyecto) {
		this.codProyecto = codProyecto;
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

	public Long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Long codCliente) {
		this.codCliente = codCliente;
	}

	public Long getValNuevaActividad() {
		return valNuevaActividad;
	}

	public void setValNuevaActividad(Long valNuevaActividad) {
		this.valNuevaActividad = valNuevaActividad;
	}

	public Long getCodEstatusProyecto() {
		return codEstatusProyecto;
	}

	public void setCodEstatusProyecto(Long codEstatusProyecto) {
		this.codEstatusProyecto = codEstatusProyecto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
