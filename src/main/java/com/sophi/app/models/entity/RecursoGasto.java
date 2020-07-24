package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECURSOS_GASTOS")
public class RecursoGasto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	RecursoGastoId recursoGastoId;
	
	@Column(name = "desc_comentario")
	private String descComentario;
	
	@Column(name = "fec_gasto")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecGasto;
	
	@Column(name = "imp_gasto")
	private float impGasto;
	
	@Lob
    @Column(name = "desc_comprobante", columnDefinition="BLOB")
    private byte[] comprobante;

	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fecRegistro;
	
	@Transient
	private ProyectoRecurso proyectoRecurso;

	public RecursoGastoId getRecursoGastoId() {
		return recursoGastoId;
	}

	public void setRecursoGastoId(RecursoGastoId recursoGastoId) {
		this.recursoGastoId = recursoGastoId;
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

	public float getImpGasto() {
		return impGasto;
	}

	public void setImpGasto(float impGasto) {
		this.impGasto = impGasto;
	}

	public byte[] getComprobante() {
		return comprobante;
	}

	public void setComprobante(byte[] comprobante) {
		this.comprobante = comprobante;
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
	
	public RecursoGasto(RecursoGastoId id, String descComentario,
			Date fecGasto, float impGasto, byte[] comprobante,
			Date fecRegistro) {
		super();
		recursoGastoId = id;
		this.descComentario = descComentario;
		this.fecGasto = fecGasto;
		this.impGasto = impGasto;
		this.comprobante = comprobante;
		this.fecRegistro = fecRegistro;

	}

	public RecursoGasto() {
		
	}

	public ProyectoRecurso getProyectoRecurso() {
		return proyectoRecurso;
	}

	public void setProyectoRecurso(ProyectoRecurso proyectoRecurso) {
		this.proyectoRecurso = proyectoRecurso;
	}
	
}
