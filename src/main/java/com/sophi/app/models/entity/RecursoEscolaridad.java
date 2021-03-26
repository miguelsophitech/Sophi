package com.sophi.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RECURSOS_ESCOLARIDAD")
public class RecursoEscolaridad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_recurso_escolaridad")
	private Long codRecursoEscolaridad;
	
	@Column(name = "cod_recurso")
	private Long codRecurso;
	
	@Column(name = "desc_institucion_academica")
	private String descInstitucionAcademica;
	
	@Column(name = "desc_cedula_profesional")
	private String descCedulaProfesional;
	
	@Column(name = "cod_grado_escolar")
	private Long codGradoEscolar;
	
	@Column(name = "cod_etapa_escolar")
	private Long codEtapaEscolar;

	public Long getCodRecursoEscolaridad() {
		return codRecursoEscolaridad;
	}

	public void setCodRecursoEscolaridad(Long codRecursoEscolaridad) {
		this.codRecursoEscolaridad = codRecursoEscolaridad;
	}

	public Long getCodRecurso() {
		return codRecurso;
	}

	public void setCodRecurso(Long codRecurso) {
		this.codRecurso = codRecurso;
	}

	public String getDescInstitucionAcademica() {
		return descInstitucionAcademica;
	}

	public void setDescInstitucionAcademica(String descInstitucionAcademica) {
		this.descInstitucionAcademica = descInstitucionAcademica;
	}

	public String getDescCedulaProfesional() {
		return descCedulaProfesional;
	}

	public void setDescCedulaProfesional(String descCedulaProfesional) {
		this.descCedulaProfesional = descCedulaProfesional;
	}

	public Long getCodGradoEscolar() {
		return codGradoEscolar;
	}

	public void setCodGradoEscolar(Long codGradoEscolar) {
		this.codGradoEscolar = codGradoEscolar;
	}

	public Long getCodEtapaEscolar() {
		return codEtapaEscolar;
	}

	public void setCodEtapaEscolar(Long codEtapaEscolar) {
		this.codEtapaEscolar = codEtapaEscolar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
