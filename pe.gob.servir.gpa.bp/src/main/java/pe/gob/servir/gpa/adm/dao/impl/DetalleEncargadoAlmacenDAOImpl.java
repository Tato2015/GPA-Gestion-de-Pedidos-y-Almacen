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

import pe.gob.servir.gpa.adm.dao.DetalleEncargadoAlmacenDAOLocal;
import pe.gob.servir.gpa.model.dto.DetalleEncargadoAlmacenDTO;
import pe.gob.servir.gpa.model.dto.DetalleJefeAlmacenDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.UsuarioPerfilesDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class DetalleEncargadoAlmacenDAOImpl implements DetalleEncargadoAlmacenDAOLocal 
{

	/** Metodo Constructor */
	public DetalleEncargadoAlmacenDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(DetalleEncargadoAlmacenDAOImpl.class);

	public List<DetalleEncargadoAlmacenDTO> buscarDetalleEncargadoAlmacen(
			Integer detalleEncargadoAlmacenId, Integer encargadoId,
			Integer detalleJefeAlmacenId, Short indicadorRecepcionJefeAlmacen,
			Short indicadorEnviadoJefeArea, Short indicadorActivo,
			Integer estadoId) throws Exception 
	{
		List<DetalleEncargadoAlmacenDTO> listaDetalleEncargadoAlmacen= null;
		DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_DETA_ENCA_ALMA.SP_BUSCAR_DETA_ENCA_ALMA(?,?,?,?,?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if(detalleEncargadoAlmacenId!=null && detalleEncargadoAlmacenId>=0)
			{
				call.setInt(1,detalleEncargadoAlmacenId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(encargadoId!=null && encargadoId>=0)
			{
				call.setInt(2,encargadoId);
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(detalleJefeAlmacenId!=null && detalleJefeAlmacenId>=0)
			{
				call.setInt(3,detalleJefeAlmacenId);
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}

			if(indicadorRecepcionJefeAlmacen!=null && indicadorRecepcionJefeAlmacen>=0)
			{
				call.setShort(4,indicadorRecepcionJefeAlmacen);
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(indicadorEnviadoJefeArea!=null && indicadorEnviadoJefeArea>=0)
			{
				call.setShort(5,indicadorEnviadoJefeArea);
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(indicadorEnviadoJefeArea!=null && indicadorEnviadoJefeArea>=0)
			{
				call.setShort(5,indicadorEnviadoJefeArea);
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			
			if(indicadorActivo!=null && indicadorActivo>=0)
			{
				call.setShort(6,indicadorActivo);
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			
			if(estadoId!=null && estadoId>=0)
			{
				call.setInt(7,estadoId);
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}			
						
			call.registerOutParameter(8, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(8);
			listaDetalleEncargadoAlmacen= new ArrayList<DetalleEncargadoAlmacenDTO>();

			while (rs.next()) {
				detalleEncargadoAlmacenDTO= new DetalleEncargadoAlmacenDTO();				

				try 
				{
					detalleEncargadoAlmacenDTO.setId(rs.getInt(1));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(1));
				}
				
				detalleEncargadoAlmacenDTO.setEncargado(new UsuarioPerfilesDTO());
				
				try 
				{
					detalleEncargadoAlmacenDTO.getEncargado().setId(rs.getInt(2));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(2));
				}
				
				
				detalleEncargadoAlmacenDTO.setDetalleJefeAlmacen(new DetalleJefeAlmacenDTO());
				try 
				{
					detalleEncargadoAlmacenDTO.getDetalleJefeAlmacen().setId(rs.getInt(3));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(3));
				}
				
				
				try 
				{
					detalleEncargadoAlmacenDTO.setIndicadorRecepcionJefeAlmacen(rs.getShort(4));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(4));
				}
				
				try 
				{
					detalleEncargadoAlmacenDTO.setFechaRecepcionJefeAlmacen(rs.getDate(5));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(5));
				}
				
				
				try 
				{
					detalleEncargadoAlmacenDTO.setIndicadorEnviadoAlJefeArea(rs.getShort(6));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(6));
				}
				
				try 
				{
					detalleEncargadoAlmacenDTO.setFechaEnviadoAlJefeArea(rs.getDate(7));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(7));
				}
				
				try 
				{
					detalleEncargadoAlmacenDTO.setIndicadorActivo(rs.getShort(8));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(8));
				}
				
				try 
				{
					detalleEncargadoAlmacenDTO.setCodigoUsuarioRegistro(rs.getString(9));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(9));
				}
				
				
				try 
				{
					detalleEncargadoAlmacenDTO.setIpRegistro(rs.getString(10));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(10));
				}
				
				
				try 
				{
					detalleEncargadoAlmacenDTO.setFechaRegistro(rs.getDate(11));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(11));
				}
				
				
				try 
				{
					detalleEncargadoAlmacenDTO.setCodigoUsuarioModificacion(rs.getString(12));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(12));
				}
				
				
				try 
				{
					detalleEncargadoAlmacenDTO.setIpModificacion(rs.getString(13));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(13));
				}
				
				
				try 
				{
					detalleEncargadoAlmacenDTO.setFechaModificacion(rs.getDate(14));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(14));
				}
				
				detalleEncargadoAlmacenDTO.setEstado(new EstadoDTO());
				try 
				{
					detalleEncargadoAlmacenDTO.getEstado().setId(rs.getInt(15));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(15));
				}
				
				listaDetalleEncargadoAlmacen.add(detalleEncargadoAlmacenDTO);
			}
			rs.close();
		}
		catch (NoResultException nre) 
		{
			listaDetalleEncargadoAlmacen= null;
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
		return listaDetalleEncargadoAlmacen;
	}

	
	public DetalleEncargadoAlmacenDTO registrarDetalleEncargadoAlmacen(
			DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();

			//7 parametros
			String sql = "begin PKG_DETA_ENCA_ALMA.SP_REGISTRAR_DETA_ENCA_ALMA(?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			
			if(detalleEncargadoAlmacenDTO.getEncargado().getId()!=null && detalleEncargadoAlmacenDTO.getEncargado().getId()>=0)
			{
				call.setInt(1,detalleEncargadoAlmacenDTO.getEncargado().getId());
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			
			if(detalleEncargadoAlmacenDTO.getDetalleJefeAlmacen().getId()!=null && detalleEncargadoAlmacenDTO.getDetalleJefeAlmacen().getId()>=0)
			{
				call.setInt(2,detalleEncargadoAlmacenDTO.getDetalleJefeAlmacen().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			
			if(detalleEncargadoAlmacenDTO.getIndicadorRecepcionJefeAlmacen()!=null && detalleEncargadoAlmacenDTO.getIndicadorRecepcionJefeAlmacen()>=0)
			{
				call.setShort(3, detalleEncargadoAlmacenDTO.getIndicadorRecepcionJefeAlmacen());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);	
			}
			
			if(detalleEncargadoAlmacenDTO.getIndicadorActivo()!=null && detalleEncargadoAlmacenDTO.getIndicadorActivo()>=0)
			{
				call.setShort(4,detalleEncargadoAlmacenDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(detalleEncargadoAlmacenDTO.getCodigoUsuarioRegistro()!=null && !detalleEncargadoAlmacenDTO.getCodigoUsuarioRegistro().equals(""))
			{
				call.setString(5,detalleEncargadoAlmacenDTO.getCodigoUsuarioRegistro());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);	
			}
			
			if(detalleEncargadoAlmacenDTO.getIpRegistro()!=null && !detalleEncargadoAlmacenDTO.getIpRegistro().equals(""))
			{
				call.setString(6,detalleEncargadoAlmacenDTO.getIpRegistro());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(detalleEncargadoAlmacenDTO.getEstado().getId()!=null && detalleEncargadoAlmacenDTO.getEstado().getId()>=0)
			{
				call.setInt(7,detalleEncargadoAlmacenDTO.getEstado().getId());
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
					detalleEncargadoAlmacenDTO.setId(identificador.intValue());
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
			
		}
		catch (NoResultException nre) 
		{
			detalleEncargadoAlmacenDTO= null;
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
		return detalleEncargadoAlmacenDTO;
	}
	

	public Boolean actualizarDetalleEncargadoAlmacen(
			DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();
			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_DETA_ENCA_ALMA.SP_ACTUALIZAR_DETA_ENCA_ALMA(?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if(detalleEncargadoAlmacenDTO.getId()!=null && detalleEncargadoAlmacenDTO.getId()>=0)
			{
				call.setInt(1,detalleEncargadoAlmacenDTO.getId());
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			
			if(detalleEncargadoAlmacenDTO.getEncargado().getId()!=null && detalleEncargadoAlmacenDTO.getEncargado().getId()>=0)
			{
				call.setInt(2,detalleEncargadoAlmacenDTO.getEncargado().getId());	
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(detalleEncargadoAlmacenDTO.getDetalleJefeAlmacen().getId()!=null && detalleEncargadoAlmacenDTO.getDetalleJefeAlmacen().getId()>=0)
			{
				call.setInt(3,detalleEncargadoAlmacenDTO.getDetalleJefeAlmacen().getId());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(detalleEncargadoAlmacenDTO.getIndicadorEnviadoAlJefeArea()!=null && detalleEncargadoAlmacenDTO.getIndicadorEnviadoAlJefeArea()>=0)
			{
				call.setShort(4,detalleEncargadoAlmacenDTO.getIndicadorEnviadoAlJefeArea());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);	
			}
			
			if(detalleEncargadoAlmacenDTO.getIndicadorActivo()!=null && detalleEncargadoAlmacenDTO.getIndicadorActivo()>=0)
			{
				call.setShort(5,detalleEncargadoAlmacenDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			
			if(detalleEncargadoAlmacenDTO.getCodigoUsuarioModificacion()!=null && !detalleEncargadoAlmacenDTO.getCodigoUsuarioModificacion().equals(""))
			{
				call.setString(6,detalleEncargadoAlmacenDTO.getCodigoUsuarioModificacion());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);				
			}
			
			if(detalleEncargadoAlmacenDTO.getIpModificacion()!=null && !detalleEncargadoAlmacenDTO.getIpModificacion().equals(""))
			{
				call.setString(7,detalleEncargadoAlmacenDTO.getIpModificacion());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			
			if(detalleEncargadoAlmacenDTO.getEstado().getId()!=null && detalleEncargadoAlmacenDTO.getEstado().getId()>=0)
			{
				call.setInt(8,detalleEncargadoAlmacenDTO.getEstado().getId());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);
			}			
			call.registerOutParameter(9, OracleTypes.INTEGER);
			call.execute();
			//Integer identificador=(Integer)call.getObject(9);
			
		} 
		catch (NoResultException nre) 
		{
			detalleEncargadoAlmacenDTO = null;
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
