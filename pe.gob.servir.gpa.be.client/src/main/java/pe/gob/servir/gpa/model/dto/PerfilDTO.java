package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class PerfilDTO extends PadreDTO implements Serializable 
{
	
	private static final long serialVersionUID = -6304493419650988930L;
	
	private String descripcionPerfil;
	private EstadoDTO estado;
	
	public PerfilDTO()
	{
		
	}

	public String getDescripcionPerfil() {
		return descripcionPerfil;
	}

	public void setDescripcionPerfil(String descripcionPerfil) {
		this.descripcionPerfil = descripcionPerfil;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
	
}