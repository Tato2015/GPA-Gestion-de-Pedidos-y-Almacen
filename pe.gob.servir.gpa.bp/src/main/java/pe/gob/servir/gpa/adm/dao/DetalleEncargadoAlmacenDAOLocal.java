package pe.gob.servir.gpa.adm.dao;
import java.util.List;

import pe.gob.servir.gpa.model.dto.DetalleEncargadoAlmacenDTO;

public interface DetalleEncargadoAlmacenDAOLocal 
{

	public List<DetalleEncargadoAlmacenDTO> buscarDetalleEncargadoAlmacen(Integer detalleEncargadoAlmacenId,Integer encargadoId,Integer detalleJefeAlmacenId,Short indicadorRecepcionJefeAlmacen,Short indicadorEnviadoJefeArea,Short indicadorActivo,Integer estadoId) throws Exception;

	public DetalleEncargadoAlmacenDTO registrarDetalleEncargadoAlmacen(DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO) throws Exception;

	public Boolean actualizarDetalleEncargadoAlmacen(DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO) throws Exception;

}
