package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import pe.gob.servir.gpa.adm.dao.DetalleRequerimientoUsuarioProductoDAOLocal;
import pe.gob.servir.gpa.model.dto.DetalleRequerimientoUsuarioProductoDTO;
import pe.gob.servir.gpa.service.ejb.DetalleRequerimientoUsuarioProductoServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class DetalleRequerimientoUsuarioProductoServiceImpl implements DetalleRequerimientoUsuarioProductoServiceLocal 
{

	/** Metodo Constructor */
	public DetalleRequerimientoUsuarioProductoServiceImpl() {
	}

	@EJB
	private DetalleRequerimientoUsuarioProductoDAOLocal detalleRequerimientoUsuarioProductoDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(DetalleRequerimientoUsuarioProductoServiceImpl.class);

	public List<DetalleRequerimientoUsuarioProductoDTO> buscarDetalleRequerimientoUsuarioProducto(
			Integer detalleRequerimientoUsuarioProductoId,
			Integer requerimientoUsuarioId, Integer productoId)
			throws Exception 
	{
		List<DetalleRequerimientoUsuarioProductoDTO> listaDetalleRequerimientoUsuarioProducto=null;
		try 
		{
			listaDetalleRequerimientoUsuarioProducto = detalleRequerimientoUsuarioProductoDAOLocal.buscarDetalleRequerimientoUsuarioProducto(detalleRequerimientoUsuarioProductoId, requerimientoUsuarioId, productoId);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage());
		}
		return listaDetalleRequerimientoUsuarioProducto;
	}

	public DetalleRequerimientoUsuarioProductoDTO registrarDetalleRequerimientoUsuarioProducto(
			DetalleRequerimientoUsuarioProductoDTO detalleRequerimientoUsuarioProductoDTO)
			throws Exception 
	{
		try 
		{
			detalleRequerimientoUsuarioProductoDTO = detalleRequerimientoUsuarioProductoDAOLocal.registrarDetalleRequerimientoUsuarioProducto(detalleRequerimientoUsuarioProductoDTO);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return detalleRequerimientoUsuarioProductoDTO;
	}

	public Boolean actualizarDetalleRequerimientoUsuarioProducto(
			DetalleRequerimientoUsuarioProductoDTO detalleRequerimientoUsuarioProductoDTO)
			throws Exception 
	{
		try 
		{
			detalleRequerimientoUsuarioProductoDAOLocal.actualizarDetalleRequerimientoUsuarioProducto(detalleRequerimientoUsuarioProductoDTO);
			return true;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}

	
}
