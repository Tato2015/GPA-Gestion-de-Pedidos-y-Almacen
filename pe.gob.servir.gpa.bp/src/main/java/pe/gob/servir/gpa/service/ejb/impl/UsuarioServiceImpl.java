package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.UsuarioDAOLocal;
import pe.gob.servir.gpa.model.dto.UsuarioDTO;
import pe.gob.servir.gpa.service.ejb.UsuarioServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class UsuarioServiceImpl implements UsuarioServiceLocal {

	/** Metodo Constructor */
	public UsuarioServiceImpl() {
		
	}
	@EJB
	private UsuarioDAOLocal usuarioDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(UsuarioServiceImpl.class);

	public List<UsuarioDTO> buscarUsuario(Integer idUsuario,
			String descripcionUsuario, String claveUsuario) throws Exception 
	{
		List<UsuarioDTO>listaUsuario = null;
		try 
		{
			listaUsuario = usuarioDAOLocal.buscarUsuario(idUsuario, descripcionUsuario, claveUsuario);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage());
		}
		return listaUsuario;
	}

	public UsuarioDTO buscarLoginUsuario(Integer idUsuario,
			String codigoUsuario, String claveUsuario, Short indicadorActivo)
			throws Exception 
	{
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		try 
		{
			usuarioDTO= usuarioDAOLocal.buscarLoginUsuario(idUsuario, codigoUsuario, claveUsuario, indicadorActivo);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage());
		}
		return usuarioDTO;
	}

			

}
