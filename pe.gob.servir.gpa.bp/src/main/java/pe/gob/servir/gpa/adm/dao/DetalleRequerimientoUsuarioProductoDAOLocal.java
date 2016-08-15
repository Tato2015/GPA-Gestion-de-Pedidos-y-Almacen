package pe.gob.servir.gpa.adm.dao;
import java.util.List;

import pe.gob.servir.gpa.model.dto.DetalleRequerimientoUsuarioProductoDTO;

public interface DetalleRequerimientoUsuarioProductoDAOLocal 
{

	public List<DetalleRequerimientoUsuarioProductoDTO> buscarDetalleRequerimientoUsuarioProducto(Integer detalleRequerimientoUsuarioProductoId , Integer requerimientoUsuarioId,Integer productoId)throws Exception;
	
	public DetalleRequerimientoUsuarioProductoDTO registrarDetalleRequerimientoUsuarioProducto(DetalleRequerimientoUsuarioProductoDTO detalleRequerimientoUsuarioProductoDTO)throws Exception;
	
	public Boolean actualizarDetalleRequerimientoUsuarioProducto(DetalleRequerimientoUsuarioProductoDTO detalleRequerimientoUsuarioProductoDTO)throws Exception;

	

}
