package pe.gob.servir.gpa.service.ejb;

import java.util.List;

import pe.gob.servir.gpa.model.dto.PersonaDTO;

public interface PersonaServiceLocal 
{

	public List<PersonaDTO>buscarPersona(Integer padreId,String apellidoPaterno,String apellidoMaterno,Integer estadoId)throws Exception;	
	
}
