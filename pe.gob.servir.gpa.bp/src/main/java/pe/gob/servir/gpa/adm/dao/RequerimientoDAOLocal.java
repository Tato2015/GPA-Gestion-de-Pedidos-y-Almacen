package pe.gob.servir.gpa.adm.dao;
import java.util.List;

import pe.gob.servir.gpa.model.dto.RequerimientoDTO;

public interface RequerimientoDAOLocal 
{

	public RequerimientoDTO registrarRequerimiento(RequerimientoDTO requerimientoDTO) throws Exception;
	
	public List<RequerimientoDTO> buscarRequerimiento(Integer requerimientoId,Integer estadoId)throws Exception;

}
