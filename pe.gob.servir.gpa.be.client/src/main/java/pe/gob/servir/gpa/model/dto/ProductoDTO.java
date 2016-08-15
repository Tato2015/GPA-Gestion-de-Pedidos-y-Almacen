/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li>
 * </ul>
 * Objeto: TipoDTO.java
 * Descripcion: Clase que transporta datos de la Entidad TipoDTO hacia la Capa de presentacion
 * @Autor 		            : Consultora  - P & P BMS (P & P Business Management Solutions)
 * @version                 : 1.0
 * @sinse 					: 2015
 * ----------------------------------------------------------------------------
 * Modificaciones
 * Codigo	Fecha		Nombre			Descripcion
 * ----------------------------------------------------------------------------
 * 
 */
package pe.gob.servir.gpa.model.dto;
import java.io.Serializable;

public class ProductoDTO extends PadreDTO implements Serializable{

  /** Atributo que debe ser distinto para cada version compilada que tengamos de esta clase */
	private static final long serialVersionUID = -7196989429647129614L;
    		
	private String  	codigoProducto;	
	private String  	descripcionProducto;	
	private TipoDTO 	tipoFamilia;
	private TipoDTO 	tipoCategoria;
	private TipoDTO 	tipoUnidadMedida;	
	private Double  	stockMinimo;	
	private Double  	stockMaximo;	
	private Short   	habilitado;
	private Short   	indicadorActivo;		   
    private EstadoDTO   estado;      
    
    public ProductoDTO()
    {
    }

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public TipoDTO getTipoFamilia() {
		return tipoFamilia;
	}

	public void setTipoFamilia(TipoDTO tipoFamilia) {
		this.tipoFamilia = tipoFamilia;
	}

	public TipoDTO getTipoCategoria() {
		return tipoCategoria;
	}

	public void setTipoCategoria(TipoDTO tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}

	public TipoDTO getTipoUnidadMedida() {
		return tipoUnidadMedida;
	}

	public void setTipoUnidadMedida(TipoDTO tipoUnidadMedida) {
		this.tipoUnidadMedida = tipoUnidadMedida;
	}

	public Double getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(Double stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public Double getStockMaximo() {
		return stockMaximo;
	}

	public void setStockMaximo(Double stockMaximo) {
		this.stockMaximo = stockMaximo;
	}

	public Short getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Short habilitado) {
		this.habilitado = habilitado;
	}

	public Short getIndicadorActivo() {
		return indicadorActivo;
	}

	public void setIndicadorActivo(Short indicadorActivo) {
		this.indicadorActivo = indicadorActivo;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

}