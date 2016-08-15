package pe.gob.servir.gpa.service.ejb;
import java.util.List;

import pe.gob.servir.gpa.model.dto.DetalleProgramacionAreaProductoDTO;


public interface DetalleProgramacionAreaProductoServiceLocal 
{
	public DetalleProgramacionAreaProductoDTO registrarDetalleProgramacionAreaProducto(DetalleProgramacionAreaProductoDTO detalleProgramacionAreaProductoDTO) throws Exception;
	
	public List<DetalleProgramacionAreaProductoDTO> buscarDetalleProgramacionAreaProducto(Integer programacionAreasId,Integer productoDisponibleId)throws Exception;
	
	public Boolean actualizarDetalleProgramacionAreaProducto(DetalleProgramacionAreaProductoDTO detalleProgramacionAreaProductoDTO) throws Exception;
	
	public Boolean eliminarDetalleProgramacionAreaProducto(Integer detalleProgramacionAreaProductoId)throws Exception;
}
