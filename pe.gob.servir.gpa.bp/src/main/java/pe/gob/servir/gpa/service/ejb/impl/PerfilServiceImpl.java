package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.PerfilDAOLocal;
import pe.gob.servir.gpa.model.dto.PerfilDTO;
import pe.gob.servir.gpa.service.ejb.PerfilServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class PerfilServiceImpl implements PerfilServiceLocal 
{

	/** Metodo Constructor */
	public PerfilServiceImpl() 
	{
	}

	@EJB
	private PerfilDAOLocal perfilDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(PerfilServiceImpl.class);

	public List<PerfilDTO> buscarPerfil(Integer perfilId,
			Integer estadoId) throws Exception 
	{
		List<PerfilDTO> listaPerfil= null;		
		try 
		{
			listaPerfil = perfilDAOLocal.buscarPerfil(perfilId, estadoId);
		}
		catch (Exception e) 
		{
		log.error(e.getMessage());
		}
		return listaPerfil;
	}

	

}
