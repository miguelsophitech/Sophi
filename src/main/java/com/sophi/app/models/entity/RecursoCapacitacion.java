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
	private RecursoCapacitacion recursoCapacitacionId;
	
	@Column(name = "documento_evidencia")
	private byte[] documentoEvidencia;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecRegistro;

	public RecursoCapacitacion getRecursoCapacitacionId() {
		return recursoCapacitacionId;
	}

	public void setRecursoCapacitacionId(RecursoCapacitacion recursoCapacitacionId) {
		this.recursoCapacitacionId = recursoCapacitacionId;
	}

	public byte[] getDocumentoEvidencia() {
		return documentoEvidencia;
	}

	public void setDocumentoEvidencia(byte[] documentoEvidencia) {
		this.documentoEvidencia = documentoEvidencia;
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
