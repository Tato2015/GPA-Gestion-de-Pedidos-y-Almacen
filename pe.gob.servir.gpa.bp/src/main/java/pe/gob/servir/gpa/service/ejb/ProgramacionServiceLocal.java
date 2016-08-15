package pe.gob.servir.gpa.service.ejb;
import java.util.List;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
public interface ProgramacionServiceLocal {
	
	public List<ProgramacionDTO> buscarProgramacion(Integer anio,Integer aprobacion, 
			Integer estado,Integer tipoPedido,Integer tipoPeriodo)throws Exception;
	
	public ProgramacionDTO registrarProgramacion(ProgramacionDTO programacionDTO) throws Exception;
	
	public Boolean actualizarProgramacion(ProgramacionDTO programacionDTO) throws Exception;
	
	public List<ProgramacionDTO>buscarProgramacionXMesActual(Integer mesActual);
	
}
