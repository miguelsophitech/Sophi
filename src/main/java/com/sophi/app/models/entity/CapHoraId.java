package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CapHoraId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "cod_actividad")
	private Long codActividad;
	
	@Column(name = "cod_recurso")
	private Long codRecurso;
	
	@Column(name = "cod_proyecto")
	private Long codProyecto;
	
	@Column(name = "cod_cliente")
	private Long codCliente;
	
	@Column(name = "cod_estatus_proyecto")
	private Long codEstatusProyecto;
	
	public CapHoraId() {
		
	}
	
	public CapHoraId(Long codActividad, Long codRecurso, Long codProyecto, Long codCliente, Long codEstatusProyecto) {
		super();
		this.codActividad = codActividad;
		this.codRecurso = codRecurso;
		this.codProyecto = codProyecto;
		this.codCliente = codCliente;
		this.codEstatusProyecto = codEstatusProyecto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codActividad,codRecurso,codProyecto,codCliente,codEstatusProyecto);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapHoraId CapHoraId = (CapHoraId) o;
        return codCliente.equals(CapHoraId.codCliente) &&
        		codRecurso.equals(CapHoraId.codRecurso) &&
        		codProyecto.equals(CapHoraId.codProyecto) &&
        		codCliente.equals(CapHoraId.codCliente) &&
        		codEstatusProyecto.equals(CapHoraId.codEstatusProyecto);
	}

	public Long getCodActividad() {
		return codActividad;
	}

	public void setCodActividad(Long codActividad) {
		this.codActividad = codActividad;
	}

	public Long getCodRecurso() {
		return codRecurso;
	}

	public void setCodRecurso(Long codRecurso) {
		this.codRecurso = codRecurso;
	}

	public Long getCodProyecto() {
		return codProyecto;
	}

	public void setCodProyecto(Long codProyecto) {
		this.codProyecto = codProyecto;
	}

	public Long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Long codCliente) {
		this.codCliente = codCliente;
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
