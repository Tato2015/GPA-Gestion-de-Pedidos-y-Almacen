package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class UsuarioPerfilesDTO extends PadreDTO implements Serializable
{
	private static final long serialVersionUID = -4050189440251567179L;
	
	private UsuarioDTO usuario;
	private PerfilDTO perfil;
	private EstadoDTO estado;
	
	public UsuarioPerfilesDTO()
	{
		
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public PerfilDTO getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilDTO perfil) {
		this.perfil = perfil;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

}