package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "DETALLE_SOLICITUDES")
public class DetalleSolicitud implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_detalle_solicitudes")
	private Long codDetalleSolicitud;

	@Column(name = "cod_solicitud")
	private Long codSolicitud;
	
	@Column(name = "fec_solicitado")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecDiaSolicitado;

	public Long getCodDetalleSolicitud() {
		return codDetalleSolicitud;
	}

	public void setCodDetalleSolicitud(Long codDetalleSolicitud) {
		this.codDetalleSolicitud = codDetalleSolicitud;
	}

	public Long getCodSolicitud() {
		return codSolicitud;
	}

	public void setCodSolicitud(Long codSolicitud) {
		this.codSolicitud = codSolicitud;
	}

	public Date getFecDiaSolicitado() {
		return fecDiaSolicitado;
	}

	public void setFecDiaSolicitado(Date fecDiaSolicitado) {
		this.fecDiaSolicitado = fecDiaSolicitado;
	}

}
