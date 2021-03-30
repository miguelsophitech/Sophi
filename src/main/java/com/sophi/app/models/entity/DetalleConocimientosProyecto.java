package com.sophi.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "DETALLE_CONOCIMIENTOS_PROYECTOS")
public class DetalleConocimientosProyecto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_detalle_conocimiento")
	private Long codDetalleConocimiento;
	
	@Column(name = "cod_trayectoria_proyecto")
	private Long codTrayectoriaProyecto;
	
	@Column(name = "cod_conocimiento")
	private Long codConocimiento;

	public Long getCodDetalleConocimiento() {
		return codDetalleConocimiento;
	}

	public void setCodDetalleConocimiento(Long codDetalleConocimiento) {
		this.codDetalleConocimiento = codDetalleConocimiento;
	}

	public Long getCodTrayectoriaProyecto() {
		return codTrayectoriaProyecto;
	}

	public void setCodTrayectoriaProyecto(Long codTrayectoriaProyecto) {
		this.codTrayectoriaProyecto = codTrayectoriaProyecto;
	}

	public Long getCodConocimiento() {
		return codConocimiento;
	}

	public void setCodConocimiento(Long codConocimiento) {
		this.codConocimiento = codConocimiento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
