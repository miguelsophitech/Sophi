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
@Table(name = "RECURSOS_CONTACTO_EMERGENCIA")
public class ContactoEmergencia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_contacto_emergencia")
	private Long codContactoEmergencia;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_nombre_contacto")
	private String descNombreContacto;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_tel_contacto_emergencia")
	private String descTelContactoEmergencia;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_parentesco_contacto_emergencia")
	private String descParentescoContactoEmergencia;
	
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

	public Long getCodContactoEmergencia() {
		return codContactoEmergencia;
	}

	public void setCodContactoEmergencia(Long codContactoEmergencia) {
		this.codContactoEmergencia = codContactoEmergencia;
	}

	public String getDescNombreContacto() {
		return descNombreContacto;
	}

	public void setDescNombreContacto(String descNombreContacto) {
		this.descNombreContacto = descNombreContacto;
	}

	public String getDescTelContactoEmergencia() {
		return descTelContactoEmergencia;
	}

	public void setDescTelContactoEmergencia(String descTelContactoEmergencia) {
		this.descTelContactoEmergencia = descTelContactoEmergencia;
	}

	public String getDescParentescoContactoEmergencia() {
		return descParentescoContactoEmergencia;
	}

	public void setDescParentescoContactoEmergencia(String descParentescoContactoEmergencia) {
		this.descParentescoContactoEmergencia = descParentescoContactoEmergencia;
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