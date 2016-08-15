package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.DetalleJefeAlmacenDAOLocal;
import pe.gob.servir.gpa.model.dto.DetalleJefeAlmacenDTO;
import pe.gob.servir.gpa.service.ejb.DetalleJefeAlmacenServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class DetalleJefeAlmacenServiceImpl implements DetalleJefeAlmacenServiceLocal 
{

	/** Metodo Constructor */
	public DetalleJefeAlmacenServiceImpl() 
	{
	}

	@EJB
	private DetalleJefeAlmacenDAOLocal detalleJefeAlmacenDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(DetalleJefeAlmacenServiceImpl.class);

	public List<DetalleJefeAlmacenDTO> buscarDetalleJefeAlmace(
			Integer detalleJefeAlmacenId, Integer jefeId,
			Integer requerimientoJefeAprobacionId,
			Short indicadorRecepcionJefeArea,
			Short indicadorEnviadoEncargadoAlmacen, Integer estadoId,Integer requerimientoAreaId)
			throws Exception 
	{
		List<DetalleJefeAlmacenDTO> listaDetalleJefeAlmacen= null;		
		try 
		{
			listaDetalleJefeAlmacen= detalleJefeAlmacenDAOLocal.buscarDetalleJefeAlmace(detalleJefeAlmacenId, jefeId, requerimientoJefeAprobacionId, indicadorRecepcionJefeArea, indicadorEnviadoEncargadoAlmacen, estadoId,requerimientoAreaId);
		} 
		catch (Exception e) 
		{
		log.error(e.getMessage());
		}
		return listaDetalleJefeAlmacen;

	}

	public DetalleJefeAlmacenDTO registrarDetalleJefeAlmacen(
			DetalleJefeAlmacenDTO detalleJefeAlmacenDTO) throws Exception 
	{
	try 
	{
		detalleJefeAlmacenDTO= detalleJefeAlmacenDAOLocal.registrarDetalleJefeAlmacen(detalleJefeAlmacenDTO);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}			
	return detalleJefeAlmacenDTO;
	
	}

	public Boolean actualizarDetalleJefeAlmacen(
			DetalleJefeAlmacenDTO detalleJefeAlmacenDTO) throws Exception 
	{
		try 
		{
			detalleJefeAlmacenDAOLocal.actualizarDetalleJefeAlmacen(detalleJefeAlmacenDTO);
			return true;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}		
		return false;
		
		
	}

	
	

}
