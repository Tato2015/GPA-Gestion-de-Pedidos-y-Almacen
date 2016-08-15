package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
import java.sql.Date;

public class RequerimientoUsuarioDTO extends PadreDTO implements Serializable 
{

	private static final long serialVersionUID = -7687289383528520998L;
	
	private RequerimientoDTO requerimiento;
	private UsuarioDTO usuario;
	private ProgramacionAreasDTO programacionAreas;
	private Short indicadorEnviadoAlEncargado;
	private java.sql.Date fechaEnviadoAlEncargado;
	private Short indicadorRequerimientoRecibidoDelEncargado;
	private Date fechaRequerimientoRecibidoDelEncargado;
	private EstadoDTO estado;
	
	public RequerimientoUsuarioDTO()
	{
		
	}

	public RequerimientoDTO getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(RequerimientoDTO requerimiento) {
		this.requerimiento = requerimiento;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public ProgramacionAreasDTO getProgramacionAreas() {
		return programacionAreas;
	}

	public void setProgramacionAreas(ProgramacionAreasDTO programacionAreas) {
		this.programacionAreas = programacionAreas;
	}

	public Short getIndicadorEnviadoAlEncargado() {
		return indicadorEnviadoAlEncargado;
	}

	public void setIndicadorEnviadoAlEncargado(Short indicadorEnviadoAlEncargado) {
		this.indicadorEnviadoAlEncargado = indicadorEnviadoAlEncargado;
	}

	
	
	public Short getIndicadorRequerimientoRecibidoDelEncargado() {
		return indicadorRequerimientoRecibidoDelEncargado;
	}

	public void setIndicadorRequerimientoRecibidoDelEncargado(
			Short indicadorRequerimientoRecibidoDelEncargado) {
		this.indicadorRequerimientoRecibidoDelEncargado = indicadorRequerimientoRecibidoDelEncargado;
	}

	public Date getFechaRequerimientoRecibidoDelEncargado() {
		return fechaRequerimientoRecibidoDelEncargado;
	}

	public void setFechaRequerimientoRecibidoDelEncargado(
			Date fechaRequerimientoRecibidoDelEncargado) {
		this.fechaRequerimientoRecibidoDelEncargado = fechaRequerimientoRecibidoDelEncargado;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
	
	

	public java.sql.Date getFechaEnviadoAlEncargado() {
		return fechaEnviadoAlEncargado;
	}

	public void setFechaEnviadoAlEncargado(java.sql.Date fechaEnviadoAlEncargado) {
		this.fechaEnviadoAlEncargado = fechaEnviadoAlEncargado;
	}

	@Override
	public String toString() {
		return "RequerimientoUsuarioDTO [requerimiento=" + requerimiento
				+ ", usuario=" + usuario + ", programacionAreas="
				+ programacionAreas + ", indicadorEnviadoAlEncargado="
				+ indicadorEnviadoAlEncargado + ", fechaEnviadoAlEncargado="
				+ fechaEnviadoAlEncargado
				+ ", indicadorRequerimientoRecibidoDelEncargado="
				+ indicadorRequerimientoRecibidoDelEncargado
				+ ", fechaRequerimientoRecibidoDelEncargado="
				+ fechaRequerimientoRecibidoDelEncargado + ", estado=" + estado
				+ "]";
	}
		
	
	
	
}