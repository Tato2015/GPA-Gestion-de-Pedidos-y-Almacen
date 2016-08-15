package pe.gob.servir.gpa.adm.dao;

import java.util.List;

import pe.gob.servir.gpa.model.dto.ProgramacionDTO;

public interface ProgramacionDAOLocal 
{
	
	public List<ProgramacionDTO> buscarProgramacion(Integer anio,Integer aprobacion, 
			Integer estado,Integer tipoPedido,Integer tipoPeriodo)throws Exception;
	
	public ProgramacionDTO registrarProgramacion(ProgramacionDTO progrmacionDTO)throws Exception;
	
	public Boolean actualizarProgramacion(ProgramacionDTO programacionDTO) throws Exception;
	
	public List<ProgramacionDTO>buscarProgramacionXMesActual(Integer mesActual);
}
