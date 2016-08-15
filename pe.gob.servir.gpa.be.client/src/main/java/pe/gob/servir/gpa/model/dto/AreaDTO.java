package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class AreaDTO  extends PadreDTO implements Serializable,Comparator{

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
	
	//Prueba para ordenar lista de menor a mayor por ID.	
	@Override
	public int compare(Object o1, Object o2) 
	{
		AreaDTO area1 = (AreaDTO)o1;
		AreaDTO area2 = (AreaDTO)o2;		
		return area1.getId().compareTo(area2.getId());
	}
	
	@Override
	public String toString() 
	{
		return "AreaDTO [areaPadre=" + areaPadre
				+ ", id"+this.id
				+ ", codigoArea=" + codigoArea
				+ ", descripcionArea=" + descripcionArea 
				+ ", abreviaturaArea="+ abreviaturaArea 
				+ ", estado=" + estado + "]";
	}	  

	
	
}
