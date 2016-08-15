
 
package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;




public class AreaDTO extends PadreDTO implements Serializable{

	private static final long serialVersionUID = 5985800496105048881L;
	
	private AreaDTO areaPadre;
	private String codigoArea;
	private String descripcionArea;
	private String abreviaturaArea;
	private EstadoDTO estado;
	public AreaDTO getAreaPadre() {
		return areaPadre;
	}
	public void setAreaPadre(AreaDTO areaPadre) {
		this.areaPadre = areaPadre;
	}
	public String getCodigoArea() {
		return codigoArea;
	}
	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}
	public String getDescripcionArea() {
		return descripcionArea;
	}
	public void setDescripcionArea(String descripcionArea) {
		this.descripcionArea = descripcionArea;
	}
	public String getAbreviaturaArea() {
		return abreviaturaArea;
	}
	public void setAbreviaturaArea(String abreviaturaArea) {
		this.abreviaturaArea = abreviaturaArea;
	}
	public EstadoDTO getEstado() {
		return estado;
	}
	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}	  
	
}
