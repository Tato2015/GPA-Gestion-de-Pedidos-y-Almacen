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
import pe.gob.servir.gpa.adm.dao.ProgramacionDAOLocal;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class ProgramacionDAOImpl implements ProgramacionDAOLocal{


	public ProgramacionDAOImpl() {}
			
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
			
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(ProgramacionDAOImpl.class);

	//Método que obtiene la lista de programaciones.
	public List<ProgramacionDTO> buscarProgramacion(Integer anio,
			Integer aprobacion, Integer estado, Integer tipoPedido,
			Integer tipoPeriodo)
			throws Exception 
		{
		List<ProgramacionDTO> listaProgramacion=null;
		ProgramacionDTO programacion=null;
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			String sql = "begin PKG_PROGRAMACION.SP_BUSCAR_PROGRAMACION(?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
						
			if (anio != null && anio!=0) 
			{
			call.setInt(1,anio);
			} else 
			{
				call.setNull(1, OracleTypes.NULL);
			}
			
			if (aprobacion != null && aprobacion !=0) 
			{
				call.setInt(2, aprobacion);
				
			} else 
			{
				call.setNull(2, OracleTypes.NULL);
			}				
			
			if (estado != null && estado !=0 ) 
			{
				call.setInt(3, estado);
				
			} else 
			{
				call.setNull(3, OracleTypes.NULL);
			}
			
			if (tipoPedido != null && tipoPedido !=0) 
			{
				call.setInt(4, tipoPedido);
			} else {
				call.setNull(4, OracleTypes.NULL);
			}
			
			if (tipoPeriodo != null && tipoPeriodo!=0) 
			{
				call.setInt(5, tipoPeriodo);
			} else {
				call.setNull(5, OracleTypes.NULL);
			}						
			call.registerOutParameter(6, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(6);
			listaProgramacion=new ArrayList<ProgramacionDTO>();
			
			while (rs.next()) 
			{
				programacion = new ProgramacionDTO();				
				try 
				{
					programacion.setId(rs.getInt(1));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setDescripcionProgramacion(rs.getString(2));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				programacion.setTipoPedido(new TipoDTO());
				try 
				{
					programacion.getTipoPedido().setId(rs.getInt(3));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				programacion.setTipoPeriodo(new TipoDTO());
				try {
					programacion.getTipoPeriodo().setId(rs.getInt(4));					
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try {
					programacion.setFechaInicioRegistro(rs.getDate(5));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try {
					programacion.setFechaFinRegistro(rs.getDate(6));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setIndicadorAprobacion(rs.getInt(7));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
								
				try 
				{
					programacion.setIndicadorActivo(rs.getShort(8));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
										
				try {
					programacion.setCodigoUsuarioRegistro(rs.getString(9));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setIpRegistro(rs.getString(10));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setFechaRegistro(rs.getDate(11));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setCodigoUsuarioModificacion(rs.getString(12));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setIpModificacion(rs.getString(13));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setFechaModificacion(rs.getDate(14));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				programacion.setEstado(new EstadoDTO());
				try 
				{
					programacion.getEstado().setId(rs.getInt(15));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setMesesDuracionPeriodo(rs.getInt(16));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.getEstado().setDescripcionEstado(rs.getString(17));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.getTipoPedido().setDescripcionTipo(rs.getString(18));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.getTipoPeriodo().setDescripcionTipo(rs.getString(19));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				
				listaProgramacion.add(programacion);
				
			}
			rs.close();
			
		} catch (NoResultException nre) 
		{
			listaProgramacion = null;
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
		return listaProgramacion;
	}

	public ProgramacionDTO registrarProgramacion(ProgramacionDTO programacionDTO)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		
		try 
		{
			InitialContext it = new InitialContext();
			DataSource ds = (DataSource)it.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			
			String sql="begin PKG_PROGRAMACION.SP_REGISTRAR_PROGRAMACION(?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if(programacionDTO.getDescripcionProgramacion() != null && !programacionDTO.getDescripcionProgramacion().equals("") )
			{
				call.setString(1, programacionDTO.getDescripcionProgramacion());
			}else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(programacionDTO.getTipoPedido().getId()!=null && programacionDTO.getTipoPedido().getId()>=0)
			{
				call.setInt(2, programacionDTO.getTipoPedido().getId());
			}else
			{
				call.setNull(2,OracleTypes.NULL);				
			}

			if(programacionDTO.getTipoPeriodo().getId()!=null && programacionDTO.getTipoPeriodo().getId()>=0)
			{
				call.setInt(3, programacionDTO.getTipoPeriodo().getId());
			}else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(programacionDTO.getFechaInicioRegistro()!=null)
			{
				call.setDate(4, new java.sql.Date(programacionDTO.getFechaInicioRegistro().getTime()));
			}else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(programacionDTO.getFechaFinRegistro()!=null)
			{
				call.setDate(5, new java.sql.Date(programacionDTO.getFechaFinRegistro().getTime()));
			}else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(programacionDTO.getIndicadorAprobacion()!=null && programacionDTO.getIndicadorAprobacion()>=0)
			{
				call.setInt(6, programacionDTO.getIndicadorAprobacion());
			}else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(programacionDTO.getIndicadorActivo()!=null && programacionDTO.getIndicadorActivo()>=0)
			{
				call.setInt(7, programacionDTO.getIndicadorAprobacion());
			}else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			
			if(programacionDTO.getCodigoUsuarioRegistro()!=null && !programacionDTO.getCodigoUsuarioRegistro().equals(""))
			{
				call.setString(8, programacionDTO.getCodigoUsuarioRegistro());
			}else
			{
				call.setNull(8,OracleTypes.NULL);
			}
			
			if(programacionDTO.getIpRegistro()!=null && !programacionDTO.getIpRegistro().equals(""))
			{
				call.setString(9, programacionDTO.getIpRegistro());
			}else
			{
				call.setNull(9,OracleTypes.NULL);				
			}
			
			if(programacionDTO.getEstado().getId()!= null && programacionDTO.getEstado().getId()>=0)
			{
				call.setInt(10, programacionDTO.getEstado().getId());
			}else
			{
				call.setNull(10,OracleTypes.NULL);
			}
			
			if(programacionDTO.getMesesDuracionPeriodo()!=null && programacionDTO.getMesesDuracionPeriodo()>=0)
			{
				call.setInt(11, programacionDTO.getMesesDuracionPeriodo());
			}else
			{
				call.setNull(11,OracleTypes.NULL);				
			}
			call.registerOutParameter(12, OracleTypes.INTEGER);
			call.execute();
			Integer identificador=(Integer)call.getObject(12);
			
			try 
			{
				programacionDTO.setId(identificador.intValue());
			} catch (Exception e) 
			{
				log.error(e.getMessage(), e);
			}
			
		} catch (NoResultException nre) 
		{
			programacionDTO = null;
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
		return programacionDTO;
	}

	public Boolean actualizarProgramacion(ProgramacionDTO programacionDTO)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource)ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			String sql ="begin PKG_PROGRAMACION.SP_ACTUALIZAR_PROGRAMACION(?,?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			call.setInt(1,programacionDTO.getId());
			
			if(programacionDTO.getDescripcionProgramacion()!=null && !programacionDTO.getDescripcionProgramacion().equals(""))
			{
				call.setString(2, programacionDTO.getDescripcionProgramacion());
			}else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(programacionDTO.getTipoPedido().getId()!=null && programacionDTO.getTipoPedido().getId()>=0)
			{
				call.setInt(3, programacionDTO.getTipoPedido().getId());
			}else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(programacionDTO.getTipoPeriodo().getId()!=null && programacionDTO.getTipoPeriodo().getId()>=0)
			{
				call.setInt(4,programacionDTO.getTipoPeriodo().getId());
			}else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(programacionDTO.getFechaInicioRegistro()!=null)
			{
				call.setDate(5,new java.sql.Date(programacionDTO.getFechaInicioRegistro().getTime()));
			}else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(programacionDTO.getFechaFinRegistro()!=null)
			{
				call.setDate(6,new java.sql.Date(programacionDTO.getFechaFinRegistro().getTime()));
			}else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(programacionDTO.getIndicadorAprobacion()!=null && programacionDTO.getIndicadorAprobacion()>=0)
			{
				call.setInt(7,programacionDTO.getIndicadorAprobacion());
			}else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			
			if(programacionDTO.getIndicadorActivo()!=null && programacionDTO.getIndicadorActivo()>=0)
			{
				call.setInt(8, programacionDTO.getIndicadorActivo());
			}else
			{
				call.setNull(8,OracleTypes.NULL);
			}
			
			if(programacionDTO.getCodigoUsuarioModificacion()!=null && !programacionDTO.getCodigoUsuarioModificacion().equals(""))
			{
				call.setString(9,programacionDTO.getCodigoUsuarioModificacion());
			}else
			{
				call.setNull(9,OracleTypes.NULL);
			}
			
			if(programacionDTO.getIpModificacion()!=null && !programacionDTO.getIpModificacion().equals(""))
			{
				call.setString(10,programacionDTO.getIpModificacion());
			}else
			{
				call.setNull(10,OracleTypes.NULL);
			}
			
			if(programacionDTO.getEstado().getId()!=null && programacionDTO.getEstado().getId()>=0)
			{
				call.setInt(11,programacionDTO.getEstado().getId());
			}else
			{
				call.setNull(11,OracleTypes.NULL);
			}
			
			if(programacionDTO.getMesesDuracionPeriodo()!=null && programacionDTO.getMesesDuracionPeriodo()>=0)
			{
				call.setInt(12,programacionDTO.getMesesDuracionPeriodo());
			}else
			{
				call.setNull(12,OracleTypes.NULL);
			}
			call.registerOutParameter(13, OracleTypes.INTEGER);
			call.execute();						
		} catch (NoResultException nre) 
		{
			programacionDTO = null;
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
		return true;				
	}

	public List<ProgramacionDTO> buscarProgramacionXMesActual(Integer mesActual) 
	{
		List<ProgramacionDTO> listaProgramacion=null;
		ProgramacionDTO programacion=null;
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			String sql = "begin PKG_PROGRAMACION.SP_BUSCAR_PROGR_XMESACTUAL(?,?); end;";
			call = conn.prepareCall(sql);
						
			if (mesActual!= null && mesActual>=0) 
			{
			call.setInt(1,mesActual);
			} else 
			{
				call.setNull(1, OracleTypes.NULL);
			}			
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(2);
			listaProgramacion=new ArrayList<ProgramacionDTO>();
			
			while (rs.next()) 
			{
				programacion = new ProgramacionDTO();				
				try 
				{
					programacion.setId(rs.getInt(1));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setDescripcionProgramacion(rs.getString(2));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				programacion.setTipoPedido(new TipoDTO());
				try 
				{
					programacion.getTipoPedido().setId(rs.getInt(3));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				programacion.setTipoPeriodo(new TipoDTO());
				try 
				{
					programacion.getTipoPeriodo().setId(rs.getInt(4));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setFechaInicioRegistro(rs.getDate(5));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.setFechaFinRegistro(rs.getDate(6));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
								
				try 
				{
					programacion.getTipoPedido().setDescripcionTipo(rs.getString(18));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					programacion.getTipoPeriodo().setDescripcionTipo(rs.getString(19));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}						
				listaProgramacion.add(programacion);				
			}
			rs.close();
			
		} catch (NoResultException nre) 
		{
			listaProgramacion = null;
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
		return listaProgramacion;

	}

	

}
