package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import pe.gob.servir.gpa.adm.dao.ProductoDAOLocal;
import pe.gob.servir.gpa.model.dto.ProductoDTO;
import pe.gob.servir.gpa.service.ejb.ProductoServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class ProductoServiceImpl implements ProductoServiceLocal{

	/** Metodo Constructor 	 */
	public ProductoServiceImpl() {}
	
	@EJB
	private ProductoDAOLocal productoDAOLocal;
	

	
	 /** Atributo del tipo properties, donde se almacenas propiedades de la aplicacion*/
		ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(ProductoServiceImpl.class);

	public List<ProductoDTO> buscarProducto(Integer idFamilia,Integer idCategoria,Integer idEstado,String descripcionProducto,String codigoProducto) throws Exception 
	{
		List<ProductoDTO> listaProducto=null;		
		try 
		{			
			listaProducto=productoDAOLocal.buscarProducto(idFamilia,idCategoria,idEstado,descripcionProducto,codigoProducto);	
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		} 
		return listaProducto;
	}

	
	
	public Boolean actualizarProducto(ProductoDTO productoDTO) throws Exception 
	{	
		try 
		{
			productoDAOLocal.actualizarProducto(productoDTO);
			return true;
		}  catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		} 
		return false;
	}
//
//
//
//	public List<ProductoDTO> buscarProductoIntegrix() {
//			List<ProductoDTO> listaProducto=null;
//		
//		try {
//			
//			listaProducto=productoDAOLocal.buscarProductoIntegrix();
//		
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		} 
//		return listaProducto;
//	}
//
//
//
//	public ProductoDTO registrarProducto(ProductoDTO productoDTO) {
//		try {
//			
//			productoDTO=productoDAOLocal.registraProducto(productoDTO);
//		
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		} 
//		return productoDTO;
//	}

	

}
