package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECURSOS_GASTO")
public class AprobacionGastos implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
	RecursoGastoId recursoGastoId;

	@OneToOne
    @JoinColumn(name = "cod_tipo_gasto", insertable = false, updatable = false)
    private TipoGasto tipogasto;

	@NotEmpty
    @Column(name = "desc_comentario")
    private String descComentario;

    @NotEmpty
    @Column(name = "fec_gasto")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecGasto;

    @NotEmpty
    @Column(name = "imp_gasto")
    private Float impGasto;

    @NotEmpty
    @Column(name = "desc_comprobante")
    private String descComprobante;

    @NotEmpty
    @Column(name = "fec_registro")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecRegistro;
    
    @Column(name = "fec_validacion")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecValidacion;
    
    public Date getFecValidacion() {
		return fecValidacion;
	}

	public void setFecValidacion(Date fecValidacion) {
		this.fecValidacion = fecValidacion;
	}

	@OneToOne
    @JoinColumn(name = "cod_recurso", insertable = false, updatable = false)
    private Recurso recurso;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public RecursoGastoId getRecursoGastoId() {
		return recursoGastoId;
	}

	public void setRecursoGastoId(RecursoGastoId recursoGastoId) {
		this.recursoGastoId = recursoGastoId;
	}
    
    public TipoGasto getTipogasto() {
		return tipogasto;
	}

	public void setTipogasto(TipoGasto tipogasto) {
		this.tipogasto = tipogasto;
	}

    public String getDescComentario() {
        return descComentario;
    }

    public void setDescComentario(String descComentario) {
        this.descComentario = descComentario;
    }

    public Date getFecGasto() {
        return fecGasto;
    }

    public void setFecGasto(Date fecGasto) {
        this.fecGasto = fecGasto;
    }

    public Float getImpGasto() {
        return impGasto;
    }

    public void setImpGasto(Float impGasto) {
        this.impGasto = impGasto;
    }

    public String getDescComprobante() {
        return descComprobante;
    }

    public void setDescComprobante(String descComprobante) {
        this.descComprobante = descComprobante;
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
    
}