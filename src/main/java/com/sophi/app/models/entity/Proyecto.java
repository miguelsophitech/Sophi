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
@Table(name = "PROYECTOS")
public class Proyecto implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_proyecto")
	private Long codProyecto;
	
	@Column(name = "cod_cliente")
	private Long codCliente;
	
	@Column(name = "cod_estatus_proyecto")
	private Long codEstatusProyecto;
	
	@NotEmpty
	@Column(name = "desc_proyecto")
	private String 	descProyecto;
	
	@Column(name = "imp_presupuesto")
	private Float impPresupuesto;
	
	@NotEmpty
	@Column(name = "desc_codigo_proyecto")
	private String 	descCodigoProyecto;
	
	@Column(name = "cod_clasificacion_proyecto")
	private Long codClasificacionProyecto;
	
	@Column(name = "cod_area_comercial")
	private Long codAreaComercial;
	
	@Column(name = "val_total_horas_vendidas")
	private Long valTotalHorasVendidas;
	
	@Column(name = "imp_precio_propuesta")
	private Float impPrecioPropuesta;
	
	@Column(name = "fec_incio_proyecto")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecIncioProyecto;
	
	@Column(name = "fec_fin_proyecto")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecFinProyecto;

	@Column(name = "cod_tipo_proyecto")
	private Long codTipoProyecto;
	
	@Column(name = "val_porcentaje_riesgo")
	private Long valPorcentajeRiesgo;
	
	@Column(name = "val_total_horas_proyecto")
	private Long valTotalHorasProyecto;
	
	@Column(name = "imp_costo_proyecto")
	private Float impCostoProyecto;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecRegistro;
	
	@Column(name = "cod_recurso_lider")
	private Long codRecursoLider;
	
	@Column(name = "cod_tipo_facturacion")
	private Long codTipoFacturacion;

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

	public String getDescProyecto() {
		return descProyecto;
	}

	public void setDescProyecto(String descProyecto) {
		this.descProyecto = descProyecto;
	}

	public Float getImpPresupuesto() {
		return impPresupuesto;
	}

	public void setImpPresupuesto(Float impPresupuesto) {
		this.impPresupuesto = impPresupuesto;
	}

	public String getDescCodigoProyecto() {
		return descCodigoProyecto;
	}

	public void setDescCodigoProyecto(String descCodigoProyecto) {
		this.descCodigoProyecto = descCodigoProyecto;
	}

	public Long getCodClasificacionProyecto() {
		return codClasificacionProyecto;
	}

	public void setCodClasificacionProyecto(Long codClasificacionProyecto) {
		this.codClasificacionProyecto = codClasificacionProyecto;
	}

	public Long getCodAreaComercial() {
		return codAreaComercial;
	}

	public void setCodAreaComercial(Long codAreaComercial) {
		this.codAreaComercial = codAreaComercial;
	}

	public Long getValTotalHorasVendidas() {
		return valTotalHorasVendidas;
	}

	public void setValTotalHorasVendidas(Long valTotalHorasVendidas) {
		this.valTotalHorasVendidas = valTotalHorasVendidas;
	}

	public Float getImpPrecioPropuesta() {
		return impPrecioPropuesta;
	}

	public void setImpPrecioPropuesta(Float impPrecioPropuesta) {
		this.impPrecioPropuesta = impPrecioPropuesta;
	}

	public Date getFecIncioProyecto() {
		return fecIncioProyecto;
	}

	public void setFecIncioProyecto(Date fecIncioProyecto) {
		this.fecIncioProyecto = fecIncioProyecto;
	}

	public Date getFecFinProyecto() {
		return fecFinProyecto;
	}

	public void setFecFinProyecto(Date fecFinProyecto) {
		this.fecFinProyecto = fecFinProyecto;
	}

	public Long getCodTipoProyecto() {
		return codTipoProyecto;
	}

	public void setCodTipoProyecto(Long codTipoProyecto) {
		this.codTipoProyecto = codTipoProyecto;
	}

	public Long getValPorcentajeRiesgo() {
		return valPorcentajeRiesgo;
	}

	public void setValPorcentajeRiesgo(Long valPorcentajeRiesgo) {
		this.valPorcentajeRiesgo = valPorcentajeRiesgo;
	}

	public Long getValTotalHorasProyecto() {
		return valTotalHorasProyecto;
	}

	public void setValTotalHorasProyecto(Long valTotalHorasProyecto) {
		this.valTotalHorasProyecto = valTotalHorasProyecto;
	}

	public Float getImpCostoProyecto() {
		return impCostoProyecto;
	}

	public void setImpCostoProyecto(Float impCostoProyecto) {
		this.impCostoProyecto = impCostoProyecto;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public Long getCodRecursoLider() {
		return codRecursoLider;
	}

	public void setCodRecursoLider(Long codRecursoLider) {
		this.codRecursoLider = codRecursoLider;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCodTipoFacturacion() {
		return codTipoFacturacion;
	}

	public void setCodTipoFacturacion(Long codTipoFacturacion) {
		this.codTipoFacturacion = codTipoFacturacion;
	}
	
}
