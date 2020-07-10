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
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECURSOS_GASTOS")
public class AprobacionGastos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_tipo_gasto")
    private Long codTipoGasto;

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
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecRegistro;

    @NotEmpty
    @Column(name = "cod_proyecto")
    private Long codProyecto;

    @NotEmpty
    @Column(name = "cod_recurso")
    private Long codRecurso;

    @NotEmpty
    @Column(name = "cod_cliente")
    private Long codCliente;

    @NotEmpty
    @Column(name = "cod_estatus_proyecto")
    private Long codEstatusProyecto;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getCodTipoGasto() {
        return codTipoGasto;
    }

    public void setCodTipoGasto(Long codTipoGasto) {
        this.codTipoGasto = codTipoGasto;
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

    public Long getCodProyecto() {
        return codProyecto;
    }

    public void setCodProyecto(Long codProyecto) {
        this.codProyecto = codProyecto;
    }

    public Long getCodRecurso() {
        return codRecurso;
    }

    public void setCodRecurso(Long codRecurso) {
        this.codRecurso = codRecurso;
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
    
}