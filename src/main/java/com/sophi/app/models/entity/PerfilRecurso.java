package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CAT_PERFILES_RECURSO")
public class PerfilRecurso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_perfil")
	private Long codPerfil;
	
	@Column(name = "desc_perfil")
	private String descPerfil;
	
	@Column(name = "desc_funciones")
	private String descFunciones;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecRegistro;

	public Long getCodPerfil() {
		return codPerfil;
	}

	public void setCodPerfil(Long codPerfil) {
		this.codPerfil = codPerfil;
	}

	public String getDescPerfil() {
		return descPerfil;
	}

	public void setDescPerfil(String descPerfil) {
		this.descPerfil = descPerfil;
	}

	public String getDescFunciones() {
		return descFunciones;
	}

	public void setDescFunciones(String descFunciones) {
		this.descFunciones = descFunciones;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}
	
}
