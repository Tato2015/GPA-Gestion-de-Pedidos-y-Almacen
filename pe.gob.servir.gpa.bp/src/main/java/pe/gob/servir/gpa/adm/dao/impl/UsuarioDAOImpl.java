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
import pe.gob.servir.gpa.adm.dao.UsuarioDAOLocal;
import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.UsuarioDTO;
import pe.gob.servir.gpa.model.dto.PersonaDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class UsuarioDAOImpl implements UsuarioDAOLocal {

	/** Metodo Constructor */
	public UsuarioDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(UsuarioDAOImpl.class);
	
	public List<UsuarioDTO> buscarUsuario(Integer idUsuario,
			String descripcionUsuario, String claveUsuario) throws Exception 
	{
		List<UsuarioDTO> listaUsuario = null;
		UsuarioDTO usuarioDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_USUARIO.SP_BUSCAR_USUARIO(?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if (idUsuario != null && idUsuario >= 0) 
			{
				call.setInt(1,idUsuario);
			} else 
			{
				call.setNull(1, OracleTypes.NULL);
			}
			
			if (descripcionUsuario!= null && !descripcionUsuario.equals("")) 
			{
				call.setString(2,descripcionUsuario);
			} else 
			{
				call.setNull(2, OracleTypes.NULL);
			}
		
			if (claveUsuario != null && !claveUsuario.equals("")) 
			{
				call.setString(3,claveUsuario);
			} else 
			{
				call.setString(3, claveUsuario);
			}
		
			call.registerOutParameter(4, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(4);
			listaUsuario= new ArrayList<UsuarioDTO>();

			while (rs.next()) 
			{
				usuarioDTO = new UsuarioDTO();				

				try 
				{
					usuarioDTO.setId(rs.getInt(1));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				usuarioDTO.setArea(new AreaDTO());
				try 
				{
					usuarioDTO.getArea().setId(rs.getInt(2));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				usuarioDTO.setPersona(new PersonaDTO());
				try 
				{
					usuarioDTO.getPersona().setId(rs.getInt(3));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setCodigoUsuario(rs.getString(4));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setClaveUsuario(rs.getString(5));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setDescripcionUsuario(rs.getString(6));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setIndicadorActivo(rs.getShort(7));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setCodigoUsuarioRegistro(rs.getString(8));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setIpRegistro(rs.getString(9));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setFechaRegistro(rs.getDate(10));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);												
				}
				
				try 
				{
					usuarioDTO.setCodigoUsuarioModificacion(rs.getString(11));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
								
				try 
				{
					usuarioDTO.setIpModificacion(rs.getString(12));					
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setFechaModificacion(rs.getDate(13));
				} 
				catch (Exception e)
				{
					log.error(e.getMessage(), e);				
				}
				
				usuarioDTO.setEstado(new EstadoDTO());
				try 
				{
					usuarioDTO.getEstado().setId(rs.getInt(14));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.getArea().setDescripcionArea(rs.getString(15));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}				
				listaUsuario.add(usuarioDTO);								
			}			
			rs.close();
		} catch (NoResultException nre) {
			listaUsuario= null;
			log.error(nre.getMessage(), nre);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return listaUsuario;
	}

	public UsuarioDTO buscarLoginUsuario(Integer idUsuario,
			String codigoUsuario, String claveUsuario, Short indicadorActivo)
			throws Exception 
	{		
		UsuarioDTO usuarioDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try 
		{
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_USUARIO.SP_BUSCAR_LOGIN_USUARIO(?,?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if (idUsuario != null && idUsuario >= 0) 
			{
				call.setInt(1,idUsuario);
			} else 
			{
				call.setNull(1, OracleTypes.NULL);
			}
			
			if (codigoUsuario!= null && !codigoUsuario.equals("")) 
			{
				call.setString(2,codigoUsuario);
			} else 
			{
				call.setNull(2, OracleTypes.NULL);
			}
		
			if (claveUsuario != null && !claveUsuario.equals("")) 
			{
				call.setString(3,claveUsuario);
			} else 
			{
				call.setString(3, claveUsuario);
			}
			
			if (indicadorActivo!= null && indicadorActivo>=0) 
			{
				call.setShort(4,indicadorActivo);
			} else 
			{
				call.setShort(4, indicadorActivo);
			}
		
			call.registerOutParameter(5, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(5);	
			
			if(rs == null)
			{
				return null;
			}

			while (rs.next()) 
			{
				usuarioDTO = new UsuarioDTO();				

				try 
				{
					usuarioDTO.setId(rs.getInt(1));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				usuarioDTO.setArea(new AreaDTO());
				try 
				{
					usuarioDTO.getArea().setId(rs.getInt(2));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				usuarioDTO.setPersona(new PersonaDTO());
				try 
				{
					usuarioDTO.getPersona().setId(rs.getInt(3));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setCodigoUsuario(rs.getString(4));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setClaveUsuario(rs.getString(5));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setDescripcionUsuario(rs.getString(6));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setIndicadorActivo(rs.getShort(7));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setCodigoUsuarioRegistro(rs.getString(8));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setIpRegistro(rs.getString(9));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setFechaRegistro(rs.getDate(10));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);												
				}
				
				try 
				{
					usuarioDTO.setCodigoUsuarioModificacion(rs.getString(11));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
								
				try 
				{
					usuarioDTO.setIpModificacion(rs.getString(12));					
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.setFechaModificacion(rs.getDate(13));
				} 
				catch (Exception e)
				{
					log.error(e.getMessage(), e);				
				}
				
				usuarioDTO.setEstado(new EstadoDTO());
				try 
				{
					usuarioDTO.getEstado().setId(rs.getInt(14));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
				try 
				{
					usuarioDTO.getArea().setDescripcionArea(rs.getString(15));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}				
//				listaUsuario.add(usuarioDTO);								
			}			
			rs.close();
		} catch (NoResultException nre) {
//			listaUsuario= null;
			log.error(nre.getMessage(), nre);
			return null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return usuarioDTO;

	}
}