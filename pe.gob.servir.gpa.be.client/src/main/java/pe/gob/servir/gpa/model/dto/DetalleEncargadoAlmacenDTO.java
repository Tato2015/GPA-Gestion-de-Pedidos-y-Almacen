package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class DetalleEncargadoAlmacenDTO extends PadreDTO implements Serializable
{
	
	private static final long serialVersionUID = 3723365844902124016L;
	
	private UsuarioPerfilesDTO encargado;
	private DetalleJefeAlmacenDTO detalleJefeAlmacen;
	private Short indicadorRecepcionJefeAlmacen;
	private java.sql.Date fechaRecepcionJefeAlmacen;
	private Short indicadorEnviadoAlJefeArea;
	private java.sql.Date fechaEnviadoAlJefeArea;
	private EstadoDTO estado;
	
	
	public DetalleEncargadoAlmacenDTO()
	{
		
	}
	
	
	public UsuarioPerfilesDTO getEncargado() {
		return encargado;
	}
	public void setEncargado(UsuarioPerfilesDTO encargado) {
		this.encargado = encargado;
	}
	public DetalleJefeAlmacenDTO getDetalleJefeAlmacen() {
		return detalleJefeAlmacen;
	}
	public void setDetalleJefeAlmacen(DetalleJefeAlmacenDTO detalleJefeAlmacen) {
		this.detalleJefeAlmacen = detalleJefeAlmacen;
	}
	public Short getIndicadorRecepcionJefeAlmacen() {
		return indicadorRecepcionJefeAlmacen;
	}
	public void setIndicadorRecepcionJefeAlmacen(Short indicadorRecepcionJefeAlmacen) {
		this.indicadorRecepcionJefeAlmacen = indicadorRecepcionJefeAlmacen;
	}
	public java.sql.Date getFechaRecepcionJefeAlmacen() {
		return fechaRecepcionJefeAlmacen;
	}
	public void setFechaRecepcionJefeAlmacen(java.sql.Date fechaRecepcionJefeAlmacen) {
		this.fechaRecepcionJefeAlmacen = fechaRecepcionJefeAlmacen;
	}
	public Short getIndicadorEnviadoAlJefeArea() {
		return indicadorEnviadoAlJefeArea;
	}
	public void setIndicadorEnviadoAlJefeArea(Short indicadorEnviadoAlJefeArea) {
		this.indicadorEnviadoAlJefeArea = indicadorEnviadoAlJefeArea;
	}
	public java.sql.Date getFechaEnviadoAlJefeArea() {
		return fechaEnviadoAlJefeArea;
	}
	public void setFechaEnviadoAlJefeArea(java.sql.Date fechaEnviadoAlJefeArea) {
		this.fechaEnviadoAlJefeArea = fechaEnviadoAlJefeArea;
	}
	public EstadoDTO getEstado() {
		return estado;
	}
	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
	
	
	


	
	
 }
