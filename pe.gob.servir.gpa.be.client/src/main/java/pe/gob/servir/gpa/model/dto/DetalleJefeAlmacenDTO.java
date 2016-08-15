package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class DetalleJefeAlmacenDTO extends PadreDTO implements Serializable
{	

	private static final long serialVersionUID = 2117423375530050247L;
	
	private UsuarioDTO jefeAlmacen;
	private RequerimientoAreaDTO requerimientoAprobacionJefe;
	private Short indicadorRecepcionJefeArea;
	private java.sql.Date fechaRecepcionJefeArea;
	private Short indicadorEnviadoEncargadoAlmacen;
	private java.sql.Date fechaEnviadoEncargadoAlmacen;
	private EstadoDTO estado;
	private RequerimientoAreaDTO requerimientoArea;
	
	public DetalleJefeAlmacenDTO()
	{
		
	}

	public UsuarioDTO getJefeAlmacen() {
		return jefeAlmacen;
	}

	public void setJefeAlmacen(UsuarioDTO jefeAlmacen) {
		this.jefeAlmacen = jefeAlmacen;
	}

	
	
	public RequerimientoAreaDTO getRequerimientoAprobacionJefe() {
		return requerimientoAprobacionJefe;
	}

	public void setRequerimientoAprobacionJefe(
			RequerimientoAreaDTO requerimientoAprobacionJefe) {
		this.requerimientoAprobacionJefe = requerimientoAprobacionJefe;
	}

	public Short getIndicadorRecepcionJefeArea() {
		return indicadorRecepcionJefeArea;
	}

	public void setIndicadorRecepcionJefeArea(Short indicadorRecepcionJefeArea) {
		this.indicadorRecepcionJefeArea = indicadorRecepcionJefeArea;
	}

	public java.sql.Date getFechaRecepcionJefeArea() {
		return fechaRecepcionJefeArea;
	}

	public void setFechaRecepcionJefeArea(java.sql.Date fechaRecepcionJefeArea) {
		this.fechaRecepcionJefeArea = fechaRecepcionJefeArea;
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

	public RequerimientoAreaDTO getRequerimientoArea() {
		return requerimientoArea;
	}

	public void setRequerimientoArea(RequerimientoAreaDTO requerimientoArea) {
		this.requerimientoArea = requerimientoArea;
	}
	
	

 }
