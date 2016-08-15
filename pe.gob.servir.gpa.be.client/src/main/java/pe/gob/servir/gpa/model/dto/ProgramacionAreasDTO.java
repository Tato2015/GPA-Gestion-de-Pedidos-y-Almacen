package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class ProgramacionAreasDTO extends PadreDTO implements Serializable
{

	private static final long serialVersionUID = 6422559045978188129L;
	
	private ProgramacionDTO programacion;
	private AreaDTO area;
	private Integer anio;
	private EstadoDTO estado;
	public ProgramacionDTO getProgramacion() {
		return programacion;
	}
	public void setProgramacion(ProgramacionDTO programacion) {
		this.programacion = programacion;
	}
	public AreaDTO getArea() {
		return area;
	}
	public void setArea(AreaDTO area) {
		this.area = area;
	}
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public EstadoDTO getEstado() {
		return estado;
	}
	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
	
}
