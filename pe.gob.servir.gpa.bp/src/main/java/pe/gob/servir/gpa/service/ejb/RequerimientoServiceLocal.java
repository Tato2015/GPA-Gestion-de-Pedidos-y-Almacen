package pe.gob.servir.gpa.service.ejb;
import java.util.List;

import pe.gob.servir.gpa.model.dto.RequerimientoDTO;

public interface RequerimientoServiceLocal 
{

	public RequerimientoDTO registrarRequerimiento(RequerimientoDTO requerimientoDTO) throws Exception;

	public List<RequerimientoDTO> buscarRequerimiento(Integer requerimientoId,Integer estadoId)throws Exception;
	
}
