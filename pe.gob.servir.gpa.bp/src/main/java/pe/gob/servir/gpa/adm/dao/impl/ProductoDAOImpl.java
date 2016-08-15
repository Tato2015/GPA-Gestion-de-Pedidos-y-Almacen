
package pe.gob.servir.gpa.adm.dao.impl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import pe.gob.servir.gpa.adm.dao.ProductoDAOLocal;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProductoDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class ProductoDAOImpl implements ProductoDAOLocal{

	/** Metodo Constructor 	 */
	public ProductoDAOImpl() {}
		
	/** Definicion del contexto de persistencia para el acceso al modelo de datos*/
	@PersistenceContext(unitName="pe.gob.servir.gpa.bp")
	private	EntityManager	em;
	
	 /** Atributo del tipo properties, donde se almacenas propiedades de la aplicacion*/
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(ProductoDAOImpl.class);

	public List<ProductoDTO> buscarProducto(Integer idFamilia,Integer idCategoria,Integer idEstado,String descripcionProducto,String codigoProducto) throws Exception 
	{
		List<ProductoDTO> listaProducto=null;
		ProductoDTO producto=null;
		Connection conn = null;
		CallableStatement call = null;
		try 
		{
			InitialContext ic = new InitialContext();			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();

			String sql = "begin PKG_PRODUCTO.SP_BUSCAR_PRODUCTO(?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if (idFamilia != null && idFamilia!=0) 
			{
					call.setInt(1, idFamilia);				
			} else 
			{
				call.setNull(1, OracleTypes.NULL);
			}
			
			if (idCategoria != null && idCategoria!=0) 
			{
				call.setInt(2, idCategoria);				
			} else 
			{
				call.setNull(2, OracleTypes.NULL);
			}
			
			if (idEstado != null && idEstado !=0) 
			{
				call.setInt(3, idEstado);				
			} else 
			{
				call.setNull(3, OracleTypes.NULL);
			}
			
			if (descripcionProducto != null && descripcionProducto.length()>0) 
			{
				call.setString(4, descripcionProducto);		
			} else 
			{
				call.setNull(4, OracleTypes.NULL);
			}
			
			if (codigoProducto != null && codigoProducto.length()>0) 
			{
				call.setString(5, codigoProducto);			
			} else 
			{
				call.setNull(5, OracleTypes.NULL);
			}						
			call.registerOutParameter(6, OracleTypes.CURSOR);
			
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(6);
			listaProducto=new ArrayList<ProductoDTO>();
			while (rs.next()) 
			{
				producto = new ProductoDTO();				
				producto.setId(rs.getInt(1));
				producto.setCodigoProducto(rs.getString(2));
				producto.setDescripcionProducto(rs.getString(3));
				producto.setTipoFamilia(new TipoDTO());
				producto.getTipoFamilia().setId(rs.getInt(4));
				producto.setTipoCategoria(new TipoDTO());				
				producto.getTipoCategoria().setId(rs.getInt(5));
				producto.setTipoUnidadMedida(new TipoDTO());
				producto.getTipoUnidadMedida().setId(rs.getInt(6));				
				producto.setStockMinimo(rs.getDouble(7));
				producto.setStockMaximo(rs.getDouble(8));
				producto.setHabilitado(rs.getShort(9));
				producto.setIndicadorActivo(rs.getShort(10));				
				producto.setCodigoUsuarioRegistro(rs.getString(11));
				producto.setIpRegistro(rs.getString(12));
				producto.setFechaRegistro(rs.getDate(13));
				producto.setCodigoUsuarioModificacion(rs.getString(14));
				producto.setIpModificacion(rs.getString(15));
				producto.setFechaModificacion(rs.getDate(16));
				producto.setEstado(new EstadoDTO());
				producto.getEstado().setId(rs.getInt(17));
				producto.getEstado().setDescripcionEstado(rs.getString(18));
				producto.getTipoFamilia().setDescripcionTipo(rs.getString(19));
				producto.getTipoUnidadMedida().setDescripcionTipo(rs.getString(20));
				producto.getTipoUnidadMedida().setAbreviaturaTipo(rs.getString(21));
				producto.getTipoCategoria().setDescripcionTipo(rs.getString(22));				
				listaProducto.add(producto);			
			}
			rs.close();			
		} 
		catch (NoResultException nre) 
		{
			listaProducto = null;
			log.error(nre.getMessage(), nre);
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		} 
		finally 
		{
			try 
			{
				conn.close();
			} 
			catch (Exception e) 
			{
				log.error(e.getMessage(), e);
			}
		}
		return listaProducto;
	}


	public Boolean actualizarProducto(ProductoDTO productoDTO) throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			String sql = "begin PKG_PRODUCTO.SP_ACTUALIZAR_PRODUCTO(?,?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);			
			call.setInt(1, productoDTO.getId());
			
			if (productoDTO.getCodigoProducto() != null && productoDTO.getCodigoProducto().length()>0) 
			{
				call.setString(2, productoDTO.getCodigoProducto());
			} else 
			{
				call.setNull(2, OracleTypes.NULL);
			}
			
			if (productoDTO.getDescripcionProducto() != null) 
			{
				call.setString(3, productoDTO.getDescripcionProducto());
			} else 
			{
				call.setNull(3, OracleTypes.NULL);
			}
			
			if (productoDTO.getTipoFamilia().getId() != null && productoDTO.getTipoFamilia().getId()!=0) 
			{
				call.setInt(4, productoDTO.getTipoFamilia().getId());
			} else 
			{
				call.setNull(4, OracleTypes.NULL);
			}
			
			if (productoDTO.getTipoCategoria().getId() != null && productoDTO.getTipoCategoria().getId()!=0) 
			{
				call.setInt(5, productoDTO.getTipoCategoria().getId());
			} else 
			{
				call.setNull(5, OracleTypes.NULL);
			}
			
			if (productoDTO.getTipoUnidadMedida().getId() != null && productoDTO.getTipoUnidadMedida().getId()!=0) 
			{
				call.setInt(6, productoDTO.getTipoUnidadMedida().getId());
			} else 
			{
				call.setNull(6, OracleTypes.NULL);
			}
									
			if (productoDTO.getStockMinimo() != null && productoDTO.getStockMinimo()!=0) 
			{
				call.setDouble(7, productoDTO.getStockMinimo());
			} else 
			{
				call.setNull(7, OracleTypes.NULL);
			}
			
			if (productoDTO.getStockMaximo() != null && productoDTO.getStockMaximo()!=0) 
			{
				call.setDouble(8, productoDTO.getStockMaximo());
			} else 
			{
				call.setNull(8, OracleTypes.NULL);
			}
			
			if (productoDTO.getHabilitado() != null && productoDTO.getHabilitado()!=0) 
			{
				call.setInt(9, productoDTO.getHabilitado());
			} else 
			{
				call.setNull(9, OracleTypes.NULL);
			}
			
			if (productoDTO.getIndicadorActivo() != null && productoDTO.getIndicadorActivo()!=0) 
			{
				call.setInt(10, productoDTO.getIndicadorActivo());
			} else 
			{
				call.setNull(10, OracleTypes.NULL);
			}					
			
			if (productoDTO.getCodigoUsuarioModificacion()!= null && productoDTO.getCodigoUsuarioModificacion()!="") 
			{
				call.setString(11, productoDTO.getCodigoUsuarioModificacion());
			} else 
			{
				call.setNull(11, OracleTypes.NULL);
			}
			
			if (productoDTO.getIpModificacion()!= null && productoDTO.getIpModificacion()!="") 
			{
				call.setString(12, productoDTO.getIpModificacion());
			} else 
			{
				call.setNull(12, OracleTypes.NULL);
			}
											
			if (productoDTO.getEstado().getId() != null) 
			{
				call.setInt(13, productoDTO.getEstado().getId());
			} else 
			{
				call.setNull(13, OracleTypes.NULL);
			}
					
			call.execute();
			conn.close();
			return true;
		}  catch (Exception e) {
			log.error(e.getMessage(), e);
		} 
		return false;
	}


	
//	public ProductoDTO registraProducto(ProductoDTO productoDTO) {
//		Connection conn = null;
//		CallableStatement call = null;
//		try {
//			InitialContext ic = new InitialContext();
//			
//			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
//
//			conn = ds.getConnection();
//
//			String sql = "begin PKG_PRODUCTO.SP_REGISTRAR_PRODUCTO(?,?,?,?,?,?,?); end;";
//			call = conn.prepareCall(sql);
//			
//			
//			
//			if (productoDTO.getCodigoProducto() != null && productoDTO.getCodigoProducto().length()>0) {
//				call.setString(1, productoDTO.getCodigoProducto());
//				
//			} else {
//				call.setNull(1, OracleTypes.NULL);
//			}
//			
//			if (productoDTO.getDescripcionProducto() != null && productoDTO.getDescripcionProducto().length()>0) {
//				call.setString(2, productoDTO.getDescripcionProducto());
//				
//			} else {
//				call.setNull(2, OracleTypes.NULL);
//			}
//			
//			if (productoDTO.getStockMinimo() != null) {
//				call.setDouble(3, productoDTO.getStockMinimo());
//				
//			} else {
//				call.setNull(3, OracleTypes.NULL);
//			}
//			
//			if (productoDTO.getAuditoria().getUsuarioRegistro() != null && productoDTO.getAuditoria().getUsuarioRegistro().length()>0) {
//				call.setString(4, productoDTO.getAuditoria().getUsuarioRegistro());
//				
//			} else {
//				call.setNull(4, OracleTypes.NULL);
//			}
//			
//			if (productoDTO.getAuditoria().getDescripcionIpRegistro() != null && productoDTO.getAuditoria().getDescripcionIpRegistro().length()>0) {
//				call.setString(5, productoDTO.getAuditoria().getDescripcionIpRegistro());
//				
//			} else {
//				call.setNull(5, OracleTypes.NULL);
//			}
//			if (productoDTO.getEstado().getIdentificadorEstado() != null && productoDTO.getEstado().getIdentificadorEstado()!=0) {
//				call.setInt(6, productoDTO.getEstado().getIdentificadorEstado());
//				
//			} else {
//				call.setNull(6, OracleTypes.NULL);
//			}
//			
//			
//			call.registerOutParameter(7, OracleTypes.INTEGER);
//			call.execute();
//			Integer identificador=(Integer)call.getObject(7);
//			
//				try {
//					productoDTO.setIdentificadorProducto(identificador);
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//			
//		} catch (NoResultException nre) {
//			productoDTO = null;
//			log.error(nre.getMessage(), nre);
//
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		} finally {
//			try {
//				conn.close();
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//			}
//		}
//		return productoDTO;
//	}
//	
//	

}
