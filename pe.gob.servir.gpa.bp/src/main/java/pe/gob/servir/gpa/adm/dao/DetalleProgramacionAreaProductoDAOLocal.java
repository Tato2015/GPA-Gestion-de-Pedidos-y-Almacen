package pe.gob.servir.gpa.adm.dao;

import java.util.List;

import pe.gob.servir.gpa.model.dto.DetalleProgramacionAreaProductoDTO;


public interface DetalleProgramacionAreaProductoDAOLocal 
{
	public DetalleProgramacionAreaProductoDTO registrarDetalleProgramacionAreaProducto(DetalleProgramacionAreaProductoDTO detalleProgramacionAreaProductoDTO) throws Exception;

	public List<DetalleProgramacionAreaProductoDTO> buscarDetalleProgramacionAreaProducto(Integer programacionAreasId,Integer productoDisponibleId)throws Exception;
	
	public Boolean actualizarDetalleProgramacionAreaProducto(DetalleProgramacionAreaProductoDTO detalleProgramacionAreaProductoDTO) throws Exception;
	
	public Boolean eliminarDetalleProgramacionAreaProducto(Integer detalleProgramacionAreaProductoId)throws Exception;
}
