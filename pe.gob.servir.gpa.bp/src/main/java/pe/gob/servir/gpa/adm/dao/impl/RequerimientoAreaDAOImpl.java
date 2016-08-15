package pe.gob.servir.gpa.adm.dao.impl;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.RequerimientoAreaDAOLocal;
import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoAreaDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class RequerimientoAreaDAOImpl implements RequerimientoAreaDAOLocal
{

	/** Metodo Constructor 	 */
	public RequerimientoAreaDAOImpl() 
	{
		
	}	
	/** Definicion del contexto de persistencia para el acceso al modelo de datos*/
	/**@PersistenceContext(unitName="pe.gob.servir.gpa.bp.pu")
	private	EntityManager	em;*/
	
	 /** Atributo del tipo properties, donde se almacenas propiedades de la aplicacion*/
		ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(RequerimientoAreaDAOImpl.class);

	public List<RequerimientoAreaDTO> buscarRequerimientoArea(
			Integer requerimientoAreaId, Integer areaId,
			Short indicadorEnviadoAlJefe, Short indicadorRequeRecibidoDelJefe,Integer programacionId)
			throws Exception 	
	{
		
		List<RequerimientoAreaDTO> listaRequerimientoArea= null;
		RequerimientoAreaDTO requerimientoAreaDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_REQUERIMIENTO_AREA.SP_BUSCAR_REQUE_AREA(?,?,?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if(requerimientoAreaId!=null && requerimientoAreaId>=0)
			{
				call.setInt(1,requerimientoAreaId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(areaId !=null && areaId>=0)
			{
				call.setInt(2,areaId);
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			if(indicadorEnviadoAlJefe!=null && indicadorEnviadoAlJefe>=0)
			{
				call.setShort(3,indicadorEnviadoAlJefe);
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			
			if(indicadorRequeRecibidoDelJefe!=null && indicadorRequeRecibidoDelJefe>=0 )
			{
				call.setShort(4,indicadorRequeRecibidoDelJefe);
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);	
			}
			
			if(programacionId!=null && programacionId>=0)
			{
				call.setInt(5,programacionId);
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			call.registerOutParameter(6, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(6);
			listaRequerimientoArea= new ArrayList<RequerimientoAreaDTO>();

			while (rs.next()) {
				requerimientoAreaDTO = new RequerimientoAreaDTO();				

				try 
				{
					requerimientoAreaDTO.setId(rs.getInt(1));
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setIndicadorEnviadoAlJefe(rs.getShort(2));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setFechaEnviadoAlJefe(rs.getDate(3));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setIndicadorRequerimientoRecepcionDelJefe(rs.getShort(4));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setFechaRequerimientoRecepcionDelJefe(rs.getDate(5));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setIndicadorActivo(rs.getShort(6));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setCodigoUsuarioRegistro(rs.getString(7));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setIpRegistro(rs.getString(8));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setFechaRegistro(rs.getDate(9));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setCodigoUsuarioModificacion(rs.getString(10));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setIpModificacion(rs.getString(11));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.setFechaModificacion(rs.getDate(12));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				requerimientoAreaDTO.setEstado(new EstadoDTO());
				try 
				{
					requerimientoAreaDTO.getEstado().setId(rs.getInt(13));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				requerimientoAreaDTO.setArea(new AreaDTO());
				try 
				{
					requerimientoAreaDTO.getArea().setId(rs.getInt(14));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				requerimientoAreaDTO.setProgramacion(new ProgramacionDTO());
				try 
				{
					requerimientoAreaDTO.getProgramacion().setId(rs.getInt(15));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.getEstado().setDescripcionEstado(rs.getString(16));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}							
				
				try 
				{
					requerimientoAreaDTO.getArea().setDescripcionArea(rs.getString(17));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					requerimientoAreaDTO.getProgramacion().setDescripcionProgramacion(rs.getString(18));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				requerimientoAreaDTO.getProgramacion().setTipoPedido(new TipoDTO());
				try 
				{
					requerimientoAreaDTO.getProgramacion().getTipoPedido().setDescripcionTipo(rs.getString(19));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				requerimientoAreaDTO.getProgramacion().setTipoPeriodo(new TipoDTO());
				try 
				{
					requerimientoAreaDTO.getProgramacion().getTipoPeriodo().setDescripcionTipo(rs.getString(20));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				
				
				listaRequerimientoArea.add(requerimientoAreaDTO);
			}
			rs.close();
		}
		catch (NoResultException nre) 
		{
			listaRequerimientoArea= null;
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

		return listaRequerimientoArea;

	}

	public RequerimientoAreaDTO registrarRequerimientoArea(
			RequerimientoAreaDTO requerimientoAreaDTO) throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
		
			String sql = "begin PKG_REQUERIMIENTO_AREA.SP_REGISTRAR_REQUE_AREA(?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);

			if(requerimientoAreaDTO.getIndicadorEnviadoAlJefe()>=0)
			{
				call.setShort(1,requerimientoAreaDTO.getIndicadorEnviadoAlJefe());
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getFechaEnviadoAlJefe()!=null)
			{
				call.setDate(2,requerimientoAreaDTO.getFechaEnviadoAlJefe());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getIndicadorRequerimientoRecepcionDelJefe()>=0)
			{
				call.setShort(3,requerimientoAreaDTO.getIndicadorRequerimientoRecepcionDelJefe());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);	
			}
			
			if(requerimientoAreaDTO.getFechaRequerimientoRecepcionDelJefe()!=null)
			{
				call.setDate(4,requerimientoAreaDTO.getFechaRequerimientoRecepcionDelJefe());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);	
			}
			
			if(requerimientoAreaDTO.getIndicadorActivo()>=0)
			{
				call.setShort(5,requerimientoAreaDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);	
			}
			
			if(requerimientoAreaDTO.getCodigoUsuarioRegistro()!=null && !requerimientoAreaDTO.getCodigoUsuarioRegistro().equals(""))
			{
				call.setString(6,requerimientoAreaDTO.getCodigoUsuarioRegistro());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getIpRegistro()!=null && !requerimientoAreaDTO.getIpRegistro().equals(""))
			{
				call.setString(7,requerimientoAreaDTO.getIpRegistro());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);	
			}
			
			if(requerimientoAreaDTO.getEstado().getId()!=null && requerimientoAreaDTO.getEstado().getId()>=0)
			{
				call.setInt(8,requerimientoAreaDTO.getEstado().getId());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getArea().getId()!=null && requerimientoAreaDTO.getArea().getId()>=0)
			{
				call.setInt(9,requerimientoAreaDTO.getArea().getId());
			}
			else
			{
				call.setNull(9,OracleTypes.NULL);	
			}
			
			if(requerimientoAreaDTO.getProgramacion().getId()!=null && requerimientoAreaDTO.getProgramacion().getId()>=0)			
			{
				call.setInt(10,requerimientoAreaDTO.getProgramacion().getId());
			}
			else
			{
				call.setNull(10,OracleTypes.NULL);	
			}
			
			call.registerOutParameter(11, OracleTypes.INTEGER);			
			call.execute();
			Integer identificador=(Integer)call.getObject(11);
				try 
				{
					requerimientoAreaDTO.setId(identificador.intValue());
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
			
		}
		catch (NoResultException nre) 
		{
			requerimientoAreaDTO = null;
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
			
		return requerimientoAreaDTO;
	}

	public boolean actualizarRequerimientoAreaDTO(
			RequerimientoAreaDTO requerimientoAreaDTO) throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();
			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_REQUERIMIENTO_AREA.SP_ACTUALIZAR_REQUE_AREA(?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if(requerimientoAreaDTO.getId()!=null && requerimientoAreaDTO.getId()>=0)			
			{
				call.setInt(1,requerimientoAreaDTO.getId());
			}	
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getIndicadorEnviadoAlJefe()>=0)			
			{
				call.setShort(2,requerimientoAreaDTO.getIndicadorEnviadoAlJefe());
			}	
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getFechaEnviadoAlJefe()!=null)			
			{
				call.setDate(3,requerimientoAreaDTO.getFechaEnviadoAlJefe());
			}	
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getIndicadorRequerimientoRecepcionDelJefe()>=0)			
			{
				call.setShort(4,requerimientoAreaDTO.getIndicadorRequerimientoRecepcionDelJefe());
			}	
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getFechaRequerimientoRecepcionDelJefe()!=null)			
			{
				call.setDate(5,requerimientoAreaDTO.getFechaRequerimientoRecepcionDelJefe());
			}	
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getIndicadorActivo()>=0)			
			{
				call.setShort(6,requerimientoAreaDTO.getIndicadorActivo());
			}	
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getCodigoUsuarioRegistro()!=null && !requerimientoAreaDTO.getCodigoUsuarioRegistro().equals(""))			
			{
				call.setString(7,requerimientoAreaDTO.getCodigoUsuarioRegistro());
			}	
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getIpModificacion()!=null && !requerimientoAreaDTO.getIpModificacion().equals(""))			
			{
				call.setString(8,requerimientoAreaDTO.getIpModificacion());
			}	
			else
			{
				call.setNull(8,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getEstado().getId()>=0 && requerimientoAreaDTO.getEstado().getId()!=null)			
			{
				call.setInt(9,requerimientoAreaDTO.getEstado().getId());
			}	
			else
			{
				call.setNull(9,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getArea().getId()!=null && requerimientoAreaDTO.getArea().getId()>=0)			
			{
				call.setInt(10,requerimientoAreaDTO.getArea().getId());
			}	
			else
			{
				call.setNull(10,OracleTypes.NULL);
			}
			
			if(requerimientoAreaDTO.getProgramacion().getId()!=null && requerimientoAreaDTO.getProgramacion().getId()>=0)
			{
				call.setInt(11,requerimientoAreaDTO.getProgramacion().getId());
			}
			else
			{
				call.setNull(11,OracleTypes.NULL);	
			}
			
			
			call.registerOutParameter(12, OracleTypes.INTEGER);
			call.execute();
			//Integer identificador=(Integer)call.getObject(12);
			
		} 
		catch (NoResultException nre) 
		{
			requerimientoAreaDTO= null;
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
		
	
	Connection conn = null;
//	public Connection conexionDataSource() throws NamingException, SQLException
//	{					
//		try 
//		{						
//			InitialContext ic = new InitialContext();			
//			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
//			conn = ds.getConnection();
//								
//		}
//		catch (Exception e) 
//		{
//		System.out.println("Error : "+e.getMessage());
//		return null;
//		}		
//		return conn;	
//	}
//	
	public Connection conexionDataSource() throws NamingException, SQLException
	{					
		try 
		{						
			 Class.forName ("oracle.jdbc.driver.OracleDriver");
		     Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SERVIR", "123456");
		     return conn;
		}
		catch (Exception e) 
		{
		System.out.println("Error : "+e.getMessage());
		return null;
		}		
			
	}
	
	
	
	
	public void cerrarConexionDataSource()
	{
		try 
		{
			conn.close();	
		}
		catch (Exception e) 
		{
		System.out.println("Error : "+e.getMessage());
		}
		
	}
		
	
}
