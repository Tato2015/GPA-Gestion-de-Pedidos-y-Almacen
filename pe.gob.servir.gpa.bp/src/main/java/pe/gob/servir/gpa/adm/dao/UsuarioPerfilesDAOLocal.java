package pe.gob.servir.gpa.adm.dao;
import java.util.List;

import pe.gob.servir.gpa.model.dto.UsuarioPerfilesDTO;

public interface UsuarioPerfilesDAOLocal 
{

	public List<UsuarioPerfilesDTO> buscarUsuarioPerfiles(Integer usuarioPerfilId,Integer usuarioId,Integer perfilId,Integer estadoId) throws Exception;
	
	public List<UsuarioPerfilesDTO> buscarUsuarioPerfilesXAreaId(Integer usuarioPerfilId,Integer usuarioId,Integer perfilId,Integer estadoId,Integer areaId) throws Exception;

}
