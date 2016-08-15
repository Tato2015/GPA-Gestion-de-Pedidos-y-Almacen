package pe.gob.servir.gpa.adm.dao;
import java.util.List;

import pe.gob.servir.gpa.model.dto.PerfilDTO;

public interface PerfilDAOLocal 
{

	public List<PerfilDTO> buscarPerfil(Integer perfilId,Integer estadoId) throws Exception;




}
