package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.DetalleEncargadoAlmacenDAOLocal;
import pe.gob.servir.gpa.model.dto.DetalleEncargadoAlmacenDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.service.ejb.DetalleEncargadoAlmacenServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class DetalleEncargadoAlmacenServiceImpl implements DetalleEncargadoAlmacenServiceLocal 
{

	/** Metodo Constructor */
	public DetalleEncargadoAlmacenServiceImpl() 
	{
	}

	@EJB
	private DetalleEncargadoAlmacenDAOLocal detalleEncargadoAlmacenDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(DetalleEncargadoAlmacenServiceImpl.class);

	public List<DetalleEncargadoAlmacenDTO> buscarDetalleEncargadoAlmacen(
			Integer detalleEncargadoAlmacenId, Integer encargadoId,
			Integer detalleJefeAlmacenId, Short indicadorRecepcionJefeAlmacen,
			Short indicadorEnviadoJefeArea, Short indicadorActivo,
			Integer estadoId) throws Exception 
	{
		List<DetalleEncargadoAlmacenDTO> listaDetalleEncargadoAlamcen= null;		
		try 
		{
			listaDetalleEncargadoAlamcen= detalleEncargadoAlmacenDAOLocal.buscarDetalleEncargadoAlmacen(detalleEncargadoAlmacenId, encargadoId, detalleJefeAlmacenId, indicadorRecepcionJefeAlmacen, indicadorEnviadoJefeArea, indicadorActivo, estadoId);
		} catch (Exception e) 
		{
		log.error(e.getMessage());
		}
		return listaDetalleEncargadoAlamcen;
		
		
		
	}

	public DetalleEncargadoAlmacenDTO registrarDetalleEncargadoAlmacen(
			DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO)
			throws Exception 
	{
		try 
		{
			detalleEncargadoAlmacenDTO = detalleEncargadoAlmacenDAOLocal.registrarDetalleEncargadoAlmacen(detalleEncargadoAlmacenDTO);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}			
		return detalleEncargadoAlmacenDTO;				
	}

	public Boolean actualizarDetalleEncargadoAlmacen(
			DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO)
			throws Exception 
	{
		try 
		{
			detalleEncargadoAlmacenDAOLocal.actualizarDetalleEncargadoAlmacen(detalleEncargadoAlmacenDTO);
			return true;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}		
		return false;

	}

	
	
	
	

}
