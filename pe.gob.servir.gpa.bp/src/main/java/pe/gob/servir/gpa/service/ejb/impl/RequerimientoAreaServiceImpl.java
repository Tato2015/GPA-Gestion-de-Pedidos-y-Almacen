package pe.gob.servir.gpa.service.ejb.impl;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import pe.gob.servir.gpa.adm.dao.RequerimientoAreaDAOLocal;
import pe.gob.servir.gpa.model.dto.RequerimientoAreaDTO;
import pe.gob.servir.gpa.service.ejb.RequerimientoAreaServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class RequerimientoAreaServiceImpl implements RequerimientoAreaServiceLocal
{

	/** Metodo Constructor 	 */
	public RequerimientoAreaServiceImpl() {}
	
@EJB
private RequerimientoAreaDAOLocal requerimientoAreaDAOLocal;

	 /** Atributo del tipo properties, donde se almacenas propiedades de la aplicacion*/
		ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(RequerimientoAreaServiceImpl.class);

	public List<RequerimientoAreaDTO> buscarRequerimientoArea(
			Integer requerimientoAreaId, Integer areaId,
			Short indicadorEnviadoAlJefe, Short indicadorRequeRecibidoDelJefe,Integer programacionId)
			throws Exception 
	{
	List<RequerimientoAreaDTO>listaRequerimientArea = null;
	try 
	{
		listaRequerimientArea = requerimientoAreaDAOLocal.buscarRequerimientoArea(requerimientoAreaId, areaId, indicadorEnviadoAlJefe, indicadorRequeRecibidoDelJefe,programacionId);
	} 
	catch (Exception e) 
	{
	log.error(e.getMessage(),e);
	}

	return listaRequerimientArea;
	}

	
	
	
	public RequerimientoAreaDTO registrarRequerimientoArea(
			RequerimientoAreaDTO requerimientoAreaDTO) throws Exception 
	{
		try 
		{
			requerimientoAreaDTO = requerimientoAreaDAOLocal.registrarRequerimientoArea(requerimientoAreaDTO);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
				
		return requerimientoAreaDTO;
	}

	
	
	
	public boolean actualizarRequerimientoAreaDTO(
			RequerimientoAreaDTO requerimientoAreaDTO) throws Exception 
	{
		try 
		{
			requerimientoAreaDAOLocal.actualizarRequerimientoAreaDTO(requerimientoAreaDTO);
			return true;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}




	public Connection conexionDataSource() throws Exception 
	{
		try 
		{
			requerimientoAreaDAOLocal.conexionDataSource();
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		
		return requerimientoAreaDAOLocal.conexionDataSource();
	}




	public void cerrarConexionDataSource() throws Exception 
	{
	try 
	{
		requerimientoAreaDAOLocal.cerrarConexionDataSource();
	} 
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
		
	}

	
	
	
	

	
	
}
