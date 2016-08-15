package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class RequerimientoJefeAprobacionDTO extends PadreDTO implements Serializable
{	
	private static final long serialVersionUID = -6594938907973943874L;
	
	private UsuarioPerfilesDTO jefe;
	private RequerimientoAreaDTO requerimientoArea;
	private Short indicadorRecibioEncargadoArea;
	private java.sql.Date fechaRecibioEncargadoArea;
	private Short indicadorEnviadoJefeAlmacen;
	private java.sql.Date fechaEnviadoJefeAlmacen;
	private Short indicadorRecibioJefeAlmacen;
	private java.sql.Date fechaRecibioJefeAlmacen;
	private Short indicadorEnviadoEncargadoAlmacen;
	private java.sql.Date fechaEnviadoEncargadoAlmacen;
	private EstadoDTO estado;
	
	public RequerimientoJefeAprobacionDTO()
	{
		
	}

	public UsuarioPerfilesDTO getJefe() {
		return jefe;
	}

	public void setJefe(UsuarioPerfilesDTO jefe) {
		this.jefe = jefe;
	}

	public RequerimientoAreaDTO getRequerimientoArea() {
		return requerimientoArea;
	}

	public void setRequerimientoArea(RequerimientoAreaDTO requerimientoArea) {
		this.requerimientoArea = requerimientoArea;
	}

	public Short getIndicadorRecibioEncargadoArea() {
		return indicadorRecibioEncargadoArea;
	}

	public void setIndicadorRecibioEncargadoArea(Short indicadorRecibioEncargadoArea) {
		this.indicadorRecibioEncargadoArea = indicadorRecibioEncargadoArea;
	}

	public java.sql.Date getFechaRecibioEncargadoArea() {
		return fechaRecibioEncargadoArea;
	}

	public void setFechaRecibioEncargadoArea(java.sql.Date fechaRecibioEncargadoArea) {
		this.fechaRecibioEncargadoArea = fechaRecibioEncargadoArea;
	}

	public Short getIndicadorEnviadoJefeAlmacen() {
		return indicadorEnviadoJefeAlmacen;
	}

	public void setIndicadorEnviadoJefeAlmacen(Short indicadorEnviadoJefeAlmacen) {
		this.indicadorEnviadoJefeAlmacen = indicadorEnviadoJefeAlmacen;
	}

	public java.sql.Date getFechaEnviadoJefeAlmacen() {
		return fechaEnviadoJefeAlmacen;
	}

	public void setFechaEnviadoJefeAlmacen(java.sql.Date fechaEnviadoJefeAlmacen) {
		this.fechaEnviadoJefeAlmacen = fechaEnviadoJefeAlmacen;
	}

	public Short getIndicadorRecibioJefeAlmacen() {
		return indicadorRecibioJefeAlmacen;
	}

	public void setIndicadorRecibioJefeAlmacen(Short indicadorRecibioJefeAlmacen) {
		this.indicadorRecibioJefeAlmacen = indicadorRecibioJefeAlmacen;
	}

	public java.sql.Date getFechaRecibioJefeAlmacen() {
		return fechaRecibioJefeAlmacen;
	}

	public void setFechaRecibioJefeAlmacen(java.sql.Date fechaRecibioJefeAlmacen) {
		this.fechaRecibioJefeAlmacen = fechaRecibioJefeAlmacen;
	}

	public Short getIndicadorEnviadoEncargadoAlmacen() {
		return indicadorEnviadoEncargadoAlmacen;
	}

	public void setIndicadorEnviadoEncargadoAlmacen(
			Short indicadorEnviadoEncargadoAlmacen) {
		this.indicadorEnviadoEncargadoAlmacen = indicadorEnviadoEncargadoAlmacen;
	}

	public java.sql.Date getFechaEnviadoEncargadoAlmacen() {
		return fechaEnviadoEncargadoAlmacen;
	}

	public void setFechaEnviadoEncargadoAlmacen(
			java.sql.Date fechaEnviadoEncargadoAlmacen) {
		this.fechaEnviadoEncargadoAlmacen = fechaEnviadoEncargadoAlmacen;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
	
	

	
}