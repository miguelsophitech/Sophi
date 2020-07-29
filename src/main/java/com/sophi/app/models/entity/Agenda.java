package com.sophi.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CONTACTOS")
public class Agenda implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_contacto")
	private Long codContacto;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_contacto")
	private String descContacto;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_puesto")
	private String descPuesto;
	
	@Column(name = "val_recursos_a_cargo")
	private Long valRecursosACargo;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Email(message = "No es un email correcto")
	@Column(name = "desc_correo_electronico")
	private String descCorreoElectronico;
	
	@Column(name = "fec_nacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecNacimiento;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Pattern(regexp = "(\\+52|55)?[ -]*(55)[ -]*([0-9][ -]*){8}")
	@Column(name = "desc_tel_celular")
	private String descTelCelular;
	
	@NotEmpty(message = "Este dato no debe estar vacío")
	@Column(name = "desc_tel_empresa")
	@Pattern(regexp = "(\\55)?[ -]*(55)[ -]*([0-9][ -]*){8}")
	private String descTelEmpresa;
	
	@Pattern(regexp = "([0-9]){3}")
	@Column(name = "desc_tel_ext")
	private String descTelExt;
	
	@Column(name = "fec_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecRegistro;
	
	@Transient
	private String nombreCliente;
	
	@Column(name = "cod_cliente")
	private Long codCliente;

	@Transient
	private String nombreCargo;
	
	@Column(name = "cod_cargo")
	private Long codCargo;
	
	@PrePersist
	public void prePersist() {
		fecRegistro = new Date();
	}

	public Long getCodContacto() {
		return codContacto;
	}

	public void setCodContacto(Long codContacto) {
		this.codContacto = codContacto;
	}

	public String getDescContacto() {
		return descContacto;
	}

	public void setDescContacto(String descContacto) {
		this.descContacto = descContacto;
	}

	public String getDescPuesto() {
		return descPuesto;
	}

	public void setDescPuesto(String descPuesto) {
		this.descPuesto = descPuesto;
	}

	public Long getValRecursosACargo() {
		return valRecursosACargo;
	}

	public void setValRecursosACargo(Long valRecursosACargo) {
		this.valRecursosACargo = valRecursosACargo;
	}

	public String getDescCorreoElectronico() {
		return descCorreoElectronico;
	}

	public void setDescCorreoElectronico(String descCorreoElectronico) {
		this.descCorreoElectronico = descCorreoElectronico;
	}

	public Date getFecNacimiento() {
		return fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public String getDescTelCelular() {
		return descTelCelular;
	}

	public void setDescTelCelular(String descTelCelular) {
		this.descTelCelular = descTelCelular;
	}

	public String getDescTelEmpresa() {
		return descTelEmpresa;
	}

	public void setDescTelEmpresa(String descTelEmpresa) {
		this.descTelEmpresa = descTelEmpresa;
	}

	public String getDescTelExt() {
		return descTelExt;
	}

	public void setDescTelExt(String descTelExt) {
		this.descTelExt = descTelExt;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public Long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Long codCliente) {
		this.codCliente = codCliente;
	}

	public Long getCodCargo() {
		return codCargo;
	}

	public void setCodCargo(Long codCargo) {
		this.codCargo = codCargo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getNombreCargo() {
		return nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	

	
}
