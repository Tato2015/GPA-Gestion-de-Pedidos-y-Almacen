package pe.gob.servir.gpa.adm.dao;

import java.util.List;

import pe.gob.servir.gpa.model.dto.PersonaDTO;

public interface PersonaDAOLocal 
{
	public List<PersonaDTO>buscarPersona(Integer padreId,String apellidoPaterno,String apellidoMaterno,Integer estadoId)throws Exception;
	
	
	
}
