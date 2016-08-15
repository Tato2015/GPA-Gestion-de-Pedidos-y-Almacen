package pe.gob.servir.gpa.service.ejb;

import java.util.List;

import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;


public interface ProgramacionAreasServiceLocal 
{
	public ProgramacionAreasDTO registrarProgramacionAreas(ProgramacionAreasDTO programacionAreasDTO) throws Exception;
	
	public List<ProgramacionAreasDTO>buscarProgramacionAreas(Integer programacionId, Integer areaId)throws Exception;
	
	public Boolean actualizarProgramacionAreas(ProgramacionAreasDTO programacionAreasDTO) throws Exception;
	
	public Boolean eliminarProgramacionAreas(Integer programacionAreaId) throws Exception;
}
