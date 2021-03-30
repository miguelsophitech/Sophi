package com.sophi.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CAT_CONOCIMIENTOS")
public class Conocimientos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_conocimiento")
	private Long codConocimiento;
	
	@Column(name = "desc_conocimiento")
	private String descConocimiento;
	
	@Column(name = "cod_tipo_conocimiento")
	private Long codTipoConocimiento;

	public Long getCodConocimiento() {
		return codConocimiento;
	}

	public void setCodConocimiento(Long codConocimiento) {
		this.codConocimiento = codConocimiento;
	}

	public String getDescConocimiento() {
		return descConocimiento;
	}

	public void setDescConocimiento(String descConocimiento) {
		this.descConocimiento = descConocimiento;
	}

	public Long getCodTipoConocimiento() {
		return codTipoConocimiento;
	}

	public void setCodTipoConocimiento(Long codTipoConocimiento) {
		this.codTipoConocimiento = codTipoConocimiento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
