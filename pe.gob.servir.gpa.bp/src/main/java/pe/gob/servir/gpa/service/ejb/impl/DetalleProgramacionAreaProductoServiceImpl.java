package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import pe.gob.servir.gpa.adm.dao.DetalleProgramacionAreaProductoDAOLocal;
import pe.gob.servir.gpa.model.dto.DetalleProgramacionAreaProductoDTO;
import pe.gob.servir.gpa.service.ejb.DetalleProgramacionAreaProductoServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class DetalleProgramacionAreaProductoServiceImpl implements DetalleProgramacionAreaProductoServiceLocal {

	/** Metodo Constructor */
	public DetalleProgramacionAreaProductoServiceImpl() {
	}

	@EJB
	private DetalleProgramacionAreaProductoDAOLocal detalleProgramacionAreaProductoDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(DetalleProgramacionAreaProductoServiceImpl.class);

	public DetalleProgramacionAreaProductoDTO registrarDetalleProgramacionAreaProducto(
			DetalleProgramacionAreaProductoDTO detalleProgramacionAreaProductoDTO)
			throws Exception 
	{
	try 
	{
		detalleProgramacionAreaProductoDTO = detalleProgramacionAreaProductoDAOLocal.registrarDetalleProgramacionAreaProducto(detalleProgramacionAreaProductoDTO);
	} catch (Exception e) 
	{
		log.error(e.getMessage(),e);
	}			
		return detalleProgramacionAreaProductoDTO;
	}

	
	public List<DetalleProgramacionAreaProductoDTO> buscarDetalleProgramacionAreaProducto(
			Integer programacionAreasId, Integer productoDisponibleId)
			throws Exception 
	{
		List<DetalleProgramacionAreaProductoDTO>listaDetalleProgramacionAreaProducto= null;
		try 
		{
			listaDetalleProgramacionAreaProducto = detalleProgramacionAreaProductoDAOLocal.buscarDetalleProgramacionAreaProducto(programacionAreasId, productoDisponibleId);
		} catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}		
		return listaDetalleProgramacionAreaProducto;
	}


	public Boolean actualizarDetalleProgramacionAreaProducto(
			DetalleProgramacionAreaProductoDTO detalleProgramacionAreaProductoDTO)
			throws Exception 
	{

		try {
			detalleProgramacionAreaProductoDAOLocal.actualizarDetalleProgramacionAreaProducto(detalleProgramacionAreaProductoDTO);
			return true;
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}		
		return false;
	}


	public Boolean eliminarDetalleProgramacionAreaProducto(
			Integer detalleProgramacionAreaProductoId) throws Exception 
	{
	
		try 
		{
			detalleProgramacionAreaProductoDAOLocal.eliminarDetalleProgramacionAreaProducto(detalleProgramacionAreaProductoId);
			return true;
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
		return false;
	}

}
