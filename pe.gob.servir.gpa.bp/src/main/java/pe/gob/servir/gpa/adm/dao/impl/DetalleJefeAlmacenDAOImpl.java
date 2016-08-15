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

import pe.gob.servir.gpa.adm.dao.DetalleJefeAlmacenDAOLocal;
import pe.gob.servir.gpa.model.dto.DetalleJefeAlmacenDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoAreaDTO;
import pe.gob.servir.gpa.model.dto.UsuarioDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class DetalleJefeAlmacenDAOImpl implements DetalleJefeAlmacenDAOLocal 
{

	/** Metodo Constructor */
	public DetalleJefeAlmacenDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(DetalleJefeAlmacenDAOImpl.class);

	public List<DetalleJefeAlmacenDTO> buscarDetalleJefeAlmace(
			Integer detalleJefeAlmacenId, Integer jefeId,
			Integer requerimientoJefeAprobacionId,
			Short indicadorRecepcionJefeArea,
			Short indicadorEnviadoEncargadoAlmacen, Integer estadoId,
			Integer requerimientoAreaId)
			throws Exception 
	{
	
		List<DetalleJefeAlmacenDTO> listaDetalleJefeAlmacen= null;
		DetalleJefeAlmacenDTO detalleJefeAlmacenDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_DETA_JEFE_ALMACEN.SP_BUSCAR_DETA_JEFE_ALMACEN(?,?,?,?,?,?,?,?);end;";
			call = conn.prepareCall(sql);
			
			if(detalleJefeAlmacenId!=null && detalleJefeAlmacenId>=0)
			{
				call.setInt(1,detalleJefeAlmacenId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(jefeId != null && jefeId>=0)
			{
				call.setInt(2,jefeId);
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(requerimientoJefeAprobacionId!=null && requerimientoJefeAprobacionId>=0)
			{
				call.setInt(3,requerimientoJefeAprobacionId);
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(indicadorRecepcionJefeArea!=null && indicadorRecepcionJefeArea>=0)
			{
				call.setShort(4,indicadorRecepcionJefeArea);
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(indicadorEnviadoEncargadoAlmacen!=null && indicadorEnviadoEncargadoAlmacen>=0)
			{
				call.setShort(5,indicadorEnviadoEncargadoAlmacen);
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(estadoId!=null && estadoId>=0)
			{
				call.setInt(6,estadoId);
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(requerimientoAreaId!=null && requerimientoAreaId>=0)
			{
				call.setInt(7,requerimientoAreaId);
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			
			call.registerOutParameter(8, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(8);
			listaDetalleJefeAlmacen= new ArrayList<DetalleJefeAlmacenDTO>();

			while (rs.next()) 
			{
				detalleJefeAlmacenDTO = new DetalleJefeAlmacenDTO();				

				try 
				{
					detalleJefeAlmacenDTO.setId(rs.getInt(1));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(1));
				}
				
				
				detalleJefeAlmacenDTO.setJefeAlmacen(new UsuarioDTO());
				try 
				{
					detalleJefeAlmacenDTO.getJefeAlmacen().setId(rs.getInt(2));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(2));
				}
				
				detalleJefeAlmacenDTO.setRequerimientoAprobacionJefe(new RequerimientoAreaDTO());
				try 
				{
					detalleJefeAlmacenDTO.getRequerimientoAprobacionJefe().setId(rs.getInt(3));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(3));
				}
				
				try 
				{
					detalleJefeAlmacenDTO.setIndicadorRecepcionJefeArea(rs.getShort(4));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(4));
				}
				
				try 
				{
					detalleJefeAlmacenDTO.setFechaRecepcionJefeArea(rs.getDate(5));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(5));
				}
				
				
				try 
				{
					detalleJefeAlmacenDTO.setIndicadorEnviadoEncargadoAlmacen(rs.getShort(6));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(6));
				}
				
				try 
				{
					detalleJefeAlmacenDTO.setFechaEnviadoEncargadoAlmacen(rs.getDate(7));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(7));
				}
				
				try 
				{
					detalleJefeAlmacenDTO.setIndicadorActivo(rs.getShort(8));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(8));
				}
				
				
				try 
				{
					detalleJefeAlmacenDTO.setCodigoUsuarioRegistro(rs.getString(9));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(9));
				}
				
				
				try 
				{
					detalleJefeAlmacenDTO.setIpRegistro(rs.getString(10));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(10));
				}
				
				
				try 
				{
					detalleJefeAlmacenDTO.setFechaRegistro(rs.getDate(11));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(11));
				}
				
				
				try 
				{
					detalleJefeAlmacenDTO.setCodigoUsuarioModificacion(rs.getString(12));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(12));
				}
				
				try 
				{
					detalleJefeAlmacenDTO.setIpModificacion(rs.getString(13));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(13));
				}
				
				
				try 
				{
					detalleJefeAlmacenDTO.setFechaModificacion(rs.getDate(14));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(14));
				}
				
				detalleJefeAlmacenDTO.setEstado(new EstadoDTO());
				try 
				{
					detalleJefeAlmacenDTO.getEstado().setId(rs.getInt(15));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(15));
				}
				
				detalleJefeAlmacenDTO.setRequerimientoArea(new RequerimientoAreaDTO());
				try 
				{
					detalleJefeAlmacenDTO.getRequerimientoArea().setId(rs.getInt(16));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(16));
				}
				
								
				try 
				{
					detalleJefeAlmacenDTO.getJefeAlmacen().setDescripcionUsuario(rs.getString(17));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(17));
				}
				
											
				try 
				{
					detalleJefeAlmacenDTO.getEstado().setDescripcionEstado(rs.getString(18));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(19));
				}
				
				listaDetalleJefeAlmacen.add(detalleJefeAlmacenDTO);
			}
			rs.close();
		}
		catch (NoResultException nre) 
		{
			listaDetalleJefeAlmacen= null;
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

		return listaDetalleJefeAlmacen;
	
	}

	
	
	public DetalleJefeAlmacenDTO registrarDetalleJefeAlmacen(
			DetalleJefeAlmacenDTO detalleJefeAlmacenDTO) throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();

			//11 parametros
			String sql = "begin PKG_DETA_JEFE_ALMACEN.SP_REGISTRAR_DETA_JEFE_ALMACEN(?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if(detalleJefeAlmacenDTO.getJefeAlmacen().getId()!=null && detalleJefeAlmacenDTO.getJefeAlmacen().getId()>=0)
			{
				call.setInt(1,detalleJefeAlmacenDTO.getJefeAlmacen().getId());
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getRequerimientoAprobacionJefe().getId()!=null && detalleJefeAlmacenDTO.getRequerimientoAprobacionJefe().getId()>=0)
			{
				call.setInt(2,detalleJefeAlmacenDTO.getRequerimientoAprobacionJefe().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getIndicadorRecepcionJefeArea()!=null && detalleJefeAlmacenDTO.getIndicadorRecepcionJefeArea()>=0)
			{
				call.setShort(3,detalleJefeAlmacenDTO.getIndicadorRecepcionJefeArea());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getFechaRecepcionJefeArea()!=null)
			{
				call.setDate(4,detalleJefeAlmacenDTO.getFechaRecepcionJefeArea());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getIndicadorEnviadoEncargadoAlmacen()!=null && detalleJefeAlmacenDTO.getIndicadorEnviadoEncargadoAlmacen()>=0)
			{
				call.setShort(5,detalleJefeAlmacenDTO.getIndicadorEnviadoEncargadoAlmacen());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			
			if(detalleJefeAlmacenDTO.getFechaEnviadoEncargadoAlmacen()!=null)
			{
				call.setDate(6,detalleJefeAlmacenDTO.getFechaEnviadoEncargadoAlmacen());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			
			if(detalleJefeAlmacenDTO.getIndicadorActivo()!=null && detalleJefeAlmacenDTO.getIndicadorActivo()>=0)
			{
				call.setShort(7,detalleJefeAlmacenDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			
			
			if(detalleJefeAlmacenDTO.getCodigoUsuarioRegistro()!=null && !detalleJefeAlmacenDTO.getCodigoUsuarioRegistro().equals(""))
			{
				call.setString(8,detalleJefeAlmacenDTO.getCodigoUsuarioRegistro());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);
			}
			
			
			if(detalleJefeAlmacenDTO.getIpRegistro()!=null && !detalleJefeAlmacenDTO.getIpRegistro().equals(""))
			{
				call.setString(9,detalleJefeAlmacenDTO.getIpRegistro());
			}
			else
			{
				call.setNull(9,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getEstado().getId()!=null && detalleJefeAlmacenDTO.getEstado().getId()>=0)
			{
				call.setInt(10,detalleJefeAlmacenDTO.getEstado().getId());
			}
			else
			{
				call.setNull(10,OracleTypes.NULL);
			}
			if(detalleJefeAlmacenDTO.getRequerimientoArea().getId() !=null && detalleJefeAlmacenDTO.getRequerimientoArea().getId() >=0)
			{
				call.setInt(11,detalleJefeAlmacenDTO.getRequerimientoArea().getId() );
			}
			else
			{
				call.setNull(11,OracleTypes.NULL);
			}
			
			
			call.registerOutParameter(12, OracleTypes.INTEGER);
			call.execute();
			Integer identificador=(Integer)call.getObject(12);
				try 
				{
					detalleJefeAlmacenDTO.setId(identificador.intValue());
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
			
		}
		catch (NoResultException nre) 
		{
			detalleJefeAlmacenDTO= null;
			log.error(nre.getMessage(), nre);

		}
		catch (Exception e) 
		{
			System.out.println("Error : "+e.getMessage());
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
			
		return detalleJefeAlmacenDTO;		
	}

	
	public Boolean actualizarDetalleJefeAlmacen(
			DetalleJefeAlmacenDTO detalleJefeAlmacenDTO) throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();
			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_DETA_JEFE_ALMACEN.SP_ACTUALIZAR_DETA_JEFE_ALMAC(?,?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			
			if(detalleJefeAlmacenDTO.getId()!=null && detalleJefeAlmacenDTO.getId()>=0)
			{
				call.setInt(1,detalleJefeAlmacenDTO.getId());
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getJefeAlmacen().getId()!=null && detalleJefeAlmacenDTO.getJefeAlmacen().getId()>=0)
			{
				call.setInt(2,detalleJefeAlmacenDTO.getJefeAlmacen().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			
			if(detalleJefeAlmacenDTO.getRequerimientoAprobacionJefe().getId()!=null && detalleJefeAlmacenDTO.getRequerimientoAprobacionJefe().getId()>=0)
			{
				call.setInt(3,detalleJefeAlmacenDTO.getRequerimientoAprobacionJefe().getId());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getIndicadorRecepcionJefeArea()!=null && detalleJefeAlmacenDTO.getIndicadorRecepcionJefeArea()>=0)
			{
				call.setShort(4,detalleJefeAlmacenDTO.getIndicadorRecepcionJefeArea());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getFechaRecepcionJefeArea()!=null)
			{
				call.setDate(5,detalleJefeAlmacenDTO.getFechaRecepcionJefeArea());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getIndicadorEnviadoEncargadoAlmacen()!=null && detalleJefeAlmacenDTO.getIndicadorEnviadoEncargadoAlmacen()>=0)
			{
				call.setShort(6,detalleJefeAlmacenDTO.getIndicadorEnviadoEncargadoAlmacen());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getFechaEnviadoEncargadoAlmacen()!=null)
			{
				call.setDate(7,detalleJefeAlmacenDTO.getFechaEnviadoEncargadoAlmacen());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			
			
			if(detalleJefeAlmacenDTO.getIndicadorActivo()!=null && detalleJefeAlmacenDTO.getIndicadorActivo()>=0)
			{
				call.setShort(8,detalleJefeAlmacenDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);
			}
			
			
			if(detalleJefeAlmacenDTO.getCodigoUsuarioModificacion()!=null && !detalleJefeAlmacenDTO.getCodigoUsuarioModificacion().equals(""))
			{
				call.setString(9,detalleJefeAlmacenDTO.getCodigoUsuarioModificacion());
			}
			else
			{
				call.setNull(9,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getIpModificacion()!=null && !detalleJefeAlmacenDTO.getIpModificacion().equals(""))
			{
				call.setString(10,detalleJefeAlmacenDTO.getIpModificacion());
			}
			else
			{
				call.setNull(10,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getEstado().getId()!=null && detalleJefeAlmacenDTO.getEstado().getId()>=0)
			{
				call.setInt(11,detalleJefeAlmacenDTO.getEstado().getId());
			}
			else
			{
				call.setNull(11,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenDTO.getRequerimientoArea().getId()!=null && detalleJefeAlmacenDTO.getRequerimientoArea().getId()>00)
			{
				call.setInt(12,detalleJefeAlmacenDTO.getRequerimientoArea().getId());
			}
			else
			{
				call.setNull(12,OracleTypes.NULL);
			}
			call.registerOutParameter(13, OracleTypes.INTEGER);
			call.execute();
			//Integer identificador=(Integer)call.getObject(12);
			
		} 
		catch (NoResultException nre) 
		{
			detalleJefeAlmacenDTO = null;
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

	

	


