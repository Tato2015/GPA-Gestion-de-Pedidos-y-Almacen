package pe.gob.servir.gpa.service.ejb;
import java.util.List;
import pe.gob.servir.gpa.model.dto.ProductoDTO;

public interface ProductoServiceLocal 
{		
	public List<ProductoDTO> buscarProducto(Integer idFamilia,Integer idCategoria,Integer idEstado,String descripcionProducto,String codigoProducto) throws Exception;
	
	public Boolean actualizarProducto(ProductoDTO productoDTO) throws Exception;

	
}
