package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.RequerimientoJefeAprobacionDAOLocal;
import pe.gob.servir.gpa.model.dto.RequerimientoJefeAprobacionDTO;
import pe.gob.servir.gpa.service.ejb.RequerimientoJefeAprobacionServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class RequerimientoJefeAprobacionServiceImpl implements RequerimientoJefeAprobacionServiceLocal 
{

	/** Metodo Constructor */
	public RequerimientoJefeAprobacionServiceImpl() 
	{
	}

	@EJB
	private RequerimientoJefeAprobacionDAOLocal requerimientoJefeAprobacionDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(RequerimientoJefeAprobacionServiceImpl.class);

	public List<RequerimientoJefeAprobacionDTO> buscarRequerimientoJefeAprobacion(
			Integer requerimientoJefeAprobacionId, Integer jefeId,
			Integer requerimientoAreaId, Short indicadorRecibioEncargadoArea,
			Short indicadorEnviadoJefeAlmacen) throws Exception 
	{
		List<RequerimientoJefeAprobacionDTO> listaRequerimientoJefeAprobacion= null;		
		try 
		{
			listaRequerimientoJefeAprobacion = requerimientoJefeAprobacionDAOLocal.buscarRequerimientoJefeAprobacion(requerimientoJefeAprobacionId, jefeId, requerimientoAreaId, indicadorRecibioEncargadoArea, indicadorEnviadoJefeAlmacen);
		}
		catch (Exception e) 
		{
		log.error(e.getMessage());
		}
		return listaRequerimientoJefeAprobacion;
	}

	public RequerimientoJefeAprobacionDTO registrarRequerimientoJefeAprobacion(
			RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO)
			throws Exception 
	{
		try 
		{
			requerimientoJefeAprobacionDTO = requerimientoJefeAprobacionDAOLocal.registrarRequerimientoJefeAprobacion(requerimientoJefeAprobacionDTO);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}			
		return requerimientoJefeAprobacionDTO;
	}

	public Boolean actualizarTipo(
			RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO)
			throws Exception 
	{	
		try 
		{
			requerimientoJefeAprobacionDAOLocal.actualizarTipo(requerimientoJefeAprobacionDTO);
			return true;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}		
		return false;
	}

		

}
