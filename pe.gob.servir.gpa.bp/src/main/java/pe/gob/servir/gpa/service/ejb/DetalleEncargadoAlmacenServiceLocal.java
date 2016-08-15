package pe.gob.servir.gpa.service.ejb;
import java.util.List;

import pe.gob.servir.gpa.model.dto.DetalleEncargadoAlmacenDTO;

public interface DetalleEncargadoAlmacenServiceLocal 
{

	public List<DetalleEncargadoAlmacenDTO> buscarDetalleEncargadoAlmacen(Integer detalleEncargadoAlmacenId,Integer encargadoId,Integer detalleJefeAlmacenId,Short indicadorRecepcionJefeAlmacen,Short indicadorEnviadoJefeArea,Short indicadorActivo,Integer estadoId) throws Exception;

	public DetalleEncargadoAlmacenDTO registrarDetalleEncargadoAlmacen(DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO) throws Exception;

	public Boolean actualizarDetalleEncargadoAlmacen(DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO) throws Exception;

	
	
}
