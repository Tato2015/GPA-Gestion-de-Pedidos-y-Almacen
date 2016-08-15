package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import pe.gob.servir.gpa.adm.dao.UsuarioPerfilesDAOLocal;
import pe.gob.servir.gpa.model.dto.UsuarioPerfilesDTO;
import pe.gob.servir.gpa.service.ejb.UsuarioPerfilesServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class UsuarioPerfilesServiceImpl implements UsuarioPerfilesServiceLocal 
{

	/** Metodo Constructor */
	public UsuarioPerfilesServiceImpl() 
	{
	}

	@EJB
	private UsuarioPerfilesDAOLocal usuarioPerfilesDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(UsuarioPerfilesServiceImpl.class);

	public List<UsuarioPerfilesDTO> buscarUsuarioPerfiles(Integer usuarioPerfilId,Integer usuarioId,Integer perfilId,Integer estadoId) throws Exception 
	{
		List<UsuarioPerfilesDTO> listaUsuarioPerfiles= null;		
		try 
		{
			listaUsuarioPerfiles= usuarioPerfilesDAOLocal.buscarUsuarioPerfiles(usuarioPerfilId, usuarioId, perfilId, estadoId);
		} catch (Exception e) 
		{
		log.error(e.getMessage());
		}
		return listaUsuarioPerfiles;
	}

	public List<UsuarioPerfilesDTO> buscarUsuarioPerfilesXAreaId(
			Integer usuarioPerfilId, Integer usuarioId, Integer perfilId,
			Integer estadoId, Integer areaId) throws Exception 
	{
		List<UsuarioPerfilesDTO> listaUsuarioPerfiles= null;		
		try 
		{
			listaUsuarioPerfiles= usuarioPerfilesDAOLocal.buscarUsuarioPerfilesXAreaId(usuarioPerfilId, usuarioId, perfilId, estadoId, areaId);
		} catch (Exception e) 
		{
		log.error(e.getMessage());
		}
		return listaUsuarioPerfiles;
	}

	

}
