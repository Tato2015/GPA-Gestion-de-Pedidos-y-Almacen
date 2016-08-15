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
import pe.gob.servir.gpa.adm.dao.ProgramacionAreasDAOLocal;
import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class ProgramacionAreasDAOImpl implements ProgramacionAreasDAOLocal{


	public ProgramacionAreasDAOImpl() {}
			
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
			
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(ProgramacionAreasDAOImpl.class);

	public ProgramacionAreasDTO registrarProgramacionAreas(
			ProgramacionAreasDTO programacionAreasDTO) throws Exception 
	{
	Connection conn = null;
	CallableStatement call = null;
	
	try 
	{
		InitialContext ic = new InitialContext();
		DataSource ds = (DataSource) ic.lookup(propGPA
				.getString(ConstanteEJB.CONEXION_SERVIR));
		conn = ds.getConnection();
		
		//7 parámetros
		String sql = "begin PKG_PROGRAMACION_AREAS.SP_REGISTRAR_PROGRMACION_AREAS(?,?,?,?,?,?,?); end;";
		call = conn.prepareCall(sql);
		
		if(programacionAreasDTO.getProgramacion().getId()!= null && programacionAreasDTO.getProgramacion().getId()>=0)
		{
			call.setInt(1, programacionAreasDTO.getProgramacion().getId());
		}else
		{
			call.setNull(1,OracleTypes.NULL);
		}
		
		if(programacionAreasDTO.getArea().getId()!=null && programacionAreasDTO.getArea().getId()>=0)
		{
			call.setInt(2, programacionAreasDTO.getArea().getId());
		}else
		{
			call.setNull(2,OracleTypes.NULL);
		}
		
		if(programacionAreasDTO.getIndicadorActivo()!=null && programacionAreasDTO.getIndicadorActivo()>=0)
		{
			call.setInt(3, programacionAreasDTO.getIndicadorActivo());
		}else
		{
			call.setNull(3,OracleTypes.NULL);
		}
		
		if(programacionAreasDTO.getCodigoUsuarioRegistro()!=null && !programacionAreasDTO.getCodigoUsuarioRegistro().equals(""))
		{
			call.setString(4, programacionAreasDTO.getCodigoUsuarioRegistro());
		}else
		{
			call.setNull(4,OracleTypes.NULL);
		}
		
		if(programacionAreasDTO.getIpRegistro()!=null && !programacionAreasDTO.getIpRegistro().equals(""))
		{
			call.setString(5, programacionAreasDTO.getIpRegistro());
		}else
		{
			call.setNull(5,OracleTypes.NULL);
		}
				
		if(programacionAreasDTO.getEstado().getId() !=null && programacionAreasDTO.getEstado().getId()>=0)
		{
			call.setInt(6, programacionAreasDTO.getEstado().getId());
		}else
		{
			call.setNull(6,OracleTypes.NULL);
		}
		call.registerOutParameter(7, OracleTypes.INTEGER);
		call.execute();
		Integer identificador=(Integer)call.getObject(7);
		try 
		{
			programacionAreasDTO.setId(identificador.intValue());
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		
	}catch (NoResultException nre) 
	{
		programacionAreasDTO = null;
		log.error(nre.getMessage(), nre);

	} catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	} finally 
	{
		try 
		{
			conn.close();
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
	}
		
	return programacionAreasDTO;
	}

	
	public List<ProgramacionAreasDTO> buscarProgramacionAreas(
			Integer programacionId, Integer areaId) throws Exception 
	{
		List<ProgramacionAreasDTO>listaProgramacionAreas= null;
		ProgramacionAreasDTO programacionAreasDTO=null;
		Connection conn = null;
		CallableStatement call = null;
		
		try 
		{
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();
			String sql = "begin PKG_PROGRAMACION_AREAS.SP_BUSCAR_PROGRAMACION_AREAS(?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if(programacionId != null && programacionId>0)
			{
				call.setInt(1,programacionId);
			}else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(areaId != null && areaId >0)
			{
				call.setInt(2,areaId );
			}else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			call.registerOutParameter(3,OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet)call.getObject(3);
			listaProgramacionAreas=new ArrayList<ProgramacionAreasDTO>();
			
			while(rs.next())
			{
				programacionAreasDTO = new ProgramacionAreasDTO();
				
				try 
				{
					programacionAreasDTO.setId(rs.getInt(1));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				programacionAreasDTO.setProgramacion(new ProgramacionDTO());
				try 
				{
					programacionAreasDTO.getProgramacion().setId(rs.getInt(2));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				programacionAreasDTO.setArea(new AreaDTO());
				try 
				{
					programacionAreasDTO.getArea().setId(rs.getInt(3));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				try 
				{
					programacionAreasDTO.setAnio(rs.getInt(4));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				
				try 
				{
					programacionAreasDTO.setIndicadorActivo(rs.getShort(5));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				try 
				{
					programacionAreasDTO.setCodigoUsuarioRegistro(rs.getString(6));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				try 
				{
					programacionAreasDTO.setIpRegistro(rs.getString(7));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				try 
				{
					programacionAreasDTO.getProgramacion().setFechaRegistro(rs.getDate(8));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				try 
				{
					programacionAreasDTO.setCodigoUsuarioModificacion(rs.getString(9));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				
				try 
				{
					programacionAreasDTO.setIpModificacion(rs.getString(10));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				try 
				{
					programacionAreasDTO.setFechaModificacion(rs.getDate(11));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				programacionAreasDTO.setEstado(new EstadoDTO());
				try 
				{
					programacionAreasDTO.getEstado().setId(rs.getInt(12));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				try 
				{
					programacionAreasDTO.getArea().setDescripcionArea(rs.getString(14));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);					
				}
				
				
				
				listaProgramacionAreas.add(programacionAreasDTO);				
				
			}
			rs.close();
			
			
		} catch (NoResultException nre) 
		{
			listaProgramacionAreas = null;
			log.error(nre.getMessage(), nre);
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		} finally 
		{
			try 
			{
				conn.close();
			} catch (Exception e) 
			{
				log.error(e.getMessage(), e);
			}
		}
	
		return listaProgramacionAreas; 

	}


	public Boolean actualizarProgramacionAreas(
			ProgramacionAreasDTO programacionAreasDTO) throws Exception {
		Connection conn = null;
		CallableStatement call = null;
		try 
		{
			InitialContext ic = new InitialContext();			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			String sql = "begin PKG_PROGRAMACION_AREAS.SP_ACTUALIZAR_PROGR_AREAS (?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if(programacionAreasDTO.getId() != null && programacionAreasDTO.getId()>=0)
			{
				call.setInt(1,programacionAreasDTO.getId());
			}
			else
			{
				call.setNull(1, OracleTypes.NULL);
			}
			
			if(programacionAreasDTO.getProgramacion().getId()!=null && programacionAreasDTO.getProgramacion().getId()>=0)
			{
				call.setInt(2,programacionAreasDTO.getProgramacion().getId());
			}
			else
			{
				call.setNull(2, OracleTypes.NULL);
			}
		
			if(programacionAreasDTO.getArea().getId()!=null && programacionAreasDTO.getArea().getId()>=0)
			{
				call.setInt(3,programacionAreasDTO.getArea().getId());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(programacionAreasDTO.getIndicadorActivo()!=null && programacionAreasDTO.getIndicadorActivo()>=0)
			{
				call.setInt(4,programacionAreasDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);	
			}
			
			if(programacionAreasDTO.getCodigoUsuarioModificacion()!=null && !programacionAreasDTO.getCodigoUsuarioModificacion().equals(""))
			{
				call.setString(5,programacionAreasDTO.getCodigoUsuarioModificacion());
			}			
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
						
			if(programacionAreasDTO.getIpModificacion()!=null &&  !programacionAreasDTO.getIpModificacion().equals(""))
			{
				call.setString(6,programacionAreasDTO.getIpModificacion());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(programacionAreasDTO.getEstado().getId()!=null && programacionAreasDTO.getEstado().getId()>=0)
			{
				call.setInt(7,programacionAreasDTO.getEstado().getId());
			}
			else
			{
				call.setNull(7, OracleTypes.NULL);
			}
			
			call.registerOutParameter(8,OracleTypes.INTEGER);
			call.execute();
		} 
		catch (NoResultException nre) 
		{
			programacionAreasDTO = null;
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


	public Boolean eliminarProgramacionAreas(Integer programacionAreaId)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try 
		{
			InitialContext ic = new InitialContext();			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			String sql = "begin PKG_PROGRAMACION_AREAS.SP_ELIMINAR_PROGR_AREAS(?,?); end;";
			call = conn.prepareCall(sql);
			
			if(programacionAreaId!=null && programacionAreaId>=0)
			{
				call.setInt(1,programacionAreaId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			call.registerOutParameter(2,OracleTypes.INTEGER);
			call.execute();		
		}
		catch (NoResultException nre) 
		{		
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

