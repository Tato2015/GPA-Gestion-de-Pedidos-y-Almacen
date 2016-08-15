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

import pe.gob.servir.gpa.adm.dao.UsuarioPerfilesDAOLocal;
import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.PerfilDTO;
import pe.gob.servir.gpa.model.dto.UsuarioDTO;
import pe.gob.servir.gpa.model.dto.UsuarioPerfilesDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class UsuarioPerfilesDAOImpl implements UsuarioPerfilesDAOLocal 
{

	/** Metodo Constructor */
	public UsuarioPerfilesDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(UsuarioPerfilesDAOImpl.class);

	public List<UsuarioPerfilesDTO> buscarUsuarioPerfiles(Integer usuarioPerfilId,Integer usuarioId,Integer perfilId,Integer estadoId) throws Exception 
	{
		List<UsuarioPerfilesDTO> listaUsuarioPerfiles= null;
		UsuarioPerfilesDTO usuarioPerfilesDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try 
		{
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_USUARIO_PERFILES.SP_BUSCAR_USUA_PERFI(?,?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if(usuarioPerfilId !=null && usuarioPerfilId>=0)
			{
				call.setInt(1,usuarioPerfilId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			
			if(usuarioId !=null && usuarioId>=0)
			{
				call.setInt(2,usuarioId);
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(perfilId != null && perfilId>=0)
			{
				call.setInt(3,perfilId);
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(estadoId != null && estadoId>=0)
			{
				call.setInt(4,estadoId);
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
						
			call.registerOutParameter(5, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(5);
			listaUsuarioPerfiles= new ArrayList<UsuarioPerfilesDTO>();

			while (rs.next()) 
			{
				usuarioPerfilesDTO = new UsuarioPerfilesDTO();				

				try 
				{
					usuarioPerfilesDTO.setId(rs.getInt(1));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(1));
				}
				
				usuarioPerfilesDTO.setUsuario(new UsuarioDTO());
				
				try 
				{
					usuarioPerfilesDTO.getUsuario().setId(rs.getInt(2));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(2));
				}


				usuarioPerfilesDTO.setPerfil(new PerfilDTO());
				try 
				{
					usuarioPerfilesDTO.getPerfil().setId(rs.getInt(3));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(3));
				}

				
				try 
				{
					usuarioPerfilesDTO.setIndicadorActivo(rs.getShort(4));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(4));
				}

				
				try 
				{
					usuarioPerfilesDTO.setCodigoUsuarioRegistro(rs.getString(5));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(5));
				}

				try 
				{
					usuarioPerfilesDTO.setIpRegistro(rs.getString(6));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				try 
				{
					usuarioPerfilesDTO.setFechaRegistro(rs.getDate(7));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}

				try 
				{
					usuarioPerfilesDTO.setCodigoUsuarioModificacion(rs.getString(8));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}

				try 
				{
					usuarioPerfilesDTO.setIpModificacion(rs.getString(9));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}

				try 
				{
					usuarioPerfilesDTO.setFechaModificacion(rs.getDate(10));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}

				usuarioPerfilesDTO.setEstado(new EstadoDTO());

				try 
				{
					usuarioPerfilesDTO.getEstado().setId(rs.getInt(11));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}

				try {
					usuarioPerfilesDTO.getEstado().setDescripcionEstado(rs.getString(12));

				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}
						
				try 
				{
					usuarioPerfilesDTO.getUsuario().setDescripcionUsuario(rs.getString(13));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}
				
				try 
				{
					usuarioPerfilesDTO.getPerfil().setDescripcionPerfil(rs.getString(14));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				listaUsuarioPerfiles.add(usuarioPerfilesDTO);
			}
			rs.close();
		}
		catch (NoResultException nre) 
		{
			listaUsuarioPerfiles = null;
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

		return listaUsuarioPerfiles;
	}

	public List<UsuarioPerfilesDTO> buscarUsuarioPerfilesXAreaId(
			Integer usuarioPerfilId, Integer usuarioId, Integer perfilId,
			Integer estadoId, Integer areaId) throws Exception 
	{
		List<UsuarioPerfilesDTO> listaUsuarioPerfiles= null;
		UsuarioPerfilesDTO usuarioPerfilesDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try 
		{
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_USUARIO_PERFILES.SP_BUSCAR_USUA_PERFIXAREA(?,?,?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if(usuarioPerfilId !=null && usuarioPerfilId>=0)
			{
				call.setInt(1,usuarioPerfilId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			
			if(usuarioId !=null && usuarioId>=0)
			{
				call.setInt(2,usuarioId);
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(perfilId != null && perfilId>=0)
			{
				call.setInt(3,perfilId);
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(estadoId != null && estadoId>=0)
			{
				call.setInt(4,estadoId);
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
						
			if(areaId!=null && areaId>=0)
			{
				call.setInt(5,areaId);
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			call.registerOutParameter(6, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(6);
			listaUsuarioPerfiles= new ArrayList<UsuarioPerfilesDTO>();

			while (rs.next()) 
			{
				usuarioPerfilesDTO = new UsuarioPerfilesDTO();				

				try 
				{
					usuarioPerfilesDTO.setId(rs.getInt(1));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(1));
				}
				
				usuarioPerfilesDTO.setUsuario(new UsuarioDTO());
				
				try 
				{
					usuarioPerfilesDTO.getUsuario().setId(rs.getInt(2));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(2));
				}


				usuarioPerfilesDTO.setPerfil(new PerfilDTO());
				try 
				{
					usuarioPerfilesDTO.getPerfil().setId(rs.getInt(3));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(3));
				}

				
				try 
				{
					usuarioPerfilesDTO.setIndicadorActivo(rs.getShort(4));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(4));
				}

				
				try 
				{
					usuarioPerfilesDTO.setCodigoUsuarioRegistro(rs.getString(5));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(5));
				}

				try 
				{
					usuarioPerfilesDTO.setIpRegistro(rs.getString(6));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				try 
				{
					usuarioPerfilesDTO.setFechaRegistro(rs.getDate(7));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}

				try 
				{
					usuarioPerfilesDTO.setCodigoUsuarioModificacion(rs.getString(8));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}

				try 
				{
					usuarioPerfilesDTO.setIpModificacion(rs.getString(9));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}

				try 
				{
					usuarioPerfilesDTO.setFechaModificacion(rs.getDate(10));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}

				usuarioPerfilesDTO.setEstado(new EstadoDTO());

				try 
				{
					usuarioPerfilesDTO.getEstado().setId(rs.getInt(11));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}

				try {
					usuarioPerfilesDTO.getEstado().setDescripcionEstado(rs.getString(12));

				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}
						
				try 
				{
					usuarioPerfilesDTO.getUsuario().setDescripcionUsuario(rs.getString(13));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}
				
				try 
				{
					usuarioPerfilesDTO.getPerfil().setDescripcionPerfil(rs.getString(14));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				usuarioPerfilesDTO.getUsuario().setArea(new AreaDTO());
				try 
				{
					usuarioPerfilesDTO.getUsuario().getArea().setId(rs.getInt(15));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				
				try 
				{
					usuarioPerfilesDTO.getUsuario().getArea().setDescripcionArea(rs.getString(16));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				listaUsuarioPerfiles.add(usuarioPerfilesDTO);
			}
			rs.close();
		}
		catch (NoResultException nre) 
		{
			listaUsuarioPerfiles = null;
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

		return listaUsuarioPerfiles;

	}


	
}
