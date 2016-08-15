package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
import java.util.Date;

public class PadreDTO implements Serializable 
{

	private static final long serialVersionUID = -3597368161141306635L;
	
	protected Integer id;
	protected Short indicadorActivo;
	protected String codigoUsuarioRegistro;
	protected String ipRegistro;
	protected Date fechaRegistro;
	protected String codigoUsuarioModificacion;
	protected String ipModificacion;
	protected Date fechaModificacion;
	
	public PadreDTO()
	{
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getIndicadorActivo() {
		return indicadorActivo;
	}

	public void setIndicadorActivo(Short indicadorActivo) {
		this.indicadorActivo = indicadorActivo;
	}

	public String getCodigoUsuarioRegistro() {
		return codigoUsuarioRegistro;
	}

	public void setCodigoUsuarioRegistro(String codigoUsuarioRegistro) {
		this.codigoUsuarioRegistro = codigoUsuarioRegistro;
	}

	public String getIpRegistro() {
		return ipRegistro;
	}

	public void setIpRegistro(String ipRegistro) {
		this.ipRegistro = ipRegistro;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getCodigoUsuarioModificacion() {
		return codigoUsuarioModificacion;
	}

	public void setCodigoUsuarioModificacion(String codigoUsuarioModificacion) {
		this.codigoUsuarioModificacion = codigoUsuarioModificacion;
	}

	public String getIpModificacion() {
		return ipModificacion;
	}

	public void setIpModificacion(String ipModificacion) {
		this.ipModificacion = ipModificacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	
	
	
	
}
