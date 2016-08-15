package pe.gob.servir.gpa.adm.dao;
import java.util.List;

import pe.gob.servir.gpa.model.dto.UsuarioDTO;

public interface UsuarioDAOLocal 
{

	public List<UsuarioDTO> buscarUsuario(Integer idUsuario,String descripcionUsuario,String claveUsuario) throws Exception;
	
	public UsuarioDTO buscarLoginUsuario(Integer idUsuario,String codigoUsuario,String claveUsuario,Short indicadorActivo)throws Exception;


}
