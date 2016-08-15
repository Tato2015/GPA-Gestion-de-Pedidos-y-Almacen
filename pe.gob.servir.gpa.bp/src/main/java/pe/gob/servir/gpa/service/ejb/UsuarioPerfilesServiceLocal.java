package pe.gob.servir.gpa.service.ejb;
import java.util.List;
import pe.gob.servir.gpa.model.dto.UsuarioPerfilesDTO;

public interface UsuarioPerfilesServiceLocal 
{
	public List<UsuarioPerfilesDTO> buscarUsuarioPerfiles(Integer usuarioPerfilId,Integer usuarioId,Integer perfilId,Integer estadoId) throws Exception;
	
	public List<UsuarioPerfilesDTO> buscarUsuarioPerfilesXAreaId(Integer usuarioPerfilId,Integer usuarioId,Integer perfilId,Integer estadoId,Integer areaId) throws Exception;

}
