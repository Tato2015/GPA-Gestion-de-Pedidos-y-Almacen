package pe.gob.servir.gpa.adm.dao;
import java.util.List;

import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;

public interface ProgramacionAreasDAOLocal 
{
	public ProgramacionAreasDTO registrarProgramacionAreas(ProgramacionAreasDTO programacionAreasDTO) throws Exception;
	
	public List<ProgramacionAreasDTO> buscarProgramacionAreas(Integer programacionId, Integer areaId)throws Exception;
	
	public Boolean actualizarProgramacionAreas(ProgramacionAreasDTO programacionAreasDTO) throws Exception;
	
	public Boolean eliminarProgramacionAreas(Integer programacionAreaId) throws Exception;
}
