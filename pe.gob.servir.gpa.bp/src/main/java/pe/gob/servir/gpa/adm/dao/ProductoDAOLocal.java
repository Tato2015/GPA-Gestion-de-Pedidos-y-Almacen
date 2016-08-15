package pe.gob.servir.gpa.adm.dao;
import java.util.List;
import pe.gob.servir.gpa.model.dto.ProductoDTO;

public interface ProductoDAOLocal 
{
		
	public List<ProductoDTO> buscarProducto(Integer idFamilia,Integer idCategoria,Integer idEstado,String descripcionProducto,String codigoProducto) throws Exception;
	
	public Boolean actualizarProducto(ProductoDTO productoDTO) throws Exception;
//
//	public ProductoDTO registraProducto(ProductoDTO productoDTO);
	
}
