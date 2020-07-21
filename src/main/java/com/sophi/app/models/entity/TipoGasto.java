package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CAT_TIPO_GASTOS")
public class TipoGasto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_tipo_gasto")
	private Long codTipoGasto;
	
	@Column(name = "desc_tipo_gasto")
	private String descTipoGasto;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecRegistro;

	public Long getCodTipoGasto() {
		return codTipoGasto;
	}

	public void setCodTipoGasto(Long codTipoGasto) {
		this.codTipoGasto = codTipoGasto;
	}

	public String getDescTipoGasto() {
		return descTipoGasto;
	}

	public void setDescTipoGasto(String descTipoGasto) {
		this.descTipoGasto = descTipoGasto;
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
