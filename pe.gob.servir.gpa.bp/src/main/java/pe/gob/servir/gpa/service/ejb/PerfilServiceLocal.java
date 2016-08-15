package pe.gob.servir.gpa.service.ejb;
import java.util.List;
import pe.gob.servir.gpa.model.dto.PerfilDTO;

public interface PerfilServiceLocal 
{
	public List<PerfilDTO> buscarPerfil(Integer perfilId,Integer estadoId) throws Exception;	

}
