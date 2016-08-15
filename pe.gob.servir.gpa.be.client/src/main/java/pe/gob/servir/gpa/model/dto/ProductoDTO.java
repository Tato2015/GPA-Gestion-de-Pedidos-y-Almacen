package pe.gob.servir.gpa.model.dto;
import java.io.Serializable;
import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class ProductoDTO extends PadreDTO implements Serializable,Comparator
{

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

	@Override
	public int compare(Object o1, Object o2) 
	{
		ProductoDTO producto1 =  (ProductoDTO)o1;
		ProductoDTO producto2 =  (ProductoDTO)o2;		
		return producto1.getId().compareTo(producto2.getId());		
	}

	@Override
	public String toString() 
	{
		return "ProductoDTO [codigoProducto=" + codigoProducto
				+ ", id ="+ this.id
				+ ", descripcionProducto=" + descripcionProducto
				+ ", tipoFamilia=" + tipoFamilia 
				+ ", tipoCategoria=" + tipoCategoria 
				+ ", tipoUnidadMedida=" + tipoUnidadMedida
				+ ", stockMinimo=" + stockMinimo 
				+ ", stockMaximo=" + stockMaximo 
				+ ", habilitado=" + habilitado
				+ ", indicadorActivo=" + indicadorActivo 
				+ ", estado=" + estado
				+ "]";
	}

	
	
}