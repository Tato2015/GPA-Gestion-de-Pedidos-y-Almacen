package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
public class RequerimientoAreaDTO extends PadreDTO implements Serializable 
{

	private static final long serialVersionUID = 6085249521632086699L;

	private Short indicadorEnviadoAlJefe;
	private java.sql.Date fechaEnviadoAlJefe;
	//Inica si el jefe realmenete ha recibido el requerimiento consolidado.
	private Short indicadorRequerimientoRecepcionDelJefe;
	private java.sql.Date fechaRequerimientoRecepcionDelJefe;
	private EstadoDTO estado;
	private AreaDTO area;
	private ProgramacionDTO programacion;
	
	public RequerimientoAreaDTO()
	{
		
	}

	public java.sql.Date getFechaEnviadoAlJefe() {
		return fechaEnviadoAlJefe;
	}

	public void setFechaEnviadoAlJefe(java.sql.Date fechaEnviadoAlJefe) {
		this.fechaEnviadoAlJefe = fechaEnviadoAlJefe;
	}

	
	public java.sql.Date getFechaRequerimientoRecepcionDelJefe() {
		return fechaRequerimientoRecepcionDelJefe;
	}

	public void setFechaRequerimientoRecepcionDelJefe(
			java.sql.Date fechaRequerimientoRecepcionDelJefe) {
		this.fechaRequerimientoRecepcionDelJefe = fechaRequerimientoRecepcionDelJefe;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

	public AreaDTO getArea() {
		return area;
	}

	public void setArea(AreaDTO area) {
		this.area = area;
	}

	public Short getIndicadorEnviadoAlJefe() {
		return indicadorEnviadoAlJefe;
	}

	public void setIndicadorEnviadoAlJefe(Short indicadorEnviadoAlJefe) {
		this.indicadorEnviadoAlJefe = indicadorEnviadoAlJefe;
	}

	public Short getIndicadorRequerimientoRecepcionDelJefe() {
		return indicadorRequerimientoRecepcionDelJefe;
	}

	public void setIndicadorRequerimientoRecepcionDelJefe(
			Short indicadorRequerimientoRecepcionDelJefe) {
		this.indicadorRequerimientoRecepcionDelJefe = indicadorRequerimientoRecepcionDelJefe;
	}

	public ProgramacionDTO getProgramacion() {
		return programacion;
	}

	public void setProgramacion(ProgramacionDTO programacion) {
		this.programacion = programacion;
	}
	
	

	


}
