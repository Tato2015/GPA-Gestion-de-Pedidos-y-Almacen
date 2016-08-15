package pe.gob.servir.gpa.adm.dao.impl;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.NoResultException;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.DetalleRequerimientoUsuarioProductoDAOLocal;
import pe.gob.servir.gpa.model.dto.DetalleRequerimientoUsuarioProductoDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProductoDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoUsuarioDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class DetalleRequerimientoUsuarioProductoDAOImpl implements DetalleRequerimientoUsuarioProductoDAOLocal 
{

	/** Metodo Constructor */
	public DetalleRequerimientoUsuarioProductoDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(DetalleRequerimientoUsuarioProductoDAOImpl.class);

	public List<DetalleRequerimientoUsuarioProductoDTO> buscarDetalleRequerimientoUsuarioProducto(
			Integer detalleRequerimientoUsuarioProductoId,
			Integer requerimientoUsuarioId, Integer productoId)
			throws Exception 
	{
		List<DetalleRequerimientoUsuarioProductoDTO> listaDetalleRequerimientoUsuarioProducto= null;
		DetalleRequerimientoUsuarioProductoDTO detalleRequerimientoUsuarioProductoDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_DETA_REQ_USU_PRO.SP_BUSCAR_DET_REQ_USU_PRO(?,?,?,?);end;";
			call = conn.prepareCall(sql);
			
			if(detalleRequerimientoUsuarioProductoId!=null && detalleRequerimientoUsuarioProductoId>=0)
			{
				call.setInt(1,detalleRequerimientoUsuarioProductoId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(requerimientoUsuarioId!=null && requerimientoUsuarioId>=0)
			{
				call.setInt(2,requerimientoUsuarioId);
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			
			if(productoId!=null && productoId>=0)
			{
				call.setInt(3,productoId);
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			call.registerOutParameter(4, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(4);
			listaDetalleRequerimientoUsuarioProducto= new ArrayList<DetalleRequerimientoUsuarioProductoDTO>();

			while(rs.next())
			{
				detalleRequerimientoUsuarioProductoDTO = new DetalleRequerimientoUsuarioProductoDTO();
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setId(rs.getInt(1));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				detalleRequerimientoUsuarioProductoDTO.setRequerimientoUsuario(new RequerimientoUsuarioDTO());
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.getRequerimientoUsuario().setId(rs.getInt(2));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				detalleRequerimientoUsuarioProductoDTO.setProducto(new ProductoDTO());
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.getProducto().setId(rs.getInt(3));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setCantidad(rs.getInt(4));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setIndicadorActivo(rs.getShort(5));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setCodigoUsuarioRegistro(rs.getString(6));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setIpRegistro(rs.getString(7));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setFechaRegistro(rs.getDate(8));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setCodigoUsuarioModificacion(rs.getString(9));					
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setIpModificacion(rs.getString(10));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setFechaModificacion(rs.getDate(11));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				detalleRequerimientoUsuarioProductoDTO.setEstado(new EstadoDTO());
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.getEstado().setId(rs.getInt(12));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
								
				listaDetalleRequerimientoUsuarioProducto.add(detalleRequerimientoUsuarioProductoDTO);				
			}
			rs.close();
		} 
		catch (NoResultException nre) 
		{
			listaDetalleRequerimientoUsuarioProducto= null;
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
	
		return listaDetalleRequerimientoUsuarioProducto;
	}

	public DetalleRequerimientoUsuarioProductoDTO registrarDetalleRequerimientoUsuarioProducto(
			DetalleRequerimientoUsuarioProductoDTO detalleRequerimientoUsuarioProductoDTO)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();

			
			String sql = "begin PKG_DETA_REQ_USU_PRO.SP_REGISTRAR_DET_REQ_USU_PRO(?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
		
			if(detalleRequerimientoUsuarioProductoDTO.getRequerimientoUsuario().getId()!=null && detalleRequerimientoUsuarioProductoDTO.getRequerimientoUsuario().getId()>=0)
			{
				call.setInt(1,detalleRequerimientoUsuarioProductoDTO.getRequerimientoUsuario().getId());
			}
			else
			{						
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(detalleRequerimientoUsuarioProductoDTO.getProducto().getId()!=null && detalleRequerimientoUsuarioProductoDTO.getProducto().getId()>=0)
			{
				call.setInt(2,detalleRequerimientoUsuarioProductoDTO.getProducto().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(detalleRequerimientoUsuarioProductoDTO.getCantidad()!=null &&  detalleRequerimientoUsuarioProductoDTO.getCantidad()>=0)
			{
				call.setInt(3, detalleRequerimientoUsuarioProductoDTO.getCantidad());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(detalleRequerimientoUsuarioProductoDTO.getIndicadorActivo()!=null && detalleRequerimientoUsuarioProductoDTO.getIndicadorActivo()>=0)
			{
				call.setShort(4,detalleRequerimientoUsuarioProductoDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}

			if(detalleRequerimientoUsuarioProductoDTO.getCodigoUsuarioRegistro()!=null && !detalleRequerimientoUsuarioProductoDTO.getCodigoUsuarioRegistro().equals(""))
			{
				call.setString(5,detalleRequerimientoUsuarioProductoDTO.getCodigoUsuarioRegistro());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}

			if(detalleRequerimientoUsuarioProductoDTO.getIpRegistro()!=null && !detalleRequerimientoUsuarioProductoDTO.getIpRegistro().equals(""))
			{
				call.setString(6,detalleRequerimientoUsuarioProductoDTO.getIpRegistro());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}

			if(detalleRequerimientoUsuarioProductoDTO.getEstado().getId()!= null && detalleRequerimientoUsuarioProductoDTO.getEstado().getId()>=0)
			{
				call.setInt(7,detalleRequerimientoUsuarioProductoDTO.getEstado().getId());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			call.registerOutParameter(8, OracleTypes.INTEGER);
			call.execute();
			Integer identificador=(Integer)call.getObject(8);
				try 
				{
					detalleRequerimientoUsuarioProductoDTO.setId(identificador.intValue());
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
			
		}
		catch (NoResultException nre) 
		{
			detalleRequerimientoUsuarioProductoDTO= null;
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
			
		return detalleRequerimientoUsuarioProductoDTO;
	}

	public Boolean actualizarDetalleRequerimientoUsuarioProducto(
			DetalleRequerimientoUsuarioProductoDTO detalleRequerimientoUsuarioProductoDTO)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try 
		{
			InitialContext ic = new InitialContext();
			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_DETA_REQ_USU_PRO.SP_ACTUALIZAR_DET_REQ_USU_PRO(?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if (detalleRequerimientoUsuarioProductoDTO.getId() !=null &&  detalleRequerimientoUsuarioProductoDTO.getId()>=0) 
			{
				call.setInt(1,detalleRequerimientoUsuarioProductoDTO.getId());
			}else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(detalleRequerimientoUsuarioProductoDTO.getRequerimientoUsuario().getId()!=null && detalleRequerimientoUsuarioProductoDTO.getRequerimientoUsuario().getId()>=0)
			{
				call.setInt(2,detalleRequerimientoUsuarioProductoDTO.getRequerimientoUsuario().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}

			if(detalleRequerimientoUsuarioProductoDTO.getProducto().getId() != null && detalleRequerimientoUsuarioProductoDTO.getProducto().getId()>=0)
			{
				call.setInt(3,detalleRequerimientoUsuarioProductoDTO.getProducto().getId());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(detalleRequerimientoUsuarioProductoDTO.getCantidad() !=null && detalleRequerimientoUsuarioProductoDTO.getCantidad()>=0)
			{
				call.setInt(4,detalleRequerimientoUsuarioProductoDTO.getCantidad());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(detalleRequerimientoUsuarioProductoDTO.getIndicadorActivo()!=null && detalleRequerimientoUsuarioProductoDTO.getIndicadorActivo()>=0)
			{
				call.setShort(5,detalleRequerimientoUsuarioProductoDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(detalleRequerimientoUsuarioProductoDTO.getCodigoUsuarioModificacion()!=null && !detalleRequerimientoUsuarioProductoDTO.getCodigoUsuarioModificacion().equals(""))
			{
				call.setString(6, detalleRequerimientoUsuarioProductoDTO.getCodigoUsuarioModificacion());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(detalleRequerimientoUsuarioProductoDTO.getIpModificacion()!=null && !detalleRequerimientoUsuarioProductoDTO.getIpModificacion().equals(""))
			{
				call.setString(7, detalleRequerimientoUsuarioProductoDTO.getIpModificacion());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);				
			}
			
			if(detalleRequerimientoUsuarioProductoDTO.getEstado().getId()!=null && detalleRequerimientoUsuarioProductoDTO.getEstado().getId()>=0)
			{
				call.setInt(8,detalleRequerimientoUsuarioProductoDTO.getEstado().getId());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);	
			}			
			
			call.registerOutParameter(9, OracleTypes.INTEGER);
			call.execute();			
			
		} 
		catch (NoResultException nre) 
		{
			detalleRequerimientoUsuarioProductoDTO = null;
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
		return true;
	}

	

}
