package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import pe.gob.servir.gpa.adm.dao.RequerimientoUsuarioDAOLocal;
import pe.gob.servir.gpa.model.dto.RequerimientoUsuarioDTO;
import pe.gob.servir.gpa.service.ejb.RequerimientoUsuarioServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class RequerimientoUsuarioServiceImpl implements RequerimientoUsuarioServiceLocal{

	/** Metodo Constructor 	 */
	public RequerimientoUsuarioServiceImpl() {}
	
@EJB
private RequerimientoUsuarioDAOLocal requerimientoUsuarioDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	final static Logger log = Logger.getLogger(RequerimientoUsuarioServiceImpl.class);

	public List<RequerimientoUsuarioDTO> buscarRequerimientoUsuarioDTO(
			Integer requerimientoUsuarioId,Integer requerimientoId,Integer usuarioId,Integer programacionAreasId,Integer estadoId,Short indicadorEnviadoAlEncargado,Short indicadorRequerimientoRecepcionEncargado) throws Exception 
	{
		List<RequerimientoUsuarioDTO>listaRequerimientoUsuario=null;
		try 
		{
			listaRequerimientoUsuario = requerimientoUsuarioDAOLocal.buscarRequerimientoUsuarioDTO(requerimientoUsuarioId, requerimientoId, usuarioId, programacionAreasId, estadoId,indicadorEnviadoAlEncargado,indicadorRequerimientoRecepcionEncargado);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage());
		}
		return listaRequerimientoUsuario;
	}

	public RequerimientoUsuarioDTO registrarRequerimientoUsuarioDTO(
			RequerimientoUsuarioDTO requerimientoUsuarioDTO) throws Exception 
	{
		try 
		{
		requerimientoUsuarioDTO = requerimientoUsuarioDAOLocal.registrarRequerimientoUsuarioDTO(requerimientoUsuarioDTO);		
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return requerimientoUsuarioDTO;
	}

	public boolean actualizarRequerimientoUsuario(
			RequerimientoUsuarioDTO requerimientoUsuarioDTO) throws Exception 
	{
		try 
		{
		requerimientoUsuarioDAOLocal.actualizarRequerimientoUsuario(requerimientoUsuarioDTO);
		return true;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}

		

}
