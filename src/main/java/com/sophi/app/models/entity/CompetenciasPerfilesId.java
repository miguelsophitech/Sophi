package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompetenciasPerfilesId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "cod_perfil")
	private Long codPerfil;
	
	@Column(name = "cod_competencias")
	private Long codCompetencias;
	
	public CompetenciasPerfilesId() {
		
	}
	
	public CompetenciasPerfilesId(Long codPerfil, Long codCompetencias) {
		super();
		this.codPerfil = codPerfil;
		this.codCompetencias = codCompetencias;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codPerfil, codCompetencias);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompetenciasPerfilesId CompetenciaPerfilId = (CompetenciasPerfilesId) o;
        return codPerfil.equals(CompetenciaPerfilId.codPerfil) &&
        		codCompetencias.equals(CompetenciaPerfilId.codCompetencias);
	}

	public Long getCodPerfil() {
		return codPerfil;
	}

	public void setCodPerfil(Long codPerfil) {
		this.codPerfil = codPerfil;
	}

	public Long getCodCompetencias() {
		return codCompetencias;
	}

	public void setCodCompetencias(Long codCompetencias) {
		this.codCompetencias = codCompetencias;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
