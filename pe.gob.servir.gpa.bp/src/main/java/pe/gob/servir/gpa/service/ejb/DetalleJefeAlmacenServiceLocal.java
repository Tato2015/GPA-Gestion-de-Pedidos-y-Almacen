package pe.gob.servir.gpa.service.ejb;
import java.util.List;

import pe.gob.servir.gpa.model.dto.DetalleJefeAlmacenDTO;

public interface DetalleJefeAlmacenServiceLocal 
{
	public List<DetalleJefeAlmacenDTO> buscarDetalleJefeAlmace(Integer detalleJefeAlmacenId,Integer jefeId, Integer requerimientoJefeAprobacionId,Short indicadorRecepcionJefeArea,Short indicadorEnviadoEncargadoAlmacen,Integer estadoId,Integer requerimientoAreaId) throws Exception;

	public DetalleJefeAlmacenDTO registrarDetalleJefeAlmacen(DetalleJefeAlmacenDTO detalleJefeAlmacenDTO) throws Exception;

	public Boolean actualizarDetalleJefeAlmacen(DetalleJefeAlmacenDTO detalleJefeAlmacenDTO) throws Exception;

}
