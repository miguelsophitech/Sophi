package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECURSOS_CAPACITACIONES")
public class RecursoCapacitacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private RecursoCapacitacionId recursoCapacitacionId;
	
	@Column(name = "desc_documento_evidencia")
	private byte[] descDocumentoEvidencia;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecRegistro;

	public RecursoCapacitacionId getRecursoCapacitacionId() {
		return recursoCapacitacionId;
	}

	public void setRecursoCapacitacionId(RecursoCapacitacionId recursoCapacitacionId) {
		this.recursoCapacitacionId = recursoCapacitacionId;
	}

	public byte[] getDescDocumentoEvidencia() {
		return descDocumentoEvidencia;
	}

	public void setDescDocumentoEvidencia(byte[] descDocumentoEvidencia) {
		this.descDocumentoEvidencia = descDocumentoEvidencia;
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
}
