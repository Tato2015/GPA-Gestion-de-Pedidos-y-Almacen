/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li></ul>
 * Objeto		: UsuarioRolDAOImpl.java
 * Descripcion	: Clase tipo interfaces de UsuarioRol que 
 * 				  persiste con la base de datos.
 * Autor		: Consultora  - P & P BMS (P & P Business Management Solutions)
 * ----------------------------------------------------------------------------
 * Modificaciones
 * Codigo	Fecha		Nombre			Descripcion
 * ----------------------------------------------------------------------------
 * 
 */

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

import pe.gob.servir.gpa.adm.dao.DetalleProgramacionAreaProductoDAOLocal;
import pe.gob.servir.gpa.model.dto.DetalleProgramacionAreaProductoDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProductoDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class DetalleProgramacionAreaProductoDAOImpl implements DetalleProgramacionAreaProductoDAOLocal 
{

	/** Metodo Constructor */
	public DetalleProgramacionAreaProductoDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(DetalleProgramacionAreaProductoDAOImpl.class);

	public DetalleProgramacionAreaProductoDTO registrarDetalleProgramacionAreaProducto(
			DetalleProgramacionAreaProductoDTO detalleProgramacionAreaProductoDTO)
			throws Exception 
	{
	Connection conn = null;
	CallableStatement call = null;
	
	try 
	{
		InitialContext ic = new InitialContext();
		DataSource ds = (DataSource) ic.lookup(propGPA
				.getString(ConstanteEJB.CONEXION_SERVIR));
		conn = ds.getConnection();
		
		//8 parametros
		String sql = "begin PKG_DETA_PROG_AREA_PROD.SP_REGISTRAR_DET_PRO_ARE_PRO(?,?,?,?,?,?,?,?); end;";
		call = conn.prepareCall(sql);
		
		if(detalleProgramacionAreaProductoDTO.getProgramacionAreas().getId() != null && detalleProgramacionAreaProductoDTO.getProgramacionAreas().getId() >= 0)
		{
			call.setInt(1, detalleProgramacionAreaProductoDTO.getProgramacionAreas().getId());
		}else
		{
			call.setNull(1,OracleTypes.NULL);
		}
		
		if(detalleProgramacionAreaProductoDTO.getProductoDisponible().getId() != null && detalleProgramacionAreaProductoDTO.getProductoDisponible().getId()>=0)
		{
			call.setInt(2,detalleProgramacionAreaProductoDTO.getProductoDisponible().getId());
		}else
		{
			call.setNull(2,OracleTypes.NULL);
		}
		
		if(detalleProgramacionAreaProductoDTO.getStockDisponible() != null && detalleProgramacionAreaProductoDTO.getStockDisponible()>=0)
		{
			call.setInt(3,detalleProgramacionAreaProductoDTO.getStockDisponible());
		}else
		{
			call.setNull(3,OracleTypes.NULL);
		}
		
		if(detalleProgramacionAreaProductoDTO.getIndicadorActivo() != null && detalleProgramacionAreaProductoDTO.getIndicadorActivo()>=0)
		{
			call.setInt(4,detalleProgramacionAreaProductoDTO.getIndicadorActivo());
		}else
		{
			call.setNull(4,OracleTypes.NULL);
		}
	
		if(detalleProgramacionAreaProductoDTO.getCodigoUsuarioRegistro()!=null && !detalleProgramacionAreaProductoDTO.getCodigoUsuarioRegistro().equals(""))
		{
			call.setString(5,detalleProgramacionAreaProductoDTO.getCodigoUsuarioRegistro());
		}else
		{
			call.setNull(5,OracleTypes.NULL);	
		}
		
		if(detalleProgramacionAreaProductoDTO.getIpRegistro()!=null && !detalleProgramacionAreaProductoDTO.getIpRegistro().equals(""))
		{
			call.setString(6,detalleProgramacionAreaProductoDTO.getIpRegistro());
		}else
		{
			call.setNull(6,OracleTypes.NULL);
		}
		
		if(detalleProgramacionAreaProductoDTO.getEstado().getId()!=null && detalleProgramacionAreaProductoDTO.getEstado().getId()>=0)
		{
			call.setInt(7,detalleProgramacionAreaProductoDTO.getEstado().getId());
		}else
		{
			call.setNull(7,OracleTypes.NULL);
		}

		call.registerOutParameter(8, OracleTypes.INTEGER);
		call.execute();
		Integer identificador=(Integer)call.getObject(8);
			try 
			{
				detalleProgramacionAreaProductoDTO.setId(identificador.intValue());
			} catch (Exception e) 
			{
				log.error(e.getMessage(), e);
			}
		
	} catch (NoResultException nre) 
	{
		detalleProgramacionAreaProductoDTO = null;
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
	return detalleProgramacionAreaProductoDTO;
	}

	public List<DetalleProgramacionAreaProductoDTO> buscarDetalleProgramacionAreaProducto(
			Integer programacionAreasId,Integer productoDisponibleId)
			throws Exception 
	{
		List<DetalleProgramacionAreaProductoDTO>listaDetalleProgramacionAreaProducto = null;
		DetalleProgramacionAreaProductoDTO detalleProgramacionAreaProductoDTO=null;
		Connection conn = null;
		CallableStatement call = null;
				
		try 
		{
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();
			String sql ="begin PKG_DETA_PROG_AREA_PROD.SP_BUSCAR_DET_PRO_ARE_PRO(?,?,?);end;";
			call = conn.prepareCall(sql);
			
			if(programacionAreasId!= null && programacionAreasId>0)
			{
				call.setInt(1,programacionAreasId);
			}else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(productoDisponibleId!= null && productoDisponibleId>0)
			{
				call.setInt(2,productoDisponibleId);
			}else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet)call.getObject(3);
			listaDetalleProgramacionAreaProducto = new ArrayList<DetalleProgramacionAreaProductoDTO>();
			
			while(rs.next())
			{
				detalleProgramacionAreaProductoDTO = new DetalleProgramacionAreaProductoDTO();
					
				try 
				{
					detalleProgramacionAreaProductoDTO.setId(rs.getInt(1));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}	
				
				detalleProgramacionAreaProductoDTO.setProgramacionAreas(new ProgramacionAreasDTO());				
				try 
				{
					detalleProgramacionAreaProductoDTO.getProgramacionAreas().setId(rs.getInt(2));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				detalleProgramacionAreaProductoDTO.setProductoDisponible(new ProductoDTO());
				try 
				{
					detalleProgramacionAreaProductoDTO.getProductoDisponible().setId(rs.getInt(3));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					detalleProgramacionAreaProductoDTO.setStockDisponible(rs.getInt(4));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					detalleProgramacionAreaProductoDTO.setIndicadorActivo(rs.getShort(5));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					detalleProgramacionAreaProductoDTO.setCodigoUsuarioRegistro(rs.getString(6));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					detalleProgramacionAreaProductoDTO.setIpRegistro(rs.getString(7));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					detalleProgramacionAreaProductoDTO.setFechaRegistro(rs.getDate(8));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					detalleProgramacionAreaProductoDTO.setCodigoUsuarioModificacion(rs.getString(9));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					detalleProgramacionAreaProductoDTO.setIpModificacion(rs.getString(10));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					detalleProgramacionAreaProductoDTO.setFechaModificacion(rs.getDate(11));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				detalleProgramacionAreaProductoDTO.setEstado(new EstadoDTO());
				try 
				{
					detalleProgramacionAreaProductoDTO.getEstado().setId(rs.getInt(12));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				try 
				{
					detalleProgramacionAreaProductoDTO.getProductoDisponible().setDescripcionProducto(rs.getString(15));
				} catch (Exception e) 
				{
					log.error(e.getMessage(),e);
				}
				
				listaDetalleProgramacionAreaProducto.add(detalleProgramacionAreaProductoDTO);				
			}
			rs.close();
			
			
		} catch (NoResultException nre) 
		{
			listaDetalleProgramacionAreaProducto= null;
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

		return listaDetalleProgramacionAreaProducto;
	}

	public Boolean actualizarDetalleProgramacionAreaProducto(
			DetalleProgramacionAreaProductoDTO detalleProgramacionAreaProductoDTO)
			throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try 
		{
			InitialContext ic = new InitialContext();			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			String sql = "begin PKG_DETA_PROG_AREA_PROD.SP_ACTUA_DET_PRO_ARE_PRO(?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if(detalleProgramacionAreaProductoDTO.getId()!=null && detalleProgramacionAreaProductoDTO.getId()>=0)
			{
				call.setInt(1,detalleProgramacionAreaProductoDTO.getId());
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(detalleProgramacionAreaProductoDTO.getProgramacionAreas().getId()!=null && detalleProgramacionAreaProductoDTO.getProgramacionAreas().getId()>=0)
			{
				call.setInt(2,detalleProgramacionAreaProductoDTO.getProgramacionAreas().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(detalleProgramacionAreaProductoDTO.getProductoDisponible().getId()!=null && detalleProgramacionAreaProductoDTO.getProductoDisponible().getId()>=0)
			{
				call.setInt(3,detalleProgramacionAreaProductoDTO.getProductoDisponible().getId());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(detalleProgramacionAreaProductoDTO.getStockDisponible()!=null && detalleProgramacionAreaProductoDTO.getStockDisponible()>=0)
			{
				call.setInt(4, detalleProgramacionAreaProductoDTO.getStockDisponible());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if(detalleProgramacionAreaProductoDTO.getIndicadorActivo()!=null && detalleProgramacionAreaProductoDTO.getIndicadorActivo()>=0)
			{
				call.setInt(5,detalleProgramacionAreaProductoDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(detalleProgramacionAreaProductoDTO.getCodigoUsuarioModificacion()!=null && !detalleProgramacionAreaProductoDTO.getCodigoUsuarioModificacion().equals(""))
			{
				call.setString(6,detalleProgramacionAreaProductoDTO.getCodigoUsuarioModificacion());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(detalleProgramacionAreaProductoDTO.getIpModificacion()!=null && !detalleProgramacionAreaProductoDTO.getIpModificacion().equals(""))
			{
				call.setString(7,detalleProgramacionAreaProductoDTO.getIpModificacion());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}
			
			if(detalleProgramacionAreaProductoDTO.getEstado().getId()!=null && detalleProgramacionAreaProductoDTO.getEstado().getId()>=0)
			{
				call.setInt(8,detalleProgramacionAreaProductoDTO.getEstado().getId());
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
				detalleProgramacionAreaProductoDTO = null;
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

	public Boolean eliminarDetalleProgramacionAreaProducto(
			Integer detalleProgramacionAreaProductoId) throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;
		try 
		{
			InitialContext ic = new InitialContext();			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			String sql = "begin PKG_DETA_PROG_AREA_PROD.SP_ELIMINAR_DET_PRO_ARE_PRO(?,?); end;";
			call = conn.prepareCall(sql);
			
			if(detalleProgramacionAreaProductoId !=null && detalleProgramacionAreaProductoId >=0)
			{
				call.setInt(1,detalleProgramacionAreaProductoId);	
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			call.registerOutParameter(2, OracleTypes.INTEGER);
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
