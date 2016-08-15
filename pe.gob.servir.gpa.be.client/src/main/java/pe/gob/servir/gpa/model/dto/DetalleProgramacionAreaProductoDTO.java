package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class DetalleProgramacionAreaProductoDTO extends PadreDTO implements Serializable 
{
	private static final long serialVersionUID = 1170040392811999892L;
	
	private ProgramacionAreasDTO programacionAreas;
	private ProductoDTO productoDisponible;
	private Integer stockDisponible;
	private EstadoDTO estado;
	public ProgramacionAreasDTO getProgramacionAreas() {
		return programacionAreas;
	}
	public void setProgramacionAreas(ProgramacionAreasDTO programacionAreas) {
		this.programacionAreas = programacionAreas;
	}
	
	public ProductoDTO getProductoDisponible() {
		return productoDisponible;
	}
	public void setProductoDisponible(ProductoDTO productoDisponible) {
		this.productoDisponible = productoDisponible;
	}
	public Integer getStockDisponible() {
		return stockDisponible;
	}
	public void setStockDisponible(Integer stockDisponible) {
		this.stockDisponible = stockDisponible;
	}
	public EstadoDTO getEstado() {
		return estado;
	}
	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

}
