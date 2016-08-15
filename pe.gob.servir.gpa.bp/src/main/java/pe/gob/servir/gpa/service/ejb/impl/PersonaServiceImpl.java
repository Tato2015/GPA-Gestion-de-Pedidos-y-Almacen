package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import pe.gob.servir.gpa.adm.dao.PersonaDAOLocal;
import pe.gob.servir.gpa.model.dto.PersonaDTO;
import pe.gob.servir.gpa.service.ejb.PersonaServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class PersonaServiceImpl implements PersonaServiceLocal 
{


	public PersonaServiceImpl() {
	}

	@EJB
	private PersonaDAOLocal personaDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(PersonaServiceImpl.class);

	public List<PersonaDTO> buscarPersona(Integer padreId,
			String apellidoPaterno, String apellidoMaterno, Integer estadoId)
			throws Exception 
	{
		List<PersonaDTO>listaPersona = null;
		
		try 
		{
			listaPersona = personaDAOLocal.buscarPersona(padreId, apellidoPaterno, apellidoMaterno, estadoId);
		} 
		catch (Exception e)		
		{
		log.error(e.getMessage());
		}

		return listaPersona;
	}

		
}
