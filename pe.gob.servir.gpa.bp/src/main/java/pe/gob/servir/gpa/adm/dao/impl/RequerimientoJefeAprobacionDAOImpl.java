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

import pe.gob.servir.gpa.adm.dao.RequerimientoJefeAprobacionDAOLocal;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoAreaDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoJefeAprobacionDTO;
import pe.gob.servir.gpa.model.dto.UsuarioPerfilesDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class RequerimientoJefeAprobacionDAOImpl implements RequerimientoJefeAprobacionDAOLocal 
{
	/** Metodo Constructor */
	public RequerimientoJefeAprobacionDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(RequerimientoJefeAprobacionDAOImpl.class);

	
	/********************************************************************************************/
	/****************************             CRUD             **********************************/
	/********************************************************************************************/
	
	public List<RequerimientoJefeAprobacionDTO> buscarRequerimientoJefeAprobacion(
			Integer requerimientoJefeAprobacionId, Integer jefeId,
			Integer requerimientoAreaId, Short indicadorRecibioEncargadoArea,
			Short indicadorEnviadoJefeAlmacen) throws Exception 
	{
		List<RequerimientoJefeAprobacionDTO> listaRequerimientoJefeAprobacion = null;
		RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin  PKG_REQU_JEFE_APRO.SP_BUSCAR_REQU_JEFE_APRO(?,?,?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if(requerimientoJefeAprobacionId != null && requerimientoJefeAprobacionId>=0)
			{
				call.setInt(1,requerimientoJefeAprobacionId);
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
			
			if(requerimientoAreaId!=null && requerimientoAreaId>=0)
			{
				call.setInt(3,requerimientoAreaId);
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);	
			}
			
			if(indicadorRecibioEncargadoArea !=null && indicadorRecibioEncargadoArea>=0)
			{
				call.setShort(4,indicadorRecibioEncargadoArea);
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);	
			}
			
			
			if(indicadorEnviadoJefeAlmacen!=null && indicadorEnviadoJefeAlmacen>=0)
			{
				call.setShort(5,indicadorEnviadoJefeAlmacen);
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			call.registerOutParameter(6, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(6);
			listaRequerimientoJefeAprobacion= new ArrayList<RequerimientoJefeAprobacionDTO>();

			while (rs.next()) 
			{
				requerimientoJefeAprobacionDTO= new RequerimientoJefeAprobacionDTO();				

				try 
				{
					requerimientoJefeAprobacionDTO.setId(rs.getInt(1));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(1));
				}
				
				requerimientoJefeAprobacionDTO.setJefe(new UsuarioPerfilesDTO());
				try 
				{
					requerimientoJefeAprobacionDTO.getJefe().setId(rs.getInt(2));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(2));
				}
				
				requerimientoJefeAprobacionDTO.setRequerimientoArea(new RequerimientoAreaDTO());
				try 
				{
					requerimientoJefeAprobacionDTO.getRequerimientoArea().setId(rs.getInt(3));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(3));
				}
				
				try 
				{
					requerimientoJefeAprobacionDTO.setIndicadorRecibioEncargadoArea(rs.getShort(4));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(4));
				}
				
				
				try 
				{
					requerimientoJefeAprobacionDTO.setFechaRecibioEncargadoArea(rs.getDate(5));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(5));
				}
				
				try 
				{
					requerimientoJefeAprobacionDTO.setIndicadorEnviadoJefeAlmacen(rs.getShort(6));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(6));
				}
				
				
				try 
				{
					requerimientoJefeAprobacionDTO.setFechaEnviadoJefeAlmacen(rs.getDate(7));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(7));
				}
				
				
				try 
				{
					requerimientoJefeAprobacionDTO.setIndicadorRecibioJefeAlmacen(rs.getShort(8));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(8));
				}
				
				try 
				{
					requerimientoJefeAprobacionDTO.setFechaRecibioJefeAlmacen(rs.getDate(9));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(9));
				}
				
				try 
				{
					requerimientoJefeAprobacionDTO.setIndicadorEnviadoEncargadoAlmacen(rs.getShort(10));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(10));
				}
				
				
				try 
				{
					requerimientoJefeAprobacionDTO.setFechaEnviadoEncargadoAlmacen(rs.getDate(11));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(11));
				}
				
				try 
				{
					requerimientoJefeAprobacionDTO.setIndicadorActivo(rs.getShort(12));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getShort(12));
				}
				
				try 
				{
					requerimientoJefeAprobacionDTO.setCodigoUsuarioRegistro(rs.getString(13));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(13));
				}
				
				
				try 
				{
					requerimientoJefeAprobacionDTO.setIpRegistro(rs.getString(14));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(14));
				}
				
				try 
				{
					requerimientoJefeAprobacionDTO.setFechaRegistro(rs.getDate(15));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(15));
				}
				
				try 
				{
					requerimientoJefeAprobacionDTO.setCodigoUsuarioModificacion(rs.getString(16));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(16));
				}
								
				try 
				{
					requerimientoJefeAprobacionDTO.setIpModificacion(rs.getString(17));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(17));
				}
				
				try 
				{
					requerimientoJefeAprobacionDTO.setFechaModificacion(rs.getDate(18));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getDate(18));
				}
				
				requerimientoJefeAprobacionDTO.setEstado(new EstadoDTO());
				try 
				{
					requerimientoJefeAprobacionDTO.getEstado().setId(rs.getInt(19));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(19));
				}
								
				listaRequerimientoJefeAprobacion.add(requerimientoJefeAprobacionDTO);
			}
			rs.close();
		}
		catch (NoResultException nre) 
		{
			listaRequerimientoJefeAprobacion= null;
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

		return listaRequerimientoJefeAprobacion;
	}

	
	
	
	public RequerimientoJefeAprobacionDTO registrarRequerimientoJefeAprobacion(
			RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();

			//15 parametros
			String sql = "begin PKG_REQU_JEFE_APRO.SP_REGISTRAR_REQU_JEFE_APRO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
					
			
			if (requerimientoJefeAprobacionDTO.getJefe().getId() != null && requerimientoJefeAprobacionDTO.getJefe().getId()>=0) 
			{
				call.setInt(1, requerimientoJefeAprobacionDTO.getJefe().getId());
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getRequerimientoArea().getId()!=null && requerimientoJefeAprobacionDTO.getRequerimientoArea().getId()>=0) 
			{
				call.setInt(2, requerimientoJefeAprobacionDTO.getRequerimientoArea().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getIndicadorRecibioEncargadoArea()!=null && requerimientoJefeAprobacionDTO.getIndicadorRecibioEncargadoArea()>=0) 
			{
				call.setShort(3, requerimientoJefeAprobacionDTO.getIndicadorRecibioEncargadoArea());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);			
			}
			
			if (requerimientoJefeAprobacionDTO.getFechaRecibioEncargadoArea()!=null) 
			{
				call.setDate(4,requerimientoJefeAprobacionDTO.getFechaRecibioEncargadoArea());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			
			if (requerimientoJefeAprobacionDTO.getIndicadorEnviadoJefeAlmacen()!=null && requerimientoJefeAprobacionDTO.getIndicadorEnviadoJefeAlmacen()>=0) 
			{
				call.setShort(5,requerimientoJefeAprobacionDTO.getIndicadorEnviadoJefeAlmacen());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getFechaEnviadoJefeAlmacen()!=null) 
			{
				call.setDate(6,requerimientoJefeAprobacionDTO.getFechaEnviadoJefeAlmacen());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getIndicadorRecibioJefeAlmacen()!=null && requerimientoJefeAprobacionDTO.getIndicadorRecibioJefeAlmacen()>=0) 
			{
				call.setShort(7,requerimientoJefeAprobacionDTO.getIndicadorRecibioJefeAlmacen());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getFechaRecibioJefeAlmacen()!=null) 
			{
				call.setDate(8,requerimientoJefeAprobacionDTO.getFechaRecibioJefeAlmacen());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getIndicadorEnviadoEncargadoAlmacen()!=null && requerimientoJefeAprobacionDTO.getIndicadorEnviadoEncargadoAlmacen()>=0) 
			{
				call.setShort(9,requerimientoJefeAprobacionDTO.getIndicadorEnviadoEncargadoAlmacen());
			}
			else
			{
				call.setNull(9,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getFechaEnviadoEncargadoAlmacen()!=null) 
			{
				call.setDate(10,requerimientoJefeAprobacionDTO.getFechaEnviadoEncargadoAlmacen());
			}
			else
			{
				call.setNull(10,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getIndicadorActivo()!= null && requerimientoJefeAprobacionDTO.getIndicadorActivo()>=0) 
			{
				call.setShort(11,requerimientoJefeAprobacionDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(11,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getCodigoUsuarioRegistro()!=null && !requerimientoJefeAprobacionDTO.getCodigoUsuarioRegistro().equals("")) 
			{
				call.setString(12,requerimientoJefeAprobacionDTO.getCodigoUsuarioRegistro());
			}
			else
			{
				call.setNull(12,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getIpRegistro()!=null && !requerimientoJefeAprobacionDTO.getIpRegistro().equals("")) 
			{
				call.setString(13,requerimientoJefeAprobacionDTO.getIpRegistro());
			}
			else
			{
				call.setNull(13,OracleTypes.NULL);
			}
			
			if (requerimientoJefeAprobacionDTO.getEstado().getId()!=null && requerimientoJefeAprobacionDTO.getEstado().getId()>=0) 
			{
				call.setInt(14,requerimientoJefeAprobacionDTO.getEstado().getId());
			}
			else
			{
				call.setNull(14,OracleTypes.NULL);
			}
			
			call.registerOutParameter(15, OracleTypes.INTEGER);
			call.execute();
			Integer identificador=(Integer)call.getObject(15);
				
			try 
			{
				requerimientoJefeAprobacionDTO.setId(identificador.intValue());
			}
			catch (Exception e) 
			{
				log.error(e.getMessage(), e);
			}
							
			
		}
		catch (NoResultException nre) 
		{
			requerimientoJefeAprobacionDTO= null;
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
			
		return requerimientoJefeAprobacionDTO;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Boolean actualizarTipo(
			RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();
			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_REQU_JEFE_APRO.SP_ACTUALIZAR_REQU_JEFE_APRO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			
			if(requerimientoJefeAprobacionDTO.getId()!=null && requerimientoJefeAprobacionDTO.getId()>=0)
			{
				call.setInt(1,requerimientoJefeAprobacionDTO.getId());
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			
			if(requerimientoJefeAprobacionDTO.getJefe().getId()!=null && requerimientoJefeAprobacionDTO.getJefe().getId()>=0)
			{
				call.setInt(2,requerimientoJefeAprobacionDTO.getJefe().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);	
			}
			
			if(requerimientoJefeAprobacionDTO.getRequerimientoArea().getId()!=null && requerimientoJefeAprobacionDTO.getRequerimientoArea().getId()>=0)
			{
				call.setInt(3,requerimientoJefeAprobacionDTO.getRequerimientoArea().getId());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);	
			}
			
			if(requerimientoJefeAprobacionDTO.getIndicadorRecibioEncargadoArea()!=null && requerimientoJefeAprobacionDTO.getIndicadorRecibioEncargadoArea()>=0)
			{
				call.setShort(4,requerimientoJefeAprobacionDTO.getIndicadorRecibioEncargadoArea());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);	
			}
			
			if(requerimientoJefeAprobacionDTO.getFechaRecibioEncargadoArea()!=null)
			{
				call.setDate(5,requerimientoJefeAprobacionDTO.getFechaRecibioEncargadoArea());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(requerimientoJefeAprobacionDTO.getIndicadorEnviadoJefeAlmacen()!=null && requerimientoJefeAprobacionDTO.getIndicadorEnviadoJefeAlmacen()>=0)
			{
				call.setShort(6,requerimientoJefeAprobacionDTO.getIndicadorEnviadoJefeAlmacen());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(requerimientoJefeAprobacionDTO.getFechaEnviadoJefeAlmacen()!=null)
			{
				call.setDate(7,requerimientoJefeAprobacionDTO.getFechaEnviadoJefeAlmacen());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);	
			}
			
			if(requerimientoJefeAprobacionDTO.getIndicadorRecibioJefeAlmacen()!=null && requerimientoJefeAprobacionDTO.getIndicadorRecibioJefeAlmacen()>=0)
			{
				call.setShort(8,requerimientoJefeAprobacionDTO.getIndicadorRecibioJefeAlmacen());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);
			}
			
			if(requerimientoJefeAprobacionDTO.getFechaRecibioJefeAlmacen()!=null)
			{
				call.setDate(9,requerimientoJefeAprobacionDTO.getFechaRecibioJefeAlmacen());
			}
			else
			{
				call.setNull(9,OracleTypes.NULL);	
			}
			
			if(requerimientoJefeAprobacionDTO.getIndicadorEnviadoEncargadoAlmacen()!=null && requerimientoJefeAprobacionDTO.getIndicadorEnviadoEncargadoAlmacen()>=0)
			{
				call.setShort(10,requerimientoJefeAprobacionDTO.getIndicadorEnviadoEncargadoAlmacen());
			}
			else
			{
				call.setNull(10,OracleTypes.NULL);
			}
			
			if(requerimientoJefeAprobacionDTO.getFechaEnviadoEncargadoAlmacen()!=null)
			{
				call.setDate(11,requerimientoJefeAprobacionDTO.getFechaEnviadoEncargadoAlmacen());
			}
			else
			{
				call.setNull(11,OracleTypes.NULL);
			}
			
			if(requerimientoJefeAprobacionDTO.getIndicadorActivo()!=null && requerimientoJefeAprobacionDTO.getIndicadorActivo()>=0)
			{
				call.setShort(12,requerimientoJefeAprobacionDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(12,OracleTypes.NULL);
			}
			
			if(requerimientoJefeAprobacionDTO.getCodigoUsuarioModificacion()!=null && !requerimientoJefeAprobacionDTO.getCodigoUsuarioModificacion().equals(""))
			{
				call.setString(13,requerimientoJefeAprobacionDTO.getCodigoUsuarioModificacion());
			}
			else
			{
				call.setNull(13,OracleTypes.NULL);
			}
						
			if(requerimientoJefeAprobacionDTO.getIpModificacion()!=null && !requerimientoJefeAprobacionDTO.getIpModificacion().equals(""))
			{
				call.setString(14,requerimientoJefeAprobacionDTO.getIpModificacion());
			}
			else
			{
				call.setNull(14,OracleTypes.NULL);
			}
			
			if(requerimientoJefeAprobacionDTO.getEstado().getId()!=null && requerimientoJefeAprobacionDTO.getEstado().getId()>=0)
			{
				call.setInt(15,requerimientoJefeAprobacionDTO.getEstado().getId());
			}
			else
			{
				call.setNull(15,OracleTypes.NULL);
			}
			
			call.registerOutParameter(16, OracleTypes.INTEGER);
			call.execute();
			//Integer identificador=(Integer)call.getObject(16);
			
		} 
		catch (NoResultException nre) 
		{
			requerimientoJefeAprobacionDTO = null;
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
