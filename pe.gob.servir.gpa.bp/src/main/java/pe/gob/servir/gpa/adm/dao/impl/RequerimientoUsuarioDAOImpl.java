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

import pe.gob.servir.gpa.adm.dao.RequerimientoUsuarioDAOLocal;
import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoUsuarioDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.model.dto.UsuarioDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class RequerimientoUsuarioDAOImpl implements RequerimientoUsuarioDAOLocal
{

	public RequerimientoUsuarioDAOImpl() {}
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	
	final static Logger log = Logger.getLogger(RequerimientoUsuarioDAOImpl.class);

	public List<RequerimientoUsuarioDTO> buscarRequerimientoUsuarioDTO(
			Integer requerimientoUsuarioId,Integer requerimientoId,Integer usuarioId,Integer programacionAreasId,Integer estadoId,Short indicadorEnviadoAlEncargado,Short indicadorRequerimientoRecepcionEncargado) throws Exception 
	{
		List<RequerimientoUsuarioDTO> listaRequerimientoUsuario= null;
		RequerimientoUsuarioDTO requerimientoUsuarioDTO = null;		
		Connection conn = null;
		CallableStatement call = null;
		
		try 
		{
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_REQUERIMIENTO_USUARIO.SP_BUSCAR_REQUE_USUARIO(?,?,?,?,?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if(requerimientoUsuarioId!=null && requerimientoUsuarioId>=0)
			{
				call.setInt(1,requerimientoUsuarioId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			
			if(requerimientoId !=null && requerimientoId>=0)
			{
				call.setInt(2,requerimientoId);
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(usuarioId !=null && usuarioId>=0)
			{
				call.setInt(3,usuarioId);
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(programacionAreasId!=null && programacionAreasId>=0)
			{
				call.setInt(4,programacionAreasId);
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);
			}
			
			
			if(estadoId!=null && estadoId>=0)
			{
				call.setInt(5,estadoId);
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}

			if(indicadorEnviadoAlEncargado != null && indicadorEnviadoAlEncargado>=0)			
			{
				call.setShort(6,indicadorEnviadoAlEncargado);
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(indicadorRequerimientoRecepcionEncargado!=null && indicadorRequerimientoRecepcionEncargado>=0)
			{
				call.setShort(7,indicadorRequerimientoRecepcionEncargado);
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);
			}
						
			call.registerOutParameter(8, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(8);
			listaRequerimientoUsuario= new ArrayList<RequerimientoUsuarioDTO>();

			while(rs.next())
			{
				requerimientoUsuarioDTO = new RequerimientoUsuarioDTO();
				
				try 
				{				
					requerimientoUsuarioDTO.setId(rs.getInt(1));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
								
				requerimientoUsuarioDTO.setRequerimiento(new RequerimientoDTO());
				try 
				{				
					requerimientoUsuarioDTO.getRequerimiento().setId(rs.getInt(2));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				requerimientoUsuarioDTO.setUsuario(new UsuarioDTO());
				try 
				{				
					requerimientoUsuarioDTO.getUsuario().setId(rs.getInt(3));
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				requerimientoUsuarioDTO.setProgramacionAreas(new ProgramacionAreasDTO());
				try 
				{				
					requerimientoUsuarioDTO.getProgramacionAreas().setId(rs.getInt(4));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setIndicadorEnviadoAlEncargado(rs.getShort(5));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setFechaEnviadoAlEncargado((java.sql.Date)rs.getDate(6));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setIndicadorRequerimientoRecibidoDelEncargado(rs.getShort(7));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setFechaRequerimientoRecibidoDelEncargado(rs.getDate(8));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setIndicadorActivo(rs.getShort(9));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setCodigoUsuarioRegistro(rs.getString(10));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setIpRegistro(rs.getString(11));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setFechaRegistro(rs.getDate(12));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setCodigoUsuarioModificacion(rs.getString(13));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				
				try 
				{				
					requerimientoUsuarioDTO.setIpModificacion(rs.getString(14));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.setFechaModificacion(rs.getDate(15));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				requerimientoUsuarioDTO.setEstado(new EstadoDTO());
				try 
				{				
					requerimientoUsuarioDTO.getEstado().setId(rs.getInt(16));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}

				//Ya se inicializó arriba . -->requerimientoUsuarioDTO.setUsuario(new UsuarioDTO());
				try 
				{				
					requerimientoUsuarioDTO.getUsuario().setDescripcionUsuario(rs.getString(17));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}

				requerimientoUsuarioDTO.getProgramacionAreas().setArea(new AreaDTO());
				try 
				{				
					requerimientoUsuarioDTO.getProgramacionAreas().getArea().setId(rs.getInt(18));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.getProgramacionAreas().getArea().setDescripcionArea(rs.getString(19));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				requerimientoUsuarioDTO.getProgramacionAreas().setProgramacion(new ProgramacionDTO());
				try 
				{				
					requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().setId(rs.getInt(20));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().setDescripcionProgramacion(rs.getString(21));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().setTipoPedido(new TipoDTO());
				try 
				{				
					requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().getTipoPedido().setId(rs.getInt(22));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				try 
				{				
					requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().getTipoPedido().setDescripcionTipo(rs.getString(23));									
				}
				catch (Exception e) 
				{								
					log.error(e.getMessage(), e);
				}
				
				listaRequerimientoUsuario.add(requerimientoUsuarioDTO);
			}
			rs.close();

		}
		catch (NoResultException nre)
		{
			listaRequerimientoUsuario= null;
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

		return listaRequerimientoUsuario;

	}

	public RequerimientoUsuarioDTO registrarRequerimientoUsuarioDTO(
			RequerimientoUsuarioDTO requerimientoUsuarioDTO) throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();


			String sql = "begin PKG_REQUERIMIENTO_USUARIO.SP_REGISTRAR_REQUE_USUARIO(?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if (requerimientoUsuarioDTO.getRequerimiento().getId()!= null && requerimientoUsuarioDTO.getRequerimiento().getId()>=0) 
			{
				call.setInt(1, requerimientoUsuarioDTO.getRequerimiento().getId());
			}else{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(requerimientoUsuarioDTO.getUsuario().getId()!=null && requerimientoUsuarioDTO.getUsuario().getId()>=0)
			{
				call.setInt(2,requerimientoUsuarioDTO.getUsuario().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);	
			}
			
			if(requerimientoUsuarioDTO.getProgramacionAreas().getId()!=null && requerimientoUsuarioDTO.getProgramacionAreas().getId()>=0)
			{
				call.setInt(3,requerimientoUsuarioDTO.getProgramacionAreas().getId());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(requerimientoUsuarioDTO.getIndicadorEnviadoAlEncargado()!=null && requerimientoUsuarioDTO.getIndicadorEnviadoAlEncargado()>=0)
			{
				call.setShort(4,requerimientoUsuarioDTO.getIndicadorEnviadoAlEncargado());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);	
			}
			
			if(requerimientoUsuarioDTO.getFechaEnviadoAlEncargado()!=null)
			{
				call.setDate(5, requerimientoUsuarioDTO.getFechaEnviadoAlEncargado());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(requerimientoUsuarioDTO.getIndicadorRequerimientoRecibidoDelEncargado()!=null && requerimientoUsuarioDTO.getIndicadorRequerimientoRecibidoDelEncargado()>=0)
			{
				call.setShort(6,requerimientoUsuarioDTO.getIndicadorRequerimientoRecibidoDelEncargado());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if(requerimientoUsuarioDTO.getFechaRequerimientoRecibidoDelEncargado()!=null)
			{
				call.setDate(7,requerimientoUsuarioDTO.getFechaRequerimientoRecibidoDelEncargado());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);	
			}
			
			if(requerimientoUsuarioDTO.getIndicadorActivo()!=null && requerimientoUsuarioDTO.getIndicadorActivo()>=0)
			{				
				call.setShort(8,requerimientoUsuarioDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);
			}
			
			if(requerimientoUsuarioDTO.getCodigoUsuarioRegistro()!=null && !requerimientoUsuarioDTO.getCodigoUsuarioRegistro().equals(""))
			{
				call.setString(9,requerimientoUsuarioDTO.getCodigoUsuarioRegistro());
			}
			else
			{
				call.setNull(9,OracleTypes.NULL);	
			}
			
			if(requerimientoUsuarioDTO.getIpRegistro()!=null && !requerimientoUsuarioDTO.getIpRegistro().equals(""))
			{
				call.setString(10,requerimientoUsuarioDTO.getIpRegistro());
			}
			else
			{
				call.setNull(10,OracleTypes.NULL);	
			}
			
			if(requerimientoUsuarioDTO.getEstado().getId()!=null && requerimientoUsuarioDTO.getEstado().getId()>=0)
			{
				call.setInt(11,requerimientoUsuarioDTO.getEstado().getId());
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
					requerimientoUsuarioDTO.setId(identificador.intValue());
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
			
		}
		catch (NoResultException nre) 
		{
			requerimientoUsuarioDTO= null;
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
			
		return requerimientoUsuarioDTO;

	}

	public boolean actualizarRequerimientoUsuario(
			RequerimientoUsuarioDTO requerimientoUsuarioDTO) throws Exception 		
	{
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();
			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_REQUERIMIENTO_USUARIO.SP_ACTUALIZAR_REQUE_USUARIO (?,?,?,?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);

			if (requerimientoUsuarioDTO.getId()!= null && requerimientoUsuarioDTO.getId()>=0 ) 
			{
				call.setInt(1, requerimientoUsuarioDTO.getId());
			}else{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(requerimientoUsuarioDTO.getRequerimiento().getId()!=null && requerimientoUsuarioDTO.getRequerimiento().getId()>=0)
			{
				call.setInt(2,requerimientoUsuarioDTO.getRequerimiento().getId());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
						
			if(requerimientoUsuarioDTO.getUsuario().getId()!=null && requerimientoUsuarioDTO.getUsuario().getId()>=0)
			{
				call.setInt(3,requerimientoUsuarioDTO.getUsuario().getId());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);	
			}
			
			
			if(requerimientoUsuarioDTO.getProgramacionAreas().getId()!=null && requerimientoUsuarioDTO.getProgramacionAreas().getId()>=0)
			{
				call.setInt(4,requerimientoUsuarioDTO.getProgramacionAreas().getId());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);	
			}
			
			if(requerimientoUsuarioDTO.getIndicadorEnviadoAlEncargado()!=null && requerimientoUsuarioDTO.getIndicadorEnviadoAlEncargado()>=0)
			{
				call.setShort(5,requerimientoUsuarioDTO.getIndicadorEnviadoAlEncargado());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			if(requerimientoUsuarioDTO.getFechaEnviadoAlEncargado()!=null)
			{
				call.setDate(6, requerimientoUsuarioDTO.getFechaEnviadoAlEncargado());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);	
			}
			
			
			if(requerimientoUsuarioDTO.getIndicadorRequerimientoRecibidoDelEncargado()!=null && requerimientoUsuarioDTO.getIndicadorRequerimientoRecibidoDelEncargado()>=0)
			{
				call.setShort(7,requerimientoUsuarioDTO.getIndicadorRequerimientoRecibidoDelEncargado());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);	
			}
			
			
			if(requerimientoUsuarioDTO.getFechaRequerimientoRecibidoDelEncargado()!=null)
			{
				call.setDate(8,requerimientoUsuarioDTO.getFechaRequerimientoRecibidoDelEncargado());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);	
			}
			
			
			if(requerimientoUsuarioDTO.getIndicadorActivo()!=null && requerimientoUsuarioDTO.getIndicadorActivo()>=0)
			{
				call.setShort(9,requerimientoUsuarioDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(9,OracleTypes.NULL);
			}
			
			if(requerimientoUsuarioDTO.getCodigoUsuarioModificacion()!=null && !requerimientoUsuarioDTO.getCodigoUsuarioModificacion().equals(""))
			{
				call.setString(10,requerimientoUsuarioDTO.getCodigoUsuarioModificacion());
			}
			else			
			{
				call.setNull(10,OracleTypes.NULL);	
			}
			
			if(requerimientoUsuarioDTO.getIpModificacion()!=null && !requerimientoUsuarioDTO.getIpModificacion().equals(""))
			{
				call.setString(11,requerimientoUsuarioDTO.getIpModificacion());
			}
			else
			{
				call.setNull(11,OracleTypes.NULL);	
			}
			
			if(requerimientoUsuarioDTO.getEstado().getId()!=null && requerimientoUsuarioDTO.getEstado().getId()>=0)
			{
				call.setInt(12,requerimientoUsuarioDTO.getEstado().getId());
			}
			else
			{
				call.setNull(12,OracleTypes.NULL);
			}
			call.registerOutParameter(13, OracleTypes.INTEGER);
			call.execute();
			//Integer identificador=(Integer)call.getObject(9);
			
		} 
		catch (NoResultException nre) 
		{
			requerimientoUsuarioDTO = null;
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
