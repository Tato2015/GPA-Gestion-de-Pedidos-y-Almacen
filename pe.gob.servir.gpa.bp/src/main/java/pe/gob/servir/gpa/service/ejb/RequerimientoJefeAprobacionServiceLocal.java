package pe.gob.servir.gpa.service.ejb;
import java.util.List;
import pe.gob.servir.gpa.model.dto.RequerimientoJefeAprobacionDTO;

public interface RequerimientoJefeAprobacionServiceLocal 
{

	public List<RequerimientoJefeAprobacionDTO> buscarRequerimientoJefeAprobacion(Integer requerimientoJefeAprobacionId,Integer jefeId,Integer requerimientoAreaId,Short indicadorRecibioEncargadoArea,Short indicadorEnviadoJefeAlmacen) throws Exception;

	public RequerimientoJefeAprobacionDTO registrarRequerimientoJefeAprobacion(RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO) throws Exception;

	public Boolean actualizarTipo(RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO) throws Exception;


}
