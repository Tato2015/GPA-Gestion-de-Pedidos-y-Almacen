

package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;


public class UsuarioDTO extends PadreDTO implements Serializable 
{

	private static final long serialVersionUID = 1516227346268624498L;

	private AreaDTO area;
	private PersonaDTO persona;
	private String codigoUsuario;
	private String claveUsuario;
	private String descripcionUsuario;
	private EstadoDTO estado;
	
	public UsuarioDTO()
	{
		
	}

	public AreaDTO getArea() {
		return area;
	}

	public void setArea(AreaDTO area) {
		this.area = area;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public String getDescripcionUsuario() {
		return descripcionUsuario;
	}

	public void setDescripcionUsuario(String descripcionUsuario) {
		this.descripcionUsuario = descripcionUsuario;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
		
}